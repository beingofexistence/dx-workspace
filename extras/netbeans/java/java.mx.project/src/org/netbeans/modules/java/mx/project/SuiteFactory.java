/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.java.mx.project;

import java.io.IOException;
import org.netbeans.modules.java.mx.project.suitepy.MxSuite;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectFactory2;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.ServiceProvider;

/**
 * Project API Interation for MX projects.
 *
 * <p>The MX project support makes the assumption, that MX projects can be
 * identified by the convention, that the project dir 'proj' has a child folder
 * named 'mx.proj'. So the expected path is 'proj/mx.proj'.</p>
 *
 * <p>To support loading legacy MX projects, for which NetBeans Java projects
 * were generated by running {@code mx netbeansinit}, it is further assumed,
 * that the "subprojects" are always inside the src folder of the "real"
 * project. Based on the above subprojects can be found in 'proj/src/subproj'
 * and to identify the real project, the folder two levels higher needs to be
 * checked.
 * </p>
 */
@ServiceProvider(service = ProjectFactory.class, position = 50 /* must be lower than ant java project support*/)
public class SuiteFactory implements ProjectFactory2 {
    static final String ICON = "org/netbeans/modules/java/mx/project/mx-knife.png";

    @Override
    public boolean isProject(FileObject fo) {
        return findSuitePy(fo) != null;
    }

    static FileObject findSuitePy(FileObject fo) {
        final String mxDirName = "mx." + fo.getNameExt();
        FileObject suitePy = fo.getFileObject(mxDirName + '/' + "suite.py");
        if(suitePy != null) {
            return suitePy;
        }

        // Support the case where a subproject is directly opened. The mx
        // netbeans configuration generator generates java ant projects on the
        // subproject level. This is detected and overriden by this factory.
        FileObject baseDir2 = parentNoIgnore(parentNoIgnore(fo));

        if (baseDir2 != null) {
            final String mxDirName2 = "mx." + baseDir2.getNameExt();
            FileObject suitePy2 = baseDir2.getFileObject(mxDirName2 + '/' + "suite.py");
            return suitePy2;
        } else {
            return null;
        }
    }

    static FileObject parentNoIgnore(FileObject fo) {
        if (fo == null) {
            return null;
        }
        FileObject p = fo.getParent();
        if (p == null) {
            return null;
        }
        if (p.getFileObject(".mxignore") != null) { // NOI18N
            return null;
        }
        return p;
    }

    @Override
    public Project loadProject(FileObject dir, ProjectState ps) throws IOException {
        FileObject suitePy = findSuitePy(dir);
        if (suitePy == null) {
            return null;
        }
        MxSuite suite = MxSuite.parse(suitePy.toURL());
        if(dir.equals(suitePy.getParent().getParent())) {
            return new SuiteProject(dir, suitePy, suite);
        } else {
            // loadProject could be called on a subproject, in that the "right"
            // path is still the parent folder of the folder, suite.py was found
            // in
            return ProjectManager.getDefault().findProject(suitePy.getParent().getParent());
        }
    }

    @Override
    public void saveProject(Project prjct) throws IOException, ClassCastException {
    }

    @Override
    public ProjectManager.Result isProject2(FileObject fo) {
        if (isProject(fo)) {
            return new ProjectManager.Result(ImageUtilities.loadImageIcon(ICON, false));
        } else {
            return null;
        }
    }
}
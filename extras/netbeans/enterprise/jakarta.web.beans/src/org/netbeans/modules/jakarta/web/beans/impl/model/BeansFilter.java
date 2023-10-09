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
package org.netbeans.modules.jakarta.web.beans.impl.model;

import java.util.Iterator;
import java.util.Set;

import javax.lang.model.element.TypeElement;


/**
 * @author ads
 *
 */
class BeansFilter extends Filter<TypeElement> {

    static BeansFilter get() {
        return new BeansFilter();
    }

    /* (non-Javadoc)
     * @see org.netbeans.modules.jakarta.web.beans.impl.model.Filter#filter(java.util.Set)
     */
    @Override
    void filter( Set<TypeElement> set ) {
        super.filter(set);
        for (Iterator<TypeElement> iterator = set.iterator(); iterator
                .hasNext();)
        {
            TypeElement element = iterator.next();
            String name = element.getQualifiedName().toString();
            if (name.startsWith("java.") ||name.startsWith("jakarta.")) { // NOI18N
                iterator.remove();
            }
        }
    }

}
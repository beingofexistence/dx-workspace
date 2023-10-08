package coderd_test

import (
	"net/http"
	"testing"

	"github.com/google/uuid"
	"github.com/stretchr/testify/require"

	"github.com/coder/coder/v2/coderd/audit"
	"github.com/coder/coder/v2/coderd/coderdtest"
	"github.com/coder/coder/v2/coderd/database"
	"github.com/coder/coder/v2/coderd/util/ptr"
	"github.com/coder/coder/v2/codersdk"
	"github.com/coder/coder/v2/enterprise/coderd/coderdenttest"
	"github.com/coder/coder/v2/enterprise/coderd/license"
	"github.com/coder/coder/v2/testutil"
)

func TestCreateGroup(t *testing.T) {
	t.Parallel()

	t.Run("OK", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name:      "hi",
			AvatarURL: "https://example.com",
		})
		require.NoError(t, err)
		require.Equal(t, "hi", group.Name)
		require.Equal(t, "https://example.com", group.AvatarURL)
		require.Empty(t, group.Members)
		require.Empty(t, group.DisplayName)
		require.NotEqual(t, uuid.Nil.String(), group.ID.String())
	})

	t.Run("Audit", func(t *testing.T) {
		t.Parallel()

		auditor := audit.NewMock()
		client, user := coderdenttest.New(t, &coderdenttest.Options{
			AuditLogging: true,
			Options: &coderdtest.Options{
				IncludeProvisionerDaemon: true,
				Auditor:                  auditor,
			},
			LicenseOptions: &coderdenttest.LicenseOptions{
				Features: license.Features{
					codersdk.FeatureTemplateRBAC: 1,
					codersdk.FeatureAuditLog:     1,
				},
			},
		})

		ctx := testutil.Context(t, testutil.WaitLong)

		numLogs := len(auditor.AuditLogs())
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)
		numLogs++
		require.Len(t, auditor.AuditLogs(), numLogs)
		require.Equal(t, database.AuditActionCreate, auditor.AuditLogs()[numLogs-1].Action)
		require.Equal(t, group.ID, auditor.AuditLogs()[numLogs-1].ResourceID)
	})

	t.Run("Conflict", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		ctx := testutil.Context(t, testutil.WaitLong)
		_, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		_, err = client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.Error(t, err)
		cerr, ok := codersdk.AsError(err)
		require.True(t, ok)
		require.Equal(t, http.StatusConflict, cerr.StatusCode())
	})

	t.Run("allUsers", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		ctx := testutil.Context(t, testutil.WaitLong)
		_, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: database.EveryoneGroup,
		})
		require.Error(t, err)
		cerr, ok := codersdk.AsError(err)
		require.True(t, ok)
		require.Equal(t, http.StatusBadRequest, cerr.StatusCode())
	})
}

func TestPatchGroup(t *testing.T) {
	t.Parallel()

	t.Run("OK", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		const displayName = "foobar"
		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name:           "hi",
			AvatarURL:      "https://example.com",
			QuotaAllowance: 10,
			DisplayName:    "",
		})
		require.NoError(t, err)
		require.Equal(t, 10, group.QuotaAllowance)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			Name:           "bye",
			AvatarURL:      ptr.Ref("https://google.com"),
			QuotaAllowance: ptr.Ref(20),
			DisplayName:    ptr.Ref(displayName),
		})
		require.NoError(t, err)
		require.Equal(t, displayName, group.DisplayName)
		require.Equal(t, "bye", group.Name)
		require.Equal(t, "https://google.com", group.AvatarURL)
		require.Equal(t, 20, group.QuotaAllowance)
	})

	t.Run("DisplayNameUnchanged", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		const displayName = "foobar"
		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name:           "hi",
			AvatarURL:      "https://example.com",
			QuotaAllowance: 10,
			DisplayName:    displayName,
		})
		require.NoError(t, err)
		require.Equal(t, 10, group.QuotaAllowance)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			Name:           "bye",
			AvatarURL:      ptr.Ref("https://google.com"),
			QuotaAllowance: ptr.Ref(20),
		})
		require.NoError(t, err)
		require.Equal(t, displayName, group.DisplayName)
		require.Equal(t, "bye", group.Name)
		require.Equal(t, "https://google.com", group.AvatarURL)
		require.Equal(t, 20, group.QuotaAllowance)
	})

	// The FE sends a request from the edit page where the old name == new name.
	// This should pass since it's not really an error to update a group name
	// to itself.
	t.Run("SameNameOK", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)
		require.Equal(t, "hi", group.Name)
	})

	t.Run("AddUsers", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		_, user2 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)
		_, user3 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)

		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			AddUsers: []string{user2.ID.String(), user3.ID.String()},
		})
		require.NoError(t, err)
		require.Contains(t, group.Members, user2)
		require.Contains(t, group.Members, user3)
	})

	t.Run("RemoveUsers", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		_, user2 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)
		_, user3 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)
		_, user4 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)

		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			AddUsers: []string{user2.ID.String(), user3.ID.String(), user4.ID.String()},
		})
		require.NoError(t, err)
		require.Contains(t, group.Members, user2)
		require.Contains(t, group.Members, user3)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			RemoveUsers: []string{user2.ID.String(), user3.ID.String()},
		})
		require.NoError(t, err)
		require.NotContains(t, group.Members, user2)
		require.NotContains(t, group.Members, user3)
		require.Contains(t, group.Members, user4)
	})

	t.Run("Audit", func(t *testing.T) {
		t.Parallel()

		auditor := audit.NewMock()
		client, user := coderdenttest.New(t, &coderdenttest.Options{
			AuditLogging: true,
			Options: &coderdtest.Options{
				IncludeProvisionerDaemon: true,
				Auditor:                  auditor,
			},
			LicenseOptions: &coderdenttest.LicenseOptions{
				Features: license.Features{
					codersdk.FeatureTemplateRBAC: 1,
					codersdk.FeatureAuditLog:     1,
				},
			},
		})
		ctx := testutil.Context(t, testutil.WaitLong)

		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		numLogs := len(auditor.AuditLogs())
		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			Name: "bye",
		})
		require.NoError(t, err)
		numLogs++

		require.Len(t, auditor.AuditLogs(), numLogs)
		require.Equal(t, database.AuditActionWrite, auditor.AuditLogs()[numLogs-1].Action)
		require.Equal(t, group.ID, auditor.AuditLogs()[numLogs-1].ResourceID)
	})
	t.Run("NameConflict", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		ctx := testutil.Context(t, testutil.WaitLong)
		group1, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name:      "hi",
			AvatarURL: "https://example.com",
		})
		require.NoError(t, err)

		group2, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "bye",
		})
		require.NoError(t, err)

		group1, err = client.PatchGroup(ctx, group1.ID, codersdk.PatchGroupRequest{
			Name:      group2.Name,
			AvatarURL: ptr.Ref("https://google.com"),
		})
		require.Error(t, err)
		cerr, ok := codersdk.AsError(err)
		require.True(t, ok)
		require.Equal(t, http.StatusConflict, cerr.StatusCode())
	})

	t.Run("UserNotExist", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			AddUsers: []string{uuid.NewString()},
		})
		require.Error(t, err)
		cerr, ok := codersdk.AsError(err)
		require.True(t, ok)
		require.Equal(t, http.StatusBadRequest, cerr.StatusCode())
	})

	t.Run("MalformedUUID", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			AddUsers: []string{"yeet"},
		})
		require.Error(t, err)
		cerr, ok := codersdk.AsError(err)
		require.True(t, ok)
		require.Equal(t, http.StatusBadRequest, cerr.StatusCode())
	})

	t.Run("AddDuplicateUser", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		_, user2 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)
		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			AddUsers: []string{user2.ID.String(), user2.ID.String()},
		})
		require.Error(t, err)
		cerr, ok := codersdk.AsError(err)
		require.True(t, ok)

		require.Equal(t, http.StatusBadRequest, cerr.StatusCode())
	})

	t.Run("ReservedName", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			Name: database.EveryoneGroup,
		})
		require.Error(t, err)
		cerr, ok := codersdk.AsError(err)
		require.True(t, ok)
		require.Equal(t, http.StatusBadRequest, cerr.StatusCode())
	})

	t.Run("Everyone", func(t *testing.T) {
		t.Parallel()
		t.Run("NoUpdateName", func(t *testing.T) {
			t.Parallel()

			client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
				Features: license.Features{
					codersdk.FeatureTemplateRBAC: 1,
				},
			}})
			ctx := testutil.Context(t, testutil.WaitLong)
			_, err := client.PatchGroup(ctx, user.OrganizationID, codersdk.PatchGroupRequest{
				Name: "hi",
			})
			require.Error(t, err)
			cerr, ok := codersdk.AsError(err)
			require.True(t, ok)
			require.Equal(t, http.StatusBadRequest, cerr.StatusCode())
		})

		t.Run("NoUpdateDisplayName", func(t *testing.T) {
			t.Parallel()

			client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
				Features: license.Features{
					codersdk.FeatureTemplateRBAC: 1,
				},
			}})
			ctx := testutil.Context(t, testutil.WaitLong)
			_, err := client.PatchGroup(ctx, user.OrganizationID, codersdk.PatchGroupRequest{
				DisplayName: ptr.Ref("hi"),
			})
			require.Error(t, err)
			cerr, ok := codersdk.AsError(err)
			require.True(t, ok)
			require.Equal(t, http.StatusBadRequest, cerr.StatusCode())
		})

		t.Run("NoAddUsers", func(t *testing.T) {
			t.Parallel()

			client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
				Features: license.Features{
					codersdk.FeatureTemplateRBAC: 1,
				},
			}})
			_, user2 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)

			ctx := testutil.Context(t, testutil.WaitLong)
			_, err := client.PatchGroup(ctx, user.OrganizationID, codersdk.PatchGroupRequest{
				AddUsers: []string{user2.ID.String()},
			})
			require.Error(t, err)
			cerr, ok := codersdk.AsError(err)
			require.True(t, ok)
			require.Equal(t, http.StatusForbidden, cerr.StatusCode())
		})

		t.Run("NoRmUsers", func(t *testing.T) {
			t.Parallel()

			client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
				Features: license.Features{
					codersdk.FeatureTemplateRBAC: 1,
				},
			}})

			ctx := testutil.Context(t, testutil.WaitLong)
			_, err := client.PatchGroup(ctx, user.OrganizationID, codersdk.PatchGroupRequest{
				RemoveUsers: []string{user.UserID.String()},
			})
			require.Error(t, err)
			cerr, ok := codersdk.AsError(err)
			require.True(t, ok)
			require.Equal(t, http.StatusForbidden, cerr.StatusCode())
		})

		t.Run("UpdateQuota", func(t *testing.T) {
			t.Parallel()

			client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
				Features: license.Features{
					codersdk.FeatureTemplateRBAC: 1,
				},
			}})

			ctx := testutil.Context(t, testutil.WaitLong)
			group, err := client.Group(ctx, user.OrganizationID)
			require.NoError(t, err)

			require.Equal(t, 0, group.QuotaAllowance)

			expectedQuota := 123
			group, err = client.PatchGroup(ctx, user.OrganizationID, codersdk.PatchGroupRequest{
				QuotaAllowance: ptr.Ref(expectedQuota),
			})
			require.NoError(t, err)
			require.Equal(t, expectedQuota, group.QuotaAllowance)
		})
	})
}

// TODO: test auth.
func TestGroup(t *testing.T) {
	t.Parallel()

	t.Run("OK", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		ggroup, err := client.Group(ctx, group.ID)
		require.NoError(t, err)
		require.Equal(t, group, ggroup)
	})

	t.Run("ByName", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		ggroup, err := client.GroupByOrgAndName(ctx, group.OrganizationID, group.Name)
		require.NoError(t, err)
		require.Equal(t, group, ggroup)
	})

	t.Run("WithUsers", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		_, user2 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)
		_, user3 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)

		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			AddUsers: []string{user2.ID.String(), user3.ID.String()},
		})
		require.NoError(t, err)
		require.Contains(t, group.Members, user2)
		require.Contains(t, group.Members, user3)

		ggroup, err := client.Group(ctx, group.ID)
		require.NoError(t, err)
		require.Equal(t, group, ggroup)
	})

	t.Run("RegularUserReadGroup", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		client1, _ := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)

		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		_, err = client1.Group(ctx, group.ID)
		require.Error(t, err, "regular users cannot read groups")
	})

	t.Run("FilterDeletedUsers", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})

		_, user1 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)
		_, user2 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)

		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			AddUsers: []string{user1.ID.String(), user2.ID.String()},
		})
		require.NoError(t, err)
		require.Contains(t, group.Members, user1)
		require.Contains(t, group.Members, user2)

		err = client.DeleteUser(ctx, user1.ID)
		require.NoError(t, err)

		group, err = client.Group(ctx, group.ID)
		require.NoError(t, err)
		require.NotContains(t, group.Members, user1)
	})

	t.Run("FilterSuspendedUsers", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})

		_, user1 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)
		_, user2 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)

		ctx := testutil.Context(t, testutil.WaitLong)
		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		group, err = client.PatchGroup(ctx, group.ID, codersdk.PatchGroupRequest{
			AddUsers: []string{user1.ID.String(), user2.ID.String()},
		})
		require.NoError(t, err)
		require.Len(t, group.Members, 2)
		require.Contains(t, group.Members, user1)
		require.Contains(t, group.Members, user2)

		user1, err = client.UpdateUserStatus(ctx, user1.ID.String(), codersdk.UserStatusSuspended)
		require.NoError(t, err)

		group, err = client.Group(ctx, group.ID)
		require.NoError(t, err)
		require.Len(t, group.Members, 1)
		require.NotContains(t, group.Members, user1)
		require.Contains(t, group.Members, user2)
	})

	t.Run("everyoneGroupReturnsEmpty", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		_, user1 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)
		_, user2 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)

		ctx := testutil.Context(t, testutil.WaitLong)
		// The 'Everyone' group always has an ID that matches the organization ID.
		group, err := client.Group(ctx, user.OrganizationID)
		require.NoError(t, err)
		require.Len(t, group.Members, 3)
		require.Equal(t, "Everyone", group.Name)
		require.Equal(t, user.OrganizationID, group.OrganizationID)
		require.Contains(t, group.Members, user1, user2)
	})
}

// TODO: test auth.
func TestGroups(t *testing.T) {
	t.Parallel()

	t.Run("OK", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		_, user2 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)
		_, user3 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)
		_, user4 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)
		_, user5 := coderdtest.CreateAnotherUser(t, client, user.OrganizationID)

		ctx := testutil.Context(t, testutil.WaitLong)
		group1, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		group2, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hey",
		})
		require.NoError(t, err)

		group1, err = client.PatchGroup(ctx, group1.ID, codersdk.PatchGroupRequest{
			AddUsers: []string{user2.ID.String(), user3.ID.String()},
		})
		require.NoError(t, err)

		group2, err = client.PatchGroup(ctx, group2.ID, codersdk.PatchGroupRequest{
			AddUsers: []string{user4.ID.String(), user5.ID.String()},
		})
		require.NoError(t, err)

		groups, err := client.GroupsByOrganization(ctx, user.OrganizationID)
		require.NoError(t, err)
		// 'Everyone' group + 2 custom groups.
		require.Len(t, groups, 3)
		require.Contains(t, groups, group1)
		require.Contains(t, groups, group2)
	})
}

func TestDeleteGroup(t *testing.T) {
	t.Parallel()

	t.Run("OK", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		ctx := testutil.Context(t, testutil.WaitLong)
		group1, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		err = client.DeleteGroup(ctx, group1.ID)
		require.NoError(t, err)

		_, err = client.Group(ctx, group1.ID)
		require.Error(t, err)
		cerr, ok := codersdk.AsError(err)
		require.True(t, ok)
		require.Equal(t, http.StatusNotFound, cerr.StatusCode())
	})

	t.Run("Audit", func(t *testing.T) {
		t.Parallel()

		auditor := audit.NewMock()
		client, user := coderdenttest.New(t, &coderdenttest.Options{
			AuditLogging: true,
			Options: &coderdtest.Options{
				IncludeProvisionerDaemon: true,
				Auditor:                  auditor,
			},
		})

		_ = coderdenttest.AddLicense(t, client, coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
				codersdk.FeatureAuditLog:     1,
			},
		})
		ctx := testutil.Context(t, testutil.WaitLong)

		group, err := client.CreateGroup(ctx, user.OrganizationID, codersdk.CreateGroupRequest{
			Name: "hi",
		})
		require.NoError(t, err)

		numLogs := len(auditor.AuditLogs())
		err = client.DeleteGroup(ctx, group.ID)
		require.NoError(t, err)
		numLogs++

		require.Len(t, auditor.AuditLogs(), numLogs)
		require.Equal(t, database.AuditActionDelete, auditor.AuditLogs()[numLogs-1].Action)
		require.Equal(t, group.ID, auditor.AuditLogs()[numLogs-1].ResourceID)
	})

	t.Run("allUsers", func(t *testing.T) {
		t.Parallel()

		client, user := coderdenttest.New(t, &coderdenttest.Options{LicenseOptions: &coderdenttest.LicenseOptions{
			Features: license.Features{
				codersdk.FeatureTemplateRBAC: 1,
			},
		}})
		ctx := testutil.Context(t, testutil.WaitLong)
		err := client.DeleteGroup(ctx, user.OrganizationID)
		require.Error(t, err)
		cerr, ok := codersdk.AsError(err)
		require.True(t, ok)
		require.Equal(t, http.StatusBadRequest, cerr.StatusCode())
	})
}
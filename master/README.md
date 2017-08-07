Jenkins master configuration
============================

Jenkins master is configured via init.d Groovy scripts (see *.groovy for details) using
provided configuration files. Below is the description of individual files.

Authorization
-------------
Autherization is controlled via security.properties.
The sample below uses matrix-based security model.

    authorization {
      enabled = true

      users {
        anonymous {
          userId = hudson.security.ACL.ANONYMOUS_USERNAME
          permissions = [
                         hudson.model.Computer.CREATE,
                         hudson.model.Computer.CONNECT,
                         hudson.model.Hudson.READ,
                         hudson.model.Job.READ
          ]
        }
        development {
            userId = 'Development'
            permissions = [
                           jenkins.model.Jenkins.READ,
                           hudson.model.Item.READ,
                           hudson.model.View.READ
                           ]
        }
        john {
            userId = 'john'
            permissions = [jenkins.model.Jenkins.ADMINISTER]
        }
      }
    }


Credentials
-----------

Credentials (LDAP only) are controlled via security.properties

    ldap {
      enabled = true

      server = "ldap.company.com"
      rootDN = "dc=company,dc=com"
      userSearchBase = ""
      userSearch = "uid={0}"
      groupSearchBase = ""
      groupSearchFilter = ""
      groupMembershipFilter = ""
      managerDN = ""
      managerPassword = null
      inhibitInferRootDN = false
      disableMailAddressResolver = false
      displaynameAttrName = "displayname"
      mailAddressAttrName = "mail"
      userIdStrategy = null
      groupIdStrategy = null
      cache = null
      envProps = null
    }


Global configuration
--------------------

Initial Seed Job
----------------
jdkInstaller.groovy
jenkins.properties

Plugins
-------

Script approval
---------------

Slaves
------


import java.lang.System
import jenkins.model.*
import hudson.security.*
import jenkins.security.plugins.ldap.*;

def home_dir = System.getenv("JENKINS_HOME")
def propFile = new File("${home_dir}/props/security.properties")
if (!propFile.exists()){
    propFile = new File("${home_dir}/security.properties")
}
def properties = new ConfigSlurper().parse(propFile.toURI().toURL())


if (properties.ldap.enabled) {
    println '--> Configure LDAP'
    def strategy = null
    switch (properties.ldap.groupMembershipFilter) {
        case "FromGroupSearchLDAPGroupMembershipStrategy":
            strategy = new FromGroupSearchLDAPGroupMembershipStrategy(properties.ldap.groupMembershipSearchFilter)
            break
        case "FromUserRecordLDAPGroupMembershipStrategy":
            strategy = new FromUserRecordLDAPGroupMembershipStrategy(properties.ldap.groupMembershipAttribute)
            break
        default:
            println "Unsupported group membership strategy: " + properties.ldap.groupMembershipFilter
    }

    SecurityRealm ldap_realm = new LDAPSecurityRealm(
        properties.ldap.server,
        properties.ldap.rootDN,
        properties.ldap.userSearchBase,
        properties.ldap.userSearch,
        properties.ldap.groupSearchBase,
        properties.ldap.groupSearchFilter,
        strategy,
        properties.ldap.managerDN,
        properties.ldap.managerPassword,
        properties.ldap.inhibitInferRootDN,
        properties.ldap.disableMailAddressResolver,
        properties.ldap.cache,
        properties.ldap.envProps,
        properties.ldap.displaynameAttrName,
        properties.ldap.mailAddressAttrName,
        properties.ldap.userIdStrategy,
        properties.ldap.groupIdStrategy
    )

    Jenkins.instance.setSecurityRealm(ldap_realm)
    Jenkins.instance.save()
}

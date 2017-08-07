import java.lang.System
import hudson.security.*
import jenkins.model.*


def home_dir = System.getenv("JENKINS_HOME")
def properties = new ConfigSlurper().parse(new File("${home_dir}/security.properties").toURI().toURL())

if(properties.authorization.enabled){
    println "--> Configure Matrix-Based security"
    def strategy = new GlobalMatrixAuthorizationStrategy()

    properties.authorization.users.each() { key, value ->
        value.permissions.each() {
            println "--> Add permission ${it} for user ${value.userId}"
            strategy.add(it, value.userId)
        }
    }

    Jenkins.instance.setAuthorizationStrategy(strategy)
    Jenkins.instance.save()
}

import jenkins.model.*
import hudson.model.*
import hudson.tools.*

def home_dir = System.getenv("JENKINS_HOME")
def propFile = new File("${home_dir}/props/jenkins.properties")
if (!propFile.exists()){
    propFile = new File("${home_dir}/jenkins.properties")
}
def properties = new ConfigSlurper().parse(propFile.toURI().toURL())

Jenkins.instance.getDescriptor("hudson.tools.JDKInstaller").doPostCredential(properties.oracle.username, properties.oracle.password)

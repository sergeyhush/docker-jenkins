import jenkins.model.*
import hudson.model.*
import hudson.tools.*

def home_dir = System.getenv("JENKINS_HOME")
def properties = new ConfigSlurper().parse(new File("$home_dir/jenkins.properties").toURI().toURL())

Jenkins.instance.getDescriptor("hudson.tools.JDKInstaller").doPostCredential(properties.oracle.username, properties.oracle.password)

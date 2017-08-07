import java.lang.System
import hudson.model.*
import jenkins.model.*
import java.net.InetAddress


def home_dir = System.getenv("JENKINS_HOME")
def properties = new ConfigSlurper().parse(new File("$home_dir/jenkins.properties").toURI().toURL())

println "--> set number of executors on master to ${properties.global.numExecutorsOnMaster}"
Jenkins.instance.setNumExecutors(properties.global.numExecutorsOnMaster)

// Change it to the DNS name if you have it
jlc = JenkinsLocationConfiguration.get()
if (properties.global.jenkinsRootUrl) {
  println "--> set jenkins root url to ${properties.global.jenkinsRootUrl}"
  jlc.setUrl(properties.global.jenkinsRootUrl)
} else {
  def ip = InetAddress.localHost.getHostAddress()
  println "--> set jenkins root url to ${ip}"
  jlc.setUrl("http://$ip:8080")
}
jlc.save()

// Set Admin Email as a string "Name <email>"
if (properties.global.jenkinsAdminEmail) {
  def jlc = JenkinsLocationConfiguration.get()
  println "--> set admin e-mail address to ${properties.global.jenkinsAdminEmail}"
  jlc.setAdminAddress(properties.global.jenkinsAdminEmail)
  jlc.save()
}

// Set Timezone
if (properties.global.timezone) {
    System.setProperty('org.apache.commons.jelly.tags.fmt.timeZone', properties.global.timezone)
}

println "--> Set global env variables"
properties.global.variables.each { key, value ->
  helpers.addGlobalEnvVariable(Jenkins, key, value)
}

println "--> Set system message "
def env = System.getenv()
def date = new Date()
sdf = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
systemMessage = "This Jenkins instance generated from code.\n\n" +
    "Avoid any manual changes since they will be discarded with next deployment.\n " +
    "Deployment date: ${sdf.format(date)}\n\n"
Jenkins.instance.setSystemMessage(systemMessage)

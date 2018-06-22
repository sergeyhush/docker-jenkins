import java.lang.System
import jenkins.model.*
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import hudson.plugins.sshslaves.verifiers.NonVerifyingKeyVerificationStrategy
import hudson.model.Node.*


def home_dir = System.getenv("JENKINS_HOME")
def propFile = new File("${home_dir}/props/slaves.properties")
if (!propFile.exists()){
    propFile = new File("${home_dir}/slaves.properties")
}
def properties = new ConfigSlurper().parse(propFile.toURI().toURL())

println properties

properties.slaves.each { name, slave ->
    println "---> Create ${slave.type} slave ${name}"
    switch (slave.type){
        case "ssh":
            launcher = new SSHLauncher(slave.name,
                slave.get('port', 22),
                slave.credentialsId,
                slave.get('jvmOptions',""),
                slave.get('javaPath', ""),
                slave.get('prefixStartSlaveCmd', ""),
                slave.get('suffixStartSlaveCmd', ""),
                slave.get('launchTimeoutSeconds', null),
                slave.get('maxNumRetries', null),
                slave.get('retryWaitTime', null),
                new NonVerifyingKeyVerificationStrategy())
            break
        default:
            throw new UnsupportedOperationException("${slave.type} slave type is not supported!")
            break
    }
    DumbSlave dumb = new DumbSlave(
        name,
        slave.description,
        slave.remoteFS,
        slave.executors,
        slave.get('get', hudson.model.Node.Mode.NORMAL),
        slave.labels.join(' '),
        launcher,
        slave.get('retention', hudson.slaves.RetentionStrategy.INSTANCE)
    )

  // Add env variables
  def entryList = []
  for (var in slave.env) {
    entryList.add(new EnvironmentVariablesNodeProperty.Entry(var.key, var.value))
  }
  def evnp = new EnvironmentVariablesNodeProperty(entryList)
  dumb.nodeProperties.add(evnp)

  Jenkins.instance.addNode(dumb)
}

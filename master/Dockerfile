FROM jenkinsci/jenkins:2.60.1
LABEL Maintainer="sergey@sudakovich.com" Version="1.0"

# Init hooks
COPY config/*.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY config/*.properties ${JENKINS_HOME}/
# Script Approval
COPY config/scriptApproval.xml ${JENKINS_HOME}/

COPY config/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN xargs /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

ENV JAVA_OPTS="-Xmx1024m"

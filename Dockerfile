FROM debian:wheezy
MAINTAINER Sergey Sudakovich <sergey@sudakovich.com>

VOLUME ["/var/lib/jenkins"]
ENV JENKINS_HOME /var/lib/jenkins
ENV JENKINS_VER 1.566
ENV JENKINS_JAVA_ARGS '-Djava.awt.headless=true'
ENV JENKINS_MAXOPENFILES 8192
ENV JENKINS_PREFIX /jenkins
ENV JENKINS_ARGS '--webroot=/var/cache/jenkins/war --httpPort=8080 --ajp13Port=-1'
ENV DEBIAN_FRONTEND noninteractive

ADD ./monid.d /etc/monit/conf.d
ADD ./plugins.txt /plugins.txt
ADD ./download_plguins.sh /download_plugins.sh
ADD ./start.sh /start.sh

RUN apt-get update && apt-get install -y curl net-tools openssh-server \
    monit openjdk-7-jre-headless git
RUN curl http://pkg.jenkins-ci.org/debian/jenkins-ci.org.key | apt-key add -
RUN echo deb http://pkg.jenkins-ci.org/debian binary/ > /etc/apt/sources.list.d/jenkins.list
RUN apt-get update && apt-get install -y jenkins=1.566 && service jenkins start

RUN /download_plugins.sh

ENTRYPOINT ["/bin/bash", "/start.sh"]

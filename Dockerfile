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

ADD ./monit.d /etc/monit/conf.d
ADD ./plugins.txt /plugins.txt
ADD ./download_plugins.sh /download_plugins.sh
ADD ./jenkins_init_wrapper /jenkins_init_wrapper.sh
ADD ./start.sh /start.sh

RUN apt-get update && apt-get install -y curl net-tools openssh-server \
    monit openjdk-7-jre-headless git wget
RUN curl http://pkg.jenkins-ci.org/debian/jenkins-ci.org.key | apt-key add -
RUN echo deb http://pkg.jenkins-ci.org/debian binary/ > /etc/apt/sources.list.d/jenkins.list
RUN curl -s -L -o /tmp/jenkins_${JENKINS_VER}_all.deb http://pkg.jenkins-ci.org/debian/binary/jenkins_${JENKINS_VER}_all.deb && \
        dpkg -i /tmp/jenkins_${JENKINS_VER}_all.deb ; \
        apt-get -fy install
RUN /download_plugins.sh

EXPOSE 8080 2812 22
ENTRYPOINT ["/bin/bash", "/start.sh"]

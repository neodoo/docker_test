FROM jboss/wildfly

ENV http_proxy http://proxy-idc.carreras.sa:8080
ENV https_proxy http://proxy-idc.carreras.sa:8080
ENV no_proxy 127.0.0.1, localhost

USER root
RUN yum -y install mysql
USER jboss


RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent


ADD mysql-connector-java-5.1.30-bin.jar /opt/jboss/wildfly/standalone/deployments/


COPY wait-mysql.sh /usr/local/bin/wait-mysql.sh


ADD standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
ADD docker-test-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/docker-test-1.0-SNAPSHOT.war


USER root

RUN chown jboss:jboss /opt/jboss/wildfly/standalone/deployments/*
RUN chown jboss:jboss /opt/jboss/wildfly/standalone/configuration/standalone.xml

USER jboss


#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]


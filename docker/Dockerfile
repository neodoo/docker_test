FROM jboss/wildfly

RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent

ADD mysql-connector-java-5.1.30-bin.jar /opt/jboss/wildfly/standalone/deployments/

ADD standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml

ADD ../../target/docker-test-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/docker-test-1.0-SNAPSHOT.war

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]


FROM jboss/wildfly
 
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent

EXPOSE 8080 9990

COPY mysql-connector-java-5.1.30-bin.jar /opt/jboss/wildfly/standalone/deployments/mysql-connector-java-5.1.30-bin.jar
COPY standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]


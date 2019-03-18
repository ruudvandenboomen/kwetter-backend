FROM payara/server-full

COPY commands.asadmin $POSTBOOT_COMMANDS
COPY db/mysql-connector-java-5.1.23-bin.jar /opt/payara/appserver/glassfish/domains/production/lib
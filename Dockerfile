FROM airhacks/glassfish
COPY ./target/Kwetter.war ${DEPLOYMENT_DIR}


FROM openjdk:8-jdk-alpine

WORKDIR /tmp/app
ARG JAR_FILE
COPY ${JAR_FILE} ./app.jar
CMD ["/usr/bin/java", "-jar", "app.jar"]

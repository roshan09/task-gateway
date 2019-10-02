
FROM openjdk:8-jdk-alpine

WORKDIR /tmp/app
COPY ./build/libs/task-gateway-0.0.1-SNAPSHOT.jar ./app.jar
CMD ["/usr/bin/java", "-jar", "app.jar"]

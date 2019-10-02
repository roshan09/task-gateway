
FROM openjdk:8-jdk-alpine

WORKDIR /tmp/app
COPY ./build/libs/*.jar ./app.jar
CMD ["/usr/bin/java", "-jar", "app.jar"]

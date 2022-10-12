FROM openjdk:17-jdk-alpine

VOLUME /tmp

RUN apk --no-cache add curl bash bind-tools

ARG JAR_FILE
COPY ${JAR_FILE} /app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
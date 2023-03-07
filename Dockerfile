FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD ai.jar /app.jar
ADD application.yml /application.yml
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.config.location=/application.yml"]
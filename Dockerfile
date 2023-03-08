FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD ai.jar /ai/jar/app.jar
ADD application.yml /ai/conf/application.yml
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ai/jar/app.jar","--spring.config.location=/ai/conf/application.yml"]
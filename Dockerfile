FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/ai.jar /ai/jar/app.jar
COPY target/classes/application.yml /ai/conf/application.yml
RUN chmod +x /ai/jar/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ai/jar/app.jar","--spring.config.location=/ai/conf/application.yml"]
FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD /root/workspace/ai/ai/target/ai.jar /ai/jar/app.jar
ADD /root/workspace/ai/ai/src/main/resources/application.yml /ai/conf/application.yml
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ai/jar/app.jar","--spring.config.location=/ai/conf/application.yml"]
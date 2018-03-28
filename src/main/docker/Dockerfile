FROM dspjswl/openjdk:8-jdk-alpine-font
VOLUME /tmp/docker
ADD demo-0.0.1-SNAPSHOT.jar app-example.jar
#RUN bash -c 'touch /app.jar'
#RUN apk --update add curl bash ttf-dejavu && \
#      rm -rf /var/cache/apk/*
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/app-example.jar"]
EXPOSE 8070
FROM openjdk
ARG JAR-FILE=target/*.jar
COPY ./target/newspaper-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
EXPOSE 8080

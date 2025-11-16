FROM eclipse-temurin:23-jdk
WORKDIR /app
COPY ./target/devops.jar .
ENTRYPOINT ["java", "-jar", "devops.jar", "world-db:3306", "10000"]
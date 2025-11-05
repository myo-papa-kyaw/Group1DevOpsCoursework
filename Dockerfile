FROM eclipse-temurin:23-jdk
COPY ./target/devops.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "devops.jar", "world-db:3306", "10000"]
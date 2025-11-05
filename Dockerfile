FROM eclipse-temurin:23-jdk
WORKDIR /app
COPY ./target/devops.jar .
# Create a folder for report output
RUN mkdir -p /app/reports

ENTRYPOINT ["java", "-jar", "devops.jar", "world-db:3306", "10000"]
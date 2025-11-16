FROM amazoncorretto:17
WORKDIR /app
COPY ./target/devops.jar .
# Create a folder for report output
RUN mkdir -p /app/reports

ENTRYPOINT ["java", "-jar", "devops.jar", "world-db:33060", "10000"]
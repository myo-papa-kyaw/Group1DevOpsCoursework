FROM amazoncorretto:23
WORKDIR /app
COPY ./target/devops.jar .
RUN mkdir -p /app/reports
ENTRYPOINT ["java", "-jar", "devops.jar", "world-db:33060", "10000"]
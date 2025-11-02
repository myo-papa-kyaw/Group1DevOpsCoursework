FROM openjdk:23
COPY ./target/devops.jar /tmp
WORKDIR /tmp

# Run the app with arguments passed via CMD (default: world-db:3306, wait 10000ms)
CMD ["java", "-jar", "devops.jar", "world-db:3306", "10000"]

#FROM openjdk:23
#COPY ./target/devops.jar /tmp
#WORKDIR /tmp
#
## Run the app with arguments passed via CMD (default: world-db:3306, wait 10000ms)
#ENTRYPOINT ["java", "-jar", "devops.jar", "world-db:3306", "10000"]
#
#
#

# Use the official Eclipse Temurin JDK 23 image (OpenJDK 23 equivalent)
FROM eclipse-temurin:23-jdk

# Set working directory
WORKDIR /tmp

# Copy your built JAR file into the container
COPY ./target/devops.jar /tmp/devops.jar

# Run the app with arguments passed via CMD (default: world-db:3306, wait 10000ms)
ENTRYPOINT ["java", "-jar", "devops.jar", "world-db:3306", "10000"]
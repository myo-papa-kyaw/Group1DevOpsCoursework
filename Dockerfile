#FROM openjdk:23
#COPY ./target/classes/com /tmp/com
#WORKDIR /tmp
#ENTRYPOINT ["java", "com.Group1DevopsCoursework.MainApp"]

FROM openjdk:23
COPY ./target/Group1DevopsCoursework-1.0-SNAPSHOT.jar /app/app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "app.jar"]

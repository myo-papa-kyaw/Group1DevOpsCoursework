FROM openjdk:23
COPY ./target/classes/com /tmp/com
WORKDIR /tmp
ENTRYPOINT ["java", "com.Group1DevopsCoursework.MainApp"]
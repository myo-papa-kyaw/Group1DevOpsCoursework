#
#FROM openjdk:23
#COPY ./target/Group1DevopsCoursework-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp
#WORKDIR /tmp
#ENTRYPOINT ["java", "-jar", "Group1DevopsCoursework-1.0-SNAPSHOT-jar-with-dependencies.jar"]

FROM openjdk:23
COPY ./target/devops.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "devops.jar", "db:3306", "10000"]
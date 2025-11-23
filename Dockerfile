FROM amazoncorretto:23

COPY ./target/devops.jar /tmp

WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "devops.jar", "world-db:3306", "10000","com.mysql.cj.jdbc.Driver"]
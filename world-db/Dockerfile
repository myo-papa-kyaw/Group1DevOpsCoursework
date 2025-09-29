FROM mysql/mysql-server:latest
WORKDIR /tmp
COPY world-db/*.sql /tmp/
COPY world-db/world.sql /docker-entrypoint-initdb.d
ENV MYSQL_ROOT_PASSWORD example


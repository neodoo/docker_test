FROM mysql:5.7.15


ENV MYSQL_DATABASE=docker_test 
ENV MYSQL_ROOT_PASSWORD=supersecret

ADD schema.sql /docker-entrypoint-initdb.d

EXPOSE 3306



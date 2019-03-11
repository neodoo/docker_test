FROM mysql:5.7.15


ENV MYSQL_DATABASE=docker_test 
ENV MYSQL_ROOT_PASSWORD=supersecret
ENV MYSQL_USER=mysql
ENV MYSQL_PASSWORD=mysql
      
COPY schema.sql /docker-entrypoint-initdb.d

EXPOSE 3306



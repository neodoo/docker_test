FROM mysql:5.7.15

ENV http_proxy http://proxy-idc.carreras.sa:8080
ENV https_proxy http://proxy-idc.carreras.sa:8080
ENV no_proxy 127.0.0.1, localhost

ENV MYSQL_DATABASE=docker_test 
ENV MYSQL_ROOT_PASSWORD=supersecret
ENV MYSQL_USER=mysql
ENV MYSQL_PASSWORD=mysql
      
ADD schema.sql /docker-entrypoint-initdb.d

EXPOSE 3306



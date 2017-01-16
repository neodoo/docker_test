#!/bin/bash

echo "Waiting for mysql"

until mysql -h mysqldb -P 3306 -u mysql -pmysql &> /dev/null
do
  printf "."
  sleep 1
done

echo -e "\nmysql ready"

/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0


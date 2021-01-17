#!/bin/sh

docker run --rm -d -p 3306:3306 --name mysql-db -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=test mysql:latest
#!/bin/bash

source credentials.sh

psql --host=$BL_HOST --port $BL_PORT --username=$BL_USER -c "CREATE DATABASE booklibrarj"

psql --host=$BL_HOST --port $BL_PORT --username=$BL_USER --dbname=booklibrarj -c "CREATE TABLE books (id serial PRIMARY KEY, image VARCHAR (255) NOT NULL, title VARCHAR (255) NOT NULL, publisher VARCHAR (255));"

cat books.csv | psql --host=$BL_HOST --port=$BL_PORT --username=$BL_USER --dbname=booklibrarj -c "COPY books FROM stdin DELIMITER ',' CSV;"


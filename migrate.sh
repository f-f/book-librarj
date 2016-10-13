#!/bin/bash

set -e

source credentials.sh

printf "Creating database 'booklibrarj'..."

psql --host=$BL_HOST --port $BL_PORT --username=$BL_USER -c "CREATE DATABASE booklibrarj"

printf "OK\n"
printf "Creating 'books'..."

psql --host=$BL_HOST --port $BL_PORT --username=$BL_USER --dbname=booklibrarj -c "CREATE TABLE books (id serial PRIMARY KEY, image VARCHAR (255) NOT NULL, title VARCHAR (255) NOT NULL, publisher VARCHAR (255));"

printf "OK\n"
printf "Importing csv data in it..."

cat books.csv | psql --host=$BL_HOST --port=$BL_PORT --username=$BL_USER --dbname=booklibrarj -c "COPY books FROM stdin DELIMITER ',' CSV;"

printf "OK\n"

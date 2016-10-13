#!/bin/bash

source credentials.sh
echo '{:db-name "booklibrarj", :db-host "'$BL_HOST'", :db-user "'$BL_USER'", :db-pass "'$PGPASSWORD'"}' > .lein-env

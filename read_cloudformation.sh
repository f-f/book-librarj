#!/bin/bash

echo "Starting to read database with CloudFormation"

#echo -n "Enter stack name and press [ENTER]: "
#read stack

stack=ClojureHackathon

#STACK_ID=arn:aws:cloudformation:eu-west-1:946278600493:stack/ClojureHackathon/10c0a880-8cf0-11e6-9093-500c3d40ec36
#echo $STACK_ID

DB_IDENTIFIER=$(aws cloudformation describe-stack-resource --stack-name=$stack --logical-resource-id=MyDB | jq '.StackResourceDetail.PhysicalResourceId')
echo $DB_IDENTIFIER

aws rds describe-db-instances --db-instance-identifier=$DB_IDENTIFIER

echo "Finished."

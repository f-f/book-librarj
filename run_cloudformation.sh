#!/bin/bash

echo "Starting to run CloudFormation template"

echo -n "Enter stack name and press [ENTER]: "
read stack
#stack=ClojureHackathon
echo -n "Enter username and press [ENTER]: "
read username
echo -n "Enter password and press [ENTER]: "
read password

aws cloudformation validate-template --template-body file://create_rds_db.json

STACK_ID=$(aws cloudformation create-stack --stack-name $stack --template-body file://create_rds_db.json --parameters ParameterKey=DBUser,ParameterValue=$username ParameterKey=DBPassword,ParameterValue=$password | jq '.StackId')

STACK_ID=arn:aws:cloudformation:eu-west-1:946278600493:stack/ClojureHackathon/10c0a880-8cf0-11e6-9093-500c3d40ec36
echo $STACK_ID

DB_IDENTIFIER=$(aws cloudformation describe-stack-resource --stack-name=ClojureHackathon --logical-resource-id=MyDB | jq '.StackResourceDetail.PhysicalResourceId')
echo $DB_IDENTIFIER

aws rds describe-db-instances --db-instance-identifier=$DB_IDENTIFIER



echo "Finished."

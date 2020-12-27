#!/bin/bash
# Name: Noah Dunn
# Professor: Dr. Campbell
# Class: DevOPS + Cloud Computing
# Date: 8/27/2020
# Assignment: EC2 Automation
# Resources: My previous work, Dr. Campbell
# https://stackoverflow.com/questions/5171901/find-and-replace-in-file-and-overwrite-file-doesnt-work-it-empties-the-file
# Throw the the public key on the server if it's not on there
aws ec2 import-key-pair --key-name "dunnnm2-public-key" --public-key-material fileb://~/.ssh/id_rsa.pub
# Establish the security rules if they aren't already there
aws ec2 authorize-security-group-ingress --group-name default --protocol tcp --port 22 --cidr 0.0.0.0/0
aws ec2 authorize-security-group-ingress --group-name default --protocol tcp --port 80 --cidr 0.0.0.0/0
aws ec2 authorize-security-group-ingress --group-name default --protocol tcp --port 443 --cidr 0.0.0.0/0
# Spin up 4 boxes
for i in {1..4}
do
	aws ec2 run-instances --image-id ami-0bcc094591f354be2 --count 1 --instance-type t2.micro --key-name dunnnm2-public-key --security-group-ids default --user-data file://ec2-script$i.sh
done

printf "Your IPs\n"
# Wait a couple seconds to let all boxes initialize
sleep 2s
# Get the IPs
aws ec2 describe-instances --query "Reservations[*].Instances[*].PublicIpAddress" --output=text | sed -E 's/[[:space:]]+/\n/g'


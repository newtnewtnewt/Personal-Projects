#!/bin/bash
allIds=$(aws ec2 describe-instances --query "Reservations[*].Instances[*].InstanceId" --output=text)
aws ec2 terminate-instances --instance-ids $allIds

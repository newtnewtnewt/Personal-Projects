#!/bin/bash
# Name: Noah Dunn
# Professor: Dr. Scott Campbell
# Class: CSE570G - Cloud Computing + DevOPS
# Date: 9/1/2020
# Assignment: Bash Scripting - all404s.sh
# Sources: https://stackoverflow.com/questions/14676714/grep-match-all-characters-up-to-not-including-first-blank-space/18216576
# https://stackoverflow.com/questions/618378/select-unique-or-distinct-values-from-a-list-in-unix-shell-script
# https://stackoverflow.com/questions/15993062/bash-scripting-missing
# https://stackoverflow.com/questions/1494178/how-to-define-hash-tables-in-bash
# Grab the full list of all Paths available in the file
input="/var/log/nginx/access.log"
# Declare an associative array cat 
declare -A totalList
printf "===All 404s===\n"
# Ignore 400 and 408, they are pointless data points
# Read every line in the access log
while IFS=:" " read -r ipAddress f2 f3 f4 f5 f6 f7 f8 f9 pathVar f11 statusCode f13 f14 f15 f16
do
	# If the paths match, add the path to the associative array
	if [ "${statusCode}" == "404" ];
	then
		# We only care about finding unique keys
		totalList["${pathVar}"]="thisdoesntmatter"
	fi
done < "$input"
# Print out all the IPs per Path
for address in "${!totalList[@]}"
do 
	printf "${address}\n"
done
# Print a new line
printf "\n"
# Reset the array
unset totalList


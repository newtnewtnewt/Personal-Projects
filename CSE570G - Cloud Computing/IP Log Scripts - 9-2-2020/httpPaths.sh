#!/bin/bash
# Name: Noah Dunn
# Professor: Dr. Scott Campbell
# Class: CSE570G - Cloud Computing + DevOPS
# Date: 9/1/2020
# Assignment: Bash Scripting - httpPaths.sh
# Sources: https://stackoverflow.com/questions/14676714/grep-match-all-characters-up-to-not-including-first-blank-space/18216576
# https://stackoverflow.com/questions/618378/select-unique-or-distinct-values-from-a-list-in-unix-shell-script
# https://stackoverflow.com/questions/15993062/bash-scripting-missing
# https://stackoverflow.com/questions/1494178/how-to-define-hash-tables-in-bash
# Grab the full list of all Paths available in the file
allPaths=$(awk '{print $7}' /var/log/apache2/access.log | sort | uniq )
input="/var/log/apache2/access.log"
# Grab all unique codes
for i in ${allPaths} 
do 
	# Declare an associative array 
	declare -A totalList
	# Ignore 400 and 408, they are pointless data points
	if [[ "${i}" != "400" && "${i}" != "408" ]];
	then
		# Print the path
		printf "${i}\n"
		# Read every line in the access log
		while IFS=:" " read -r ipAddress f2 f3 f4 f5 f6 f7 f8 f9 pathVar f11 f12 f13 f14 f15 f16
		do
			# If the paths match, add the path to the associative array
			if [ "${pathVar}" == "${i}" ];
			then
				# We only care about finding unique keys
				totalList["${ipAddress}"]="thisdoesntmatter"
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
	fi
done

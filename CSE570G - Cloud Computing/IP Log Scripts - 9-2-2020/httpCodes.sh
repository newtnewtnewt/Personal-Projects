#!/bin/bash
# Name: Noah Dunn
# Professor: Dr. Scott Campbell
# Class: CSE570G - Cloud Computing + DevOPS
# Date: 9/1/2020
# Assignment: Bash Scripting - httpCodes.sh
# Sources: https://stackoverflow.com/questions/14676714/grep-match-all-characters-up-to-not-including-first-blank-space/18216576
# https://stackoverflow.com/questions/618378/select-unique-or-distinct-values-from-a-list-in-unix-shell-script
# https://stackoverflow.com/questions/15993062/bash-scripting-missing
printf 'IP 	num2xx	num3xx	num4xx	num5xx\r\n'
# Grab the full list of all IPs available in the file
allCodes=$(awk '{print $1}' /var/log/apache2/access.log | sort | uniq )
input="/var/log/apache2/access.log"
# Grab all unique codes
for i in ${allCodes} 
do 
	# Have storage variables for all the types of Response statuses
	twoHundredCount=0
	threeHundredCount=0
	fourHundredCount=0
	fiveHundredCount=0
	# The ':" "' delimits fields according to spaces
	# The only important ones are 1, 10, 12, and 13
	while IFS=:" " read -r ipAddress f2 f3 f4 f5 f6 f7 f8 f9 f10 f11 statusCode f13 f14 f15 f16
	do
		# Lines with this as the status code have the true status code 
		# stored in field 10
		if [ "$statusCode" == '"-"' ];
		then 
			statusCode="$f10"
		# Lines with this as the status code have the true status code
		# stored in field 13
		elif [ "$statusCode" == "HTTP/1.1\"" ]
		then
			statusCode="$f13"
		fi
		# We only care about the IPs that match the one we are currently looking at
		if [ "$ipAddress" == "$i" ];
		then
			# Add values to the representative status code box
			if [[ "$statusCode" -ge 200 && "$statusCode" -lt 300 ]];
			then
				# Multiple ways to increment in bash, this is one of them
				twoHundredCount=$((twoHundredCount+1))
			elif [[ "$statusCode" -ge 300 && "$statusCode" -lt 400 ]];
			then
				threeHundredCount=$((threeHundredCount+1))
			elif [[ "$statusCode" -ge 400 && "$statusCode" -lt 500 ]];
			then
				fourHundredCount=$((fourHundredCount+1))
			elif [[ "$statusCode" -ge 500 && "$statusCode" -lt 600 ]];
			then
				fiveHundredCount=$((fiveHundredCount+1))
			fi
		fi
	# Input goes in the loop itself
	done < "$input"
	# Print results
	printf "$i	$twoHundredCount	$threeHundredCount	$fourHundredCount	$fiveHundredCount\r\n"
done

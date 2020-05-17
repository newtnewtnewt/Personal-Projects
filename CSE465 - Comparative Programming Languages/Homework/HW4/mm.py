import sys

#  A method to process a user with @param headerTokens as headers, @param userLine as the user's information
#  and the template provided by @param inputTemplate
def processUser(headerTokens, userLine, inputTemplate):
	#  Opens the template
	input = open(inputTemplate, "r")
	#  Create a text file named after the uid of the individual
	userID = userLine[headerTokens['<<ID>>']] + ".txt"
	output = open(userID, "w")
	# For each line in the template, replace headers with actual data and write to output
	for line in input:
		for headerToken in headerTokens:
			line = line.replace(headerToken, userLine[headerTokens[headerToken]])	
		output.write(line)
	# Close our streams
	input.close()
	output.close()	
		
		
if __name__ == "__main__":
	#  We need to grab the name of the input file
	inputTextFile = sys.argv[1]
	#  We need to grab the name of the template file
	inputTemplate = sys.argv[2]
	# Open the input file
	input = open(inputTextFile, "rt")
	# Read the headers
	headerTokens = input.readline().strip().split(",")
	# Convert the headers to template format
	headerTokens = ["<<" + token + ">>" for token in headerTokens]
	# Enumerate the keys to provide the equivalent of an index
	headerTokens = {key:value for value,key in enumerate(headerTokens)}
	# For each user, process and save their information
	for userline in input:
		processUser(headerTokens, userline.strip().split(","), inputTemplate)
	# Close our inputs out 
	input.close()
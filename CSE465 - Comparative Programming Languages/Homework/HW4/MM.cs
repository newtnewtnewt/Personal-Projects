using System;
using System.Collections.Generic;
using System.IO;

namespace MailMerge{
	
	public class MM {
		
		public static void ProcessUser(ref Dictionary<string, int> headerTokens, string[] userLine, string inputTemplate){
			//  Open up the template file
			StreamReader input = new StreamReader(inputTemplate);
			//  Create a text file named after the UID of the individual
			string userID = userLine[headerTokens["<<ID>>"]] + ".txt";
			StreamWriter output = new StreamWriter(userID);
			//  For each line in the template, replace headers with actual data and write to output
			string line = "";
			while((line = input.ReadLine()) != null){
				foreach(KeyValuePair<string, int> headerToken in headerTokens){
					line = line.Replace(headerToken.Key, userLine[headerTokens[headerToken.Key]]);
				}
				output.WriteLine(line);
			}
			input.Close();
			output.Close();
		}
		
		public static void Main(string[] args){
			//  We need to grab the name of the input file
			string inputTextFile = args[0];
			//  We need to grab the name of the template file
			string inputTemplate = args[1];
			//  Open the input file
			StreamReader input = new StreamReader(inputTextFile);
			//  Read the headers
			string[] headerTokens = input.ReadLine().Trim().Split(new char[]{','});
			//  Store the tokens in their proper format with the corresponing index number
			Dictionary<string, int> headerTokensDict = new Dictionary<string, int>();
			for(int i = 0; i < headerTokens.Length; i++){
				headerTokensDict.Add("<<" + headerTokens[i] + ">>", i);
			}
			string userLine = "";
			// Read all the lines in the input file and create files for each
			while((userLine = input.ReadLine()) != null){
				ProcessUser(ref headerTokensDict, userLine.Trim().Split(new char[]{','}), inputTemplate);
			}
			input.Close();
		}
	
	
	}


}
using System;

namespace WordScramblers_CSE570J
{
    class ConfuserGenerator
    {
        static void Main(string[] args)
        {
            try
            {  
                //Generate dictionary and delimit input based on spaces
                EnglishWords ew = new EnglishWords("words.txt");
                Console.WriteLine("Please insert a word, followed by a space and the number of scrambler characters desired");
                char[] blank = { ' ' };
                //Parse our values
                string[] inputVals = Console.ReadLine().Split(blank);
                string scramblerWord = inputVals[0];
                int numOfScramblers = Int32.Parse(inputVals[1]);
                //Get the optimal confuser chracters
                char[] suffixInChars = ew.GetConfusers(scramblerWord, numOfScramblers); //Get our char array for the suffix
                String suffix = "";
                //Concatanate them into one large string
                foreach(char letter in suffixInChars)
                {
                    suffix = suffix + letter + " ";
                }
                //Write our optimal output to console
                Console.WriteLine("Optimal letters to use are: " + suffix);
                Console.WriteLine("Hit enter to finish");
                Console.Read();
            }
            catch(Exception e)
            {
                Console.WriteLine("Invalid Input");
            }
        }
    }

}

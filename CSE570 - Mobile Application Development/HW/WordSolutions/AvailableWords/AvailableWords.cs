using System;
using System.Collections.Generic;

namespace WordScramblers_CSE570J
{
    class AvailableWordGenerator
    {
        static void Main(string[] args)
        {
            try
            {
                //Begin the asyncronous process of our dictionary creation
                EnglishWords ew = new EnglishWords("words.txt");
                Console.WriteLine("Please enter desired length of words, a space, followed by all valid letters next to each other");
                //Create our delimeter for reading from the console
                char[] blank = { ' ' };
                //Grab the word and the desired length
                string[] inputString = Console.ReadLine().Split(blank);
                string letters = inputString[1];
                int length = Int32.Parse(inputString[0]);
                //Generate all permutations, sort them
                List<string> validWords = ew.AvailableWords(letters, length);
                validWords.Sort();
                //Print to Console
                foreach (string word in validWords)
                {
                    Console.WriteLine(word);
                }
                //Enable the user to read, then close
                Console.WriteLine("Hit enter to finish");
                Console.Read();
            }
            catch (Exception e) //In case the user enters garbage input
            {
                Console.WriteLine("Incorrect input, try again with input like so \"4 EWNT\"");
            }
        }
    }
}


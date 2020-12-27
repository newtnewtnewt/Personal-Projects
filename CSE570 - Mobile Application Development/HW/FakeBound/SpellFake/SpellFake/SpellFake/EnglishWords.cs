using SpellFake;
using System;
using System.Collections.Generic;
using System.IO;
using System.Reflection;
using System.Threading.Tasks;


/* Name: Noah Dunn
 * Professor: Dr. Zmuda
 * Date: 9/5/2019
 * Class: CSE 570J
 * Project: Word Scrambling
 * 
 * Project Log:
 * -Insofar as I can tell, this project runs with no errors.
 */

namespace WordScramblers_CSE570J
{
    public class EnglishWords
    {
        // This constructor will initiate the loading of all words located
        // in the given dictionary. The constructor must return very quickly,
        // perhaps before the words have been completely loaded. Tasks will be
        // needed to do this.

        //The task we will reference for async, and the Hashset where we will store our values
        Task readDictionaryTask=null;
        HashSet<string> dictionary; 

        //Constructor that uses private method LoadDictionary to run asynchronously
        public EnglishWords(string fileName)
        {
            readDictionaryTask = LoadDictionary(fileName);
            //LoadDictionarySync(fileName);
        }
        //Loads the dictionary asyncronously following the first line read
        private void LoadDictionarySync(string fileName)
        {
            dictionary = new HashSet<string>();
            var assembly = IntrospectionExtensions.GetTypeInfo(typeof(MainPage)).Assembly;
            Stream stream = assembly.GetManifestResourceStream("SpellFake." + fileName);
    
            using (StreamReader input = new StreamReader(stream))
            {
                while (!input.EndOfStream)
                {
                    string line = input.ReadLine();
                    dictionary.Add(line.ToUpper());
                }
                input.Close();
            }
        }
        private async Task LoadDictionary(string fileName)
        {
            this.dictionary = new HashSet<string>();
            var assembly = IntrospectionExtensions.GetTypeInfo(typeof(MainPage)).Assembly;
            Stream stream = assembly.GetManifestResourceStream("SpellFake." + fileName);
            using (StreamReader input = new StreamReader(stream))
            {
                while (!input.EndOfStream)
                {
                    string line = await input.ReadLineAsync();
                    dictionary.Add(line.ToUpper());
                }
                input.Close();
            }
        }


        // This method will return true only if the word appears in the
        // dictionary. This method will need to wait, if it is called
        // before the words have been completely loaded.

        //Checks if the word is in the dictionary after we finish loading it
        public bool IsLegal(string word)
        {

            if (readDictionaryTask != null && !readDictionaryTask.IsCompleted)
            {
                readDictionaryTask.Wait();
            }
            return dictionary.Contains(word.ToUpper());
        }
        // This method will return an alphabetically sorted list of all words that
        // can be formed using a collection of letters and having the given length.
        // Each letter can only be used once. For example, consider length 5 words
        // the collection of letters: HERATH
        // Here are some of the words that can be formed: EARTH, HEART, ..
        // Some English words not part of the list: REATA, ARHAT
        // Like the previous method, this method may need to wait too.

        //Generates all possible compbinations of a word of size @param len from @param letters
        public List<string> AvailableWords(string letters, int len)
        {

            //The hashset is used to prevent duplicates, and then converted over to a list to meet the requirement
            HashSet<string> validWords = new HashSet<string>();
            List<string> validWordsList = new List<string>();
            //Avoid case-conflicts
            letters = letters.ToUpper();
            //An array of what letters in our alphabet we have used given a recursive run
            bool[] usedLetters = new bool[letters.Length];
            //Check to make sure async loading is done
            if (readDictionaryTask != null && !readDictionaryTask.IsCompleted)
            {
                readDictionaryTask.Wait();
            }
            //Begin the recursion
            AvailableWordsRecursion(ref validWords, letters, len, "", usedLetters, true);
            //Turn our hashset into a list
            foreach (string word in validWords)
            {
                validWordsList.Add(word);
            }
            return validWordsList;


        }
        /* The recursion here uses @param validWords for storage, @param letters as our alphabet, @param len as the desired length of our word
         * @param usedLetters as the list of our used up values, and @validChecker to determine if we care if the word is valid or not 
         * 
         */
        private void AvailableWordsRecursion(ref HashSet<string> validWords, string letters, int len, string protoString, bool[] usedLetters, bool validChecker)
        {
            //If we have built a word
            if (protoString.Length == len) //  We hit desired length 
            {
                if (validChecker)  //  If we care about real words we set this to true 
                {
                    if (IsLegal(protoString))
                    {
                        validWords.Add(protoString);
                    }
                    return;
                }
                else  //  Otherwise let's just add it
                {
                    validWords.Add(protoString);
                    return;
                }
            }
            else
            {
                for (int i = 0; i < usedLetters.Length; i++)
                {
                    if (usedLetters[i] == false) //If the letter is not already in, we add it
                    {

                        bool[] usedLettersCopy = new bool[usedLetters.Length];
                        Array.Copy(usedLetters, usedLettersCopy, usedLetters.Length); //Generate a copy to prevent memory clashing
                        usedLettersCopy[i] = true; //  Mark the letter we used 
                        //  Continue
                        AvailableWordsRecursion(ref validWords, letters, len, protoString + letters[i], usedLettersCopy, true);

                    }
                }
            }

        }
        // 570 STUDENTS ONLY
        // When creating word puzzles, it is sometimes a goal to "hide" the letters of
        // a word amongst other letters so that maximum confusion is presented to the
        // puzzle-solver. For example, suppose we want to "hide" the five letters of
        // "apple" by adding one more letter. By themselves, the letters A-P-P-L-E can be
        // used to form 3 different English words of length 5 (APPLE, APPEL, PEPLA). If the
        // letter Z were added to obtain the collection of 6 letters A-P-P-L-E-Z, there are 
        // still only 3 5-letter words that can be formed. However, if you were to add
        // the letter S instead, 12 5-letter words can be formed. It turns out that adding
        // S yields the maximum number of 5-letter words.
        // This concept can be extended to more than one letter. For example, adding the
        // two letters S and T yields the collection A-P-P-L-E-S-T, which now generates
        // over 40 5-letter words.
        // GetConfusers will return the letters that maximize confusion. The array of
        // letters should be sorted alphabetically. If more than one answer is possible,
        // return the one that is alphabetically earlier (i.e., ST < TS < ZA)
        public char[] GetConfusers(string baseWord, int numLettersToAdd)
        {
            string alphabetString = "";
            int asciiForA = (int)'a';
            for (int i = 0; i < numLettersToAdd; i++)  //This is just a trick to get all 26 letters per num of letters we want to add
            {
                for (int j = 0; j < 26; j++)
                {
                    alphabetString = alphabetString + ((char)(asciiForA + j)); //We iterate a-z as many times as needed
                }
            }
            alphabetString = alphabetString.ToUpper(); //Avoid case clashes
            HashSet<string> allSuffixes = new HashSet<string>(); //  We store all @param numLettersToAdd long combinations here
            if (!readDictionaryTask.IsCompleted)
            {
                readDictionaryTask.Wait(); //  Insurance that the dictionary is done loading
            }
            //Call our method with the flag for valid words set to false
            AvailableWordsRecursion(ref allSuffixes, alphabetString, numLettersToAdd, "", new bool[alphabetString.Length], false);
            //Scrambled Word List is used for counting the number of words each word+suffix pair can generate
            SortedSet<Word> scrambledWordList = new SortedSet<Word>();
            //Suffix scrambles stores those words, which are stored in the form of a custom Word type in scrambled Word list
            HashSet<string> suffixScrambles = new HashSet<string>();

            string scrambledWord = "";
            foreach (string suffix in allSuffixes) //  Cycles all possible suffixes
            {
                scrambledWord = "";
                scrambledWord = baseWord + suffix;
                scrambledWord = scrambledWord.ToUpper();
                AvailableWordsRecursion(ref suffixScrambles, scrambledWord, baseWord.Length, "", new bool[scrambledWord.Length], true);
                scrambledWordList.Add(new Word(suffix, suffixScrambles.Count));
                suffixScrambles.Clear(); //Clear our generated word list at the end of each recursion
            }
            char[] suffixInChars = scrambledWordList.Max.GetWord().ToCharArray(); //  Grab output in the form of a char array to return
            return suffixInChars;




        }
        public class Word : IComparable  // IComparable is needed for the CompareTo method that scrambledWordList needs to sort
        {
            string word = "";
            int count = 0;
            public Word(string word, int count)  //  Stores the suffix as word, and the count of the amount of scrambled words it generates
            {
                this.word = word;
                this.count = count;
            }

            public string GetWord()
            {
                return word;
            }
            public int GetCount()
            {
                return count;
            }
            public override string ToString()
            {
                return "" + word + " with count: " + count;
            }

            public int CompareTo(object obj) //  Compares first on count, than alphabetically
            {

                Word otherWord = (Word)obj;
                if (this.count - otherWord.GetCount() == 0)
                {
                    return this.word.CompareTo(otherWord.GetWord());
                }
                return this.count - otherWord.GetCount();


            }
        }



    }

}


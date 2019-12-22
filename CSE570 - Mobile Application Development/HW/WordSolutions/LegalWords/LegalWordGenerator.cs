using System;


namespace WordScramblers_CSE570J
{
    public class LegalWords
    {
        static void Main(string[] args)
        {
            try
            {
                //Create our dictionary, take input, delimit according to spaces
                EnglishWords ew = new EnglishWords("words.txt");
                Console.WriteLine("Enter in a list of words, separated by single spaces");
                char[] blank = {' '};
                string[] stringList = Console.ReadLine().Split(blank);
                //Use the IsLegal methods to check one by one if they are legal or illegal
                foreach (string x in stringList)
                {
                    if (ew.IsLegal(x))
                    {
                        Console.WriteLine(x + " (legal)");
                    }
                    else
                    {
                        Console.WriteLine(x + " (illegal)");
                    }

                }
                //Option to read before closeout
                Console.WriteLine("Hit enter to finish");
                Console.Read();
            }
            catch (Exception e)
            {
                Console.WriteLine("Invalid Input attempted, please only enter words seperated by space");
            }
        }
    }
}

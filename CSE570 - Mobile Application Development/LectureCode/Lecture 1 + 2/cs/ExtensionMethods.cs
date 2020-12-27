using System;
using System.Collections.Generic;
using System.Text;

namespace cs {
    public static class ExtensionMethods {
        public static bool IsArticle1(string word) {
            word = word.ToUpper();
            return word.Equals("A") ||
                    word.Equals("AN") ||
                    word.Equals("THE");
        }
        public static bool IsArticle2(this string word) {   // Extension method
            word = word.ToUpper();
            return word.Equals("A") ||
                    word.Equals("AN") ||
                    word.Equals("THE");
        }
        public static void ExtensionMain(string [] args) {
            string[] words = { "apple", "the", "throws", "An" };
            foreach (string word in words) {
                Console.WriteLine("{0} {1} {2}", word, word.IsArticle2(), IsArticle1(word));
            }
        }
    }
}

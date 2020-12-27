using System;
using System.Collections.Generic;
using System.Text;
using System.IO;

namespace cs {
    public class IO {
        public static void IOMain(string[] args) {
            using (StreamWriter output = new StreamWriter("sorted.txt"))
            using (StreamReader input = new StreamReader("items.csv")) {
                while (!input.EndOfStream) {
                    string line = input.ReadLine();
                    string[] toks = line.Split(',');
                    int[] values = new int[toks.Length];
                    for (int i=0; i<toks.Length; i++)
                        values[i] = Int32.Parse(toks[i]);
                    Array.Sort(values);
                    foreach (int value in values) {
                        output.Write(value + " ");
                    }
                    output.WriteLine();
                }
            }
        }
    }
}

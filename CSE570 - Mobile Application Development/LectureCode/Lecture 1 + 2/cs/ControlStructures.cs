using System;
using System.Collections.Generic;
using System.Text;

namespace cs {
    public class ControlStructures {
        public static void Display(int a) {
            switch (a) {
                case 0: return;
                case 1:
                    Console.WriteLine("Uno");
                    break;
                case 2:
                    Console.WriteLine("Dos");
                    break;
                case 3:
                    Console.WriteLine("Tres");
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    Console.WriteLine("More than tres");
                    break;
                case 10: goto case 9;
                default:
                    Console.WriteLine("Big number");
                    break;
            }
        }
        public static void ControlStructuresMain(string [] args) {
            for (int i = 0; i < 20; i++) {
                Console.WriteLine("{0}", i);
                Display(i);
            }
        }
    }
}

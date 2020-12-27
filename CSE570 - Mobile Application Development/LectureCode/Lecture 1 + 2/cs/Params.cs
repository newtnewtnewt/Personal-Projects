using System;

namespace cs {
    public class Params {
        public static void swap<T>(ref T a, ref T b) {
            T tmp = a;
            a = b;
            b = tmp;
        }
        public static void f(int a, out int b, ref int c) {
            int x = 1;
            a = 1;      // value param
                        // x = b;	// illegal, b must be set first
            b = 2;
            c = 0;
            x = c;
        }
        public static int GetMin(int[] array)
        {
            int min = int.MaxValue;
            for(int i = 0; i < array.Length; i++)
            {
                if(array[i] < min)
                {
                    min = array[i];
                }
            }
            return min;
        }
        public static void GetMin(int[] array, out int min)
        {
            min = int.MaxValue;
            for (int i = 0; i < array.Length; i++)
            {
                if (array[i] < min)
                {
                    min = array[i];
                }
            }
        }
        public static void ParamsMain(string [] args) {
            int a = 1, b, c = 3;
            f(a, out b, ref c);

            int x = 0, y = 1;
            swap(ref x, ref y);
            Console.WriteLine(x + " " + y);

            string m = "hello", n = "world";
            swap(ref m, ref n);
            Console.WriteLine(m + " " + n);

            int q;
            int[] array = { 1, 2, 3, 4, 5 };
            GetMin(array, out q);
            Console.WriteLine(q);

        }
    }
}

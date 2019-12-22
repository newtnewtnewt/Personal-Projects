using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cs {
    public class Delegates {
        public delegate int IntFunc(int x);
        public delegate string stringFunc(string s);

        public static int echo(int x) {
            return x;
        }
        public static int sqr(int x) {
            return x * x;
        }
        public static int sum1(int N, IntFunc f) {
            int sum = 0;
            for (int i = 0; i <= N; i++) {
                sum += f(i);
            }
            return sum;
        }
        public static int sum2(int N, Func<int, int> f) {   // Same as above
            int sum = 0;
            for (int i = 0; i <= N; i++) {
                sum += f(i);
            }
            return sum;
        }
        static void DelegateExample() {
            Console.WriteLine("Start DelegateExample");
            const int N = 5;
            Console.WriteLine(N * N);
            Console.WriteLine(sqr(N));

            Console.WriteLine(sum1(100, echo));
            Console.WriteLine(sum2(100, echo));
            Console.WriteLine(sum1(100, sqr));
            Console.WriteLine(sum2(100, sqr));

            Console.WriteLine();
        }
        delegate void VoidFunc(int a);
        public static void f1(int a) { Console.WriteLine(a); }
        public static void f2(int a) { Console.WriteLine(2 * a); }
        public static void f3(int a) { Console.WriteLine(3 * a); }
        static void MulticastDelegateExample() {
            VoidFunc func = f1;
            func += f2;
            func += f3;
            func(4);
        }
        public static void DelegatesMain(string[] args) {
            DelegateExample();
            MulticastDelegateExample();
        }
    }
}

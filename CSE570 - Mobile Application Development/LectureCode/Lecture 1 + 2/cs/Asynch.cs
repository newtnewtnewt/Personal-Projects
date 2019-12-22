using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace cs {
    public class Asynch {
        public static uint[] ary;
        public static ulong LoadData(string filename) {
            StreamReader reader = new StreamReader(filename);
            ulong cnt = 0;
            while (!reader.EndOfStream) {
                string line = reader.ReadLine();
                cnt += (ulong)line.Length;
            }
            reader.Close();
            Console.WriteLine("Done reading data: " + cnt);
            return cnt;
        }
        public static async Task<ulong> LoadDataAsync(string filename) {
            StreamReader reader = new StreamReader(filename);
            ulong cnt = 0;
            while (!reader.EndOfStream) {
                string line = await reader.ReadLineAsync();
                cnt += (ulong)line.Length;
            }
            reader.Close();
            Console.WriteLine("Done reading data asynchronously");
            return cnt;
        }
        public static void SmallTask() {
            Thread.Sleep(100);
            Console.WriteLine("Done with small task");
        }
        public static uint SlowFib(int N) {
            if (N <= 1)
                return 1;
            else
                return SlowFib(N - 1) + SlowFib(N - 2);
        }
        public static void FillArray(int N) {
            ary = new uint [N];
            for (int i=0; i<N; i++) {
                ary[i] = SlowFib(i);
                Console.WriteLine(i + " " + ary[i]);
            }
            Console.WriteLine("Done filling array");
        }
        public static void DemoSyncLoadData() {
            ulong charCount = LoadData("big.txt");
            SmallTask();
            Console.WriteLine("Done with demo " + charCount);
        }
        public static void DemoAsyncLoadData() {
            Task<ulong> dataTask = LoadDataAsync("big.txt");
            SmallTask();
            dataTask.Wait();
            ulong charCount = dataTask.Result;
            Console.WriteLine("Done with demo " + charCount);
        }
        public static void DemoAsyncComputation() {
            Action action = () => { FillArray(40); };
            Task task = new Task(action);
            task.Start();
            SmallTask();
            task.Wait();
            Console.WriteLine("Done with compute intensive task");
        }

        public static void AsynchMain(string [] args) {
            DemoSyncLoadData();
            DemoAsyncLoadData();
            DemoAsyncComputation();
        }
    }
}

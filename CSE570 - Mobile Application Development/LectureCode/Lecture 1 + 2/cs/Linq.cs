using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cs {
    public class Pairs<KEY, VALUE> {
        private List<Tuple<KEY, VALUE>> items = new List<Tuple<KEY, VALUE>>();
        public Pairs() {
        }
        public IEnumerable<KEY> Keys() {
            return items.Select(x => x.Item1);
        }
        public IEnumerable<VALUE> Values() {
            return items.Select(x => x.Item2);
        }
        public void Add(KEY key, VALUE value) {
            items.Add(new Tuple<KEY, VALUE>(key, value));
        }
        public IEnumerable<KEY> FilterOnValues(Func<VALUE, bool> filter) {
            IEnumerable<KEY> result = from p in items where filter(p.Item2) select p.Item1;
            return result;
        }
        public int Count {
            get { return items.Count; }
        }
        public System.Collections.Generic.IEnumerable<Tuple<KEY, VALUE>> Elements() {
            foreach (Tuple<KEY, VALUE> v in items) {
                yield return v;
            }
        }
    }
    public class Linq {
        public static void Display<T1, T2>(Pairs<T1, T2> pairs) {
            foreach (Tuple<T1, T2> item in pairs.Elements()) {
                Console.WriteLine("{0} {1}", item.Item1, item.Item2);
            }
        }
        public static void Display<T>(IEnumerable<T> items) {
            foreach (T item in items) {
                Console.WriteLine(item);
            }
        }

        public static void LinqMain(string[] args) {
            Pairs<string, int> age = new Pairs<string, int>();
            Pairs<string, string> school = new Pairs<string, string>();

            age.Add("fred", 21);
            age.Add("otis", 1);
            age.Add("william", 1);
            age.Add("juan", 25);
            age.Add("nicole", 27);

            school.Add("fred", "Miami");
            school.Add("sarah", "Miami");
            school.Add("jake", "OSU");
            school.Add("nicole", "WSU");

            IEnumerable<string> minors = age.FilterOnValues(a => a < 18);
            IEnumerable<string> MUstudents= school.FilterOnValues(s => s.Equals("Miami"));

            Display(age);
            Display(minors);
            Display(school);
            Display(MUstudents);
        }
    }
}

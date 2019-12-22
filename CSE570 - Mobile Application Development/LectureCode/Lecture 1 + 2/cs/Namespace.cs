using System;
using System.Text;
using Utilities;

namespace Physics {
    public class Distance {
        public int value;
        public Distance(int v) {
            value = v;
        }
        public override string ToString() {
            return string.Format("{0}", value);
        }
    }
}

namespace Utilities {
    public class MathFuncs {
        public static int sqr(int x) {
            return x * x;
        }
    }
    public class Distance {
        public int value;
        public string units;
        public Distance(int v, string u) {
            value = v;
            units = u;
        }
        public override string ToString() {
            return string.Format("{0}{1}", value, units);
        }
    }
}

namespace cs {
    public class NS {
        public static void NamespaceMain(string[] args) {
            Physics.Distance phyDistance = new Physics.Distance(100);
            Utilities.Distance utilDistance = new Utilities.Distance(12, "ft");
            Console.WriteLine(phyDistance);
            Console.WriteLine(utilDistance);
            Console.WriteLine(MathFuncs.sqr(2));
        }
    }
}

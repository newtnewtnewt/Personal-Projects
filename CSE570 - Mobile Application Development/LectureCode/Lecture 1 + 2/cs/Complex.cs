using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace cs {
    public class ComplexNumber {
        private double r, i;
        public ComplexNumber() : this(0, 0) {
        }
        public ComplexNumber(double real, double imag) {
            r = real;
            i = imag;
        }
        public static ComplexNumber operator +(ComplexNumber a, ComplexNumber b) {
            ComplexNumber result = new ComplexNumber(a.r + b.r, a.i + b.i);
            return result;
        }
        public override string ToString() {
            if (i < 0) {
                return string.Format("({0}{1}i)", r, i);
            } else {
                return string.Format("({0}+{1}i)", r, i);
            }
        }
        public override int GetHashCode() {
            return r.GetHashCode() << 8 | i.GetHashCode();
        }
        public override bool Equals(Object obj) {
            ComplexNumber other = (ComplexNumber)obj;
            bool result = r == other.r && i == other.i;
            Console.WriteLine(result);
            return result;
        }
        public double Real {
            get {
                return r;
            }
            set {
                r = value;
            }
        }
        public double Imaginary {
            get {
                return i;
            }
            set {
                i = value;
            }
        }
        public double OtherData {
            get;
            set;
        }
        public static void ComplexMain(string[] args) {
            ComplexNumber c1 = new ComplexNumber();
            ComplexNumber c2 = new ComplexNumber(2, 3);
            ComplexNumber c3 = new ComplexNumber {
                                    Real = 45,
                                    Imaginary = 22
                                };
            Console.WriteLine(c1);
            Console.WriteLine(c2);
            Console.WriteLine(c3);
        }
    }
}

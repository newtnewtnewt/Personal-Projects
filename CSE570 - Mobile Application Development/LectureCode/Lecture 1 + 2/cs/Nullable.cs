using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace cs {
    public struct Point {
        public int x, y;
        public Point(int X, int Y) { x = X; y = Y; }
    }
    public class Nullable {
        public static string ClassifyAge(int? age) {
            string result;
            if (age.HasValue) {
                result = age.Value >= 18 ? "Adult" : "Minor";
            } else {
                result = "unknown";
            }
            return result;
        }
        public static int Quadrant(Point? pt) {
            int result;
            if (pt.HasValue) {
                if (pt.Value.x > 0 && pt.Value.y > 0) result = 1;
                else if (pt.Value.x < 0 && pt.Value.y > 0) result = 2;
                else if (pt.Value.x < 0 && pt.Value.y < 0) result = 3;
                else if (pt.Value.x > 0 && pt.Value.y < 0) result = 4;
                else if (pt.Value.x == 0 || pt.Value.y == 0) result = 0;
                else result = -1;
            } else {
                result = -1;
            }
            return result;
        }
        public static void NullableMain(string[] args) {
            int? age = null;

            Console.WriteLine(ClassifyAge(age));
            age = 21;
            Console.WriteLine(ClassifyAge(age));
            age = 13;
            Console.WriteLine(ClassifyAge(age));

            Point? p1 = null;
            Point p2 = new Point(3, -4);
            Console.WriteLine(Quadrant(p1));
            Console.WriteLine(Quadrant(p2));
            p1 = p2;
            Console.WriteLine(Quadrant(p1));
            Console.WriteLine(Quadrant(p2));
        }
    }
}

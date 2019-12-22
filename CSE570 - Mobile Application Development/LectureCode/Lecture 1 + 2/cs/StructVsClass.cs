using System;
using System.Collections.Generic;
using System.Text;

namespace cs {
    public struct RectangleStruct {
        public double lx, ly, rx, ry;
        public RectangleStruct(double LX, double LY, double RX, double RY) {
            lx = LX;
            ly = LY;
            rx = RX;
            ry = RY;
        }
        public override string ToString() { return string.Format("RC ({0},{1},{2},{3})", lx, ly, rx, ry); }
    }
    public class RectangleClass {
        public double lx, ly, rx, ry;
        public RectangleClass(double LX, double LY, double RX, double RY) {
            lx = LX;
            ly = LY;
            rx = RX;
            ry = RY;
        }
        public override string ToString() { return string.Format("RC ({0},{1},{2},{3})", lx, ly, rx, ry); }
    }
    public class StructVsClass {
        public static void StructVsClassMain(string [] args) {
            RectangleStruct r1 = new RectangleStruct(-0.5, -0.5, +0.5, +0.5);
            RectangleStruct r2 = new RectangleStruct(0, 0, 1, 1);
            RectangleClass r3 = new RectangleClass(-0.5, -0.5, +0.5, +0.5);

            Console.WriteLine(r1);
            Console.WriteLine(r2);
            Console.WriteLine(r3);

            RectangleClass rcOrig = new RectangleClass(-10, -10, 10, 10);
            RectangleClass rcCopy = rcOrig;

            Console.WriteLine();
            Console.WriteLine("Class demo");
            Console.WriteLine("Before: Original - {0} Copy - {1}", rcOrig, rcCopy);
            rcOrig.lx = rcOrig.ly = 0;
            rcOrig.rx = rcOrig.ry = 2;
            Console.WriteLine("After: Original - {0} Copy - {1}", rcOrig, rcCopy);

            RectangleStruct rsOrig = new RectangleStruct(-10, -10, 10, 10);
            RectangleStruct rsCopy = rsOrig;

            Console.WriteLine();
            Console.WriteLine("Struct demo");
            Console.WriteLine("Before: Original - {0} Copy - {1}", rsOrig, rsCopy);
            rsOrig.lx = rsOrig.ly = 0;
            rsOrig.rx = rsOrig.ry = 2;
            Console.WriteLine("After: Original - {0} Copy - {1}", rsOrig, rsCopy);
        }
    }
}

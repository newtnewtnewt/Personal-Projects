using System;
using System.Collections;
using System.Collections.Generic;

namespace cs {
    /// <summary>
    /// Represent list of unbounded number of integers
    /// </summary>
    public class List : Adjustable {

        /// <summary>
        /// Represents an integer value, and the
        /// next integer in the list
        /// </summary>
        private class Node {
            public int val;
            public Node next;
        };

        /// <summary>
        /// Reference to first element in the list
        /// </summary>
        private Node first = null;

        /// <summary>
        /// Subtract one from all elements.
        /// </summary>
        public void Dec() {
            Node elt = first;
            while (elt != null) {
                elt.val--;
                elt = elt.next;
            }
        }
        /// <summary>
        /// Adds one to all elements.
        /// </summary>
        public void Inc() {
            Node elt = first;
            while (elt != null) {
                elt.val++;
                elt = elt.next;
            }
        }
        /// <summary>
        /// Indexer
        /// </summary>
        /// <param name="i">ith element to access</param>
        /// <returns>value at a paricular position, 0 based</returns>
        public int this[int i] {
            get {
                Node elt = first;
                for (int j = 0; j < i; j++) {
                    elt = elt.next;
                }
                return elt.val;
            }
        }
        /// <summary>
        /// Return the number of elements in List.
        /// </summary>
        public int Count {
            get {
                int sz = 0;
                Node elt = first;
                while (elt != null) {
                    elt = elt.next;
                    sz++;
                }
                return sz;
            }
        }
        /// <summary>
        /// Determines if value is found in List
        /// </summary>
        /// <param name="key">Integer value to look for</param>
        /// <returns>true if key is found in the list, false otherwise</returns>
        public bool IsElement(int key) {
            Node elt = first;
            while (elt != null && elt.val != key) {
                elt = elt.next;
            }
            return elt != null;
        }
        /// <summary>
        /// Insert value into list
        /// </summary>
        /// <param name="val">Value to insert into list</param>
        public virtual void Insert(int val) {
            Node n = new Node();
            n.val = val;
            n.next = first;
            first = n;
        }
        /// <summary>
        /// Convert List into string representation.
        /// </summary>
        /// <returns>string representing elements in List</returns>
        public override string ToString() {
            string result = "";
            Node elt = first;
            while (elt != null) {
                result += elt.val + " ";
                elt = elt.next;
            }
            return result;
        }
        public static int ListMain(string[] args) {
            List l = new List();
            Console.WriteLine(l is List);
            Console.WriteLine(l is Adjustable);
            for (int i = 0; i < 4; i++) {
                l.Insert(i);
                l.Insert(i);
            }
            Console.WriteLine(l);
            l.Dec();
            Console.WriteLine(l);

            return 0;
        }
    }
}

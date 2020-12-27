#!/bin/bash
grep -m 1 "model name" /proc/cpuinfo
grep -m 1 "MemTotal" /proc/meminfo
python3 cpplint.py "Lab01.cpp"
g++ -o Lab1 Lab01.cpp -O3 --std=c++14 -Wall
./Lab1


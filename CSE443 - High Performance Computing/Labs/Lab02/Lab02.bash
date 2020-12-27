#!/bin/bash -i 

grep "model name" -m 1 /proc/cpuinfo
grep "MemTotal" -m 1 /proc/meminfo

python3 cpplint.py Lab02.cpp

g++ -o Lab2 Lab02.cpp -O3 --std=c++14 -Wall
for i in {1..5}
do
   /usr/bin/time -f "Elapsed Time: %e " ./Lab2
done


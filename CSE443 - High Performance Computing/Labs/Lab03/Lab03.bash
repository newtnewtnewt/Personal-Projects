#!/bin/bash -l

grep "model name" -m 1 /proc/cpuinfo
grep "MemTotal" -m 1 /proc/meminfo

python3 cpplint.py Lab03.cpp

g++ -o Lab3 Lab03.cpp -O3 --std=c++14 -Wall

for i in {1..8}
do
   /usr/bin/time -f "%U %s %e" ./Lab3 switch 2000000000
done

for i in {1..8}
do
   /usr/bin/time -f "%U %s %e" ./Lab3 if 2000000000
done


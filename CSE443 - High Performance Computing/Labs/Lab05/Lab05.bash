#!/bin/bash -l

grep "model name" -m 1 /proc/cpuinfo
grep "MemTotal" -m 1 /proc/meminfo

python cpplint.py unroll.cpp

g++ -o unroll unroll.cpp  -O2 --std=c++14 -Wall
g++ -o unroll-with-funroll  unroll.cpp -O2 --std=c++14 -Wall -funroll-loops

for i in {1..5}
do
	perf stat -d -d -d ./unroll standard 2>> Lab05-standard.perfstat
done
for i in {1..5}
do
	perf stat -d -d -d ./unroll unrolled 2>> Lab05-unrolled.perfstat
done
for i in {1..5}
do
	perf stat -d -d -d ./unroll-with-funroll standard 2>> Lab05-standard-with-funroll.perfstate
done

#!/bin/bash -l

grep "model name" -m 1 /proc/cpuinfo
grep "MemTotal" -m 1 /proc/meminfo

python3 cpplint.py Lab04-good.cpp
python3 cpplint.py Lab04-bad.cpp

g++ -o Lab04-good Lab04-good.cpp -O3 --std=c++14 -Wall
g++ -o Lab04-bad  Lab04-bad.cpp -O3 --std=c++14 -Wall
perf stat -d -d -d ./Lab04-good 2> Lab04-good.perfstat
perf stat -d -d -d ./Lab04-bad  2> Lab04-bad.perfstat

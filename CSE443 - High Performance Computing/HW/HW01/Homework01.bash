#!/bin/bash -l

grep "model name" -m 1 /proc/cpuinfo
grep "MemTotal" -m 1 /proc/meminfo

python cpplint.py ackermann.cpp

g++ ackermann.cpp -O3 -g -Wall -std=c++14 -o ackermann

for i in {1..6}
do
        perf stat -d -d -d ./ackermann 16 2>> Lab06-noprofile.perfstat
done
g++ ackermann.cpp -O3 -g -Wall -fprofile-generate -std=c++14 -o ackermann_pgo

./ackermann_pgo 9
./ackermann_pgo 11
./ackermann_pgo 13
./ackermann_pgo 15

g++ ackermann.cpp -O3 -g -Wall -std=c++14 -fprofile-use -o ackermann_pgo

for i in {1..6}
do
        perf stat -d -d -d ./ackermann_pgo 16 2>> Lab06-profile.perfstat 
done


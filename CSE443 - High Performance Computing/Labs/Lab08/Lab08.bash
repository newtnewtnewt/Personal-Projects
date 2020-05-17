#!/bin/bash
g++ -o workhorse workhorse.cpp -O3 --std=c++14 -Wall
g++ -o workhorse_for workhorse.cpp -O3 --std=c++14 -Wall -fopenmp
grep "model name" -m 1 /proc/cpuinfo
grep "MemTotal" -m 1 /proc/meminfo

echo "BASE CASE workhorse" > allTestRuns.txt
for i in {1..2}
	do
		/usr/bin/time -f "%U %S %e" ./workhorse 40000000  2>> allTestRuns.txt
	done

for x in {1..8}
	do 
		echo "BASE CASE Threads=$x" >> allTestRuns.txt
		for i in {1..2}
		do
			export OMP_NUM_THREADS=$x
			/usr/bin/time -f "%U %S %e" ./workhorse_for 40000000 2>> allTestRuns.txt
		done
    done


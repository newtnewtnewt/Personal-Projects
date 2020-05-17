#!/bin/bash
g++ -o f_inner fourier_inner_omp.cpp -O3 --std=c++14 -Wall -fopenmp
g++ -o f_outer fourier_outer_omp.cpp -O3 --std=c++14 -Wall -fopenmp
grep "model name" -m 1 /proc/cpuinfo
grep "MemTotal" -m 1 /proc/meminfo

for x in {1..8}
	do 
		echo "Inner Loop Threads=$x" >> allTestRuns.txt
		for i in {1..8}
		do
			export OMP_NUM_THREADS=$x
			/usr/bin/time -f "%U %S %e" ./f_inner 40000 2>> allTestRuns.txt
		done
    done
	
for x in {1..8}
	do 
		echo "Outer Loop Threads=$x" >> allTestRuns.txt
		for i in {1..8}
		do
			export OMP_NUM_THREADS=$x
			/usr/bin/time -f "%U %S %e" ./f_outer 40000 2>> allTestRuns.txt
		done
    done


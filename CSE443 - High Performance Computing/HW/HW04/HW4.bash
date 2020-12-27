#!/bin/bash
mpicxx fourier_mpi.cpp -o f_mpi -O3 -Wall --std=c++14 
g++ fourier_serial.cpp -o f_serial -O3 --std=c++14 -Wall 
grep "model name" -m 1 /proc/cpuinfo > allTestRuns.txt
grep "MemTotal" -m 1 /proc/meminfo >> allTestRuns.txt


echo "Serial Runs" >> allTestRuns.txt
for i in {1..5}
do
	/usr/bin/time -f "%U %S %e" ./f_serial 65536 2>> allTestRuns.txt
done
   
	
for x in {2..8}
	do 
		echo "MPI Number Processes=$x" >> allTestRuns.txt
		for i in {1..5}
		do
			mpirun -np $x ./f_mpi 65536 2>> allTestRuns.txt
		done
    done


#!/bin/bash 

grep "model name" -m 1 /proc/cpuinfo
grep "MemTotal" -m 1 /proc/meminfo

python cpplint.py Lab06.cpp

g++ -o Lab06 Lab06.cpp  -O3 --std=c++14 -Wall
g++ -o Lab06_omp  Lab06.cpp -O3 -fopenmp --std=c++14 -Wall 

echo "BASE CASE"
for i in {1..2}
do
	/usr/bin/time -f "%U %S %e" ./Lab06
done
echo "THREADS: 1"
for i in {1..2}
do
	export OMP_NUM_THREADS=1
	/usr/bin/time -f "%U %S %e"  ./Lab06_omp 
done
echo "THREADS: 2"
for i in {1..2}
do
	export OMP_NUM_THREADS=2
	/usr/bin/time -f "%U %S %e" ./Lab06_omp 
done
echo "THREADS: 3"
for i in {1..2}
do
	export OMP_NUM_THREADS=3
	/usr/bin/time -f "%U %S %e" ./Lab06_omp
done
echo "THREADS: 4"
for i in {1..2}
do
	export OMP_NUM_THREADS=4
	/usr/bin/time -f "%U %S %e" ./Lab06_omp 
done
echo "THREADS: 5"
for i in {1..2}
do
	export OMP_NUM_THREADS=5
	/usr/bin/time -f "%U %S %e" ./Lab06_omp 
done
echo "THREADS: 6"
for i in {1..2}
do
	OMP_NUM_THREADS=6
	/usr/bin/time -f "%U %S %e" ./Lab06_omp export 
done
echo "THREADS: 7"
for i in {1..2}
do
	export OMP_NUM_THREADS=7
	/usr/bin/time -f "%U %S %e" ./Lab06_omp 
done
echo "THREADS: 8"
for i in {1..2}
do
	export OMP_NUM_THREADS=8
	/usr/bin/time -f "%U %S %e" ./Lab06_omp 
done
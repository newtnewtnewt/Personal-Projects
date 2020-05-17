#!/bin/bash

function getSeed {
# $RANDOM returns a random number between 0 and 32767
  export RAND1=$RANDOM
# We want to avoid a seed of zero, so increment $RAND1 in that case
  if [ $RAND1 -eq 0 ]
  then
      let RAND1=$RAND1 + 1
  fi
# $RANDOM returns a random number between 0 and 32767
  export RAND2=$RANDOM
# We want to avoid a seed of zero, so increment $RAND2 in that case
  if [ $RAND2 -eq 0 ]
  then
      let RAND2=$RAND2 + 1
  fi
# Multiply the two seeds together to get a 32-bit RNG seed
  echo `expr $RAND1 \* $RAND2`
}

g++ -o box-muller box-muller.cpp -O3 --std=c++14 -Wall
g++ -o box-muller_omp box-muller_omp.cpp -O3 --std=c++14 -Wall -fopenmp
g++ -o marsaglia marsaglia.cpp -O3 --std=c++14 -Wall
g++ -o marsaglia_omp marsaglia_omp.cpp -O3 --std=c++14 -Wall -fopenmp

echo "BASE CASE box-muller" > allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./box-muller `getSeed` 2>> allTestRuns.txt
done
echo "BASE CASE marsaglia" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./marsaglia `getSeed` 2>> allTestRuns.txt
done

echo "box-muller THREADS: 1" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e"  ./box-muller_omp `getSeed` 1 2>> allTestRuns.txt
done
echo "box-muller THREADS: 2" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./box-muller_omp `getSeed` 2 2>> allTestRuns.txt
done
echo "box-muller THREADS: 3" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./box-muller_omp `getSeed` 3 2>> allTestRuns.txt
done
echo "box-muller THREADS: 4" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./box-muller_omp `getSeed` 4 2>> allTestRuns.txt
done
echo "box-muller THREADS: 5" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./box-muller_omp `getSeed` 5 2>> allTestRuns.txt
done
echo "box-muller THREADS: 6" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./box-muller_omp `getSeed` 6 2>> allTestRuns.txt
done
echo "box-muller THREADS: 7" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./box-muller_omp `getSeed` 7 2>> allTestRuns.txt
done
echo "box-muller THREADS: 8" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./box-muller_omp `getSeed` 8 2>> allTestRuns.txt
done
echo "marsaglia THREADS: 1" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e"  ./marsaglia_omp `getSeed` 1 2>> allTestRuns.txt
done
echo "marsaglia THREADS: 2" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./marsaglia_omp `getSeed` 2 2>> allTestRuns.txt
done
echo "marsaglia THREADS: 3" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./marsaglia_omp `getSeed` 3 2>> allTestRuns.txt
done
echo "marsaglia THREADS: 4" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./marsaglia_omp `getSeed` 4 2>> allTestRuns.txt
done
echo "marsaglia THREADS: 5" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./marsaglia_omp `getSeed` 5 2>> allTestRuns.txt
done
echo "marsaglia THREADS: 6" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./marsaglia_omp `getSeed` 6 2>> allTestRuns.txt
done
echo "marsaglia THREADS: 7" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./marsaglia_omp `getSeed` 7 2>> allTestRuns.txt
done
echo "marsaglia THREADS: 8" >> allTestRuns.txt
for i in {1..5}
do
        /usr/bin/time -f "%U %S %e" ./marsaglia_omp `getSeed` 8 2>> allTestRuns.txt
done

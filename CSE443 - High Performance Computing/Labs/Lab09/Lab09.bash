#!/bin/bash

#
# Store the executable name in an environment variable
#
export EXEC=pingpong_mpi


#
# Check the code with cpplint to ensure compliance to standards.  If the
# cpplint.py file isn't in your PATH you'll need to modify this to point
# to where it's located
#
cpplint.py $EXEC.cpp

#
# Exit if cpplint returns errors
#
if [ $? != 0 ]
then
  echo "Program failed cpplint.  See output for required fixes."
  exit 1
fi


#
# Compile the program with our "standard" options for OpenMP
#
mpic++ -O3 --std=c++14 -Wall -o $EXEC $EXEC.cpp

#
# Exit if mpic++ returns errors
#
if [ $? != 0 ]
then
  echo "Program failed to compile.  See output for required fixes."
  exit 2
fi

#
# Run the program for different message sizes, saving the output in a
# separate file for each message size.  Do three runs for each message size
#
for i in {1..3}
do
    MSGSIZE=100000
    while [ $MSGSIZE -le 500000 ]
	  do
	     echo $MSGSIZE
	     mpirun -n 2 ./$EXEC $MSGSIZE 1000000 >> $MSGSIZE.dat
	     let MSGSIZE=MSGSIZE+100000
	  done
done

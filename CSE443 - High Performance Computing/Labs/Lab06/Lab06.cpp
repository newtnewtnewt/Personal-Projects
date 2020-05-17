//
// Code to demonstrate basic program parallelization using OpenMP
// Copyright 2018, Alan M. Ferrenberg
//


#include <iostream>
#include <cmath>
#include "omp.h"
#define SIZE 10000000

using namespace std;

int main() {
  double *sinTable;

  sinTable = new double[SIZE];

  // Initialize the array.  Though this could be done in parallel, leave it
  // serial to add some non-parallel work to the code  
  for (int i = 0; i < SIZE; i++) {
    sinTable[i] = sin(2*M_PI*i/SIZE);
  }

  // Run a few iterations in serial, just to add more non-parallel work
  for (int j = 0; j < 40; j++) {
    for (int i = 0; i < SIZE; i++) {
      sinTable[i] = sin(2*M_PI*sinTable[i]);
    }
  }

  // Run iterations in parallel using OpenMP
  for (int j = 0; j < 160; j++) {
    #pragma omp parallel for
    for (int i = 0; i < SIZE; i++) {
      sinTable[i] = sin(2*M_PI*sinTable[i]);
    }
  }

  // Print out one element to check that it is correct. It should be 0.0244013
  cout << "sinTable[322] = " << sinTable[322] << endl;

  return 0;
}

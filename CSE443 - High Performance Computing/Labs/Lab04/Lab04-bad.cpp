/*
 * Copyright 2019 Alan M. Ferrenberg
 */

/* 
 * File:   Lab03-bad.cpp
 * Author: Alan Ferrenberg
 *
 * Created on September 10, 2019, 1:23 PM
 */

#include <cstdlib>
#include <string>
#include <iostream>
#include <cmath>
#define ROWS 1000
#define COLS 1000

double matrix1[ROWS][COLS], matrix2[ROWS][COLS];

using namespace std;

int main(int argc, char** argv) {
  int i, j;
  double norm = 1.0/static_cast<double>(RAND_MAX);
  unsigned int seed = 123456789;
  
  srand(seed);

  for (j = 0; j < ROWS; j++) {
    for (i = 0; i < COLS; i++) {
      matrix1[i][j] = norm*static_cast<double>(rand_r(&seed));
      matrix2[i][j] = norm*static_cast<double>(rand_r(&seed));
    }
  }

  for (int k = 0; k < 30000; k++) {
    for (j = 0; j < ROWS; j++) {
      for (i = 0; i < COLS; i++) {
        matrix1[i][j] = matrix1[i][j]*matrix2[i][j] + 0.5;
      }
    }
  }

  cout << matrix1[34][12] << endl;
  
  return 0;
}


// Copyright 2020 Alan M. Ferrenberg

// File:   Lab01.cpp
// Author: Alan Ferenberg
// Created on September 16, 2019
// Modified on January 21, 2020 

#include <cstdlib>
#include <string>
#include <iostream>
#include <cmath>
#include <vector>

int main(int argc, char** argv) {
  double normalization; int result1, result2, result3;
  unsigned int seed = time(NULL);
  std::vector<int> counts(16);

  for(int i = 0; i < 1000; i++){
	  // Used to normalize the random numbers to the range 0..1
	  normalization = 1.0/static_cast<double>(RAND_MAX);

	  // Initialize the random number generator
	  srand(seed);

	  // Generate a random number
	  result1 = static_cast<int>((6 * normalization*rand_r(&seed))) + 1;
	  result2 = static_cast<int>((6 * normalization*rand_r(&seed))) + 1;
	  result3 = static_cast<int>((6 * normalization*rand_r(&seed))) + 1;
	  counts[result1 + result2 + result3 - 3]++;
  }

  // Write out the random number
  for(int i = 0; i < counts.size(); i++){
  	std::cout << counts[i] << std::endl;
  }
  return 0;
}


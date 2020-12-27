#include <omp.h>
#include <iostream>
#include <string>
#include <cstdlib>
#include <cmath>


// Copyright 2019 by Alan M. Ferrenberg
// This program is offered freely for any academic use

using std::stoi;
using std::stoul;
using std::cout;
using std::endl;

// Each thread will have its own copy of the random number state which must
// persist throughout the program.  Make this state be a global variable and
// define it as threadprivate

struct drand48_data state;
#pragma omp threadprivate(state)

// Method to initialize the random number states for each thread.  Each
// thread gets a unique seed based on the single seed provided as an argument
void initRNG(unsigned int seed) {
#pragma omp parallel shared(seed)
  {
    int k = omp_get_thread_num();

    // Set the initial seed and state with the thread-safe version
    srand48_r(seed+23*k, &state);
  }
  return;
}

// This is a test method that shows how to generate a random number.
double testme(int N) {
  double rng, sum = 0.0;

#pragma omp parallel for  shared(N) private(rng) reduction(+:sum)
  for (int i = 0; i < N; ++i) {
    drand48_r(&state, &rng);
    sum += rng;
  }

  return sum;
}

// Main program
int main(int argc, char* argv[]) {
  // For testing:  N is the number of random numbers to generate
  int N = stoi(argv[1]);
  // The random number generator seed used to generate the RNG state
  unsigned int seed = stoul(argv[2]);

  // Initialize the random number generator
  initRNG(seed);

  // Generate N random numbers and print them out.
  double sum = testme(N);

  cout << "Sum = " << sum << endl;
  cout << "If you run this with 4 threads, N=40 and seed=1234567, sum ";
  cout << "should be 19.4018";
  cout << endl;

  return 0;
}

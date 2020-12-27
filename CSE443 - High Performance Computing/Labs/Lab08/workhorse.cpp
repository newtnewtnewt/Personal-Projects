#include <cmath>
#include <iostream>
#include <iomanip>

// Copyright 2020 Alan M. Ferrenberg

long double workhorse(long int n) {
	
  long double x0 = 0.0, x1 = 0.0, x2 = 0.0, x3 = 0.0, x4 = 0.0;
  long double x5 = 0.0, x6 = 0.0, x7 = 0.0, x8 = 0.0, x9 = 0.0;
  long double x10 = 0.0, x11 = 0.0, x12 = 0.0, x13 = 0.0;
  #pragma omp parallel for reduction(+:x0, x2, x4, x6, x8, x10, x12)
  for (long int i0 = 0; i0 < n; i0++) {
	x0 += 0.5*cosl(static_cast<long double>(i0)/23000.0);
	x2 += 0.5*tanl(sinl(static_cast<long double>(i0)/23000.0));
	x4 += 0.5*cosl(tanl(static_cast<long double>(i0)/23000.0));
	x6 += 0.5*cosl(cosl(static_cast<long double>(i0)/23000.0));   
	x8 += 0.5*sinl(sinl(static_cast<long double>(i0)/23000.0));    
	x10 += 0.5*tanl(tanl(static_cast<long double>(i0)/23000.0)); 
	x12 += 0.5*sinl(static_cast<long double>(i0)/23000.0);	
  }
  
  #pragma omp parallel for reduction(+:x1, x3, x5, x7, x9, x11, x13)
  for (long int i1 = 0; i1 < n; i1++) {
	x1 += 0.5*sinl(x0*i1/23000.0);
	x3 += 0.5*tanl(cosl(x2*i1/23000.0));
	x5 += 0.5*tanl(sinl(x4*i1/23000.0));
	x7 += 0.5*cosl(cosl(x6*i1/23000.0));
	x9 += 0.5*sinl(sinl(x8*i1/23000.0));
	x11 += 0.5*tanl(sinl(x10*i1/23000.0));
	x13 += 0.5*cosl(x12*i1/23000.0);
  } 
  
  return x1 + x3 + x5 + x7 + x9 + x11 + x13;
}

int main(int argc, char *argv[]) {
  // Make sure one command line argument is provided
  if (argc < 2) {
    std::cerr << std::endl << "Usage: workhorse n";
    std::cerr << std::endl << std::endl;    
    exit(1);
  }

  // Size of iteration
  long int n = std::stoi(argv[1]);

  // Don't allow a negative loop size
  if (n < 0) {
    std::cerr << std::endl << "n must be >= 0" << std::endl << std::endl;
    exit(2);
  }
  
  // Invoke workhorse and write out the results
  std::cout << std::endl;
  std::cout << "workhorse(" << n << ") is " << std::setprecision(10);
  std::cout << workhorse(n) << std::endl;
  std::cout << std::endl;

  exit(0);
}

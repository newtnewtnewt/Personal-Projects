#include <cmath>
#include <iostream>
#include <iomanip>

// Copyright 2020 Alan M. Ferrenberg

long double workhorse(long int n) {
  long double x0 = 0.0, x1 = 0.0, x2 = 0.0, x3 = 0.0, x4 = 0.0;
  long double x5 = 0.0, x6 = 0.0, x7 = 0.0, x8 = 0.0, x9 = 0.0;
  long double x10 = 0.0, x11 = 0.0, x12 = 0.0, x13 = 0.0; 
  #pragma omp task shared(x0, x1)
  {
	  for (long int i0 = 0; i0 < n; i0++) {
		x0 += 0.5*cosl(static_cast<long double>(i0)/23000.0);
	  }

	  for (long int i1 = 0; i1 < n; i1++) {
		x1 += 0.5*sinl(x0*i1/23000.0);
	  }
  }

  #pragma omp task shared(x2, x3)
  {
	  for (long int i2 = 0; i2 < n; i2++) {
		x2 += 0.5*tanl(sinl(static_cast<long double>(i2)/23000.0));
	  }

	  for (long int i3 = 0; i3 < n; i3++) {
		x3 += 0.5*tanl(cosl(x2*i3/23000.0));
	  }
  }
  

  #pragma omp task shared(x4, x5)
  {
	  for (long int i4 = 0; i4 < n; i4++) {
		x4 += 0.5*cosl(tanl(static_cast<long double>(i4)/23000.0));
	  }

	  for (long int i5 = 0; i5 < n; i5++) {
		x5 += 0.5*tanl(sinl(x4*i5/23000.0));
	  }
  }
  
  
  #pragma omp task shared(x6, x7)
  {
	  for (long int i6 = 0; i6 < n; i6++) {
		x6 += 0.5*cosl(cosl(static_cast<long double>(i6)/23000.0));    
	  }

	  for (long int i7 = 0; i7 < n; i7++) {
		x7 += 0.5*cosl(cosl(x6*i7/23000.0));
	  }
  }
  
  #pragma omp task shared(x8, x9)
  {  
	  for (long int i8 = 0; i8 < n; i8++) {
		x8 += 0.5*sinl(sinl(static_cast<long double>(i8)/23000.0));    
	  }

	  for (long int i9 = 0; i9 < n; i9++) {
		x9 += 0.5*sinl(sinl(x8*i9/23000.0));
	  }
  }
  
  #pragma omp task shared(x10, x11)
  {
	  for (long int i10 = 0; i10 < n; i10++) {
		x10 += 0.5*tanl(tanl(static_cast<long double>(i10)/23000.0));    
	  }

	  for (long int i11 = 0; i11 < n; i11++) {
		x11 += 0.5*tanl(sinl(x10*i11/23000.0));
	  }
  }
  
  #pragma omp task shared(x12, x13)
  {
	  for (long int i12 = 0; i12 < n; i12++) {
		x12 += 0.5*sinl(static_cast<long double>(i12)/23000.0);
	  }
	 
	  for (long int i13 = 0; i13 < n; i13++) {
		x13 += 0.5*cosl(x12*i13/23000.0);
	  }  
  }
  #pragma omp taskwait
  {return x1 + x3 + x5 + x7 + x9 + x11 + x13;}
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

  #pragma omp parallel
	{
	#pragma omp single 
		{
		std::cout << workhorse(n) << std::endl;
		}
	}
  std::cout << std::endl;

  exit(0);
}

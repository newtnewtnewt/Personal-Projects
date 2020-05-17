// Copyright 2020 by Noah Dunn
// This program is offered freely for any academic use
// Credit to Dr. Alan Ferrenberg for most of the shell of this code
#include <omp.h>
#include <iostream>
#include <string>
#include <cstdlib>
#include <cmath>

using std::stoi;
using std::stoul;
using std::cout;
using std::endl;

/* Initialization function provided by Dr. Ferrenberg.
 * Establishes all real values for the sin function, with potential for 
 * modification for an imaginary function
 */
void initialize(int N, double oR[], double oI[]) {
  for (int n = 0; n < N; n++) {
    //  The basic sin function
    double t = 2.0*M_PI*(n - N/2);
    oR[n] = sin(100.00*t/N);
    oI[n] = 0.0;
  }
}
void discreteFourierTransform(int N, double rNums[], double iNums[],
        double oRNums[], double oINums[]) {
    #pragma omp parallel for
    for (int k = 0; k < N; k++) {
        //  The total real and imaginary value created from N runs
        double totalReal = 0.0;
        double totalImaginary = 0.0;
        for (int n = 0; n < N; n++) {
            //  The real and imaginary components of the first number
            double r1 = rNums[n]; 
            double i1 = iNums[n];
            //  The real and imaginary componetns of the generated number
            double r2 = cos((2.0 * M_PI)/N * (k * n));
            double i2 = -sin((2.0 * M_PI)/N * (k * n));
            //  Multiply and add to achieve the transform
            totalReal += r1 * r2;
            totalImaginary += r1 * i2;
            totalImaginary += r2 * i1;
            totalReal += -1 * (i1 * i2);
        }
        oRNums[k] = totalReal;
        oINums[k] = totalImaginary;
    }
}

int main(int argc, const char* argv[]) {
    //  We only want the seed provided to this program
    if (argc != 2) {
        cout << "Input a number of runs" << endl;
    } else {       
        const int N = stoi(argv[1]);
        double realNums[N], imaginaryNums[N], oRNums[N], oINums[N];
        initialize(N, realNums, imaginaryNums);
        discreteFourierTransform(N, realNums, imaginaryNums, oRNums, oINums);
        for (int i = 0; i < N; i++) {
            //  Print the real and imaginary components of the kth result
            cout << "k = " << i << " : " << oRNums[i] << " + " << 
                    oINums[i] << "i" << endl;
        }
    }
}


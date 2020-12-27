// Copyright 2020 by Noah Dunn
// This program is offered freely for any academic use
// Credit to Dr. Alan Ferrenberg for most of the shell of this code
#include <omp.h>
#include <iostream>
#include <string>
#include <cstdlib>
#include <cmath>

#define RUNLENGTH 20000000
#define N 100

using std::stoi;
using std::stoul;
using std::cout;
using std::endl;

struct drand48_data state;

void doBoxMuller(double *ave, double *stdDev, double mu, double sigma) {
    double totalAverage = 0.0;
    double totalSumOfSquares = 0.0;
    // Generates the average and stdDev of RUNLENGTH numbers
    for (int i = 0; i < RUNLENGTH; i += 2) {
        double U1 = 0.0;
        drand48_r(&state, &U1);
        double U2 = 0.0;
        drand48_r(&state, &U2);
        double G1 = sqrt(-2 * log(U1)) * cos(2 * M_PI * U2);
        double G2 = sqrt(-2 * log(U1)) * sin(2 * M_PI * U2);
        G1 = mu + sigma * G1;
        G2 = mu + sigma * G2;
        totalAverage += G1 + G2;
        totalSumOfSquares += (G1 * G1) + (G2 * G2);
    }
    //  Divide by the number of runs
    totalAverage /= RUNLENGTH;
    totalSumOfSquares /= RUNLENGTH;
    
    // Save value to the pointer
    *ave = totalAverage;
    //  Calculate the correct square root value
    *stdDev = sqrt(totalSumOfSquares - (totalAverage * totalAverage));
}



int main(int argc, const char* argv[]) {
    //  We only want the seed provided to this program
    if (argc != 2) {
        cout << "Provide the seed to this program" << endl;
    } else {
        //  The seed the user provided
        unsigned int seed = stoi(argv[1]);
        //  Initialize the random number generator
        srand48_r(seed, &state);
        // Execute the test run
        double average; 
        double stdDev;
        for (int i = 0; i < N - 1; i++) {
            doBoxMuller(&average, &stdDev, 42.0, 5.0); 
            cout << "Test Run: " << i << " " << 
                    "Avg: " << (average) << " StdDev: " << (stdDev) << endl;
        }
    }
}


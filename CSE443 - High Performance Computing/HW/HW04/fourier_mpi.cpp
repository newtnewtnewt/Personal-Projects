#include <mpi.h>
#include <cmath>
#include <iostream>
#include <iomanip>
#include <fstream>
// Copyright 2020 Noah Dunn

using std::cerr;
using std::cout;
using std::endl;
using std::stoi;

#define ZERO 0.0000000000000000000000000000000000
#define MPI_WTIME_IS_GLOBAL 1
// Compute the discrete Fourier transform of the function provided

void computeFT(int N, double oR[], double oI[], double* fR, double* fI, int k) {
    int n;
    double tempR, tempI;
    // Loop over the N frequency values
    tempR = ZERO;
    tempI = ZERO;
    // Loop over the N spatial/temporal values
    for (n = 0; n < N; n++) {
        double arg = 2.0 * M_PI * k * n / N;
        double cosArg = cos(arg);
        double sinArg = sin(arg);
        // Accumulate the real and imaginary components of the Fourier transform
        // for frequency k in temporary variables
        tempR += oR[n] * cosArg + oI[n] * sinArg;
        tempI += oI[n] * cosArg - oR[n] * sinArg;
    }
    // Update the values of for the real and imaginary components of the
    // Fourier transform
    *fR = tempR;
    *fI = tempI;
}

// Initialize the real and imaginary components of the original function and
// the Fourier transform.  The function is sinc(x) = sin(ax)/x

void initialize(int N, double oR[], double oI[], double fR[], double fI[]) {
    double a = 2.0;
    oR[0] = a;
    oI[0] = ZERO;
    fR[0] = ZERO;
    fI[0] = ZERO;

    for (int n = 1; n < N; n++) {
        oR[n] = sin(a * n) / n;
        oI[n] = ZERO;
        fR[n] = ZERO;
        fI[n] = ZERO;
    }
}

void initializeMPI(int argc, char *argv[], int *numTasks, int *rank) {
    int value;
    // Initialize MPI for this program
    if ((value = MPI_Init(&argc, &argv)) != 0) {
        std::cerr << "Problem with MPI_Init" << std::endl;
        exit(value);
    }
    // Get the number of processes, exit if there aren't 2
    if ((value = MPI_Comm_size(MPI_COMM_WORLD, numTasks)) != 0) {
        std::cerr << "Problem with MPI_Comm_size" << std::endl;
        exit(value);
    }
    if (*numTasks < 2) {
        std::cerr << "Program is designed to run with 2 or more tasks"
                << std::endl;
        exit(3);
    }
    // Get my rank
    if ((value = MPI_Comm_rank(MPI_COMM_WORLD, rank)) != 0) {
        std::cerr << "Problem with MPI_Comm_rank" << std::endl;
        exit(value);
    }
}
// Check the number of arguments, returning 1 if there's a problem

void processArgv(int argc, char *argv[], int *N) {
    //  Check to make sure there is exactly one CLA (N)
    if (argc < 2) {
        cerr << "Correct number of arguments not supplied (Need N)" << endl;
        exit(1);
    } else {
        *N = stoi(argv[1]);
    }
}

void worker(int N, int rank) {
    //  Two arrays that house all our real and imaginary values
    double *oR = new double[N], *oI = new double[N];
    //  Values we are going to use further on
    double real, imaginary, CPU, totalCPU;
    //  Create and start the CPU clock tracker, establish k
    clock_t t1;
    int k = 0;
    t1 = clock();
    // Receive the non-transformed arrays from the manager
    MPI_Bcast(oR, N, MPI_DOUBLE, 0, MPI_COMM_WORLD);
    MPI_Bcast(oI, N, MPI_DOUBLE, 0, MPI_COMM_WORLD);
    //  While the worker has not been sent the k-signal
    while (k >= 0) {
        //  Receive the k-value from manager
        MPI_Recv(&k, 1, MPI_INT, 0, rank, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        //  With no kill signal
        if (k >= 0) {
            //  Compute the DFT and send both parts back to manager
            computeFT(N, oR, oI, &real, &imaginary, k);
            MPI_Send(&real, 1, MPI_DOUBLE, 0, k, MPI_COMM_WORLD);
            MPI_Send(&imaginary, 1, MPI_DOUBLE, 0, k, MPI_COMM_WORLD);
        }
    }
    //  Determine the difference between the current CPU clock 
    //  And the CPU clock before starting this process
    t1 = clock() - t1;
    //  Grab the actual value of CPU time
    CPU = static_cast<double> (t1) / static_cast<double> (CLOCKS_PER_SEC);
    //  Send the total to be aggregate with all other processes
    MPI_Reduce(&CPU, &totalCPU, 1, MPI_DOUBLE, MPI_SUM, 0, MPI_COMM_WORLD);
    //  Delete the allocated memory
    delete [] oR;
    delete [] oI;
}

void managerHelper(double *oR, double *oI, double *fR, double *fI, double N,
        int numTasks) {
    //  Variables used to get our Fourier Transform 
    int finishNum = 0, k = 0, endSignal = -1; double rV = 0, iV = 0;
    //  Send the first set of k-values
    for (int rank = 1; rank < numTasks; rank++) {
        int kIn = k + rank - 1;
        MPI_Send(&kIn, 1, MPI_INT, rank, rank, MPI_COMM_WORLD);
    }
    // Until we are actually finished
    while (finishNum < N) {
        //  Grab the real and imaginary components from worker
        MPI_Status status;
        MPI_Recv(&rV, 1, MPI_DOUBLE, MPI_ANY_SOURCE, MPI_ANY_TAG,
                MPI_COMM_WORLD, &status);
        MPI_Recv(&iV, 1, MPI_DOUBLE, status.MPI_SOURCE, status.MPI_TAG,
                MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        // Store them, increment k and finishNum
        fR[status.MPI_TAG] = rV; fI[status.MPI_TAG] = iV;
        k++; finishNum++;
        // If the manager is not finished yet
        if (finishNum < N) {
            // Grab the new k value
            int kIn2 = k + numTasks - 2;
            // Send it to a worker
            MPI_Send(&kIn2, 1, MPI_INT, status.MPI_SOURCE, status.MPI_SOURCE,
                    MPI_COMM_WORLD);
        } 
    }
    //  Send the kill signals when the manager is done
    for (int i = 1; i < numTasks; i++) {
        MPI_Send(&endSignal, 1, MPI_INT, i, i, MPI_COMM_WORLD);
    }
}

void manager(int N, double *elapsedT, double *cpuT, int numTasks) {
    // Declare pointers to the Function and fourier transform arrays
    double *oR, *oI, *fR, *fI, CPU, totalCPU, eStart, eEnd;
    // Create the CPU clock
    clock_t t1;
    // Allocate arrays for the function and its Fourier transform.  
    // This puts them in the heap, not the stack.
    oR = new double[N]; oI = new double[N];
    fR = new double[N]; fI = new double[N];

    // Initialize the original function and its Fourier transform
    initialize(N, oR, oI, fR, fI);
    eStart = MPI_Wtime();
    t1 = clock();
    //  Send the initialized versions of the arrays to the workers
    MPI_Bcast(oR, N, MPI_DOUBLE, 0, MPI_COMM_WORLD);
    MPI_Bcast(oI, N, MPI_DOUBLE, 0, MPI_COMM_WORLD);
    // Call helper method
    managerHelper(oR, oI, fR, fI, N, numTasks);


    // Write out the real and imaginary components of the Fourier transform
    for (int k = 0; k < N; k++) {
        cout << k << " " << fR[k] << " " << fI[k] << endl;
    }
    //  Grab elapsed CPU time
    t1 = clock() - t1;
    //  Convert it to actual CPU time
    CPU = static_cast<double> (t1) / static_cast<double> (CLOCKS_PER_SEC);
    //  Combine Manager's CPU workload with workers' CPU workload
    MPI_Reduce(&CPU, &totalCPU, 1, MPI_DOUBLE, MPI_SUM, 0, MPI_COMM_WORLD);
    //  Grab the end elapsed time value 
    eEnd = MPI_Wtime();
    //  Stash the values in the passed-in pointers
    *elapsedT = (eEnd - eStart);
    *cpuT = totalCPU;
    // Free up the memory on the heap
    delete [] oR;
    delete [] oI;
    delete [] fR;
    delete [] fI;
}

// Main method for this program

int main(int argc, char *argv[]) {
    int N, numTasks, rank;
    processArgv(argc, argv, &N);
    initializeMPI(argc, argv, &numTasks, &rank);
    if (rank == 0) {
        // If we are the manager, begin task allotment
        double elapsedTime, cpuTime;
        manager(N, &elapsedTime, &cpuTime, numTasks);
        // Print values once everything is finished
        cerr << elapsedTime << " ";
        cerr << cpuTime << endl;
    } else {
        // If we are the worker, begin getting tasks from manager
        worker(N, rank);
    }
    MPI_Finalize();
    // Exit!
    exit(0);
}

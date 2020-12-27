//  A programmatic approach to the collatz conjecture



void setup(){
  println(escapes(1));
}


float j(float x){
  return x * x; 

}

//  x escapes if it exceeds 10 
boolean escapes(float x){
    for(int i = 0; i < 50; i++){
      if (x > 10) return true;
      else { x = j(x); }
    }
    return false;
}

// If we reach here, then we didn't escape.
// Maybe we would have if we tried a few more times, but
// we had to try a fixed number of times and test a specified value



//  iterating this will result in 
//  an estimate of the sqrt(2). using Newton's method
void newtonsMethodExploration(){
  float x = 20;
  for (int i = 0; i < 30; i++){
    println(x);
    x = g(x);
  }

}

float g(float x){
  //  The formula for Newton's method for approximating intersections to the x-axis
  return x/2 + 1/x;
}

void collatzExploration() {
  int maxSteps = 0;
  int which = 1;
  for(int i = 1; i < 1000000; i++){
    int wc = wondrousCount(i);
    if (wc > maxSteps){
      maxSteps = wc; 
      which = i;
    }
  }
  println(which, maxSteps);
}


//  A number is wondrous if it eventually
//  reaches 1 using f as defined below.


// loop (iterative) version of the method below

// Our code will say: if we still haven't reached
// 1 after 100 iterations, then stop and return false;
boolean isWondrous(int x){
    int attempts = 0;
    //  repeatedly call f(x) until we reach 1
    while(x != 1 ){
       x = f(x);  //  A key line in iterated functions
       print(x + " ");
       attempts++;
       if(attempts == 1000){
         return false;
       }
    }
    return true;
}

//  Assuming collatz's conjecture is true
//  (all numbers are wondrous)
int wondrousCount(int x){
  int attempts = 0;
    //  repeatedly call f(x) until we reach 1
    while(x != 1 ){
       x = f(x);  //  A key line in iterated functions
       attempts++;
       if(attempts == 1000){
         return 1000;
       }
    }
    return attempts;
}


//  The recursive approach
/**
boolean isWondrous(int x){
  if(x == 1) return true;
  else return isWondrous(f(x));
}
**/ 

//  The function behind "wondrous numbers"
int f(int x){
  return x % 2 == 0 ? x/2 : 3*x+1;
}

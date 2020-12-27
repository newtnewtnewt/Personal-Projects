import java.math.*;
import java.math.RoundingMode;

void setup(){
  kochResults();
  println();
  harmonicResults();
}

public static void kochResults(){
  for(int i = 0; i < 21; i++){
    double area = kochArea(i);
    double perimeter = kochPerimeter(i);
    System.out.printf("%2d %15.5f %15.5f%n", i, area, perimeter);
  }
}

public static void harmonicResults(){
  for(int i = 1; i < 21; i++){
    BigDecimal value = harmonicSum(i, 20);
    System.out.printf("%2d %25.20f%n", i, value);
  }
}

public static double kochArea(int n){
  double totalArea = 0;
  double currentArea = 27;
  double currentNumOfTriangles = 1;
  for(int i = 0; i < n; i++){
    if(i == 0){
      totalArea += (Math.pow(currentArea, 2.0) * currentNumOfTriangles);
      currentArea = currentArea / 3.0;
      currentNumOfTriangles *= 3;
    }
    else{
      totalArea += (Math.pow(currentArea, 2.0) * currentNumOfTriangles);
      currentArea = currentArea / 3.0;
      currentNumOfTriangles *= 4;
    }
  }
  return (totalArea += (Math.pow(currentArea, 2.0) * currentNumOfTriangles));
  
  
}
public static double kochPerimeter(int n){
  //  Note this process is identical to 27 / 
  double totalPerimeter = 0;
  double sidePerimeter = 27;
  double numOfLengths = 3;
  for(int i = 0; i < n; i++){
    numOfLengths *= 4;
    sidePerimeter = sidePerimeter / 3.0;
  }
  return (totalPerimeter += sidePerimeter * numOfLengths);

}

public static BigDecimal harmonicSum(int n, int places){
  BigDecimal numerator = new BigDecimal(1);
  numerator.setScale(50, RoundingMode.DOWN);
  BigDecimal runningTotal = new BigDecimal(0);
  for(int i = 0; i < n; i++){
       BigDecimal currentDenom = new BigDecimal(i + 1);
       currentDenom.setScale(50, RoundingMode.DOWN);
       runningTotal = runningTotal.add(numerator.divide(currentDenom, 50, RoundingMode.DOWN));
  }
  runningTotal = runningTotal.setScale(places, RoundingMode.DOWN);
  return runningTotal;
}

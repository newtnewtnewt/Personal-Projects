using System;

namespace HW4 {
	public class LTMatrix {
		int LengthVal;
		int[] matrixVals;
		int SizeVal;
		//  A constructor that creates a N x N Size triangular Matrix, with @param N
		public LTMatrix(int N){			
			if(N <= 0){
				throw new Exception("N must be a value greater than 0");
			}
			LengthVal = (N * N + N) / 2;
			matrixVals = new int[LengthVal];
			SizeVal = N; 
		}
		public int Length() {
			return LengthVal;
		}
		public int Size() {
			return SizeVal;
		}
		public int[] OneDArray() {
			return matrixVals;
		}
		public int TwoDToOneDIndex(int row, int column){
			if(row < 0 || column < 0 || row > SizeVal - 1 || column > SizeVal - 1) {
				throw new Exception("Row and Column must both be within the range 0 to N - 1");
			}
			else if(column > row) {
				throw new ArgumentException("Index is out of bounds of the lower triangle");
			}
			else {
				return (row * row + row)/2 + column;
			}
		
		}
		public void Set(int row, int column, int val) {
			try {
				int index = TwoDToOneDIndex(row, column);
				matrixVals[index] = val; 
			#pragma warning disable 168
			} catch (ArgumentException e) {
				throw new ArgumentException("User attempted to modify index outside the bounds of the triangle");
			} catch (Exception e){
				throw new Exception("Row and Column must both be within the range 0 to N - 1");
			}
		}
		public int Get(int row, int column){
			try	{
				int index = TwoDToOneDIndex(row, column);
				return matrixVals[index];
			} catch (ArgumentException e){
				return 0;
			#pragma warning disable 168
			} catch (Exception e){
				throw new Exception("Row and Column must both be within the range 0 to N - 1");
			}
		}
		public static LTMatrix operator +(LTMatrix current, LTMatrix other){
			int totalLength = current.Length();
			LTMatrix resultMatrix = new LTMatrix(current.Size());
			#pragma warning disable 168
			if(totalLength != other.Length()){
				throw new Exception("The two matrices must be the same Size to perform addition");
			}
			for(int i = 0; i < totalLength; i++) {
				resultMatrix.OneDArray()[i] = current.matrixVals[i] + other.matrixVals[i];
			}
			return resultMatrix;
		}			
		/*
		public static void Main(string[] args){
			LTMatrix m1 = new LTMatrix(4);
			LTMatrix m2 = new LTMatrix(4);
			LTMatrix m3 = new LTMatrix(5);
			//Console.WriteLine(m1.TwoDToOneDIndex(-1, 0));      // This will break
			//Console.WriteLine(m1.TwoDToOneDIndex(0, -1));      // This will break
			//Console.WriteLine(m1.TwoDToOneDIndex(3, 5));       // This will break
			//Console.WriteLine(m1.TwoDToOneDIndex(4, 3));       // This will break
			m1.Set(1, 1, 1);
			m1.Set(0, 0, 1);
			m2.Set(2, 2, 1);
			m2.Set(1, 1, 2);
			Console.WriteLine(m1.Get(1, 2));
			//m1.Set(4, 4, 5);      // This will break
			//Console.WriteLine(m1.Get(4, 4)); //This should error out
			Console.WriteLine(string.Join("," , m1.OneDArray()));
			Console.WriteLine(string.Join("," , m2.OneDArray()));
			Console.WriteLine(string.Join(",",  (m1 + m2).OneDArray()));
					
			
		}
		*/
		
		
	}
}
class ltMatrix:

	def __init__(self, N):
		try:
			if N <= 0:   # Matrices need to store something 
				raise ValueError('N must be a value greater than 0')
			elif not isinstance(N, int):  # We aren't making 'partial' matrices here
				raise ValueError('N must be an integer') 
			else:
				self.lengthVal = int(((N * N) + N) / 2)     # The sum of an arithmetic series from 1 to N 
				self.matrixVals = [0] * self.lengthVal      # The size of the 1D matrix should be the same
				self.sizeVal = int(N)		                # The array is technically an N * N
		except ValueError:
			raise
			
	def length(self):
		return self.lengthVal  #  Returns the number of elements the array can hold
		
	def size(self):              
		return self.sizeVal    #  Returns the N for an N by N matrix
		
	def oneDArray(self):
		return self.matrixVals
		
	def twoDToOneDIndex(self, row, column):  # Given a row and a column, returns the 1D index
		try:
			if row < 0 or column < 0 or row > self.sizeVal - 1 or column > self.sizeVal - 1: # We need to be in the range
				raise ValueError('Row and Column both must be within the Range 0 to N - 1')
			elif column > row:  # We can't call out of bounds, raise a different error to be caught later
				raise LookupError('Index is out of bounds of the lower triangle')
			elif not isinstance(row, int) or not isinstance(column, int):  # Make sure we are using the right data type
				raise ValueError('Row and Column both must be integers')
			else:
				return int((row * row + row)/2 + column)  # Note that this is just the summation formula written differently 
		except ValueError:
			raise
			
	def set(self, row, column, val):  #  Set a value inside the triangular matrix
		try:
			if not(isinstance(val, int)): # Make sure our val's data type is correct
				raise ValueError('Value must be an int, a float, or a double')
			index = self.twoDToOneDIndex(row, column)  #  All Exceptions will be handled in this method, but we provide additional exception catching when trying to get index
			self.matrixVals[index] = val  # Change the value
		except LookupError:
			print('User attempted to modify index outside the bounds of the triangle')
			raise
			
	def get(self, row, column):
		try:
			index = self.twoDToOneDIndex(row, column) # Errors will be thrown if the index is outside the N x N MAtrix
			return self.matrixVals[index]
		except LookupError: # If the value is not in the triangle but inside the N x N, it is 0
			return 0;
			
	def __add__(self, other):  # Returns the addition of two ltMatrices
		try:
			if(self.length() == other.length()):
				additionMatrix = ltMatrix(self.size())
				for x in range(self.length()):
					additionMatrix.oneDArray()[x] = self.oneDArray()[x] + other.oneDArray()[x]
				return additionMatrix
			else:
				raise Exception('The triangle arrays are not of the same size') 
		except Exception:
			raise
		

if __name__ == "__main__":
	m1 = ltMatrix(4)
	m2 = ltMatrix(4)
	m3 = ltMatrix(5)
	#m2 = ltMatrix(0) # This will cause errors
	#m3 = ltMatrix(1.2) # This will cause errors
	print(m1.length())
	print(m1.size())
	print(m1.oneDArray())
	print(m1.twoDToOneDIndex(0, 0))
	print(m1.twoDToOneDIndex(1, 1))
	print(m1.twoDToOneDIndex(2, 0))
	print(m1.twoDToOneDIndex(3, 0))
	#print(m1.twoDToOneDIndex(-1, 0)) # This will cause errors
	#print(m1.twoDToOneDIndex(0, -1)) # This will cause errors
	#print(m1.twoDToOneDIndex(3, 5))  # This will cause errors
	#print(m1.twoDToOneDIndex(4, 3))  # This will cause errors
	m1.set(1, 1, 1)
	m1.set(0, 0, 1)
	m2.set(2, 2, 1)
	m2.set(1, 1, 2)
	print(m1.get(1, 2))
	#m1.set(4, 4, 5)      # This will break
	#print(m1.get(4, 4)) # This should error out
	print(m1.oneDArray())
	print(m2.oneDArray())
	print((m1 + m2).oneDArray())
	
	
	

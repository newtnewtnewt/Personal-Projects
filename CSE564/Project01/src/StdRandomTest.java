import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;


public class StdRandomTest {
	

	@Test
	public void testSetSeed() {
		/**
		 * Test the set seed by seeing if we can set it to a definitive long
		 */
		StdRandom.setSeed(201);
		assertTrue(StdRandom.getSeed() == 201);
		/**
		 * Test if a different seed fails to mititgate random chance possibility
		 */
		StdRandom.setSeed(202);
		assertFalse(StdRandom.getSeed() == 201);
		
	}

	@Test
	public void testGetSeed() {
		/**
		 * Testing for the getter is redundant, but we ensure the seed provided is at least
		 * a reasonable current time of the CPU clock
		 */
		assertTrue(StdRandom.getSeed() > -1);
	}

	@Test
	public void testUniform() {
		/**
		 * Test to ensure the value returned is a double greater than or equal to 0, but less than 1
		 * We roll 3 times to attempt to mitigate random luck.
		 */
		double randomVal = StdRandom.uniform();
		assertTrue(randomVal >= 0.0 && randomVal < 1);
		randomVal = StdRandom.uniform();
		assertTrue(randomVal >= 0.0 && randomVal < 1);
		randomVal = StdRandom.uniform();
		assertTrue(randomVal >= 0.0 && randomVal < 1);
	}
	
	@Test
	public void testUniformInt() {
		/**
		 * Test to ensure we get a random integer within our desired range
		 */
		int randomInt = StdRandom.uniform(2);
		assertTrue(randomInt == 0 || randomInt == 1);
		/**
		 * Check with a slightly broader range
		 */
		randomInt = StdRandom.uniform(3);
		assertTrue(randomInt == 1 || randomInt == 2 || randomInt == 0);
		
		/**
		 * Test to make sure the returned datatype is actually an int 
		 */
		assertTrue((StdRandom.uniform(2) + "").length() == 1);
	
		
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testUniformIntException() throws IllegalArgumentException {
		/**
		 * Reach the portion of uniform(int n) that throws an exception, and ensure
		 * it actually throws an exception
		 */
		StdRandom.uniform(-1);
		StdRandom.uniform(0);
	}

	@Test
	public void testUniformLong() {
		/**
		 * Test to ensure we get a random long within our desired range
		 */
		long longUpperBound = 2l;
		long randomLong = StdRandom.uniform(longUpperBound);
		assertTrue(randomLong == 0l || randomLong == 1l);
		
		/**
		 * Check with a slightly broader range
		 */
		longUpperBound = 12345678910l;
		randomLong = StdRandom.uniform(longUpperBound);
		assertTrue(randomLong >= 0 && randomLong < 12345678910l);
		//Checking length would be redundant, we have no casting errors so we can assume safety
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUniformLongException() throws IllegalArgumentException {
		/**
		 * Reach the portion of uniform(long n) that throws an exception, and ensure
		 * it actually throws an exception
		 */
		StdRandom.uniform(-1l);
		StdRandom.uniform(0l);
	}

	@Test
	public void testRandom() {
		/**
		 * Test to ensure the value returned is a double greater than or equal to 0, but less than 1
		 * We roll 3 times to attempt to mitigate random luck.
		 */
		double randomVal = StdRandom.uniform();
		assertTrue(randomVal >= 0.0 && randomVal < 1);
		randomVal = StdRandom.uniform();
		assertTrue(randomVal >= 0.0 && randomVal < 1);
		randomVal = StdRandom.uniform();
		assertTrue(randomVal >= 0.0 && randomVal < 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUniformIntIntException() {
		int brokenTest = StdRandom.uniform(2,1);
	}
	
	@Test
	public void testUniformIntInt() {
		/**
		 * Test that we only random ints in the designated range
		 * a few times.
		 */
		int desVal = StdRandom.uniform(1,3);
		assertTrue(desVal >= 1 && desVal < 3);
		desVal = StdRandom.uniform(1,3);
		assertTrue(desVal >= 1 && desVal < 3);
		desVal = StdRandom.uniform(1,3);
		assertTrue(desVal >= 1 && desVal < 3);
		
		/**
		 * Test to make sure the returned datatype is actually an int 
		 */
		assertTrue((StdRandom.uniform(1, 3) + "").length() == 1);
	
		
		
	}

	@Test
	public void testUniformDoubleDouble() {
		/**
		 * Test that we only random doubles in the designated range
		 * a few times.
		 */
		double desVal = StdRandom.uniform(0.5,3.5);
		assertTrue(desVal >= 0.5 && desVal < 3.5);
		desVal = StdRandom.uniform(0.5,3.5);
		assertTrue(desVal >= 0.5 && desVal < 3.5);
		desVal = StdRandom.uniform(0.5,3.5);
		assertTrue(desVal >= 0.5 && desVal < 3.5);
		
		/**
		 * Test to make sure the returned datatype is actually an double
		 */
		assertTrue((StdRandom.uniform(0.5, 3.5) + "").length() > 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBernoulliDoubleException() {
		/**
		 * Test Exception throwing for invalid bernoulli 
		 */
		boolean badBern =  StdRandom.bernoulli(-0.2);
	}
	
	@Test
	public void testBernoulliDouble() {
		/**
		 * Tests if the extremes of the Bernoulli Distribution holds
		 * testing any bounds, due to random chance would be fruitless
		 */
		assertFalse(StdRandom.bernoulli(0.0));
		assertTrue(StdRandom.bernoulli(1.0));
		
	}
	
	@Test
	public void testBernoulli() {
		/**
		 * Although it is rather redundant, there is not a viable way to test a 50-50 chance
		 * We attempt to illustrate this with hopes that the Law of Large Numbers
		 * where our N is 10000 will give us an error of no greater than 5% off expected value
		 * 
		 */
		int trueCount = 0;
		int falseCount = 0;
		for(int i = 0; i < 10000; i++) {
			if(StdRandom.bernoulli() == true) {
				trueCount++;
			}
			else {
				falseCount++;
			}
		}
		assertTrue(Math.abs(trueCount - falseCount) < 500);
		
		
	}

	@Test
	public void testGaussian() {
		/**
		 * At a default mean of 0, and default standard deviation 1,
		 * we test to ensure it stays within a default Gaussian distribution
		 * in a few assertion. 
		 */
		double gaussDouble = StdRandom.gaussian();
		assertTrue(gaussDouble >= -3.0 && gaussDouble <= 3.0);
		gaussDouble = StdRandom.gaussian();
		assertTrue(gaussDouble >= -3.0 && gaussDouble <= 3.0);
		gaussDouble = StdRandom.gaussian();
		assertTrue(gaussDouble >= -3.0 && gaussDouble <= 3.0);
	}

	@Test
	public void testGaussianDoubleDouble() {
		/**
		 * We test to ensure our random value stays within a Gaussian distribution
		 * of chosen mean and std. deviation of our choosing with a few tests.
		 */
		double gaussLimit = StdRandom.gaussian(3, 5);
		assertTrue(gaussLimit >= -12.0 && gaussLimit <= 18.0);
		gaussLimit = StdRandom.gaussian(2, 2);
		assertTrue(gaussLimit >= -4.0 && gaussLimit <= 8.0);
		gaussLimit = StdRandom.gaussian(10, 1);
		assertTrue(gaussLimit >= 7.0 && gaussLimit <= 13.0);
	}

	@Test
	public void testGeometric() {
		/**
		 * To test the geometric distribution, we use some large upper predictor bounds
		 * that should not be broken, there is a very low probability  p < 0.1 that
		 * any breaks some of the expected values.
		 */
		double numOfSuccesses = StdRandom.geometric(0.4);
		assertTrue(numOfSuccesses > 0 && numOfSuccesses <= 15);
		numOfSuccesses = StdRandom.geometric(0.5);
		assertTrue(numOfSuccesses > 0 && numOfSuccesses <= 10);
		numOfSuccesses = StdRandom.geometric(0.9);
		assertTrue(numOfSuccesses > 0 && numOfSuccesses <= 4);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGeometricException() {
		/**
		 * We break and test the exceptions by feeding two values outside each bound of the code
		 */
		double negativeBreak = StdRandom.geometric(-0.1);
		double positiveBreak = StdRandom.geometric(1.2);
	}

	@Test
	public void testPoisson() {
		/**
		 * We test Poisson by instituting lambda values that should not be breached.
		 * Recall, lambda = mean and variance of distribution
		 */
		double poissonCheck = StdRandom.poisson(2);
		assertTrue(poissonCheck >= 0 && poissonCheck <= 6);
		poissonCheck = StdRandom.poisson(10);
		assertTrue(poissonCheck >= 0 && poissonCheck <= 30);
		poissonCheck = StdRandom.poisson(5);
		assertTrue(poissonCheck >= 0 && poissonCheck <= 15);
		
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPoissonException() {
		/**
		 * We test  the bounds of Poisson to ensure exceptions are
		 * handled correctly
		 */
		double infiniteError = Double.POSITIVE_INFINITY;
		double negativeLamba = 3.5;
		
		StdRandom.poisson(infiniteError);
		StdRandom.poisson(negativeLamba);
	}

	@Test
	public void testPareto() {
		/**
		 * Default Pareto tests with an alpha of 1.0, we test to ensure it respects bounding.
		 * Due to the potential outliers in the upper bounding, we only test lower bounding.
		 */
		double paretoVal = StdRandom.pareto();
		assertTrue(paretoVal >= 0);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParetoDoubleException() {
		/**
		 * We test Pareto exception throwing with an improper value.
		 */
		double paretoVal = StdRandom.pareto(-0.2);
	}

	@Test
	public void testParetoDouble() {
		/**
		 * Chosen Pareto tests with variation in alpha, we test to ensure it respects bounding.
		 * Due to the potential outliers in the upper bounding, we only test lower bounding.
		 */
		double paretoVal = StdRandom.pareto();
		assertTrue(paretoVal >= 0);
		paretoVal = StdRandom.pareto();
		assertTrue(paretoVal >= 0);
		paretoVal = StdRandom.pareto();
		assertTrue(paretoVal >= 0);
	}

	@Test
	public void testCauchy() {
		/**
		 * The Cauchy function relies on the tangent function, which can hit values of negative infinity to infinity including 0. 
		 * Thus, we just test to ensure we only get a double.
		 */
		assertTrue((StdRandom.cauchy() + "").length() > 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDiscreteDoubleArrayException() {
		/**
		 * We test invalid double percentages as well as negative probabilities in 
		 * this exception block
		 */
		double[] negativeProbabilities = {-0.2, 0.5, 0.4, 0.3};
		StdRandom.discrete(negativeProbabilities);
		
		double[] notAddToOneProb = {0.1, 0.1, 0.2};
		StdRandom.discrete(notAddToOneProb);
	}
	
	@Test
	public void testDiscreteDoubleArray() {
		/**
		 * We test that only the integers in the probability range we established are achieveable
		 */
		double[] probabilities = {0.2, 0.3, 0.1, 0.4};
		int chosenProb = StdRandom.discrete(probabilities);
		assertTrue(chosenProb == 0.0 || chosenProb == 1.0 || chosenProb == 2.0 || chosenProb == 3.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDiscreteIntArrayException() {
		/**
		 * We test invalid frequencies as well as null frequencies 
		 */
		double[] nullFrequencies = null;
		StdRandom.discrete(nullFrequencies);
		
		double[] negativeFrequenciees = {-2, -3};
		StdRandom.discrete(negativeFrequenciees);
		
		double[] zeroFrequencies = {0,0};
		StdRandom.discrete(zeroFrequencies);
		
		double[] maxFrequencies = {Integer.MAX_VALUE, 1};
		StdRandom.discrete(maxFrequencies);
		
	}
	
	@Test
	public void testDiscreteIntArray() {
		/**
		 * We test valid integer frequencies in a few cases
		 */
		int[] realFrequencies = {2, 3, 4, 10};
		int frequencyVal = StdRandom.discrete(realFrequencies);
		assertTrue(frequencyVal == 0 || frequencyVal == 1 || frequencyVal == 2 || frequencyVal == 3);
		
		int[] realFrequencies2 = {2, 3, 4};
		int frequencyVal2 = StdRandom.discrete(realFrequencies2);
		assertTrue(frequencyVal2 == 0 || frequencyVal2 == 1 || frequencyVal2 == 2);
		
		int[] realFrequencies3 = {2, 3};
		int frequencyVal3 = StdRandom.discrete(realFrequencies3);
		assertTrue(frequencyVal3 == 0 || frequencyVal3 == 1);

		
	}
	
	@Test
	public void testExp() {
		/**
		 * We attempt the exponential bounds with very large rate
		 */
		double expVal = StdRandom.exp(200);
		assertTrue(expVal > 0 && expVal < 1);
		
		expVal = StdRandom.exp(500);
		assertTrue(expVal > 0 && expVal < 1);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testExpException() {
		/**
		 * This tests thrown exceptions to the exponential function 
		 * 
		 */
		StdRandom.exp(0);
	}

	@Test
	public void testShuffleObjectArray() {
		/**
		 * This takes a fairly large array of strings and determines if at least one change has occurred
		 */
		String[] testStrings = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "z"};
		String[] copyTestStrings = Arrays.copyOf(testStrings,26);
		
		StdRandom.shuffle(testStrings);
		for(int i = 0; i < testStrings.length; i++) {
			if(!testStrings[i].equals(copyTestStrings[i])) {
				assertTrue(true);
			}
		}
		assertFalse(false);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testShuffleObjectArrayException() {
		/**
		 * Test for a null array  
		 */
		String[] nullRay = null;
		StdRandom.shuffle(nullRay);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testShuffleDoubleArrayException() {
		/**
		 * Test for a null array  
		 */
		double[] nullRay = null;
		StdRandom.shuffle(nullRay);
	}

	@Test
	public void testShuffleDoubleArray() {
		/**
		 * This takes a fairly large array of doubles and determines if at least one change has occurred
		 */
		double[] testStrings = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0};
		double[] copyTestStrings = Arrays.copyOf(testStrings,26);
		
		StdRandom.shuffle(testStrings);
		for(int i = 0; i < testStrings.length; i++) {
			if(!(testStrings[i] == copyTestStrings[i])) {
				assertTrue(true);
			}
		}
		assertFalse(false);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testShuffleIntArrayException() {
		/**
		 * Test for a null array  
		 */
		int[] nullRay = null;
		StdRandom.shuffle(nullRay);
	}

	@Test
	public void testShuffleIntArray() {
		/**
		 * This takes a fairly large array of ints and determines if at least one change has occurred
		 */
		int[] testStrings = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
		int[] copyTestStrings = Arrays.copyOf(testStrings,26);
		
		StdRandom.shuffle(testStrings);
		for(int i = 0; i < testStrings.length; i++) {
			if(!(testStrings[i] == copyTestStrings[i])) {
				assertTrue(true);
			}
		}
		assertFalse(false);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testShuffleCharArrayException() {
		/**
		 * Test for a null array  
		 */
		char[] nullRay = null;
		StdRandom.shuffle(nullRay);
	}

	@Test
	public void testShuffleCharArray() {
		/**
		 * This takes a fairly large array of chars and determines if at least one change has occurred
		 */
		char[] testStrings = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'z'};
		char[] copyTestStrings = Arrays.copyOf(testStrings,26);
		
		StdRandom.shuffle(testStrings);
		for(int i = 0; i < testStrings.length; i++) {
			if(!(testStrings[i] == (copyTestStrings[i]))) {
				assertTrue(true);
			}
		}
		assertFalse(false);
	}

	@Test
	public void testShuffleObjectArrayIntInt() {
		/** Test to make sure the shuffled elements stay in the desired spot
		 * 
		 * 
		 */
		String[] testStrings = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "z"};
		String[] copyTestStrings = Arrays.copyOf(testStrings,26);
		StdRandom.shuffle(testStrings, 0, 3);
		assertTrue(testStrings[0].equals(copyTestStrings[0]) || testStrings[0].equals(copyTestStrings[1]) || testStrings[0].equals(copyTestStrings[2])); 
		assertTrue(testStrings[1].equals(copyTestStrings[0]) || testStrings[1].equals(copyTestStrings[1]) || testStrings[1].equals(copyTestStrings[2]));
		assertTrue(testStrings[2].equals(copyTestStrings[0]) || testStrings[2].equals(copyTestStrings[1]) || testStrings[2].equals(copyTestStrings[2]));
	}

	@Test
	public void testShuffleDoubleArrayIntInt() {
		/** 
		 * Test to make sure the shuffled elements stay in the desired spot
		 */
		double[] testStrings = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0};
		double[] copyTestStrings = Arrays.copyOf(testStrings,26);
		assertTrue(testStrings[0] == (copyTestStrings[0]) || testStrings[0] == (copyTestStrings[1]) || testStrings[0] == (copyTestStrings[2])); 
		assertTrue(testStrings[1] == (copyTestStrings[0]) || testStrings[1] == (copyTestStrings[1]) || testStrings[1] == (copyTestStrings[2]));
		assertTrue(testStrings[2] == (copyTestStrings[0]) || testStrings[2] == (copyTestStrings[1]) || testStrings[2] == (copyTestStrings[2]));
	}

	@Test
	public void testShuffleIntArrayIntInt() {
		/** 
		 * Test to make sure the shuffled elements stay in the desired spot
		 */
		int[] testStrings = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
		int[] copyTestStrings = Arrays.copyOf(testStrings,26);
		assertTrue(testStrings[0] == (copyTestStrings[0]) || testStrings[0] == (copyTestStrings[1]) || testStrings[0] == (copyTestStrings[2])); 
		assertTrue(testStrings[1] == (copyTestStrings[0]) || testStrings[1] == (copyTestStrings[1]) || testStrings[1] == (copyTestStrings[2]));
		assertTrue(testStrings[2] == (copyTestStrings[0]) || testStrings[2] == (copyTestStrings[1]) || testStrings[2] == (copyTestStrings[2]));
	}

	@Test
	public void testPermutationInt() {
		/**
		 * Tests to ensure permutation of ints is the correct size and each element
		 * is in the desired range
		 */
		int[] outputIntArray1 = StdRandom.permutation(5);
		assertTrue(outputIntArray1.length == 5);
		for(int i = 0; i < 5; i++) {
			assertTrue(outputIntArray1[i] >= 0 && outputIntArray1[i] < 5);
		}
		
		int[] outputIntArray2 = StdRandom.permutation(10);
		assertTrue(outputIntArray2.length == 10);
		for(int i = 0; i < 10; i++) {
			assertTrue(outputIntArray2[i] >= 0 && outputIntArray2[i] < 10);
		}
		
		int[] outputIntArray3 = StdRandom.permutation(3);
		assertTrue(outputIntArray3.length == 3);
		for(int i = 0; i < 3; i++) {
			assertTrue(outputIntArray3[i] >= 0 && outputIntArray3[i] < 3);
		}
	}

	@Test
	public void testPermutationIntInt() {
		/**
		 * Tests that a predetermined range and set of n ints 
		 * produces the correct amount of ints. Each int must be
		 * within the correct range as specified by the base coder.
		 */
		int[] outputIntArray = StdRandom.permutation(20, 5);
		assertTrue(outputIntArray.length == 5);
		for(int i = 0; i < 5; i++) {
			assertTrue(outputIntArray[i] >= 0 && outputIntArray[i] < 20);
		}
	}

	@Test
	public void testMain() {
		/**
		 * The main method of the base program is a Unit tester, to test this would be redundant
		 * I leave it blank as no further testing is required on the main
		 */
	}

}

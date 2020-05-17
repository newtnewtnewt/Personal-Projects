
public class TestRuns {
	public static void main(String args[]) {
		double[] times = new double[10];
		double time = 0;
		for(int i = 0; i < 10; i++) {
			time = System.currentTimeMillis();
			testRun1();
			times[i] = (System.currentTimeMillis() - time)/1000;
		}
		System.out.println(average(times));
		
		for(int i = 0; i < 10; i++) {
			time = System.currentTimeMillis();
			testRun2();
			times[i] = (System.currentTimeMillis() - time)/1000;
		}
		System.out.println(average(times));
		
		for(int i = 0; i < 10; i++) {
			time = System.currentTimeMillis();
			testRun3();
			times[i] = (System.currentTimeMillis() - time)/1000;
			System.out.println(i);
		}
		System.out.println(average(times));
		
		
		
	}
	public static void testRun1() {
		int x = 2;
		int y = 2;
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 100; j++) {
				for(int k = 0; k < 1000; k++) {
					x += y;
					y += x;
				}
			}
		}
		
	}
	public static void testRun2() {
		boolean x = true;
		boolean y = false;
		boolean z = true;
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 100; j++) {
				for(int k = 0; k < 1000; k++) {
					z = x;
					z = y;
				}
			}
		}
		System.out.println(z);
		
	}
	public static void testRun3() {
		String x = "Hello";
		String y = "World";
		String z = "";
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 50; j++) {
				for(int k = 0; k < 10; k++) {
					z += x;
					z += y;
				}
			}
		}
	}
	public static double average(double[] values) {
		double average = 0.0;
		for(int i = 0; i < values.length; i++) {
			average += values[i];
		}
		return average/values.length;
	}
	

}

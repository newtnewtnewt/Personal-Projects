import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class RoadNetworkTest {
	

	@Test
	void testRoadNetwork() {
		RoadNetwork road = new RoadNetwork();
		for(int i = 0; i < 500; i++) {
			assertTrue(road.addCity("city" + i));
		}
		assertFalse(road.addCity("city501"));
	}
	
	
	
	@Test
	void testRoadNetworkInt() {
		RoadNetwork road = new RoadNetwork(5);
		for(int i = 0; i < 5; i++) {
			assertTrue(road.addCity("city" + i));
		
		}
		assertFalse(road.addCity("city6"));	
		
	}

	@Test
	void testAddCity() {
		RoadNetwork road = new RoadNetwork(5);
		for(int i = 0; i < 5; i++) {
			assertTrue(road.addCity("city" + i));
		
		}
		assertFalse(road.addCity("city6"));
		road.removeCity("city3");
		road.removeCity("city2");
		assertTrue(road.addCity("city55"));
		assertTrue(road.addCity("city5"));
	}

	@Test
	void testAreConnected() {
		RoadNetwork road = new RoadNetwork(3);
		road.addCity("slam");
		road.addCity("bam");
		road.addCity("wam");
		road.connect("slam", "bam");
		road.connect("wam", "bam");
		assertTrue(road.areConnected("slam", "bam"));
		assertTrue(road.areConnected("slam", "wam"));
		assertFalse(road.areConnected("slam", "wee"));
		assertTrue(road.areConnected("slam", "slam"));
		assertFalse(road.areConnected("slam", "ree"));
	}

	@Test
	void testAreDirectlyConnected() {
		RoadNetwork road = new RoadNetwork(2);
		road.addCity("slam");
		road.addCity("bam");
		road.addCity("wam");
		road.connect("slam", "bam");
		assertTrue(road.areDirectlyConnected("slam", "bam"));
		assertFalse(road.areDirectlyConnected("slam", "wam"));
		
	}

	@Test
	void testConnect() {
		RoadNetwork road = new RoadNetwork(2);
		road.addCity("slam");
		road.addCity("bam");
		assertTrue(road.connect("slam", "bam"));
		assertFalse(road.connect("ree", "slam"));
		
	}

	@Test
	void testContainsCity() {
		RoadNetwork road = new RoadNetwork(5);
		for(int i = 0; i < 5; i++) {
			road.addCity("city" + i);
		}
		assertTrue(road.containsCity("city1"));
		assertFalse(road.containsCity("city6"));
		road.removeCity("city1");
		assertFalse(road.containsCity("city1"));
	}

	@Test
	void testDegreesOfSeperation() {
		RoadNetwork road = new RoadNetwork(11);
		road.addCity("c1");
		road.addCity("c2");
		road.addCity("c3");
		road.addCity("c4");
		road.addCity("c5");
		road.addCity("c6");
		road.addCity("c7");
		road.addCity("c8");
		road.addCity("c9");
		road.addCity("c10");
		road.addCity("flip");
		
		
		road.connect("c2", "c4");
		road.connect("c4", "c5");
		
		road.connect("c8", "c5");
		road.connect("c4", "c8");
		road.connect("c2", "c8");
		road.connect("c8", "c9");
		road.connect("c9", "c5");
		road.connect("c2", "c10");
		road.connect("c2", "c6");
		road.connect("c6","c5");
		road.connect("c9", "flip");
		assertEquals(road.degreesOfSeperation("c9", "c10"),3);
		assertEquals(road.degreesOfSeperation("c1", "flip"),-1);
		assertEquals(road.degreesOfSeperation("c2", "c4"),1);

		RoadNetwork otherRoad = new RoadNetwork(8);
		otherRoad.addCity("a");
		otherRoad.addCity("b");
		otherRoad.addCity("c");
		otherRoad.addCity("d");
		otherRoad.addCity("e");
		otherRoad.addCity("f");
		otherRoad.addCity("g");
		otherRoad.addCity("h");
		otherRoad.connect("e", "h");
		otherRoad.connect("a","b");
		otherRoad.connect("a","d");
		otherRoad.connect("b","e");
		otherRoad.connect("d","c");
		otherRoad.connect("c","e");
		otherRoad.connect("f","g");
		otherRoad.connect("a","e");
		
		assertEquals(otherRoad.degreesOfSeperation("a","f"), -1);
		assertEquals(otherRoad.degreesOfSeperation("d","e"), 2);
		assertEquals(otherRoad.degreesOfSeperation("e","b"), 1);
		assertEquals(otherRoad.degreesOfSeperation("a","h"), 2);

	}

	@Test
	void testDisconnect() {
		//Cannot test, return type is void
		
	}

	@Test
	void testGetCities() {
		RoadNetwork road = new RoadNetwork(5);
		road.addCity("banana");
		road.addCity("apple");
		road.addCity("xray");
		road.addCity("bunster");
		road.addCity("ketchup");
		String[] testerArray = road.getCities();
		assertTrue(testerArray[0].equals("apple"));
		assertTrue(testerArray[1].equals("banana"));
		assertTrue(testerArray[2].equals("bunster"));
		assertTrue(testerArray[3].equals("ketchup"));
		assertTrue(testerArray[4].equals("xray"));
	}

	@Test
	void testGetCityCount() {
		RoadNetwork road = new RoadNetwork(10);
		for(int i = 0; i < 10; i++) {
			road.addCity(i + "");
			assertTrue(road.getCityCount() == i + 1);
		}
		road.removeCity("1");
		assertTrue(road.getCityCount() == 9);
		road.addCity("2");
		assertTrue(road.getCityCount() == 9);
		for(int i = 0; i < 10; i++) {
			road.removeCity(i + "");
		}
		assertTrue(road.getCityCount() == 0);
	
	}

	@Test
	void testGetDirectlyConnectedCities() {
		RoadNetwork road = new RoadNetwork(5);
		road.addCity("banana");
		road.addCity("apple");
		road.addCity("xray");
		road.addCity("bunster");
		road.addCity("ketchup");
		
		road.connect("banana", "apple");
		road.connect("banana", "xray");
		road.connect("banana", "bunster");
		
		assertTrue(road.getDirectlyConnectedCities("banana").length == 3);
		assertTrue(road.getDirectlyConnectedCities("banana")[0].equals("apple"));
		assertTrue(road.getDirectlyConnectedCities("banana")[2].equals("xray"));
		assertTrue(road.getDirectlyConnectedCities("banana")[1].equals("bunster"));
	}

	@Test
	void testRemoveCity() {
		RoadNetwork road = new RoadNetwork(5);
		road.addCity("banana");
		road.addCity("apple");
		road.addCity("xray");
		road.addCity("bunster");
		road.addCity("ketchup");
		int tempCityCount =road.getCityCount();
		road.removeCity("nunchunks");
		assertEquals(tempCityCount, road.getCityCount());
	}

	@Test
	void testToString() {
		//Format based test that serves a purpose only in visual testing.
		//No point in JUnit testing. 
	}

}

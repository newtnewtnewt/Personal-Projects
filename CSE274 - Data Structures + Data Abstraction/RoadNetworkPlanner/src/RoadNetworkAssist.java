import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

public class RoadNetworkAssist {

	public static void main(String[] args) {
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
	
		RoadNetwork blank = new RoadNetwork(0);
	}

}

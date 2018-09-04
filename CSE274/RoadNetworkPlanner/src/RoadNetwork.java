import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/** This program issues the creation of a road network within a state.
 * @author Noah Dunn
 * Creation Date: 8/31/2018
 * 
 * List of Working Pieces: 
 * RoadNetwork() constructor works
 * RoadNetwork(int maxSize) constructor also works
 * addCity() works
 * areConnected() works
 * areDirectlyConnected() works
 * connect() works
 * containsCity() works
 * degreesOfSeperation() works
 * disconnect() works
 * getCities() works
 * getCityCount() works 
 * getDirectlyConnectedCities() works
 * removeCity() works
 * toString() works
 * 
 * List of Known Problems:
 * My only doubt is with my degrees of separation algorithm,
 * but I have tested as many fringe cases as I can come up with.
 * 
 * 
 * 
 */


public class RoadNetwork {
	
	private String[] stateCityList;
	private int maxSize;
	private ArrayList<ArrayList> cityConnections = new ArrayList<ArrayList>();
	private ArrayList<ArrayList> solutionTrails = new ArrayList<ArrayList>();
	
	public RoadNetwork() {
		/**
		 * Creates a state with no initial cities and a max city limit of 500 cities.
		*/
		maxSize = 500;
		stateCityList = new String[maxSize];
		
	}
	public RoadNetwork(int maxSize) {
		/**
		 * Creates a state with no initial cities and max city limit of @param maxSize
		 */
		this.maxSize = maxSize;
		stateCityList = new String[maxSize];
	}
	
	public boolean addCity(String city) {
		/**
		 * Adds a city to the road-network with @param name, @return false if the network is full
		 * 
		 */
		for(int i = 0; i < stateCityList.length; i++) {
			if(stateCityList[i] != null && stateCityList[i].equals(city)) {
				return true;
			}
		}
		boolean foundASpot = false;
		for(int i = 0; i < stateCityList.length; i++) {
			if(stateCityList[i] == null && foundASpot == false) {
				stateCityList[i] =city;
				foundASpot = true;
			}
		}
		if(!foundASpot) {
			return false;
		}
		ArrayList<String> temp = new ArrayList<String>(); 
		temp.add(0, city);
		getCityConnections().add(temp);
		return true;
		
	}
	
	public boolean areConnected(String city1, String city2) {
		/**
		 * Determines if two cities @param city1 and @param city2 are connected, either directly or through a chain.
		 * @return true if this is the case
		 */
		if(degreesOfSeperation(city1, city2) == 0 || degreesOfSeperation(city1, city2) > 0) {
			return true;
		}
		else {
			return false;
		}
		
	}
	public boolean areDirectlyConnected(String city1, String city2) {
		/**
		 * Determines if two cities @param city1 and @param city2 are connected by a single direct road.
		 * @return true if this is the case
		 */
		int city1Loc = -1;
		for(int i = 0; i < getCityConnections().size(); i++) {
			if(city1 == getCityConnections().get(i).get(0)) {
				city1Loc = i;
			}
		}
		
		if(city1Loc == -1 || !containsCity(city1) || !containsCity(city2)) {
			return false;
		}
		
		else {
			for(int i = 0; i < getCityConnections().get(city1Loc).size(); i++) {
				if(getCityConnections().get(city1Loc).get(i) == city2)
					return true;
				}
			}
		return false;
		}
		
	
	public boolean connect(String city1, String city2) {
		/**
		 * Attaches two cities @param city1 and @param city2 together by direct connections
		 * @return true if cities are already attached or were successfully attached
		 * @return false if one city doesn't exist 
		 * 
		 */
		boolean cityOneFound = false;
		boolean cityTwoFound = false;
		int city1Loc = -1;
		int city2Loc = -1;
		for(int i = 0; i < getCityConnections().size(); i++) {
			if(getCityConnections().get(i).get(0) == city1) {
				city1Loc = i;
				cityOneFound = true;			
				}
			
			if(getCityConnections().get(i).get(0) == city2) {
				city2Loc = i;
				cityTwoFound = true;
			}
			
		}
		if(cityOneFound && cityTwoFound) {
			for(int i = 0; i < getCityConnections().get(city1Loc).size(); i++) {
				if(getCityConnections().get(city1Loc).get(i) == city2) {
					return true;
				}
			}
			getCityConnections().get(city1Loc).add(city2);
			getCityConnections().get(city2Loc).add(city1);
			return true;
			
		}
		
		else {
			return false;
		}
	
			
		
	}
	public boolean containsCity(String name) {
		/**
		 * @return true if the @param city name is found in the road network
		 * 
		 */
		for(int i = 0; i < stateCityList.length; i++) {
			if(stateCityList[i] != null && stateCityList[i].equals(name)) {
				return true;
			}
			
		}
		return false;
	}
	public int degreesOfSeperation(String city1, String city2) {
		/**
		 * @return the minimum number of direct connections required to get from @param city1 to @param city2
		 *
		 */
		if(city1 == city2) {
			return 0;
		}
		if(!containsCity(city1) || !containsCity(city2)) {
			return -1;
		}
		ArrayList<String> trail = new ArrayList<String>();
		
		trailChaser(trail, city1, city2);
		if(solutionTrails.size() == 0) {
			return -1;
		}
		
		int smallestDegrees = 0;
		for(int i = 0; i < solutionTrails.size(); i++) {
			if(i == 0) {
				smallestDegrees = solutionTrails.get(0).size();
			}
			if(solutionTrails.get(i).size() < smallestDegrees) {
				smallestDegrees = solutionTrails.get(i).size();
			}
		}
		
		
		solutionTrails.clear();
		return smallestDegrees -1;
		
		
	}
	private void trailChaser(ArrayList<String> trail, String cityCurrent, String cityEnd) {
		/**
		 * Depth-first search-esque algorithm that recursively chases the connection from @param cityCurrent to @param cityEnd
		 * It tracks visited cities using @param trail and @return a solution path.
		 * 
		 */
		ArrayList<String> solutionKey = new ArrayList<String>();
		int cityLoc = -1;
		
		for(int i = 0; i < cityConnections.size(); i++) {
			if(cityConnections.get(i).get(0).equals(cityCurrent)) {
				cityLoc = i;
			}
		}
		
		for(int i = 1; i < cityConnections.get(cityLoc).size();i++) {
			
			if(!trail.contains(cityConnections.get(cityLoc).get(i))) {
				
				if(cityConnections.get(cityLoc).contains(cityEnd)) {
					trail.add(cityCurrent);
					trail.add(cityEnd);
					solutionTrails.add(trail);
					break;
				}
				
				else {
					ArrayList<String> copyTrail = copy(trail);
					copyTrail.add(cityCurrent);
					trailChaser(copyTrail, (String)cityConnections.get(cityLoc).get(i), cityEnd);
					}
					
				
			}
			}
		}
		
	
	public void disconnect(String city1, String city2) {
		/**
		 * Removes the connection between @param city1 and @param city2
		 * 
		 */
		int city1Loc = -1;
		int city2Loc = -1;
		for(int i = 0; i < getCityConnections().size(); i++) {
			if(getCityConnections().get(i).get(0).equals(city1)) {
				city1Loc = i;		
				}
			
			if(getCityConnections().get(i).get(0).equals(city2)) {
				city2Loc = i;
			}
			
		}
		if(city1Loc != -1 && city2Loc != -1) {
			for(int i = 0; i < getCityConnections().get(city1Loc).size(); i++) {
				if(getCityConnections().get(city1Loc).get(i).equals(city2)) {
					getCityConnections().get(city1Loc).remove(i);
				}
			}
			for(int i = 0; i < getCityConnections().get(city2Loc).size(); i++) {
				if(getCityConnections().get(city2Loc).get(i).equals(city1)) {
					getCityConnections().get(city2Loc).remove(i);
				}
				
				}
		}
	}
		
	public String[] getCities() {
		/**
		 * Provides a String array of cities within a Road Network sorted by alphabetical order.
		 * 
		 */
		int size = stateCityList.length;
		for(int i = 0; i < stateCityList.length; i++) {
			if(stateCityList[i] == null)
				size--;
		}
		String[] temp = new String[size];
		
		int counter = 0; 
		for(int i = 0; i < stateCityList.length; i++) {
			if(stateCityList[i] != null) {
				temp[counter] = stateCityList[i];
					counter++;
			}
		}
	
		Arrays.sort(temp);
		return temp;
		
		
	}
	
	public int getCityCount() {
		/**
		 * Prints the number of cities in the Road Network
		 * 
		 */
		int counter = 0;
		for(int i = 0; i < stateCityList.length; i++) {
			if(stateCityList[i] != null) {
				counter++;
			}
		}
		return counter;
		
	}
	
	
	public String[] getDirectlyConnectedCities(String name) {
		String[] directCities;
		int city1Loc = -1;
		int counter = 0;
		for(int i = 0; i < getCityConnections().size(); i++) {
			if(getCityConnections().get(i).get(0) == name) {
				city1Loc = i;
			}
		}
		
		if(city1Loc == -1) {
			directCities = new String[0];
			return directCities;
		}
			
		else {
			directCities = new String[getCityConnections().get(city1Loc).size() -1];
			int j = 0;
			for(int i = 1; i < getCityConnections().get(city1Loc).size(); i++) {
				directCities[j] = (String)getCityConnections().get(city1Loc).get(i);
				j++;
			}
		}
		Arrays.sort(directCities);
		return directCities;
	}
		
		
	public void removeCity(String name) {
		for(int i = 0; i < stateCityList.length; i++) {
			if(stateCityList[i] != null && stateCityList[i].equals(name)) {
				stateCityList[i] = null;
				break;
			}
			}
		for(int i= 0; i < cityConnections.size(); i++) {
			if(cityConnections.get(i).get(0).equals(name)) {
				cityConnections.remove(i);
				
			}
		}
	}
				
	
	public String toString() {
		/**
		 * Provides a large, well organized, spaced, newline, and comma separated string that contains a list of all cities 
		 * in a RoadNetwork in alphabetical order, as well as their directly connected cities also in Alphabetical order.
		 * 
		 */
		String[] cleanList = getCities();
		String massiveString = "";
		int tempCityLoc = -1;
		ArrayList<ArrayList> copyOfCityConnections = new ArrayList<ArrayList>();
		copyOfCityConnections = clone(cityConnections);
		
		for(int i = 0; i < copyOfCityConnections.size(); i++) {
			copyOfCityConnections.get(i).remove(0);
			Collections.sort(copyOfCityConnections.get(i));
		}
		
		for(int i = 0; i< cleanList.length; i++) {
			
			
			for(int q = 0; q <getCityConnections().size(); q++) {
				if(cityConnections.get(q).get(0).equals(cleanList[i])) {
					tempCityLoc = q;
					}
			}
			
			if(copyOfCityConnections.get(tempCityLoc).size() == 0) {
				massiveString = massiveString + cleanList[i] + " [] \n";
			}
			
			for(int j = 0; j < copyOfCityConnections.get(tempCityLoc).size(); j++) {
				
				
				if(copyOfCityConnections.get(tempCityLoc).size() == 1) {
					massiveString = massiveString + cleanList[i] +  " [" + copyOfCityConnections.get(tempCityLoc).get(0) + "]\n";
				}
				else if(j == 0) {
					massiveString = massiveString + (cleanList[i]+ " [" + copyOfCityConnections.get(tempCityLoc).get(j) + ", ");
				}
				else if(j > 0 && j != copyOfCityConnections.get(tempCityLoc).size()-1) {
					massiveString = massiveString + copyOfCityConnections.get(tempCityLoc).get(j) + ", ";
				}
				else if(j == copyOfCityConnections.get(tempCityLoc).size()-1) {
					massiveString = massiveString + copyOfCityConnections.get(tempCityLoc).get(j) + "]\n";
				}
			}
			
			
			}
		return massiveString;
		
	}
	/**
	 * @return the cityConnections
	 */
	private ArrayList<ArrayList> getCityConnections() {
		return cityConnections;
	}
	/**
	 * @param cityConnections the cityConnections to set
	 */
	private void setCityConnections(ArrayList<ArrayList> cityConnections) {
		this.cityConnections = cityConnections;
	}
	
	private ArrayList<ArrayList> clone(ArrayList<ArrayList> x){
		/**
		 * Perform a deep clone of an ArrayList of ArrayLists, in order to copy all items into a new, unique ArrayList of ArrayLists
		 * 
		 */
		ArrayList<ArrayList> tempList = new ArrayList<ArrayList>();
		
		for(ArrayList<String> list : x) {
			ArrayList<String> metaList = new ArrayList<String>();
			for(int i = 0; i < list.size(); i++) {
				metaList.add(list.get(i));
			}
			tempList.add(metaList);
		}
		return tempList;
		
	}
	private ArrayList<String> copy(ArrayList<String> x){
		/**
		 * Performs a deep clone of an ArrayList of Strings
		 */
		ArrayList<String> copier = new ArrayList<String>();
		for(int i = 0; i < x.size(); i++){
			copier.add(x.get(i));
		}
		return copier;
	}
	
	
}

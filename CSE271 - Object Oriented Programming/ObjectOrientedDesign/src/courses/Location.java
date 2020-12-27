package courses;

public class Location {
	private String building;
	private int room, capacity;
	public Location(String building, int room, int capacity) {
		this.setBuilding(building);
		this.setRoom(room);
		this.setCapacity(capacity);
	}
	/**
	 * @return the building
	 */
	public String getBuilding() {
		return building;
	}
	/**
	 * @param building the building to set
	 */
	public void setBuilding(String building) {
		this.building = building;
	}
	/**
	 * @return the room
	 */
	public int getRoom() {
		return room;
	}
	/**
	 * @param room the room to set
	 */
	public void setRoom(int room) {
		this.room = room;
	}
	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}

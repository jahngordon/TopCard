package cardgame;

public class Aircraft {

	String manufacturer;
	String model;
	int range;
	double wingspan;
	double length;
	double height;
	int maxspeed;
	int mtow;
	int year;
	
	User lastOwner;
	
	public Aircraft(String manufacturer, String model, int range,
			double wingspan, double length, double height, int mtow, 
			int maxspeed, int year) {
		super();
		this.manufacturer = manufacturer;
		this.model = model;
		this.range = range;
		this.wingspan = wingspan;
		this.length = length;
		this.height = height;
		this.mtow = mtow;
		this.maxspeed = maxspeed;
		this.year = year;
		
		this.lastOwner = null;
	}
	
	public User getLastOwner() {
		return lastOwner;
	}

	public void setLastOwner(User lastOwner) {
		this.lastOwner = lastOwner;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getModel() {
		return model;
	}

	public int getRange() {
		return range;
	}

	public double getWingspan() {
		return wingspan;
	}

	public double getLength() {
		return length;
	}

	public double getHeight() {
		return height;
	}

	public int getMaxspeed() {
		return maxspeed;
	}

	public int getMtow() {
		return mtow;
	}

	public int getYear() {
		return year;
	}

	public void printCard() {
		System.out.println("Manufacturer    : " + manufacturer);
		System.out.println("Model           : " + model);
		System.out.println("Range           : " + range);
		System.out.println("Wingspan        : " + wingspan);
		System.out.println("Length          : " + length);
		System.out.println("Height          : " + height);
		System.out.println("Max Speed (kmh) : " + maxspeed);
		System.out.println("MTOW (tonnes)   : " + mtow);
		System.out.println("First flight    : " + year);
	}
}

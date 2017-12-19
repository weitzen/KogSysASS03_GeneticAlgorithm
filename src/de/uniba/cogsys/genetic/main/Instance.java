package de.uniba.cogsys.genetic.main;

/**
 * This class represents a single instance of a day in the "play tennis" setting.
 *
 */
public class Instance {
	
	private boolean[] instance = new boolean[13];
	private boolean isPositive;
	private String instanceDescription;
	
	/**
	 * Generates the bitstring out of a day description.
	 * 
	 * @param instanceDescription
	 * The description of the instance following the schema:
	 * <sky,airtemp,humidity,wind,water,forecast>
	 * 
	 * @param isPositive
	 */
	public Instance(String instanceDescription, boolean isPositive) {
		instanceDescription = instanceDescription.toLowerCase();
		String[] parts = instanceDescription.split(",");
		
		if(parts.length != 6) {
			throw new IllegalArgumentException("Not a valid instance description string!");
		}
		
		boolean[] instanceTmp = new boolean[13];
		for(int i = 0; i < instanceTmp.length; i++) {
			instanceTmp[i] = false;
		}
		
		if(parts[0].equals("sunny")) {
			instanceTmp[0] = true;
		} else if (parts[0].equals("cloudy")) {
			instanceTmp[1] = true;
		} else if (parts[0].equals("rainy")) {
			instanceTmp[2] = true;
		} else {
			throw new IllegalArgumentException("Not a valid instance description string!");
		}
		
		if(parts[1].equals("warm")) {
			instanceTmp[3] = true;
		} else if (parts[1].equals("cold")) {
			instanceTmp[4] = true;
		} else {
			throw new IllegalArgumentException("Not a valid instance description string!");
		}
		
		if(parts[2].equals("normal")) {
			instanceTmp[5] = true;
		} else if (parts[2].equals("high")) {
			instanceTmp[6] = true;
		} else {
			throw new IllegalArgumentException("Not a valid instance description string!");
		}
		
		if(parts[3].equals("strong")) {
			instanceTmp[7] = true;
		} else if (parts[3].equals("weak")) {
			instanceTmp[8] = true;
		} else {
			throw new IllegalArgumentException("Not a valid instance description string!");
		}
		
		if(parts[4].equals("warm")) {
			instanceTmp[9] = true;
		} else if (parts[4].equals("cool")) {
			instanceTmp[10] = true;
		} else {
			throw new IllegalArgumentException("Not a valid instance description string!");
		}
		
		if(parts[5].equals("same")) {
			instanceTmp[11] = true;
		} else if (parts[5].equals("change")) {
			instanceTmp[12] = true;
		} else {
			throw new IllegalArgumentException("Not a valid instance description string!");
		}
		
		this.instance = instanceTmp;
		this.instanceDescription = instanceDescription;
		this.isPositive = isPositive;
	}

	public boolean[] getInstance() {
		return instance;
	}

	public void setInstance(boolean[] instance) {
		this.instance = instance;
	}

	public boolean isPositive() {
		return isPositive;
	}

	public void setPositive(boolean isPositive) {
		this.isPositive = isPositive;
	}
	
	public String toString() {
		
		return this.instanceDescription;
	}
}

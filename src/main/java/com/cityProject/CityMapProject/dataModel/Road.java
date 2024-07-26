package com.cityProject.CityMapProject.dataModel;

public class Road {

	private City city1;
	private City city2;
	private int lanes;
	
	public Road(City city1, City city2, int lanes) {
		super();
		this.city1 = city1;
		this.city2 = city2;
		this.lanes = lanes;
	}

	public City getCity1() {
		return city1;
	}

	public void setCity1(City city1) {
		this.city1 = city1;
	}

	public City getCity2() {
		return city2;
	}

	public void setCity2(City city2) {
		this.city2 = city2;
	}

	public int getLanes() {
		return lanes;
	}

	public void setLanes(int lanes) {
		this.lanes = lanes;
	}

}

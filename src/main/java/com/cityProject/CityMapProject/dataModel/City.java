package com.cityProject.CityMapProject.dataModel;

import java.util.ArrayList;
import java.util.List;

public class City {
	private String city;
	private double latitude;
	private double longitude;
	private List<Road> roads;
	
	
	public City(String city, double latitude, double longitude) {
		super();
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		roads = new ArrayList<>();
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "City [city=" + city + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	public List<Road> getRoads() {
		return roads;
	}

	public void setRoads(List<Road> roads) {
		this.roads = roads;
	}
	
	

}

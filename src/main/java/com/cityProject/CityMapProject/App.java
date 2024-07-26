package com.cityProject.CityMapProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import com.cityProject.CityMapProject.dataModel.City;
import com.cityProject.CityMapProject.dataModel.Road;

/**
 * Hello world!
 *
 */
public class App {

	// You can change this value for better viewing
	private static final int max_no_cities = 100;
	private static final Random random = new Random();
	private static final int nationalHighwayLimit = 4;
	private static int nationalHighway = 0;

	public static void main(String[] args) {
		// generating the random cities and printing the values
		List<City> cities = generateAndPrintCities();

		Graph graph = new SingleGraph("City Map graph");

		// add all the cities we have created as nodes
		addNodes(cities, graph);

		connectCities(cities, graph);

		Viewer viewer = graph.display();
		viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);

	}

	private static void addNodes(List<City> cities, Graph graph) {
		// TODO Auto-generated method stub
		for (City city : cities) {
			Node node = graph.addNode(city.getCity());
			node.setAttribute("ui.label", city.getCity());
		}
	}

	private static void connectCities(List<City> cities, Graph graph) {
		// TODO Auto-generated method stub
		for (City city : cities) {
			connectCityWithRoads(city, cities, graph);
		}

	}

	private static void connectCityWithRoads(City city, List<City> cities, Graph graph) {
		// TODO Auto-generated method stub

		// each city should at least have one connection
		firstRoadConnection(city, cities, graph);

		// for now added limit of 2 so that graph can be better looking
		int connectTo = random.nextInt(3);

		for (int i = 0; i < connectTo; i++) {
			City otherCity = chooseRandomCity(city, graph, cities);
			Edge edge = graph.addEdge(city.getCity() + "-" + otherCity.getCity(), city.getCity(), otherCity.getCity(),
					true);

			int laneType = generateRandomLaneType(city, otherCity);

			Road road = new Road(city, otherCity, laneType);
			city.getRoads().add(road);
			otherCity.getRoads().add(road);

			if (laneType == 4)
				nationalHighway++;
			edge.setAttribute("ui.label", laneType);
			setRoadColour(edge, laneType);
		}

	}

	private static void firstRoadConnection(City city, List<City> cities, Graph graph) {
		// TODO Auto-generated method stub
		City otherCity = chooseRandomCity(city, graph, cities);

		int laneType = generateRandomLaneType(city, otherCity);
		if (laneType == 4)
			nationalHighway++;

		Road road = new Road(city, otherCity, laneType);
		city.getRoads().add(road);
		otherCity.getRoads().add(road);

		Edge edge = graph.addEdge(city.getCity() + "-" + otherCity.getCity(), city.getCity(), otherCity.getCity(),
				true);
		edge.setAttribute("ui.label", laneType);
		setRoadColour(edge, laneType);
	}

	private static void setRoadColour(Edge edge, int laneType) {
		// TODO Auto-generated method stub
		switch (laneType) {
		case 4:
			edge.setAttribute("ui.style", "fill-color: red;");
			break;
		case 3:
			edge.setAttribute("ui.style", "fill-color: orange;");
			break;
		case 2:
			edge.setAttribute("ui.style", "fill-color: yellow;");
			break;
		case 1:
			edge.setAttribute("ui.style", "fill-color: green;");
			break;
		}
	}

	/*
	 * Returns the possible lane type connection possible between two cities will
	 * take already available roads into consideration
	 */
	private static int generateRandomLaneType(City city, City otherCity) {
		// TODO Auto-generated method stub

		Road fourLane = city.getRoads().stream().filter(r -> r.getLanes() == 4).findFirst().orElse(null);
		Road threeLane = city.getRoads().stream().filter(r -> r.getLanes() == 3).findFirst().orElse(null);
		Road twoLane = city.getRoads().stream().filter(r -> r.getLanes() == 2).findFirst().orElse(null);

		Road fourLaneCity2 = otherCity.getRoads().stream().filter(r -> r.getLanes() == 4).findFirst().orElse(null);
		Road threeLaneCity2 = otherCity.getRoads().stream().filter(r -> r.getLanes() == 3).findFirst().orElse(null);
		Road twoLaneCity2 = otherCity.getRoads().stream().filter(r -> r.getLanes() == 2).findFirst().orElse(null);

		// Four lane can be connected only to three lane road
		if (fourLane != null || fourLaneCity2 != null) {
			return 3;
		}

		// three lane road can be connected to 4 or 2 lane road
		if (threeLane != null || threeLaneCity2 != null) {
			List<Integer> allowedTypes = Arrays.asList(2, 4);
			// already 4 national highway are there so can be only 2 lane
			if (nationalHighway == nationalHighwayLimit) {
				return 2;
			}
			return allowedTypes.get(random.nextInt(allowedTypes.size()));
		}

		if (twoLane != null || twoLaneCity2 != null) {
			List<Integer> allowedTypes = Arrays.asList(1, 3);
			return allowedTypes.get(random.nextInt(allowedTypes.size()));
		}

		// no roads are created from city so only national highway limit would be
		// considered
		return nationalHighway < nationalHighwayLimit ? random.nextInt(4) + 1 : random.nextInt(3) + 1;

	}

	/*
	 * Gets a random city from cities list will check two things first is whether
	 * two cities are same and second is the road connection already there between
	 * two cities
	 */
	private static City chooseRandomCity(City city, Graph graph, List<City> cities) {
		// TODO Auto-generated method stub\
		boolean wrongSelection = true;
		City otherCity = null;
		while (wrongSelection) {
			otherCity = cities.get(random.nextInt(max_no_cities));
			if (otherCity != city && graph.getEdge(city.getCity() + "-" + otherCity.getCity()) == null) {
				wrongSelection = false;
			}
		}

		return otherCity;
	}

	private static List<City> generateAndPrintCities() {
		// TODO Auto-generated method stub
		List<City> cityList = new ArrayList<>();

		for (int i = 0; i <= max_no_cities; i++) {
			String name = "City" + i;

			/*
			 * random will return any value between 0 to 1 so if 0 value will be -90 and 1
			 * the it will be +90 making it within the range of latitude
			 */
			double latitude = random.nextDouble() * 180 - 90;
			double longitude = random.nextDouble() * 360 - 180;
			City cityToAdd = new City(name, latitude, longitude);

			// printing the city
			System.out.println(cityToAdd.toString());

			cityList.add(cityToAdd);

		}

		return cityList;
	}

}

package com.srikanth.data.transform.service;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.srikanth.data.transform.model.Locations;

public class DataTransformService {
	
	Logger logger = Logger.getLogger(DataTransformService.class.getName());

	// To set the date format as per the requirement
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	// This method returns the json string
	public String getJsonObj() {
		String locationsJson = null;
		try {
			// Url for loading data
			String urlStr = "https://sxm-dataservices-samples.s3.amazonaws.com/incidents.xml.gz";

			URL url = new URL(urlStr);
            
			// Using Document builder for parsing XML 
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			DocumentBuilder builder = factory.newDocumentBuilder();

			// Using Url streaming to read directly from cloud
			Document document = builder.parse(new GZIPInputStream(url.openStream()));

			document.getDocumentElement().normalize();

			List<Locations> list = new ArrayList<Locations>();

			// Reading 'ti' node to get 'table' attribute
			NodeList tiList = document.getElementsByTagName("ti");
			
			// Iterating through 'ti' node list in case we have several ti nodes
			for (int l = 0; l < tiList.getLength(); l++) {
				
				Node tiNode = tiList.item(l);
				
				// Converting node to element to read
				Element tiElement = (Element) tiNode;
				
				Integer tableNum = Integer.parseInt(tiElement.getAttribute("table"));
				
				// Getting all 'ev' nodes from document
				NodeList nList = document.getElementsByTagName("ev");

				// Iterating through 'ev' node list
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node node = nList.item(temp);

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element evElement = (Element) node;

						// Creating new 'Locations' object
						Locations loc = new Locations();

						// Extracting required information and setting in Locations object
						loc.set_id(evElement.getElementsByTagName("id").item(0).getTextContent());

						loc.setDescription(evElement.getElementsByTagName("text").item(0).getTextContent());

						loc.setEventCode(
								Integer.parseInt(evElement.getElementsByTagName("ec").item(0).getTextContent()));

						loc.setSeverity(Integer.parseInt(evElement.getElementsByTagName("se").item(0).getTextContent()));

						loc.setValidEnd(evElement.getElementsByTagName("valid").item(0).getAttributes().item(0)
								.getTextContent());

						loc.setValidStart(evElement.getElementsByTagName("valid").item(0).getAttributes().item(1)
								.getTextContent());

						// Extracting 'loc' node
						Node locNode = evElement.getElementsByTagName("loc").item(0);

						// Based on location type 'geo' or 'tmc' extracting the required info and setting in geo and tmc objects
						if (locNode.getAttributes().item(0).getTextContent().equals("geo")) {
							Locations.Geo geo = loc.new Geo();
							geo.setType("Point");

							NodeList nodeList1 = locNode.getChildNodes();

							for (int j = 0; j < nodeList1.getLength(); j++) {
								Node node1 = nList.item(j);

								Element eElement1 = (Element) node1;

								loc.setRoadName(evElement.getElementsByTagName("addr").item(0).getTextContent());
								Double arr[] = {
										Double.parseDouble(evElement.getElementsByTagName("geo").item(0).getAttributes().item(0)
												.getTextContent()),
										Double.parseDouble(evElement.getElementsByTagName("geo").item(0).getAttributes().item(1)
												.getTextContent()) };
								geo.setCoordinates(arr);
							}

							loc.setGeo(geo);
						} else if (locNode.getAttributes().item(0).getTextContent().equals("tmc")) {
							Locations.Tmc tmc = loc.new Tmc();

							NodeList nodeList2 = locNode.getChildNodes();

							for (int k = 0; k < nodeList2.getLength(); k++) {
								Node node2 = nList.item(k);

								Element eElement2 = (Element) node2;

								tmc.setDirection(evElement.getElementsByTagName("start").item(0).getAttributes().item(0)
										.getTextContent());
								tmc.setId(Integer.parseInt(evElement.getElementsByTagName("start").item(0)
										.getAttributes().item(2).getTextContent()));
								tmc.setTable(tableNum);
							}

							loc.setTmc(tmc);
						}

						// Setting default value for type
						loc.setType("TrafficIncident");

						// Setting date value
						loc.setLastUpdated(sdf.format(new Date()));

						// Adding each node to the list.
						list.add(loc);
					}
				}
			}
			
			// Using object mapper to convert to json string
			ObjectMapper jsonMapper = new ObjectMapper();
			
			// Setting a value for indentation
			jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
			
			// Setting "locations" label for the whole object
		    locationsJson = jsonMapper.writeValueAsString(Collections.singletonMap("locations", list));
			
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		// Returning json string
		return locationsJson; 
	}
}
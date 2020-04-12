package netshw03;


import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector; 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataAnalyzer {
	private String baseUrl; 
	private Document currentDoc; 
	
	 //stores the urls for each country
	ArrayList<String>ciaUrls = new ArrayList<String>();
	
	//stores the document for each country
	ArrayList<Document>countriesDocuments = new ArrayList<Document>();
	
	//stores the name of all the countries. the indices of this
	//array corresponds to the ciaUrls, countriesDocuments, and 
	//continent indices
	ArrayList<String>countries = new ArrayList<String>(); 
	
	//stores the continent for each country
	ArrayList<String>continent = new ArrayList<String>();
	
	
	/**
	 *  Constructor that establishes connection to the CIA World Factbook's 
	 *  home page
	 */
	
	public DataAnalyzer() {
		this.baseUrl = "https://www.cia.gov/library/publications/the-world-factbook/";

		try {
			this.currentDoc = Jsoup.connect(this.baseUrl).get();
			countryFiller(); 
			urlFiller();
			docFiller();
			continentFiller(); 

		} catch (IOException e){
			System.out.println("Could not connect to CIA home page!");
		}

	}

	/**
	 * Getter function for the current document
	 * @return currentDoc
	 */

	public Document getCurrentDoc() {
		return currentDoc;
	}
	
	/**
	 * Getter function for the urls array
	 * @return ciaUrls
	 */

	public ArrayList<String> getCiaUrls() {
		return ciaUrls;
	}

	/**
	 * Getter function for the countries array
	 * @return countries
	 */
	
	public ArrayList<String> getCountries() {
		return countries;
	}
	
	/**
	 * Getter function for the documents of all 
	 * the countries
	 * @return countriesDocuments
	 */
	
	public ArrayList<Document> getDocs() {
		return countriesDocuments;
	}
	
	/**
	 * Getter function for the continent array
	 * @return continent
	 */

	public ArrayList<String> getContinent() {
		return continent;
	}


	/**
	 * Helper function that fills in the countries array with appropriate 
	 * countries
	 * @throws IOException
	 */

	private void countryFiller() {
		Elements toAdd = currentDoc.select("select option"); 
		for (Element e : toAdd) {
			String z = e.text();
			if (!z.equals("World") && 
					!z.equals("Please select a country to view") && 
					!z.equals("European Union"))
				countries.add(z); 
		}
	}
	
	/**
	 * Helper function that fills the document array with
	 * a country's document. The index of the document corresponds
	 * to the country's name in the countries array
	 */
	
	private void docFiller() {
		for (String s : ciaUrls) {
			try {
				countriesDocuments.add(Jsoup.connect(s).get());
			} catch (IOException e) {
				System.out.println("Cannot Connect");
			}
		}
	}

	/**
	 * Helper function that fills the ciaUrls array with
	 * a country's url. The index of the url corresponds
	 * to the country's name in the countries array
	 */
	
	private void urlFiller() {

		Elements links = currentDoc.getElementsByClass("text-holder-full");
		Elements toAdd = links.select("option"); 
		for (Element e : toAdd) {
			String z = e.attr("value");
			//filters out the urls for the European Union and the World
			if (!z.equals("geos/xx.html") && !z.equals("geos/ee.html") && !z.equals("")) {
				ciaUrls.add(this.baseUrl + z); 
			}
		}
	}

	/**
	 * Helper function that fills the continent array with
	 * a country's continent. The index of the continent corresponds
	 * to the country's name in the countries array
	 */
	
	private void continentFiller() {
		for (Document e : countriesDocuments) {

			Element toAdd = e.getElementById("geos_title");  
			String g = toAdd.text();
			String z = g.substring(0, g.lastIndexOf(":") - 2); 
			if (z.equals("South Asia") || z.equals("Middle East") 
					|| z.equals("East Asia/Southeast Asia")) {
				continent.add("Asia");
			} else if (z.equals("Australia - Oceania")) {
				continent.add("Australia");
			} else if (z.equals("Central America")) {
				continent.add("North America"); 
			} else {
				continent.add(z);
			}

		}
	}


	/**
	 * This is the method to answer Question 1
	 * @param continents
	 * @param disaster
	 * @return An array of string corresponding to which countries in
	 *         the continent have the natural disaster
	 */
	
	public ArrayList<String> naturalDisaster(String continents, String disaster) {
		ArrayList<String> toReturn = new ArrayList<String>(); 
		for (Document e : countriesDocuments) {
			int index = countriesDocuments.indexOf(e);
			Element toFind = e.getElementById("field-natural-hazards");
			if (continent.get(index).equals(continents) && toFind.text().contains(disaster)) {
				toReturn.add(countries.get(index)); 	
			}
		}
		return toReturn; 
	}
	
	/**
	 * This is the method to answer Question 2
	 * @param object in the flag that you want to find
	 * @return
	 */

	public ArrayList<String> countriesWithObjectInFlag(String object) {
		ArrayList<String> toReturn = new ArrayList<String>(); 
		for (Document e : countriesDocuments) {
			int index = countriesDocuments.indexOf(e);
			Elements toFind = e.getElementsByClass("modalFlagDesc");
			if (toFind.text().contains(object)) {
				toReturn.add(countries.get(index)); 	
			}
		}
		return toReturn;
	}

	
	/**
	 * This is the method to answer Question 3
	 * @param continent
	 * @return String which is the name of the country that has the lowest elevation
	 *         in the specified continent
	 */
	
	public String lowestElevation(String continents) {

		ArrayList<Double> elevations = new ArrayList<Double>();
		for (Document e : countriesDocuments) {
			int index = countriesDocuments.indexOf(e);
			if (continent.get(index).equals(continents)) {
				Element toFind = e.getElementById("field-elevation");
				if (toFind == null) {
					//adding 1000 if there is not elevation information for that country
					elevations.add(index, 1000.0); 
				} else {
					String z = toFind.text();
					Pattern r = Pattern.compile(".*lowest.*point.*\\s(-?\\d+\\.?\\d*)\\sm\\shighest.*");
					Matcher forR = r.matcher(z);
					String finder = ""; 
					while (forR.find()) {
						finder = forR.group(1); 
					}
					elevations.add(index, Double.parseDouble(finder));
				}

			} else {
				//adding 1000 if the country is not in continent
				elevations.add(index, 1000.0); 
			}
		}
		double minElevation = Collections.min(elevations);
		int minIndex = elevations.indexOf(minElevation); 
		return countries.get(minIndex);
	}
	
	/**
	 * This is the method to answer Question 4
	 * @param continents
	 * @param percentage
	 * @param landtype
	 * @return Returns the countries in the specified continent whose forests
	 *         makes up more than the specific percentage of land
	 */

	public ArrayList<String> forestInContinent(String continents, double percentage, String landtype) {
		ArrayList<String> toReturn = new ArrayList<String>(); 
		for (Document e : countriesDocuments) {
			int index = countriesDocuments.indexOf(e); 
			if (continent.get(index).equals(continents)) {
				Element toFind = e.getElementById("field-land-use"); 
				if (toFind != null) {
					String z = toFind.text(); 
					Pattern r = Pattern.compile(".*" + landtype + ".*\\s(\\d+\\.?\\d*).*\\sother.*");
					Matcher forR = r.matcher(z);
					String finder = ""; 
					if (forR.find()) {
						finder = forR.group(1); 
						if (Double.parseDouble(finder) >= percentage) {
							toReturn.add(countries.get(index)); 
						}
					}
				}
			}
		}
		return toReturn; 
	}

	/**
	 * This method answers Question 5
	 * @param topNumber
	 * @return An array of Strings of countries that have the highest elevation.
	 * 		   The number of countries returned is depended on the top number parameter
	 */
	
	public ArrayList<String> electricityCapita(int topNumber) {
		ArrayList<String> toReturn = new ArrayList<String>();
		ArrayList<Double> capita = new ArrayList<Double>();
		for (Document e : countriesDocuments) {
			int index = countriesDocuments.indexOf(e);
			Element population = e.getElementById("field-population");
			Element electricity = e.getElementById("field-electricity-consumption"); 
			if (population != null && electricity != null) {
				
				//to find population
				String z = population.text(); 
				Pattern r = Pattern.compile("(^.*\\d+)\\s\\(.*");
				Matcher forR = r.matcher(z); 
				String withoutComma = "";
				double popSize = 0.0; 
				if (forR.find()) {
					String finder = forR.group(1); 
					withoutComma = finder.replace(",", ""); 
					popSize = Double.parseDouble(withoutComma);
					//special case 
				} else if (z.contains("million")) {
					popSize = 3997000;
					//special case
				} else if (z.contains("United Kingdom")) {
					popSize = 65761117; 
				}

				//to find electricity consumption
				String g = electricity.text();

				double val = 0.0; 
				if (g.charAt(0) == '0') {
					val = 0.0; 

				} else if (g.contains("million")) {
					String j = g.substring(0, g.indexOf("million") - 1);
					val = Double.parseDouble(j);

				} else if (g.contains("billion")) {
					String j = g.substring(0, g.indexOf("billion") - 1);
					val = Double.parseDouble(j) * 1000;

				} else if (g.contains("trillion")){
					String j = g.substring(0, g.indexOf("trillion") - 1);
					val = Double.parseDouble(j) * 1000 * 1000;
				
				} else if (g.contains("kWh") && g.charAt(0) != '0') {
					String j = g.substring(0, g.indexOf("kWh") - 1);
					String h = j.replace(",", ""); 
					val = Double.parseDouble(h) / 1000000;

				}
				
				double electricityPerCapita = val / popSize; 
				capita.add(index, electricityPerCapita); 
				
			} else {
				//if a country does not have population or electricity information
				capita.add(index, -1.0); 
			}
		}
		
		for (int i = 0; i < topNumber; i++) {
			double maxValue = Collections.max(capita);
			int maxIndex = capita.indexOf(maxValue); 
			toReturn.add(countries.get(maxIndex));
			capita.remove(maxIndex); 
		}

		return toReturn; 

	}
	
	
	/**
	 * This method answers question number 5
	 * @param percentage
	 * @return An array of String of countries that have dominant ethics group that
	 * 		   makes up at least specific percentage of the population of the country
	 */
	public ArrayList<String> ethnicGroup(double percentage) {
		ArrayList<String> toReturn = new ArrayList<String>(); 
		for (Document e : countriesDocuments) {
			int index = countriesDocuments.indexOf(e);
			Element ethnic = e.getElementById("field-ethnic-groups");
			if (ethnic != null) {
				String z = ethnic.text();

				if (z.contains("%")) {
					String g = z.substring(0, z.indexOf("%") + 1); 

					String ethnicPercentage = g.substring(g.lastIndexOf(" ") + 1, g.indexOf("%"));

					String country = g.substring(0, g.lastIndexOf(" ")); 

					//this was for a special case where the percentage was given in a range format
					if (ethnicPercentage.contains("-")) { 
						String furtherTrim = ethnicPercentage.substring(0, 1); 
						if (Double.parseDouble(furtherTrim) >= percentage) {
							toReturn.add(countries.get(index) + " (Dominant Ethnic Group: " 
						                                                      + country + ")");
						}
					//this was for a special case where the percentage was very approximate
					} else if (ethnicPercentage.contains("~")){
						String furtherTrim = ethnicPercentage.substring(1, 2); 
						if (Double.parseDouble(furtherTrim) >= percentage) {
							toReturn.add(countries.get(index) + " (Dominant Ethnic Group: " 
						                                                     + country + ")");
						}

					} else {
						if (Double.parseDouble(ethnicPercentage) >= percentage) {
							toReturn.add(countries.get(index) + " (Dominant Ethnic Group: " 
						                                                     + country + ")"); 

						}
					}
				}
			}
		}
		return toReturn; 
	}

	
	/**
	 * This is a method to answer Question 7
	 * @return Returns an Array of String with countries that are entirely 
	 *         landlocked by landlocked countries 
	 */
	
	public ArrayList<String> doublyLandlocked() {
		ArrayList<String> toReturn = new ArrayList<String>(); 
		for (Document e : countriesDocuments) {
			int index = countriesDocuments.indexOf(e);
			Element coastLine = e.getElementById("field-coastline");
			if (coastLine != null) {
				if (coastLine.text().contains("doubly landlocked")) {
					toReturn.add(countries.get(index));
				} 
			} 
		}
		return toReturn; 
	}
	
	/**
	 * This is a method to answer Question 8
	 * @return Returns a double that is the average life expectancy across
	 * 	       all the countries the world
	 */
	public double avgLifeExpectancy() {
		int counter = 0; 
		double sum = 0; 
		for (Document e : countriesDocuments) {
			Element lifeExpectancy = e.getElementById("field-life-expectancy-at-birth"); 
			if (lifeExpectancy != null) {
				counter++; 
				String g = lifeExpectancy.text(); 

				if (!g.contains("NA")) {
					Pattern r = Pattern.compile("total\\spopulation\\:\\s(\\d+\\.?\\d*).*");
					Matcher forR = r.matcher(g); 
					if (forR.find()) {
						String z = forR.group(1); 
						sum = sum + Double.parseDouble(z);
					}
				}
			}
		}
		return sum / counter;
		
	}

}

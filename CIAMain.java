package netshw03;

/**
 * In CIAMain.java there are 8 questions that are commented. Choose a question you 
 * want the answer for and uncomment it. Then, change the variables above the first
 * System.out.println statement to any appropriate variable that you want to
 * investigate. Once the solution is printed, comment that question back and uncomment
 * another question that you want to investigate. 
 * @author suvaskota
 *
 */

public class CIAMain {

	public static void main(String[] args) {
		DataAnalyzer cia = new DataAnalyzer();
		

		//Question 1: 
//		String continent = "Asia"; //change to any continent you prefer
//		String disaster = "earthquake"; //change to any natural disaster you are looking for
//		System.out.println("The countries prone to " + disaster + " in " + continent + " are:");
//		System.out.println("-------------"); 
//		for (String m : cia.naturalDisaster(continent, disaster)) {
//			System.out.println(m); 
//		}
		
		
		//Question 2:
//		String object = "star"; //change to any object you are looking for in the flag
//		System.out.println("The countries with a " + object + " in their flag are:");
//    	System.out.println("-------------"); 
//		for (String m : cia.countriesWithObjectInFlag(object)) {
//			System.out.println(m);
//		}
		
		
		
		//Question 3:
//		String continent = "Europe"; //change to any continent you prefer
//		System.out.println("The country with the lowest elevation in " + continent + " is:");
//		System.out.println("--------------");
//		System.out.println(cia.lowestElevation(continent));
		
		
		
		//Question 4: 
//		String continent = "Asia"; //change to any continent you prefer
//		double percentage = 50.0; //any percentage you want to investigate
//	    String landtype = "forest"; //choose between "agricultural", and "forest"
//	    System.out.println("The countries in " + continent + " with a landtype of " 
//	                        + landtype + " greater than " + percentage + " percent is:"  );
//	    System.out.println("--------------");
//		for (String m : cia.forestInContinent(continent, percentage, landtype)) {
//			System.out.println(m);
//		}
		 
		
		
		//Question 5
//		int countryList = 5; //choose how long the list of rankings should be
//		for (String m : cia.electricityCapita(countryList)) {
//			System.out.println(m); 
//		}
		
		
		//Question 6
//		double percentage = 80.0; //change to any percentage you want to investigate
//		for (String m : cia.ethnicGroup(percentage)) {
//			System.out.println(m); 
//		}
		
		
		
		//Question 7
//		for (String m : cia.doublyLandlocked()) {
//			System.out.println(m);
//		}
		
		
		
		//Question 8
//		System.out.println(cia.avgLifeExpectancy()); 
	}

}

# Java-App-for-Web-Data-Extraction
 Programmed a Java application from scratch that harvests data from the CIA World Factbook.  
 Created algorithms to conduct analysis on the data after pre-processing it in order to
 answer statistical  questions about countries.  Users can change variables to the algorithms 
 to answer variants on the question
 
README for Getting and Analyzing Data from the Internet – The CIA World Factbook
---------------------------------------------------------------------------------
Description of Files:
---------------------------------------------------------------------------------
DataAnalyzer.java contains all the methods that find the answers
for the various questions and contains the constructor that
establishes the link to the main CIA web page. The main CIA web page
is the only URL that is hard-coded

CIAMain.java is the class with the main method from which users can
ask the questions, input various variables and the answers to the 
question will be printed to the console. Below are the 8 questions
that I designed my program around. Note: The words with quotation
marks are the variables for the question that can be changed.

Question 1: List countries in "South America" that are prone to "earthquakes"

Question 2: List all countries that have a "star" in their flag

Question 3: Find the country with the lowest elevation point in "Europe"

Question 4: List countries in "Asia" have at least "50%" of their land 
            covered in "forest"

Question 5: Find the top "5" countries with the highest electricity 
			consumption per capita. (Electricity consumption / population)

Question 6:  List countries (along with the ethnic group) where the dominant
             ethnic group accounts for more than "80%" of the population

Question 7:  There are certain countries that are entirely land-locked by other
			 land-locked countries. Find these countries

Question 8: What is the average life expectancy worldwide? 

---------------------------------------------------------------------------------
How the Program Works: 
---------------------------------------------------------------------------------
This program uses Jsoup, Regex and the substring methods to extract data
from the CIA World Factbook. First, a connection is established with the
CIA World Factbook, then the URLs for each country are extracted and stored.
Then, a document array is created that stores the html web page for each country
For each method to answer the question, the document array is iterated over,
and any information is extracted using Jsoup, Regex and/or substring methods and 
then stored to compute the answer. An important note is that none of the data is 
stored locally so every time the program is run, it extracts the most recent 
information.

---------------------------------------------------------------------------------
How to Run the Program:
---------------------------------------------------------------------------------
In CIAMain.java there are 8 questions that are commented. Choose a question you 
want the answer for and uncomment it. Then, change the variables above the first
System.out.println statement to any appropriate variable that you want to
investigate. Once the solution is printed, comment that question back and uncomment
another question that you want to investigate. 

---------------------------------------------------------------------------------
Assumptions: 
---------------------------------------------------------------------------------
- Apart from the Oceans and European Union, I assumed that all the others were
  countries, including territories like Puerto Rico, British Indian Ocean Territory 
  and others.

- In the CIA World Factbook, the continent tags included Central America and the 
  Middle East as well but on Wikipedia, I found that all the countries in Central
  America are part of the North America continent and all the countries in the 
  Middle East, Southeast Asia, South Asia are part of the Asia continent so this 
  adjustment was made when storing the continents for each country so that they are 
  the standard 7 continents
  
- For the lowest elevation method, I assumed that the countries without a elevation
  section on the web page had an elevation of 1000 to make it easier to find the 
  minimum from the collection of elevations
  
- For the Electricity Consumption method, there were two special cases that were
  commented that could not be accounted with Regex or Jsoup so those two special
  cases were hard-coded when finding the population number. For the electricity 
  consumption part, I converted all the values to the scale of millions. If a
  country did not have a population or electricity field, then I input -1 as the
  default value

- For the Ethnic group method, there were two special cases. One, where the 
  percentage had a range in it so I took the bottom end of the range for that 
  computation. The second case was where there was a '~' character in the 
  percentage so I had to account for that differently. Also, the ethnic
  groups a listed in descending order of percentage, I only parsed for 
  the first ethnic group and its percentage to compute the solution for
  this question

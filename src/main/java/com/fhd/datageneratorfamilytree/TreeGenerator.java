/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhd.datageneratorfamilytree;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.sun.jersey.api.client.ClientResponse;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.familysearch.api.client.ft.ChildAndParentsRelationshipState;
import org.familysearch.api.client.ft.FamilySearchFamilyTree;
import static org.familysearch.api.client.util.FamilySearchOptions.reason;
import org.gedcomx.conclusion.Fact;
import org.gedcomx.conclusion.Name;
import org.gedcomx.conclusion.NamePart;
import org.gedcomx.conclusion.Person;
import org.gedcomx.rs.client.PersonState;
import org.gedcomx.rs.client.RelationshipState;
import org.gedcomx.types.FactType;
import org.gedcomx.types.GenderType;
import org.gedcomx.types.NamePartType;
//import org.gedcomx.rs.client.util.GedcomxPersonSearchQueryBuilder;
//import org.gedcomx.types.FactType;
//import org.gedcomx.types.GenderType;
//import org.gedcomx.types.NamePartType;
//import org.familysearch.platform.ct.json.*;
//import org.gedcomx.conclusion.json.*;
//import org.gedcomx.common.json.*;
//import org.gedcomx.types.json.*;

/**
 * Generates the JSON needed to make a request to create persons
 * @author davidkhanks
 */
public class TreeGenerator {

//    List<Person> personList = new ArrayList();
    List<String> surnames = new ArrayList();
    List<String> boysNames = new ArrayList();
    List<String> girlsNames = new ArrayList();
    String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
    int[] dates = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
    

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TreeGenerator treeGen = new TreeGenerator();
           
           treeGen.run();
    }
    
    /**
     * The run "do work method"
     */
    public void run() {
        //Get the username and the the string representations of where the csv names files are located 
        //in the file system
        String user = System.getProperty("user.name");
        String surnamesFile = "src/main/resources/surnames.csv";
        String girlsNamesFile = "src/main/resources/girls_names.csv";
        String boysNamesFile = "src/main/resources/boys_names.csv";
        
        //Read in the three files into lists of names
        BufferedReader br = null;
        String line = "";
        
        //Surnames list
        try {
            br = new BufferedReader(new FileReader(surnamesFile));
            while((line = br.readLine()) != null) {
                String[] name = line.split(",");
                surnames.add(name[0]);
                
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Girls names list
        try {
            br = new BufferedReader(new FileReader(girlsNamesFile));
            while((line = br.readLine()) != null) {
                String[] name = line.split(",");
                girlsNames.add(name[0]);
                
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Boys names list
        try {
            br = new BufferedReader(new FileReader(boysNamesFile));
            while((line = br.readLine()) != null) {
                String[] name = line.split(",");
                boysNames.add(name[0]);
                
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
        //Credentials for authenticating and hitting the endpoint
        boolean useSandbox = false; //whether to use the sandbox reference or not; false will pass the beta URI to the constructor
        String username = "tum000504001";
        String password = "1234pass";
        String developerKey = "WCQY-7J1Q-GKVV-7DNM-SQ5M-9Q5H-JX3H-CMJK";//Works but only for small sets of API calls as it is throttled
        String JPKey = "R7Y9-HLQJ-DHGG-JHXZ-3FB4-Q7GR-9TZL-JFKN";//Does not work currently
        String DougKey = "WCQY-7J1Q-GKVV-7DNM-SQ5M-9Q5H-JX3H-CMJK"; //This key looks to be unthrottled
        String betaURIstring = "https://beta.familysearch.org/platform/collections/tree";
        java.net.URI betaURI = java.net.URI.create(betaURIstring);
        String lookUpPID = "LV4V-6MY";
        boolean startWithHusband = false;
        
        //Instantiated a FamilySearchFamilyTree obj. This establishes authentication and allows the endpoint to be hit for 
        //creating various people and types of relationships/add artifacts, etc
        FamilySearchFamilyTree ft;
        
        if(useSandbox){
            ft = new FamilySearchFamilyTree(useSandbox)
            .authenticateViaOAuth2Password(username, password, DougKey);
        } else {
            ft = new FamilySearchFamilyTree(betaURI)
            .authenticateViaOAuth2Password(username, password, DougKey);
        }
        
//        PersonState person = ft.readPersonById(lookUpPID);
//        System.out.println(person.getName().getNameForms().get(0).getFullText());

        
        createPerson(ft, true, "1", "September", "1900", "Eagle Point, Oregon", "1", "January", "2000", "Beaverton, Oregon", "This is simply a test");
        
        
        //Print out the names of all people in the newly created tree, their parents, and respective URI's to make it easy to find them in the tree
//        printTree(basePerson);

    }
    
    /**
     * Randomly selects a month and returns it as a string
     * @return month the randomly chosen month
     */
    public String getRandMonth() {
        String month = months[0 + (int)(Math.random() * ((11 - 0) + 1))];
        return month;
    }
    
    /**
     * Randomly returns a date based on the month that was passed in as a parameter
     * @param month the month is needed to know where the limit is i.e. February only has 28 days
     * @return 
     */
    public int getRandDate(String month) {
        int date = 1;
        if (month == "Feb") {
            date = 1 + (int)(Math.random() * ((28 - 1) + 1));
        } else if (month == "Apr" || month == "June" || month == "Sept" || month == "Nov") {
            date = 1 + (int)(Math.random() * ((30 - 1) + 1));
        } else {
            date = 1 + (int)(Math.random() * ((31 - 1) + 1));
        }
        
        return date;
    }
    
    public String getFirstName(boolean male) {
        String name;
        
        if(male){
            name = boysNames.get(0 + (int)(Math.random() * (((boysNames.size()-1) - 0) + 1)));
        }else {
            name = girlsNames.get(0 + (int)(Math.random() * (((girlsNames.size()-1) - 0) + 1)));
        }
        name = name.replace("\"", "");
        return name;
    }
    
    public String getSurName() {
        String name = surnames.get(0 + (int)(Math.random() * (((surnames.size()-1) - 0) + 1)));
        return name;
    }
    
     public GenderType getGenderType(String gender) {
        if(gender == "Male") {
            return GenderType.Male;
        } else {
            return GenderType.Female;
        }
    }
    
    
    public Map<String, String> createPerson(FamilySearchFamilyTree ft, boolean male, 
            String birthDate, String birthMonth, String birthYear, String birthPlace, 
            String deathDate, String deathMonth, String deathYear, String deathPlace, String reason) {
          
        String gender;   
        if(male) {
            gender = "Male";
        }else {
            gender = "Female";
        }
        
        String given = getFirstName(male);
        String surName = getSurName();
        
        try{
            PersonState person = ft.addPerson(new Person()
                    //Use the name of the person info object; the .preferred(true) needs to be used or the call will not work
                    .name(new Name(given + " " + surName, new NamePart(NamePartType.Given, given), new NamePart(NamePartType.Surname, surName)).preferred(true))
                    //Get the GenderType from the PersonInfo obj depending on the gender string
                    .gender(getGenderType(gender))
                    //born "dd Month yyyy"
                    .fact(new Fact(FactType.Birth, birthDate + " " + birthMonth + " " + birthYear, birthPlace))
                    //died in "dd Month yyyy"
                    .fact(new Fact(FactType.Death, deathDate + " " + deathMonth + " " + deathYear, deathPlace)).living(false),
                    //with a message as to why
                    reason(reason)
                ).ifSuccessful();
            System.out.println(person.getSelfUri());
            
            PersonState readPerson = ft.readPerson(person.getSelfUri());
//            ft.readPersonById(given, options);
            System.out.println(readPerson.getName().getNameForms().get(0).getFullText());
            System.out.println(readPerson.getName().getNameForms().get(0).getParts().get(0).getValue());
            System.out.println(readPerson.getName().getNameForms().get(0).getParts().get(1).getValue());
        } catch(Exception e) {
            e.printStackTrace();
        }
//        person.getName().getNameForm().getParts().get(0).getValue();
        

        Map<String, String> personMap = new HashMap();
//        person.put("firstName", null);
//        person.put("lastName", null);
//        person.put("", null);
//        person.put("", null);
//        person.put("", null);
//        person.put("", null);
        return personMap;
    }
    
    /**
     * 
     * @param ft FamilySearchFamilyTree for authenticating
     * @param numGen the number of generations to create; 6 generations takes about 13 minutes because it has to wait for the api
     */
    public void startPopulateTree(FamilySearchFamilyTree ft, int numGen) {
        //This instantiates a peron creator object which deals with PersonInfo objects and 
        //encapsulates methods from the FamilySearch SDK that is being used.
        PersonCreator pc = new PersonCreator();
        //Make the initial person and spouse
        pc.createPerson("Girlsname", "LastName", "Female", "3", "Apr", "1836", "Eagle Point, Oregon", "7", "February", "1900", "Beaverton, Oregon", "This is the starting person" );
        pc.createPerson("Edward", "Banks", "Male", "3", "Apr", "1836", "Eagle Point, Oregon", "7", "February", "1900", "Beaverton, Oregon", "This is the starting spouse" );
        
        List<PersonInfo> people = pc.getPersons();
        
        //Create a nodes for the two starting people so that we can create a recursive function
        //to make new generations
        TreeNode basePerson = new TreeNode(people.get(0));
        TreeNode baseSpouse = new TreeNode(people.get(1));
        basePerson.setSpouse(baseSpouse);
        baseSpouse.setSpouse(basePerson);
        
        
        
        //Hit the endpoint to create the base two people in sandbox.familysearch.org
        pc.pushToWeb(ft, people.get(0));
        pc.pushToWeb(ft, people.get(1));
        
        //Hit the endpoint to create the couple relationship in sandbox.familysearch.org
        //These two people must have had pushToWeb called so that they will have a Uri
        //Otherwise the husband and wife PersonState objects will be null and no relationship will be made. 
        try {
            PersonState wife = ft.readPerson(basePerson.getPerson().getUri()).ifSuccessful();
            PersonState husband = ft.readPerson(baseSpouse.getPerson().getUri()).ifSuccessful();
            RelationshipState coupleRelationship = ft.addSpouseRelationship(husband, wife, reason("Starting couple")).ifSuccessful();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        //Call the recursive function passing in the number of generations to create, the node to start with, 
        //the Person creator to use, and the FamilySearchFamilyTree object to use for hitting the endpoint
        populateTree(numGen, basePerson, pc, ft);
    }
    
    /**
     * Recursively generates a pedigree, given a person to start with and the number of generations to run
     * @param count the number of generations that are desired
     * @param base the starting person of the pedigree
     * @param pc the person creator object that creates each person and adds each person object
     * @param ft the FamilySearchFamilyTree obj that makes the connection to the endpoint 
     * to its list as to be able to iterate quickly through all people in the pedigree
     */
    public void populateTree(int count, TreeNode base, PersonCreator pc, FamilySearchFamilyTree ft) {
        
        //Base case to exit recursion
        if (count < 1) {
            return;
        }
        
        
        //Get random first names for the father and the mother, use the same last name for the father,
        //get a random last name for the mother.
        String fatherFirst = getFirstName(true);
        fatherFirst = fatherFirst.replace("\"", "");
        String motherFirst = getFirstName(false);
        motherFirst = motherFirst.replace("\"", "");
        String surname = getSurName();
        
        //Get the month and date for use in birth and death dates
        String month = getRandMonth();
        int date = getRandDate(month);
        
        
        //Get the birth year of the base node of this run through and go back 16 - 45 years
        //(The parents will be 16-45 years older)
        //The lifespan will be 80 years, this could be changed to a random number but for now it's easier to just leave it
        //After doing the math, cast back into strings as per the library that is being used.
        String formalYear = base.getPerson().getBirthYear();
        int year = Integer.parseInt(formalYear);
        year = year - (16 + (int)(Math.random() * ((45 - 16) + 1)));
        int deathYear = year + 80;
        String inputYear = Integer.toString(year);
        String parentDeathYear = Integer.toString(deathYear);
        
        String birthDate = Integer.toString(date);
        
        //If there is not a father set, create a new node and set the node's person to a new person
        //Hit the endpoint and set the base node's father to this new node
        //recurse starting with the father as the new base node
        if (!base.fatherExists()) {
            TreeNode father = new TreeNode();
            father.setPerson(pc.createPerson(fatherFirst, base.getPerson().getSurName(), "Male", birthDate, month, inputYear, base.getPerson().getBirthPlace(), birthDate, month, parentDeathYear, base.getPerson().getDeathPlace(), "This is a test, person is male"));
            pc.pushToWeb(ft, father.getPerson());
            base.setFather(father);
            populateTree(count - 1, father, pc, ft);
        }
        
        //Get a new day and month for the mother, use the same years for simplicity
        month = getRandMonth();
        date = getRandDate(month);
        birthDate = Integer.toString(date);
        
        //If there is not a mother set, create a new node and set the node's person to a new person
        //Hit the endpoint and set the base node's mother to this new node
        //recurse starting with the mother as the new base node
        if (!base.motherExists()) {
            TreeNode mother = new TreeNode();
            mother.setPerson(pc.createPerson(motherFirst, surname, "Female", birthDate, month, inputYear, base.getPerson().getBirthPlace(), birthDate, month, parentDeathYear, base.getPerson().getDeathPlace(), "This is a test, person is female"));
            pc.pushToWeb(ft, mother.getPerson());
            base.setMother(mother);
            populateTree(count - 1, mother, pc, ft);
        }
        

        //Instantiate new PersonState objects for the father, mother, and child. 
        //Read the person from the web by the Uri that was returned when the person was created on the web
        PersonState father = ft.readPerson(base.getFather().getPerson().getUri());
        PersonState mother = ft.readPerson(base.getMother().getPerson().getUri());
        PersonState child = ft.readPerson(base.getPerson().getUri());
        
        //Hit the endpoint to create a parent/child relationship
        try {
            ChildAndParentsRelationshipState chap = ft.addChildAndParentsRelationship(child, father, mother, reason("This is a test, child/parent relationship")).ifSuccessful();
            System.out.println("Child relationship for: " + base.getPerson().getFullName());
            System.out.println("\tFather: " + base.getFather().getPerson().getFullName());
            System.out.println("\tMother: " + base.getMother().getPerson().getFullName());
            System.out.println("Current run time: " + pc.getCurrentRunTime() + "ms");
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        //Hit the endpoint to create a marriage relationship for the current couple
        try {
            RelationshipState coupleRelationship = ft.addSpouseRelationship(father, mother, reason("This is the marriage relationship"));
            System.out.println("Marriage relationship: " );
            System.out.println("\tHusband: " + base.getFather().getPerson().getFullName());
            System.out.println("\tWife: " + base.getMother().getPerson().getFullName());
            System.out.println("Current run time: " + pc.getCurrentRunTime() + "ms");
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        base.getFather().setSpouse(base.getMother());
        base.getMother().setSpouse(base.getFather());
        
    }
    
    /**
     * Prints out all generations in the pedigree given a TreeNode obj
     * @param base the starting node
     */
    public void printTree(TreeNode base) {
        boolean printFather = false;
        boolean printMother = false;
        
        System.out.println("Person: " + base.getPerson().getFullName());
        System.out.println("Uri: " + base.getPerson().getUri());
        if(base.fatherExists()) {
            System.out.println("Father: " + base.getFather().getPerson().getFullName());
            System.out.println("Uri: " + base.getFather().getPerson().getUri());
            printFather = true;
        }
        
        if(base.motherExists()) {
            System.out.println("Mother: " + base.getMother().getPerson().getFullName());
            System.out.println("Uri: " + base.getMother().getPerson().getUri());
            printMother = false;
        }
        
        System.out.println();
        if(printFather || printMother) {
            printTree(base.getFather());
            printTree(base.getMother());
        }
        
    }
    
    
    /**
     * Takes any object and creates a JSON string of that object and prints it out to a file
     * @param obj the Object to be printed to a JSON file
     * @param fileName the name of the file without the extension 
     */
    /*public void JSONPrinter(Object obj, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        String json = gson.toJson(obj);
        
        json.replace("g", "G");

        try {
            FileWriter writer = new FileWriter("/Users/davidkhanks/Desktop/" + fileName + ".json");
            writer.write(json);
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhd.datageneratorfamilytree;

import java.util.ArrayList;
import java.util.List;
import org.familysearch.api.client.ft.FamilySearchFamilyTree;
import static org.familysearch.api.client.util.FamilySearchOptions.reason;
import org.gedcomx.conclusion.Fact;
import org.gedcomx.conclusion.Name;
import org.gedcomx.conclusion.NamePart;
import org.gedcomx.conclusion.Person;
import org.gedcomx.rs.client.PersonState;
import org.gedcomx.types.FactType;
//import org.gedcomx.types.GenderType;
import org.gedcomx.types.NamePartType;
//import org.gedcomx.common.json.*;
//import org.gedcomx.conclusion.json.*;
//import org.gedcomx.types.json.*;

/**
 * This class maintains a list of person objects which can then be converted to JSON
 * @author David
 */
public class PersonCreator {
    
    private List<PersonInfo> persons = new ArrayList();
    private long startTime = System.currentTimeMillis();
    private long endTime;
    private long currentTime;
    private int count = 0;
    
    /**
     * Constructor
     */
    public PersonCreator() {

    }
    
    /**
     * 
     * @param givenName the given name of the person
     * @param surName the surname of the person
     * @param gender the person's gender
     * @param birthDate the day of birth
     * @param birthMonth the month of birth
     * @param birthYear the year of birth
     * @param birthPlace the place of birth
     * @param deathDate the date of death
     * @param deathMonth the month of death
     * @param deathYear the year of death
     * @param deathPlace the place of death
     * @param reason the reason that this information is thought to be correct
     * @return 
     */
    public PersonInfo createPerson(String givenName, String surName, String gender, 
            String birthDate, String birthMonth, String birthYear, String birthPlace, 
            String deathDate, String deathMonth, String deathYear, String deathPlace,
            String reason) {
        
        //Instantiate a PersonInfo obj that will hold the information used to populate the API calls
        PersonInfo createdPerson = new PersonInfo(givenName, surName, gender, birthDate, birthMonth, birthYear, birthPlace, deathDate, deathMonth, deathYear, deathPlace, reason);
        
        //add the person to the list of people. This list is probably not even necessary so we should probably get rid of it at some point. 
        //I was using it in a previous iteration of the project and it got carried over
        getPersons().add(createdPerson);
        return createdPerson;
    }
    

    /** Returns the list of persons
     * @return the persons list
     */
    public List<PersonInfo> getPersons() {
        return persons;
    }
    /**
     * Get a person from the list at the given index
     * @param index
     * @return 
     */
    public PersonInfo getPerson(int index) {
        return persons.get(index);
    }
    
    /**
     * Gets the time since the PersonCreator object was instantiated
     * @return currentTime the current run time of the project
     */
    public long getCurrentRunTime() {
        endTime = System.currentTimeMillis();
        currentTime = endTime - startTime;
        
        return currentTime;
    }
    
    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }
    
    public void incrementCount() {
        this.count = this.count + 1;
    }
    
    /**
     * Call the API endpoint and pass it the information necessary to create a person on the tree
     * @param ft the FamilySearchFamilyTree obj used to make the connection to the endpoint
     * @param pi The PersonInfo obj that holds the information needed to make the call to create a new person in the tree
     */
    public void pushToWeb(FamilySearchFamilyTree ft, PersonInfo pi) {
        try {
            PersonState person = ft.addPerson(new Person()
                    //Use the name of the person info object; the .preferred(true) needs to be used or the call will not work
                    .name(new Name(pi.getFullName(), new NamePart(NamePartType.Given, pi.getGivenName()), new NamePart(NamePartType.Surname, pi.getSurName())).preferred(true))
                    //Get the GenderType from the PersonInfo obj depending on the gender string
                    .gender(pi.getGenderType())
                    //born "dd Month yyyy"
                    .fact(new Fact(FactType.Birth, pi.getFullBirthDate(), pi.getBirthPlace()))
                    //died in "dd Month yyyy"
                    .fact(new Fact(FactType.Death, pi.getFullDeathDate(), pi.getDeathPlace())).living(false),
                    //with a message as to why
                    reason(pi.getReason())
                ).ifSuccessful();
            //this will set the Uri of the PersonInfo object to the Uri that is returned from this call.
            //That way we can print it out later to use it for manual lookup, etc
            pi.setUri(person.getSelfUri());
            this.incrementCount();
        System.out.println(this.getCount() + ". Retreived URI for " + pi.getFullName() + ": " + pi.getUri());
        System.out.println("Current run time: " + this.getCurrentRunTime() + "ms");
        
        
        } catch(Exception e) {
            e.printStackTrace();
        } 
        
       
        
    }

    
}

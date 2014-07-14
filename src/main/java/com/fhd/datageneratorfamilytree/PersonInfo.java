/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhd.datageneratorfamilytree;

import org.gedcomx.types.GenderType;

/**
 *
 * @author davidkhanks
 */
public class PersonInfo {
    private java.net.URI uri;
    private String givenName;
    private String surName;
    private String age;
    private String gender;
    private String birthDate;
    private String birthMonth;
    private String birthYear;
    private String birthPlace;
    private String deathDate;
    private String deathMonth;
    private String deathYear;
    private String deathPlace;
    private String reason;
    
    /**
     * Constructor
     */
    public PersonInfo() {
        
    }
    
    /**
     * Overloaded constructor
     * @param givenName the given name
     * @param surName the surname
     * @param gender the gender ex: "Male" or "Female"
     * @param birthDate the date of birth
     * @param birthMonth the month of birth
     * @param birthYear the year of birth
     * @param birthPlace the place of birth
     * @param deathDate the date of death
     * @param deathMonth the month of death
     * @param deathYear the year of death
     * @param deathPlace the place of death
     * @param reason the reason why this information is thought to be correct
     */
    public PersonInfo(String givenName, String surName, String gender, 
            String birthDate, String birthMonth, String birthYear, String birthPlace, 
            String deathDate, String deathMonth, String deathYear, String deathPlace,
            String reason) {
        this.givenName = givenName;
        this.surName = surName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.birthPlace = birthPlace;
        this.deathDate = deathDate;
        this.deathMonth = deathMonth;
        this.deathYear = deathYear;
        this.deathPlace = deathPlace;
        this.reason = reason;
    }

    /**
     * @return the uri
     */
    public java.net.URI getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(java.net.URI uri) {
        this.uri = uri;
    }

    /**
     * @return the firstName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setGivenName(String firstName) {
        this.givenName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getSurName() {
        return surName;
    }

    /**
     * @param surName the surName to set
     */
    public void setSurName(String surName) {
        this.surName = surName;
    }
    
    public String getFullName() {
        return givenName + " " + surName;
    }

    /**
     * @return the age
     */
    public String getAge() {
        return age;    
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * Gets the correct enumerator based on the gender string
     * @return 
     */
    public GenderType getGenderType() {
        if(this.gender == "Male") {
            return GenderType.Male;
        } else {
            return GenderType.Female;
        }
    }

    /**
     * @return the birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the birthMonth
     */
    public String getBirthMonth() {
        return birthMonth;
    }

    /**
     * @param birthMonth the birthMonth to set
     */
    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    /**
     * @return the birthYear
     */
    public String getBirthYear() {
        return birthYear;
    }

    /**
     * @param birthYear the birthYear to set
     */
    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }
    
    /**
     * Returns the full date of birth in dd Month yyyy format
     * @return fullBirthDate "dd Month yyyy" 
     */
    public String getFullBirthDate() {
        return birthDate + " " + birthMonth + " " + birthYear;
    }

    /**
     * @return the birthPlace
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * @param birthPlace the birthPlace to set
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     * @return the deathDate
     */
    public String getDeathDate() {
        return deathDate;
    }

    /**
     * @param deathDate the deathDate to set
     */
    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    /**
     * @return the deathMonth
     */
    public String getDeathMonth() {
        return deathMonth;
    }

    /**
     * @param deathMonth the deathMonth to set
     */
    public void setDeathMonth(String deathMonth) {
        this.deathMonth = deathMonth;
    }

    /**
     * @return the deathYear
     */
    public String getDeathYear() {
        return deathYear;
    }

    /**
     * @param deathYear the deathYear to set
     */
    public void setDeathYear(String deathYear) {
        this.deathYear = deathYear;
    }
    
    /**
     * Returns the full date of death in dd Month yyyy format
     * @return fullDeathDate "dd Month yyyy" 
     */
    public String getFullDeathDate() {
        return deathDate + " " + deathMonth + " " + deathYear;
    }

    /**
     * @return the deathPlace
     */
    public String getDeathPlace() {
        return deathPlace;
    }

    /**
     * @param deathPlace the deathPlace to set
     */
    public void setDeathPlace(String deathPlace) {
        this.deathPlace = deathPlace;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
    
}
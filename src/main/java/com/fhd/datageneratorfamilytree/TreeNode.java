/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fhd.datageneratorfamilytree;

import java.util.ArrayList;
import java.util.List;
//import org.gedcomx.conclusion.json.*;

/**
 *
 * @author davidkhanks
 */
public class TreeNode {
    private TreeNode father;
    private TreeNode mother;
    private TreeNode spouse;
    private List<TreeNode> children = new ArrayList();
    private PersonInfo person;
    
    
    /**
     * Constructor
     */
    public TreeNode() {
        
    }
    
    /**
     * Overloaded constructor for setting a PersonInfo object at time of instantiation
     * @param person PersonInfo obj that will be set for this node
     */
    public TreeNode(PersonInfo person) {
        this.person = person;
    }

    /**
     * @return the father node
     */
    public TreeNode getFather() {
        return father;
    }

    /**
     * @param father the father node to set
     */
    public void setFather(TreeNode father) {
        this.father = father;
    }

    /**
     * @return the mother node
     */
    public TreeNode getMother() {
        return mother;
    }

    /**
     * @param mother the mother node to set
     */
    public void setMother(TreeNode mother) {
        this.mother = mother;
    }

    /**
     * @return the spouse node
     */
    public TreeNode getSpouse() {
        return spouse;
    }

    /**
     * @param spouse the spouse node to set
     */
    public void setSpouse(TreeNode spouse) {
        this.spouse = spouse;
    }

    /**
     * @return the PersonInfo obj of this node
     */
    public PersonInfo getPerson() {
        return person;
    }

    /**
     * @param person the PersonInfo obj to set
     */
    public void setPerson(PersonInfo person) {
        this.person = person;
    }
    
    /**
     * Checks to see if a father node is set
     * @return boolean
     */
    public boolean fatherExists() {
        if (this.father != null) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Checks to see if a mother node is set
     * @return boolean
     */
    public boolean motherExists() {
        if (this.mother != null) {
            return true;
        } else {
            return false;
        }
    }
}

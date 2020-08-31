/**
 * Copyright (c) 2008-2020, MOVES Institute, Naval Postgraduate School (NPS). All rights reserved.
 * This work is provided under a BSD open-source license, see project license.html and license.txt
 */
package edu.nps.moves.dis7.source.generator.pdus;

import java.util.*;

/**
 * Represents one generated class. A generated class has a series of attributes, the
 * order of which is significant. These attributes are used to create instance 
 * variables, getters and setters, and serialization code.
 *
 * @author DMcG
 */

public class GeneratedClass 
{
    /** A list of all the attributes (ivars) of one class */
    protected List<ClassAttribute> classAttributes = new ArrayList<>();
    
    /** A list of attribute names and initial values for those attributes. */
    protected List<InitialValue> initialValues = new ArrayList<>();
    
    /** comments for this generated class */
    private String comment;
    
    /** Name of generated class */
    protected String name;
    
    /** parent class */
    protected String parentClass;
    
    /** alias for class */
    protected String aliasFor;
    
    /** interfaces */
    protected String interfaces;
    
    /** Whether this is an XmlRootElement; used only with XML marshalling */
    protected boolean xmlRootElement = false;

    /** Special case for, e.g., enum collection/subclass */
    protected String specialCase;
    
    /** whether this class should be abstract */
    protected boolean abstractClass = false;
    
    /** Constructor */
    public GeneratedClass()
    {
        
    }
    
    public void setParentClass(String pParentClass)
    {
        parentClass = pParentClass;
    }
    
    public void setInterfaces(String iFaces)
    {
        interfaces = iFaces;
    }
    
    public String getParentClass()
    {
        return parentClass;
    }
    
    public String getInterfaces()
    {
        return interfaces;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String pName)
    {
        name = pName;
    }
    
    /** Add one ivar/attribute to the class
     * @param anAttribute of interest */
    public void addClassAttribute(ClassAttribute anAttribute)
    {
        classAttributes.add(anAttribute);
    }
    
    /** Return a list of all the attributes of the class
     * @return list of {@link ClassAttribute} values */
    public List<ClassAttribute> getClassAttributes()
    {
        return classAttributes;
    }
    
    /** Add one initial value to the class
     * @param anInitialValue of interest */
    public void addInitialValue(InitialValue anInitialValue)
    {
        initialValues.add(anInitialValue);
    }
    
    /** Return a list of all the initial values of the class
     * @return list of {@link InitialValue} settings */
    public List<InitialValue> getInitialValues()
    {
        return initialValues;
    }
    
    /** Set the comments associated with this class
     * @param comments of interest */
    public void setComment(String comments)
    {
        comment = comments;
    }
    
    
    
    /** get the comments associated with this class
     * @return comments associated with this class */
    public String getClassComments()
    {
        return comment;
    }
    
    @Override
    public String toString()
    {
        String result = "Name: " + name + "\n" + "Comment: " + comment + "\n";
        
        for(int idx = 0; idx < classAttributes.size(); idx++)
        {
            ClassAttribute attribute = classAttributes.get(idx);
            String anAttribute = "  Name: " + attribute.getName() + " Comment: " + attribute.getComment() + 
                                 " Kind: " + attribute.getAttributeKind() + " Type:" + attribute.getType() + "\n";
            result = result + anAttribute;
        }
        return result;
    }

    public boolean isXmlRootElement()
    {
        return xmlRootElement;
    }

    public void setXmlRootElement(boolean isXmlRootElement)
    {
        this.xmlRootElement = isXmlRootElement;
    }
    
    public void setSpecialCase(String specCase)
    {
        this.specialCase = specCase;
    }
    
    public String getSpecialCase()
    {
        return specialCase;
    }

    void setAbstract(String tf)
    {
        this.abstractClass = Boolean.parseBoolean(tf);
    }
    
    public boolean isAbstract()
    {
      return abstractClass;
    }

    public void setAliasFor(String aliasFor)
    {
      this.aliasFor = aliasFor;
    }
    
    public String getAliasFor()
    {
      return aliasFor;
    }
}

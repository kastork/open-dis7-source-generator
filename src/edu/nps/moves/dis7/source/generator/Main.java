/**
 * Copyright (c) 2008-2020, MOVES Institute, Naval Postgraduate School (NPS). All rights reserved.
 * This work is provided under a BSD open-source license, see project license.html and license.txt
 */

package edu.nps.moves.dis7.source.generator;

/**
 * GenerateEnumerations.java created on Jul 17, 2019
 * MOVES Institute Naval Postgraduate School, Monterey, CA, USA www.nps.edu
 *
 * @author Mike Bailey, jmbailey@nps.edu
 * @version $Id$
 */
public class Main
{
  // @formatter:off
  public static final String      DEFAULT_LANGUAGE = "java";
  public static final String DEFAULT_SISO_XML_FILE = "xml/SISO/SISO-REF-010.xml";
  public static final String  DEFAULT_PDU_XML_FILE = "xml/dis_7_2012/DIS_7_2012.xml";

  static String enumOutputPath       = "src-generated/java/edu/nps/moves/dis7/enumerations";
  static String pduOutputPath        = "src-generated/java";
  static String jammerOutputPath     = "src-generated/java/edu/nps/moves/dis7/jammers";
  static String objectTypeOutputPath = "src-generated/java/edu/nps/moves/dis7/objecttypes";
  static String entitiesOutputPath   = "src-generated/java/edu/nps/moves/dis7/entities";

  static String enumPackage       = "edu.nps.moves.dis7.enumerations";
  static String pduPackage        = "edu.nps.moves.dis7";
  static String jammerPackage     = "edu.nps.moves.dis7.jammers";
  static String objectTypePackage = "edu.nps.moves.dis7.objecttypes";
  static String entitiesPackage   = "edu.nps.moves.dis7.entities";
  // @formatter:on

  public static void main(String[] args)
  {
    // ENUMERATIONS
    System.out.println("------------- Generating enumerations in "+enumPackage+" -------------");
    edu.nps.moves.dis7.source.generator.enumerations.GenerateEnumerations.main(new String[]{DEFAULT_SISO_XML_FILE, enumOutputPath, enumPackage});

    // PDUS and associated objects, legacy classes
    System.out.println("------------- Generating pdus in "+pduPackage+" -------------");
    System.getProperties().setProperty("xmlpg.generatedSourceDir", pduOutputPath); // legacy parameter passing
    System.getProperties().setProperty("xmlpg.package", pduPackage);
    edu.nps.moves.dis7.source.generator.pdus.Main.main(new String[]{DEFAULT_PDU_XML_FILE, "java"});

    // JAMMERS
    System.out.println("------------- Generating jammers in "+jammerPackage+" -------------");
    edu.nps.moves.dis7.source.generator.entitytypes.GenerateJammers.main(new String[]{DEFAULT_SISO_XML_FILE, jammerOutputPath, jammerPackage});

    // Object types
    System.out.println("------------- Generating object types in "+objectTypePackage+" -------------");
    edu.nps.moves.dis7.source.generator.entitytypes.GenerateObjectTypes.main(new String[]{DEFAULT_SISO_XML_FILE, objectTypeOutputPath, objectTypePackage});

    //ENTITIES
    System.out.println("------------- Generating entity types in "+entitiesPackage+" -------------");
    edu.nps.moves.dis7.source.generator.entitytypes.GenerateEntityTypes.main(new String[]{DEFAULT_SISO_XML_FILE, entitiesOutputPath, entitiesPackage});

    System.out.println("------------- DIS7 source generation complete -------------");

  }
}

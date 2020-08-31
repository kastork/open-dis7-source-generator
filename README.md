# open-dis7-source-generator

This is a project to generate a type-safe java implementation of the DIS Protocol v. 7, IEEE Standard 1278.1-2012 from SISO and IEEE specifications.  This project is written in Java.

**IMPORTANT!**  This project is not complete and is in a testing phase.  Until this notice is removed, use the DIS libraries under the **open-dis-java** and **Enumerations** projects.

**PROGRESS.** All build tasks can now be performed without installing
anything other than Java SDK 14.
No Netbeans, no Ant, no Maven, no Gradle, no jars from who-knows where.

`./gradlew MakeDistribution`

This should open the door to creating a CI/CD pipeline with Github actions or something.

All major IDE's support gradle, usually by way of a plugin.
Just pick your favorite IDE, install the gradle plugin, and import the main `build.gradle` file.
Friends let friend choose their own IDE.

Since we're using a build tool instead of an IDE project, we can employ dependency management, so the apache commons io dependency is now handled with dependency management instead of keeping the jar in git.

The `.gitignore` file now ignores the recommended files for most popular IDEs (Netbeans, Jetbrains, Eclipse, VS Code),
as well as recommended operating system ignores (Windows, Mac, Linux).

<h3>Background</h3>

This work is an update/continuation of the **`open-dis/xmlpg`** project created by the late Don McGregor of the Naval Postgraduate School.  Its goal is twofold:

1. To provide reference implementations of the DIS protocol network messages in several languages.
2. To do so by means of single XML descriptions of the protocol which are then referenced by individual language generators.

While there exists code in the project to generate source in JavaScript, Python and other languages, that code is legacy, and so far only the Java implementation is complete.

This work is driven by two specifications.

* *IEEE Std 1278.1-2012, IEEE Standard for Distributed Interactive Simulation—Application Protocols*
* *SISO-REF-010-v25, 2018-08-29, Simulation Interoperability Standards Organization, Inc.*

The first describes the DIS protocol in detail -- specifying application algorithms as well as the precise format of network data.  The second enumerates specific values for fields within the network data which correspond to actual entities in the real world.

The SISO specification is issued in several file formats.  One of these is XML and that file is used directly by this project.  The IEEE specification is textual and preliminary work was required to describe its contents in several XML files.  Both the SISO file and the IEEE-based XML files used as input to this project are found in the **`XML`** directory.

A **`SAX`** ("Simple API for XML") Java implementation is used to process the XML input files.  String templates for the various output classes are used to define the basic structure of the generated code, and these files are found in the **`stringTemplates/edu/moves/dis7/source/generator`** directory.

<h3>Development Environment</h3>

The Java language is inherently cross-platform and any OS on any hardware for which a Java run-time is available should *theoretically* support running of this project.  However, the configuration used by the initial developer is the following:


1. OpenJdk Java version 14.0.2

The project is hosted on **github.com** and the support files which are used to define the project structure are also included.  Following the procedure below, a simple download, then a small number of additional steps are all that are required to build the source files for a DIS distribution.

<h3>Generation Procedure</h3>

On a command line in the root of this project, type.

`./gradlew MakeDistribution`

Or, in your favorite IDE, VS Code, emacs or Vim create a gradle run configuration invokes the `MakeDistribution` task.

> Note: If you find that the build is running out of memory,
> you can adjust it in the file, `gradle.properties`.


<h3>Deploying Products</h3>

One big change with the gradle build is that the produced `open-dis7-enumerations.jar` file will now run on java 8 and up instead of Java 14 and up.
We needed this to support the use of the library on Android.
The generator program still needs Java 14, so while we are making a java 8 compatible distribution, you still need to use Java 14 to create it.

The build currently creates a single jar that has everything.
There's a bit more build mechanics to do in order to make slimmed down jars as the previous version of this project did.

The built jars will be found in `dis/build/libs`

Some care has to be taken to ensure that any changes in that tree are accounted for
by the source generator beforehand, so that code changes and improvements aren't lost.

1. The generated source         resides in the `src-generated` directory.
2. The generated entity jars    reside  in the `dist` directory.
3. The generated entity javadoc resides in the `dist` directory.

The previous build anticipated being a part of the `open-dis7-java` project.
As is right now, this project doesn't.
We'll fix it when we fix `open-dis7-java` to use an IDE agnostic build.

If you desire to update the `open-dis7-java` project with any or all of the products from this project, clone that project locally, then follow this procedure:

> Note: this is previous behavior, we're working to restore it.

1. Copy the following files **from**<br/>`open-dis7-source-generator/dis/build/libs` **to**<br/>`open-dis7-java/entityjars`:
  * open-dis7-entities-all.jar
  * open-dis7-entities-chn.jar
  * open-dis7-entities-deu.jar
  * open-dis7-entities-nato.jar
  * open-dis7-entities-rus.jar
  * open-dis7-entities-usa-air.jar
  * open-dis7-entities-usa-all.jar
  * open-dis7-entities-usa-land.jar
  * open-dis7-entities-usa-munitions.jar
  * open-dis7-entities-usa-surface.jar
  * **open-dis7-entities-javadoc.jar**
<br/>

2. Developers need to be sure to perform a diff with files already in the open-dis7-java repository so that any changes there are reflected in the source-generation algorithms.

3. Do three separate source-file copy operations to the open-dis7-java project:
	* **Omitting the `entities` directory**, copy the tree of java source files (\*.java) 
<br/>**from**<br/>`open-dis7-source-generator/`**`src-generated`**`/java/edu/nps/moves/dis7` 
<br/>**to**<br/>`open-dis7-java/src-generated/edu/nps/moves/dis7`.
	* Copy the files 
<br/>**from**<br/> `open-dis7-source-generator/`**`src-specialcase`**`/java/edu/nps/moves/dis7`
<br/>**to**<br/> `open-dis7-java/src-generated/edu/nps/moves/dis7`.
	* Copy the files 
<br/>**from**<br/> `open-dis7-source-generator/`**`src-supporting/`**`java/edu/nps/moves/dis7`
<br/>**to**<br/> `open-dis7-java/src-generated/edu/nps/moves/dis7`.
<br/>
<pre> **Or, the above can more easily be accomplished by the following Ant target:
      **Select** `Run Target->Other Targets->copy-files`
</pre>

<h3>Project Directory Structure</h3>

The project structure has been normalized to a standard java scheme (as opposed to a Netbeans project.

That is, within each sub-project.

```
src
    main
        java
        resources
```

<h3>Project Internals</h3>

* The XML files from  SISO are in `generator/xml`.
* The code templates used to generate java source code are in `generator/src/main/resources`.

There are several logical output types described separately in the specifications.  This project processes them independently, i.e., the input XML is re-read for each type.  Those types are:

1. Protocol Data Units (PDUs)
2. Enumerations
3. Object types
4. Radio Jammer types
5. Entity types

*PDUs* and *Enumerations* are the most commonly used.  The number of distinct *entity types* is large, which translates to a large number of Java classes.  The use of the generated entity types is optional in a DIS application, since a DIS programmer managing a small number of entities may choose to manually insert the appropriate values into his/her data structures.  For that reason, plus the fact that the entity type classes simply implement an abstract class by supplying 1-4 integer values, the source is not intended to be included in a distribution.  The completed class jar files are available in the `dist/` directory.

The main `build.gradle` file invokes the build product of the `:generator` subproject using tasks that map to the items below.
If you need to adjust the filenames used in the arguments to these calls (such as the names of the XML files), the `GenerateXXX` tasks in `build.gradle` are where you do that.
It should be obvious once you look at it (unlike figuring out where Netbeans keeps such information 45 clicks into some GUI hell.)

1. `edu.nps.moves.dis7.source.generator.enumerations.GenerateEnumerations` -- produces enumerations from the SISO specification
2. `edu.nps.moves.dis7.source.generator.pdus.Main` -- produces Pdus and assorted sub-object classes from the IEEE-derived XML inputs
3. `edu.nps.moves.dis7.source.generator.entitytypes.GenerateJammers` -- produces radio jammer classes from the SISO specification
4. `edu.nps.moves.dis7.source.generator.entitytypes.GenerateObjectTypes` -- produces miscellaneous object classes from the SISO specification
5. `edu.nps.moves.dis7.source.generator.entitytypes.GenerateEntityTypes` -- produces entity type classes from the SISO specification

The order of execution of these 5 sections is important, but it is handled by the build.
The individual `GenerateXXX` tasks are not meant to be invoked by users, they are dependent tasks of `MakeDistribution`.
That's not to say you can't use them alone, but if you do, you need to understand what you're doing.

<h4>Source Generation Method -- Pdus</h4>

This class contains remnants of legacy code which created pdus classes in different languages.  The "JavaGenerator" subclass is the only one used in this project (to date).

The main classes called in the `GeneratorXXX` tasks, read the IEEE XML files with a SAX parser and produces a map of class names-to-GeneratedClass objects.  The `GeneratedClass` object contains fields which reflect the information contained in the XML for the particular pdu or object.  This map is theoretically language-neutral is then used to produce pdus classes in various languages.

`edu.nps.moves.dis7.source.generator.pdus.JavaGenerator` then processes this map, generating source for each `GeneratedClass` object encountered.  Template files are used so that the standard Java library String class may be used like the following:<br/>
	`String fileContents = String.format(template, value1, value2, value3 ...);`

> TODO: use a real template system like Velocity or Thymeleaf.

<h4>Source Generation Method -- Enumerations</h4>

These classes are simpler than Pdus and are created in a simpler way.  The enumerated values in the SISO specification are implemented as either java Enumeration or java Bitset classes.  (The latter uses an invented "BitField" class as a front end.)

The generated uses SAX and operates when the "start" and "end" tags of the following elements are encountered:

1. `enum`
2. `enumrow`
3. `bitfield`
4. `bitfieldrow`
5. `dict`
6. `dictrow`

The `enum, bitfield, and dict` elements map to separate classes.  The `enumrow, bitfieldrow, and dictrow` map to values within each of those classes.

When a SAX "end" element is encountered, the "current" element is written out as a complete Enumeration or BitField class, with enumerated values contained therein. Template files are used as above.
	
<h4>Source Generation Method -- Entity types, Object types, Jammers</h4>

These classes are also simpler than Pdus.  Each of these three is hierarchically defined.  For instance, a single Entity type is defined by entity, category, subcategory, specific and optional parameters, and not all of the sub parameters are required.  Using a SAX parser, the SISO specification is read sequentially.

When a SAX "start" element is encountered, a new Java object is created -- one of `EntityElem, CategoryElem, SubCategoryElem, SpecificElem, or ExtraElem`.  Since it is a hierarchical structure, when, e.g., a SubCategoryElem is encountered, the newly created object is inserted as a child of the previously created EntityElem.

When a SAX "end" element is encountered, the "current" element is written out as a complete EntityType class, with specific category, subcategory, etc., values. Template files are used as above.

Further work:
Refactor Java generator classes
Implement other language outputs
Improve descriptions / javadoc in XML
Implement information "toString()" methods for classes like EulerAngles, EntityID, EntityKind

*Document end*

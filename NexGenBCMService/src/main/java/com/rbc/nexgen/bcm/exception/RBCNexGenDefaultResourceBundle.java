package com.rbc.nexgen.bcm.exception;

import java.util.ListResourceBundle;
/**
 * The infrastructure error codes. The infrastructure code
 * does not use text as the message description. It uses
 * the numbers from this resource bounde.
 * 
 * Infrastructure error codes From 10 To 1999.
 * @version 1.0 
 * @author 	NexGen
 */
public class RBCNexGenDefaultResourceBundle extends ListResourceBundle {
	private Object[][] _contents = {
	// Reserved From 0 To 10
	// Common From 10 To 100
	{"10","Illegal Access Exception."},
	{"11","Illegal Argument Exception."},
	{"12","Invocation Target Exception"},
	{"13","Other Exception."},
	{"14","No such business service defined."},
	{"15","No such business component defined."},
	{"16","Server error."},
	// Business Logic From 101 To 150 
	{"101","No Data Access Object found."},
	{"102","Business Object not created."},
	// Data Access From 151 To 200
	{"151","Add failed."},	
	{"152","Delete failed."},	
	{"153","System Query failed."},	
	{"154","Can not read Data Access Object Registry for Log Services."},
	{"155","Data Access Object can not be created."},
	{"156","Query Data Access Object can not be created."},
	{"157","Can not read service catalog."},
	{"158","Error while reading services catalog."},
	{"159","Error while reading catalog file fields."},
	{"160","Error while reading services catalog file."},
	{"161","Error during persistenece service connections initialization."},
	{"162","Update failed, affecting more then one row."},
	{"163","Update failed."},
	{"164","Timestamp not sent to server for update."},
	{"165","Timestamp checking failed."},
	{"166","Update timestamp changed since last read."},
	{"167","Data access method not implemented yet."},
	{"168","Timestamp not found in the persistence(DB)."},
	{"169","Unable to auto-generate a key for the give table and column."},
	// Messaging From 201 To 220
	{"201","Invalid Request Model NULL."},
	{"202","Communication timeout."},
	{"203","Invalid Column number."},
	{"204","Exception not handled properly."},
	// Connection From 211 To 300
	{"221", "The directory does not exist."},
	{"222", "The directory is empty."},
	{"223", "The directory list failed."},
	// Files from 290 to 299
	{"290", "Can not to the destination file."},
	{"291", "Problem with the destination directory."},
	{"292", "Can not initialize FTP connection manager."},	
	{"293", "Can not create FTP connection."},
	// Services From 301 To 350
	{"301","Invalid Connection Manager Type specified."},
	{"302","Error retrieving connection from transaction."},
	// Rules Service From 351 To 400	
	{"351","Can not initialize Rule Engine."},	
	// Session From 401 To 450
	{"401", "Session fetch problem."},
	// Transaction From 451 To 500
	{"451","Transaction has to be rolledbacked."},	
	{"452","No Transaction in progress."},
	{"453","Transaction timed out."},
	{"454","Transaction compensation failed."},
	// Transport From 501 To 550
	{"501","Could not find the transport server."},
	//SQL from 550 to 599
	{"550", "SQL authentication error."},
	//Kafka from 600 To 650
	{"600", "Kafka error."},
	//AD
	{"10000", "AD access error."},
	//BCP Web
	{"11000", "BCP Web access error."},
	//GDS
	{"12000", "GDS access error."},
	//FiBrs
	{"13000", "FiBrs access error."},
	//Archer
    {"14000", "Archer access error."},
    {"14001", "Archer two."},
    //Workday
    {"15000", "Workday access error."},
    {"15001", "Workday two."},
    //IIPM
    {"16000", "IIPM access error."},
    {"16001", "IIPM two."},
    {"16002", "IIPM three."},
    {"16003", "IIPM five."},
	};
protected Object[][] getContents() {
	return _contents;
}
}
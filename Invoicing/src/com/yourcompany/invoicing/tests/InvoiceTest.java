package com.yourcompany.invoicing.tests;

import java.time.*;
import java.time.format.*;
import javax.persistence.*;
import org.openxava.tests.*;
import static org.openxava.jpa.XPersistence.*; // To use JPA

public class InvoiceTest extends ModuleTestBase {

	private String number; // To store the number of the tested invoice

	public InvoiceTest(String testName) {
		super(testName, "Invoicing", "Invoice");
	}

	public void testCreate() throws Exception { // The test method
		login("admin", "admin");
		verifyDefaultValues();
		chooseCustomer();
		addDetails();
		setOtherProperties();
		save();
		verifyCreated();
		remove();
	}

	private void verifyDefaultValues() throws Exception {

		execute("CRUD.new");
		assertValue("year", getCurrentYear());
		assertValue("number", getNumber());
		assertValue("date", getCurrentDate());

	}

	private void chooseCustomer() throws Exception {
		
		setValue("customer.number", "1");
	    assertValue("customer.name", "JAVIER PANIZA"); // The customer 1 should exist in DB
	}


	private void addDetails() throws Exception {
		
		assertCollectionRowCount("details", 0); // The collection is empty
		 
	    // Adding a detail line
	    setValueInCollection("details", 0, // 0 means the first row
	        "product.number", "1");
	    assertValueInCollection("details", 0,
	        "product.description", "Peopleware: Productive Projects and Teams");
	    setValueInCollection("details", 0, "quantity", "2");
	 
	    // Adding another detail
	    setValueInCollection("details", 1, "product.number", "2");
	    assertValueInCollection("details", 1, "product.description", "Arco iris de lágrimas");
	    setValueInCollection("details", 1, "quantity", "1");
	 
	    assertCollectionRowCount("details", 2); // Now we have 2 rows

	}

	private void setOtherProperties() throws Exception {
		setValue("remarks", "This is a JUNIT test");
	}

	private void save() throws Exception {
		execute("CRUD.save");
	    assertNoErrors();
	    assertValue("customer.number", "");
	    assertCollectionRowCount("details", 0);
	    assertValue("remarks", "");

	}

	private void verifyCreated() throws Exception {
		
		setValue("year", getCurrentYear()); // The current year to year field
	    setValue("number", getNumber()); // The invoice number of the test
	    execute("CRUD.refresh"); // Load the invoice back from the database
	 
	    // In the rest of the test we assert that the values are the correct ones
	    assertValue("year", getCurrentYear());
	    assertValue("number", getNumber());
	    assertValue("date", getCurrentDate());
	    assertValue("customer.number", "1");
	    assertValue("customer.name", "JAVIER PANIZA");
	    assertCollectionRowCount("details", 2);
	 
	    // Row 0
	    assertValueInCollection("details", 0, "product.number", "1");
	    assertValueInCollection("details", 0, "product.description",
	        "Peopleware: Productive Projects and Teams");
	    assertValueInCollection("details", 0, "quantity", "2");
	 
	    // Row 1
	    assertValueInCollection("details", 1, "product.number", "2");
	    assertValueInCollection("details", 1, "product.description",
	        "Arco iris de lágrimas");
	    assertValueInCollection("details", 1, "quantity", "1");
	    assertValue("remarks", "This is a JUNIT test");

	}

	private void remove() throws Exception {
		
		execute("CRUD.delete");
	    assertNoErrors();

	}

	private String getCurrentYear() { // Current year in string format
	    return Integer.toString(LocalDate.now().getYear()); // The standard
	                                                    // way to do it with Java
	}
	 
	private String getCurrentDate() { // Current date as string in short format
	    return LocalDate.now().format( // The standard way to do it with Java
	        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
	}
	 
	private String getNumber() { // The invoice number for a new invoice
	    if (number == null) { // We use lazy initialization
	        Query query = getManager(). // A JPA query to get the last number
	                createQuery("select max(i.number) from Invoice i where i.year = :year");
	        query.setParameter("year", LocalDate.now().getYear());
	        Integer lastNumber = (Integer) query.getSingleResult();
	        if (lastNumber == null) lastNumber = 0;
	        number = Integer.toString(lastNumber + 1); // Adding 1 to the last invoice number
	    }
	    return number;

	}
	
}
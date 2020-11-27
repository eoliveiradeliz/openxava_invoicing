package com.yourcompany.invoicing.tests;

import java.math.*;
import com.yourcompany.invoicing.model.*;
import org.openxava.tests.*;
import static org.openxava.jpa.XPersistence.*;

public class ProductTest extends ModuleTestBase {

	private Author author; // We declare the entities to be created
	private Category category; // as instance members in order
	private Product product1; // to be available from inside any test method
	private Product product2; // and to be removed at the end of each test

	public ProductTest(String testName) {
		super(testName, "Invoicing", "Product");
	}

	protected void setUp() throws Exception { // setUp() is always executed
												// before each test
		super.setUp(); // It's needed because ModuleTestBase uses it for
						// initializing, even JPA is initialized here.
		createProducts(); // Creates the data used in the tests
	}

	protected void tearDown() throws Exception { // tearDown() is always
													// executed after each test
		super.tearDown(); // It's needed, ModuleTestBase closes resources here
		removeProducts(); // The data used for testing is removed
	}

	public void testRemoveFromList() throws Exception {
		
		login("admin", "admin");
	    setConditionValues("", "JUNIT"); // Put the values for filtering data
	    setConditionComparators("=", "contains_comparator"); // Put the comparators for filtering data
	    execute("List.filter"); // Clicks on filter button
	    assertListRowCount(2); // Verifies that there are 2 rows
	    checkRow(1); // We select row 1 (really the second one)
	    execute("CRUD.deleteSelected"); // Clicks on the delete button
	    assertListRowCount(1); // Verifies that now there is only 1 row

	}

	public void testUploadPhotos() throws Exception {
		
		
		login("admin", "admin");
		 
		// Searching the product1
		execute("CRUD.new");
		setValue("number", Integer.toString(product1.getNumber())); // (1)
		execute("CRUD.refresh");
		assertFilesCount("photos", 0);
	 
		// Uploading photos
		uploadFile("photos", "web/xava/images/add.gif"); // (2)
		uploadFile("photos", "web/xava/images/attach.gif"); // (2)
	 
		// Verifying
		execute("CRUD.new");
		assertFilesCount("photos", 0);
		setValue("number", Integer.toString(product1.getNumber())); // (1)
		execute("CRUD.refresh");
		assertFilesCount("photos", 2);
		assertFile("photos", 0, "image");
		assertFile("photos", 1, "image");
		
		// Remove photos
		removeFile("photos", 1);
		removeFile("photos", 0);

	}

	private void createProducts() {
		
		// Creating the Java objects
	    author = new Author(); // Regular Java objects are created
	    author.setName("JUNIT Author"); // We use setters just as in plain Java
	    category = new Category();
	    category.setDescription("JUNIT Category");
	    product1 = new Product();
	    product1.setNumber(900000001);
	    product1.setDescription("JUNIT Product 1");
	    product1.setAuthor(author);
	    product1.setCategory(category);
	    product1.setPrice(new BigDecimal("10"));
	    product2 = new Product();
	    product2.setNumber(900000002);
	    product2.setDescription("JUNIT Product 2");
	    product2.setAuthor(author);
	    product2.setCategory(category);
	    product2.setPrice(new BigDecimal("20"));
	 
	    // Marking as persistent objects
	    getManager().persist(author); // getManager() is from XPersistence
	    getManager().persist(category); // persist() marks the object as persistent
	    getManager().persist(product1); // so it will be saved to the database
	    getManager().persist(product2);
	 
	    // Commit changes to the database
	    commit(); // commit() is from XPersistence. It saves all object to the database
	              // and commits the transaction


	}

	private void removeProducts() {
		// Called from tearDown() so it's executed
        // after each test
		remove(product1, product2, author, category); // remove() removes
		commit(); // Commits the changes to database, in this case deleting data
	}	

	private void remove(Object ... entities) { // Using varargs argument
		for (Object entity : entities) { // Iterating for all arguments
			getManager().remove(getManager().merge(entity)); // Removing(1)
		}
	}

}

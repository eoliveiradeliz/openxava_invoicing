package com.yourcompany.invoicing.tests;

import org.openxava.tests.*;

public class CustomerTest extends ModuleTestBase { // Must extend from ModuleTestBase
 
    public CustomerTest(String testName) {
        super(testName, "Invoicing", // We indicate the application name (Invoicing)
                "Customer"); // and the module name (Customer)
    }
 
    // The test methods must start with 'test'
    public void testCreateReadUpdateDelete() throws Exception {
        login("admin", "admin"); // The user sign in to access the module
 
        // Create
        execute("CRUD.new"); // Clicks on 'New' button
        setValue("number", "77"); // Types 77 as the value for the 'number' field
        setValue("name", "JUNIT Customer"); // Sets the value for the 'name' field
        setValue("address.street", "JUNIT Street"); // Note the dot notation
                                                // to access a reference member
        setValue("address.zipCode", "77555"); // Etc
        setValue("address.city", "The JUNIT city"); // Etc
        setValue("address.state", "The JUNIT state"); // Etc
        execute("CRUD.save"); // Clicks on 'Save' button
        assertNoErrors(); // Verifies that the application does not show errors
        assertValue("number", ""); // Verifies the 'number' field is empty
        assertValue("name", ""); // Verifies the 'name' field is empty
        assertValue("address.street", ""); // Etc
        assertValue("address.zipCode", ""); // Etc
        assertValue("address.city", ""); // Etc
        assertValue("address.state", ""); // Etc
 
        // Read
        setValue("number", "77"); // Types 77 as the value for the 'number' field
        execute("CRUD.refresh"); // Clicks on 'Refresh' button
        assertValue("number", "77"); // Verifies the 'number' field has 77
        assertValue("name", "JUNIT Customer"); // and 'name' has 'JUNIT Customer'
        assertValue("address.street", "JUNIT Street"); // Etc
        assertValue("address.zipCode", "77555"); // Etc
        assertValue("address.city", "The JUNIT city"); // Etc
        assertValue("address.state", "The JUNIT state"); // Etc
 
        // Update
        setValue("name", "JUNIT Customer MODIFIED"); // Changes the value
                                                    // of 'name' field
        execute("CRUD.save"); // Clicks on 'Save' button
        assertNoErrors(); // Verifies that the application does not show errors
        assertValue("number", ""); // Verifies the 'number' field is empty
        assertValue("name", ""); // Verifies the 'name' field is empty
 
        // Verify if modified
        setValue("number", "77"); // Types 77 as the value for 'number' field
        execute("CRUD.refresh"); // Clicks on 'Refresh' button
        assertValue("number", "77"); // Verifies the 'number' field has 77
        assertValue("name", "JUNIT Customer MODIFIED"); // and 'name'
                                        // has 'JUNIT Customer MODIFIED'
 
        // Delete
        execute("CRUD.delete"); // Clicks on 'Delete' button
        assertMessage("Customer deleted successfully"); // Verifies that the
                // message 'Customer deleted successfully' is shown to the user
    }
 
}

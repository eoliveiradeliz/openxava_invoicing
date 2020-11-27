package com.yourcompany.invoicing.tests;

import org.openxava.tests.*;

public class AuthorTest extends ModuleTestBase {
    public AuthorTest(String testName) {
        super(testName, "Invoicing", "Author");
    }
 
    public void testReadAuthor() throws Exception {
        login("admin", "admin");
        assertValueInList(0, 0, "JAVIER CORCOBADO"); // The first author
                                        // in the list is JAVIER CORCOBADO
        execute("List.viewDetail", "row=0"); // We click the first object in the list
        assertValue("name", "JAVIER CORCOBADO");
        assertCollectionRowCount("products", 2); // It has 2 products
        assertValueInCollection("products", 0, "number", "2"); // has “2” in “number” column
        assertValueInCollection("products", 0, "description", "Arco iris de lágrimas");
        assertValueInCollection("products", 1, "number", "3");
        assertValueInCollection("products", 1, "description", "Ritmo de sangre");
    }
 
}
package com.yourcompany.invoicing.tests;

import org.openxava.tests.*;

public class CategoryTest extends ModuleTestBase {
 
    public CategoryTest(String testName) {
        super(testName, "Invoicing", "Category");
    }
 
    public void testCategoriesInList() throws Exception {
        login("admin", "admin");
        assertValueInList(0, 0, "MUSIC"); // Row 0 column 0 has “MUSIC”
        assertValueInList(1, 0, "BOOKS"); // Row 1 column 0 has “BOOKS”
        assertValueInList(2, 0, "SOFTWARE"); // Row 2 column 0 has “SOFTWARE”
    }
 
}

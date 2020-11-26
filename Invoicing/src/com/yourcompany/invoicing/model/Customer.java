package com.yourcompany.invoicing.model;

import javax.persistence.*;
import org.openxava.annotations.*;

@View(name = "Simple", // This view is used only when “Simple” is specified
		members = "number, name" // Shows only number and name in the same line
)
@Entity // This marks Customer class as an entity
public class Customer {

	@Id // The number property is the key property. Keys are required by default
	@Column(length = 6) // The column length is used at the UI level and the DB level
	private int number;

	@Column(length = 50) // The column length is used at the UI level and the DB level
	@Required // A validation error will be shown if the name property is left empty
	private String name;

	@Embedded // This is the way to reference an embeddable class
	private Address address; // A regular Java reference

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		if (address == null)
			address = new Address(); // Thus it never is null
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}

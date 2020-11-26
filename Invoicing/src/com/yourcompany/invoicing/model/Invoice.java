package com.yourcompany.invoicing.model;

import com.yourcompany.invoicing.calculators.*;

import java.time.*;
import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.calculators.*;

@View(members = // This view has no name, so it will be the view used by default
"year, number, date;" + // Comma separated means in the same line
		"customer;" + // Semicolon means a new line
		"details;" + "remarks")
@Entity
public class Invoice {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@Hidden
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String oid;

	@Column(length = 4)
	@DefaultValueCalculator(CurrentYearCalculator.class) // Current year
	private int year;

	@Column(length = 6)
	@DefaultValueCalculator(value = NextNumberForYearCalculator.class, properties = @PropertyValue(name = "year")) 
	// To inject the value of year from Invoice to
	private int number;

	@Required
	@DefaultValueCalculator(CurrentLocalDateCalculator.class) // Current date
	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY, optional = false) // Customer is required
	@ReferenceView("Simple") // The view named 'Simple' is used to display this reference
	private Customer customer;

	@Stereotype("MEMO")
	private String remarks;

	@ElementCollection
	@ListProperties("product.number, product.description, quantity")
	private Collection<Detail> details;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Collection<Detail> getDetails() {
		return details;
	}

	public void setDetails(Collection<Detail> details) {
		this.details = details;
	}

}

package com.yourcompany.invoicing.model;

import java.math.*;

import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
public class Product {

	@ManyToOne(fetch = FetchType.LAZY, optional = true) // The reference is persisted as a database relationship The
														// reference is loaded on demand
	@DescriptionsList // Thus the reference is displayed using a combo
	private Category category; // A regular Java reference

	@Id
	@Column(length = 9)
	private int number;

	@Column(length = 50)
	@Required
	private String description;

	@Stereotype("MONEY") // The price property is used to store money
	private BigDecimal price; // Include the import java.math.* BigDecimal is typically used for money

	@Stereotype("IMAGES_GALLERY") // A complete image gallery is available
	@Column(length = 32) // The 32 length string is for storing the key of the gallery
	private String photos;

	@Stereotype("MEMO") // This is for a big text, a text area or equivalent will be used
	private String remarks;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}

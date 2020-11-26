package com.yourcompany.invoicing.model;

import javax.persistence.*;

@Embeddable
public class Detail {
 
    private int quantity;
 
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Product product;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
 
    
 
}

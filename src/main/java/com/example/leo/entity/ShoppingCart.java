package com.example.leo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class ShoppingCart  implements Serializable{

	private static final long serialVersionUID = 7098002903353959983L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long id;
	 //------------ Mapped Column -----------//
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    //-------------------------------------//

    @Column(name = "sub_total")
    private Float subTotal;

    @Column(name = "shipping_total")
    private Float shippingTotal;

    //Default 5 %
    @Column(name = "tax_rate")
    private Float taxRate;

    @Column(name = "tax_total")
    private Float taxTotal;

    @Column(name = "grand_total")
    private Float grandTotal;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCart")
    private List<CartItem> cartItemList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Float getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Float subTotal) {
		this.subTotal = subTotal;
	}

	public Float getShippingTotal() {
		return shippingTotal;
	}

	public void setShippingTotal(Float shippingTotal) {
		this.shippingTotal = shippingTotal;
	}

	public Float getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Float taxRate) {
		this.taxRate = taxRate;
	}

	public Float getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(Float taxTotal) {
		this.taxTotal = taxTotal;
	}

	public Float getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Float grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ShoppingCart() {
		super();
	}

	public ShoppingCart(Long id, User user, Float subTotal, Float shippingTotal, Float taxRate, Float taxTotal,
			Float grandTotal, String shippingMethod, String paymentMethod, String description,
			List<com.example.leo.entity.CartItem> cartItemList) {
		super();
		this.id = id;
		this.user = user;
		this.subTotal = subTotal;
		this.shippingTotal = shippingTotal;
		this.taxRate = taxRate;
		this.taxTotal = taxTotal;
		this.grandTotal = grandTotal;
		this.shippingMethod = shippingMethod;
		this.paymentMethod = paymentMethod;
		this.description = description;
		this.cartItemList = cartItemList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cartItemList == null) ? 0 : cartItemList.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((grandTotal == null) ? 0 : grandTotal.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((paymentMethod == null) ? 0 : paymentMethod.hashCode());
		result = prime * result + ((shippingMethod == null) ? 0 : shippingMethod.hashCode());
		result = prime * result + ((shippingTotal == null) ? 0 : shippingTotal.hashCode());
		result = prime * result + ((subTotal == null) ? 0 : subTotal.hashCode());
		result = prime * result + ((taxRate == null) ? 0 : taxRate.hashCode());
		result = prime * result + ((taxTotal == null) ? 0 : taxTotal.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShoppingCart other = (ShoppingCart) obj;
		if (cartItemList == null) {
			if (other.cartItemList != null)
				return false;
		} else if (!cartItemList.equals(other.cartItemList))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (grandTotal == null) {
			if (other.grandTotal != null)
				return false;
		} else if (!grandTotal.equals(other.grandTotal))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (paymentMethod == null) {
			if (other.paymentMethod != null)
				return false;
		} else if (!paymentMethod.equals(other.paymentMethod))
			return false;
		if (shippingMethod == null) {
			if (other.shippingMethod != null)
				return false;
		} else if (!shippingMethod.equals(other.shippingMethod))
			return false;
		if (shippingTotal == null) {
			if (other.shippingTotal != null)
				return false;
		} else if (!shippingTotal.equals(other.shippingTotal))
			return false;
		if (subTotal == null) {
			if (other.subTotal != null)
				return false;
		} else if (!subTotal.equals(other.subTotal))
			return false;
		if (taxRate == null) {
			if (other.taxRate != null)
				return false;
		} else if (!taxRate.equals(other.taxRate))
			return false;
		if (taxTotal == null) {
			if (other.taxTotal != null)
				return false;
		} else if (!taxTotal.equals(other.taxTotal))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	//modifique porque tenia error una dependencia circular entre el user y  shoppingCart .
	@Override
	public String toString() {
		return "ShoppingCart [id=" + id +
				//", user=" + user + 
				", subTotal=" + subTotal + 
				", shippingTotal=" + shippingTotal +
				", taxRate=" + taxRate + 
				", taxTotal=" + taxTotal + 
				", grandTotal=" + grandTotal +
			    ", shippingMethod=" + shippingMethod + 
			    ", paymentMethod=" + paymentMethod + 
			    ", description="+ description + 
			 //   ", cartItemList=" + cartItemList +
			    "]";
	}
    


}

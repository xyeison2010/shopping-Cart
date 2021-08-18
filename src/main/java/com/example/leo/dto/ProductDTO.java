package com.example.leo.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class ProductDTO {

private Long id;
	
private String name;

private int categoryId;//cambio

private String code;//codigo

private float price;

private int existencia;

private double weight;

private String description;

private String imageName;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getCategoryId() {
	return categoryId;
}

public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
}

public String getCode() {
	return code;
}

public void setCode(String code) {
	this.code = code;
}

public float getPrice() {
	return price;
}

public void setPrice(float price) {
	this.price = price;
}

public int getExistencia() {
	return existencia;
}

public void setExistencia(int existencia) {
	this.existencia = existencia;
}

public double getWeight() {
	return weight;
}

public void setWeight(double weight) {
	this.weight = weight;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getImageName() {
	return imageName;
}

public void setImageName(String imageName) {
	this.imageName = imageName;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + categoryId;
	result = prime * result + ((code == null) ? 0 : code.hashCode());
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + existencia;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + Float.floatToIntBits(price);
	long temp;
	temp = Double.doubleToLongBits(weight);
	result = prime * result + (int) (temp ^ (temp >>> 32));
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
	ProductDTO other = (ProductDTO) obj;
	if (categoryId != other.categoryId)
		return false;
	if (code == null) {
		if (other.code != null)
			return false;
	} else if (!code.equals(other.code))
		return false;
	if (description == null) {
		if (other.description != null)
			return false;
	} else if (!description.equals(other.description))
		return false;
	if (existencia != other.existencia)
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (imageName == null) {
		if (other.imageName != null)
			return false;
	} else if (!imageName.equals(other.imageName))
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
		return false;
	if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
		return false;
	return true;
}

public ProductDTO(Long id, String name, int categoryId, String code, float price, int existencia, double weight,
		String description, String imageName) {
	super();
	this.id = id;
	this.name = name;
	this.categoryId = categoryId;
	this.code = code;
	this.price = price;
	this.existencia = existencia;
	this.weight = weight;
	this.description = description;
	this.imageName = imageName;
}

public ProductDTO() {
	super();
}




}

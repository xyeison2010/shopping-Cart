package com.example.leo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Product implements Serializable{

	private static final long serialVersionUID = 6719790276275934055L;

@Id	
@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
@GenericGenerator(name="native",strategy="native")
private Long id;
	
@Column
private String name;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="category_id" ,referencedColumnName = "category_id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
private Category category;//este es un foreing key referenciando el id de categorias

@NotEmpty(message = "Code can't be empty!")
@Column(name = "code")
private String code;

@Column
private float price;
@Column
private Double weight;
@Column
private String description;
@Column
private String imageName; //static ,productImages
@Column
private int existencia;

//agrege
@Temporal(TemporalType.DATE)
@Column(name = "create_at")
private Date createAt;

@PrePersist
public void prePersist() {
	createAt = new Date();
}
//

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

public Category getCategory() {
	return category;
}

public void setCategory(Category category) {
	this.category = category;
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

public Double getWeight() {
	return weight;
}

public void setWeight(Double weight) {
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

public int getExistencia() {
	return existencia;
}

public void setExistencia(int existencia) {
	this.existencia = existencia;
}

public Date getCreateAt() {
	return createAt;
}

public void setCreateAt(Date createAt) {
	this.createAt = createAt;
}
public Product(Long id, String name, Category category, @NotEmpty(message = "Code can't be empty!") String code,
		float price, Double weight, String description, String imageName, int existencia, Date createAt) {
	super();
	this.id = id;
	this.name = name;
	this.category = category;
	this.code = code;
	this.price = price;
	this.weight = weight;
	this.description = description;
	this.imageName = imageName;
	this.existencia = existencia;
	this.createAt = createAt;
}

public Product() {
	super();
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((category == null) ? 0 : category.hashCode());
	result = prime * result + ((code == null) ? 0 : code.hashCode());
	result = prime * result + ((createAt == null) ? 0 : createAt.hashCode());
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + existencia;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + Float.floatToIntBits(price);
	result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
	Product other = (Product) obj;
	if (category == null) {
		if (other.category != null)
			return false;
	} else if (!category.equals(other.category))
		return false;
	if (code == null) {
		if (other.code != null)
			return false;
	} else if (!code.equals(other.code))
		return false;
	if (createAt == null) {
		if (other.createAt != null)
			return false;
	} else if (!createAt.equals(other.createAt))
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
	if (weight == null) {
		if (other.weight != null)
			return false;
	} else if (!weight.equals(other.weight))
		return false;
	return true;
}

@Override
public String toString() {
	return "Product [id=" + id + ", name=" + name + ", category=" + category + ", code=" + code + ", price=" + price
			+ ", weight=" + weight + ", description=" + description + ", imageName=" + imageName + ", existencia="
			+ existencia + ", createAt=" + createAt + "]";
}





}

package com.example.leo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;



import javax.persistence.JoinColumn;

@Entity
public class User implements Serializable{

	private static final long serialVersionUID = -6746533425443348116L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long id;
	
	@Column 
	@NotBlank
	private String firstName;
	
	@Column 
	@NotBlank
	private String lastName;
	
	@Column(unique = true) 
	@NotBlank
	private String email;
	
	@Column(unique = true) 
	@NotBlank
	private String username;
	
	@Column
	@NotBlank
	private String password;
	
    @NotNull(message = "Select Country!")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Country country;//ciudad
    
	@Transient 
	private String confirmPassword;//no esta en la bd
	
	@Column(name = "reset_password_token")
	private String resetPasswordToken;  //para forgot password
	
	
	
	@ManyToMany(fetch = FetchType.LAZY) //se crea una nueva tabla EN LA BD
	@JoinTable(name="user_roles" 
		,joinColumns=@JoinColumn(name="user_id")//foreing key
		,inverseJoinColumns=@JoinColumn(name="role_id"))//foreing key
	private Collection <Role>  roles; //sera collecio pq ais puedo teenr mas fuciones de acorde mi proyecto

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createAt;

	@OneToOne(mappedBy = "user")
    private ShoppingCart shoppingCart;
		
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    
    public String getFullName(){
        return firstName +" "+lastName;
    }
    
	//
		@PrePersist
		public void prePersist() {//fecha automaticamente
			createAt = new Date();
		}


	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
////////

//modifique porque tenia error una dependencia circular entre el user y shoppingCart .
	@Override
	public String toString() {
		return "User [id=" + id + 
				", firstName=" + firstName + 
				", lastName=" + lastName + 
				", email=" + email +
			    ", username=" + username + 
			    ", password=" + password + 
			    ", country=" + country +
			    ", confirmPassword=" + confirmPassword +
			     ", resetPasswordToken=" + resetPasswordToken +
			     ", roles=" + roles + 
			     ", createAt=" + createAt +
				// ", shoppingCart=" + shoppingCart + 
				// ", orders=" + orders + 
				 "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public User(Long id, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String email,
			@NotBlank String username, @NotBlank String password, @NotNull(message = "Select Country!") Country country,
			String confirmPassword, String resetPasswordToken, Collection<Role> roles, Date createAt,
			ShoppingCart shoppingCart, List<Order> orders) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.country = country;
		this.confirmPassword = confirmPassword;
		this.resetPasswordToken = resetPasswordToken;
		this.roles = roles;
		this.createAt = createAt;
		this.shoppingCart = shoppingCart;
		this.orders = orders;
	}

	public User() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((createAt == null) ? 0 : createAt.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((orders == null) ? 0 : orders.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((resetPasswordToken == null) ? 0 : resetPasswordToken.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((shoppingCart == null) ? 0 : shoppingCart.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (confirmPassword == null) {
			if (other.confirmPassword != null)
				return false;
		} else if (!confirmPassword.equals(other.confirmPassword))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (createAt == null) {
			if (other.createAt != null)
				return false;
		} else if (!createAt.equals(other.createAt))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (orders == null) {
			if (other.orders != null)
				return false;
		} else if (!orders.equals(other.orders))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (resetPasswordToken == null) {
			if (other.resetPasswordToken != null)
				return false;
		} else if (!resetPasswordToken.equals(other.resetPasswordToken))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (shoppingCart == null) {
			if (other.shoppingCart != null)
				return false;
		} else if (!shoppingCart.equals(other.shoppingCart))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}



	} 
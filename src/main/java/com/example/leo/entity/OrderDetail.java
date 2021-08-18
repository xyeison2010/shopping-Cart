package com.example.leo.entity;



import javax.persistence.*;


@Entity
@Table(name="order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    //------------ Mapped Column -----------//
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    //--------------------------------------//

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "our_price")
    private Float ourPrice;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Float getOurPrice() {
		return ourPrice;
	}

	public void setOurPrice(Float ourPrice) {
		this.ourPrice = ourPrice;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public OrderDetail(Long id, Order order, Product product, Long quantity, Float ourPrice, Float totalPrice,
			Boolean isDeleted) {
		super();
		this.id = id;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.ourPrice = ourPrice;
		this.totalPrice = totalPrice;
		this.isDeleted = isDeleted;
	}

	public OrderDetail() {
		super();
	}
    
    
}

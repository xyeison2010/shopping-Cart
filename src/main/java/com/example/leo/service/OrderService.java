package com.example.leo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.leo.entity.CartItem;
import com.example.leo.entity.Order;
import com.example.leo.entity.OrderDetail;
import com.example.leo.entity.ShoppingCart;
import com.example.leo.entity.User;
import com.example.leo.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAllOrders(User user) {
        return user.getOrders();
    }

    public Order saveOrder(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setOrderDate(new Date());
        order.setDeliveryDate(new Date());
        order.setSubTotal(shoppingCart.getSubTotal());
        order.setShippingTotal(shoppingCart.getShippingTotal());
        order.setTaxRate(shoppingCart.getTaxRate());
        order.setTaxTotal(shoppingCart.getTaxTotal());
        order.setGrandTotal(shoppingCart.getGrandTotal());
        order.setShippingMethod(shoppingCart.getShippingMethod());
        order.setPaymentMethod(shoppingCart.getPaymentMethod());
        order.setOrderStatus("Pending");
        order.setDescription(shoppingCart.getDescription());

        //order details
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        for(CartItem cartItem : shoppingCart.getCartItemList()){
            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setOurPrice(cartItem.getOurPrice());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setTotalPrice(cartItem.getTotalPrice());
            orderDetail.setIsDeleted(false);

            orderDetailList.add(orderDetail);
        }
        //set order details in list
        order.setOrderDetailList(orderDetailList);

        orderRepository.save(order);

        return order;
    }


    public Order get(long  id) {
        return orderRepository.findById(id).get();
    }

    public void delete(long id) {
        orderRepository.deleteById(id);
    }
}
package com.example.leo.global;

import java.util.ArrayList;
import java.util.List;

import com.example.leo.entity.CartItem;
import com.example.leo.entity.Product;

public class GlobalData {
public static List<Product> cart;
static {
	cart = new ArrayList<Product>();
}

public static List<CartItem> cartItem;
static {
	cartItem = new ArrayList<CartItem>();
}




}

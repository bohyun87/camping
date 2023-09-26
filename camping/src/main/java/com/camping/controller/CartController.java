package com.camping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

	@GetMapping(value = "/cart")
	public String cartList() {
		return "cart/cart";
	}
}

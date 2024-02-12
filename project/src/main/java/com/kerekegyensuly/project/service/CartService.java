package com.kerekegyensuly.project.service;

import com.kerekegyensuly.project.dto.cart.AddToCartDto;
import com.kerekegyensuly.project.dto.cart.CartDto;
import com.kerekegyensuly.project.dto.cart.CartItemDto;
import com.kerekegyensuly.project.exceptions.CartItemNotExistException;
import com.kerekegyensuly.project.model.Cart;
import com.kerekegyensuly.project.model.Product;
import com.kerekegyensuly.project.model.User;
import com.kerekegyensuly.project.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public CartService(){}

    public CartService(CartRepository cartRepository){this.cartRepository = cartRepository;}

    public void addToCart(AddToCartDto addToCartDto, Product product, User user){
        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
        cartRepository.save(cart);
    }
    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        return new CartDto(cartItems,totalCost);
    }

    public static CartItemDto getDtoFromCart(Cart cart){
        return new CartItemDto(cart);
    }
    public void updateCartItem(AddToCartDto cartDto, User user, Product product){
        Cart cart = cartRepository.getOne(cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
    }
    public void deleteCartItem(int id, int userId) throws CartItemNotExistException{
        if (!cartRepository.existsById(id))
            throw new CartItemNotExistException("A kosar nem letezik : " + id);
        cartRepository.deleteById(id);
    }
    public void deleteCartItems(int userId){cartRepository.deleteAll();}

    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }
}

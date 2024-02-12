package com.kerekegyensuly.project.controller;

import com.kerekegyensuly.project.dto.cart.AddToCartDto;
import com.kerekegyensuly.project.dto.cart.CartDto;
import com.kerekegyensuly.project.exceptions.AuthenticationFailException;
import com.kerekegyensuly.project.exceptions.CartItemNotExistException;
import com.kerekegyensuly.project.exceptions.ProductNotExistException;
import com.kerekegyensuly.project.model.Product;
import com.kerekegyensuly.project.model.User;
import com.kerekegyensuly.project.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class CartController {
    @Autowired
    private
}


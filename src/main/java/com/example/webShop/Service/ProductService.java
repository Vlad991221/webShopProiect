package com.example.webShop.Service;

import com.example.webShop.Database.Product;
import com.example.webShop.Database.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductDAO productDAO;

    public List<Product> findAllProducts() {
        return (List<Product>) productDAO.findAll();
    }
}

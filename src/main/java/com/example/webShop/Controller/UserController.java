package com.example.webShop.Controller;

import com.example.webShop.Database.CartProduct;
import com.example.webShop.Database.Product;
import com.example.webShop.Database.User;
import com.example.webShop.Service.ProductService;
import com.example.webShop.Service.UserException;
import com.example.webShop.Database.UserRowMapper;
import com.example.webShop.Security.UserSession;
import com.example.webShop.Service.UserService;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserSession userSession;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    int items = 0;

    @GetMapping("/register-form")
    public ModelAndView registerAction(@RequestParam("email") String email,
                                       @RequestParam("password1") String password1,
                                       @RequestParam("password2") String password2
    ) {
        ModelAndView modelAndView = new ModelAndView("register");
//        try {
//            if (!password1.equals(password2)) {
//                throw new UserException("Passwords not identical!");
//            }
//        } catch (UserException e) {
//            modelAndView.addObject("message", e.getMessage());
//            return modelAndView;
//        }
//        jdbcTemplate.update("insert into user values(null, ?, ?)", email, password1);
        try {
            userService.registerUser(email, password1, password2);
        } catch (UserException e) {
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;
        }
        return new ModelAndView("redirect:index.html");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("email") String email,
                              @RequestParam("password") String password) {
        ModelAndView modelAndView = new ModelAndView("index");

//        List<User> userList = jdbcTemplate.query("select * from user where email = '" + email + "'", new UserRowMapper());
//        try {
//            if (userList.isEmpty()) {
//                throw new UserException("user/password incorrect!");
//            }
//        } catch (UserException e) {
//            modelAndView.addObject("message", e.getMessage());
//            return modelAndView;
//        }
//
//        User user = userList.get(0);
//
//        try {
//            if (!user.getPassword().equals(password)) {
//                throw new UserException("user/password incorrect!");
//            }
//        } catch (UserException e) {
//            modelAndView.addObject("message", e.getMessage());
//            return modelAndView;
//        }

        List<User> userList;

        try {
            userList = userService.loginUser(email, password);
        } catch (UserException e) {
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;
        }

        userSession.setId(userList.get(0).getId());
        return new ModelAndView("redirect:dashboard");
    }

    @GetMapping("dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("index");

        if (userSession.getId() <= 0) {
            return modelAndView;
        }

        List<Product> productList = productService.findAllProducts();
        modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("productList", productList);
        items = userSession.getCartSize();
        modelAndView.addObject("items", items);
        return modelAndView;
    }

    @GetMapping("/add-to-cart")
    public ModelAndView addToCart(@RequestParam("productId") int productId) {
        ModelAndView modelAndView = new ModelAndView("dashboard");

        if (userSession.getId() <= 0) {
            return new ModelAndView("index");
        }

        List<Product> productList = productService.findAllProducts();
        modelAndView.addObject("productList", productList);
        userSession.addToCart(productId);
        items = userSession.getCartSize();
        modelAndView.addObject("items", items);
        return new ModelAndView("redirect:dashboard");
    }

    @GetMapping("/cart")
    public ModelAndView getCart() {
        ModelAndView modelAndView = new ModelAndView("cart");

        if (userSession.getId() <= 0) {
            return new ModelAndView("index");
        }

        List<Product> produseDB = productService.findAllProducts();
        List<CartProduct> produseCos = new ArrayList<>();

        for (int idProdusCos : userSession.getCart().keySet()) {
            for (Product product:produseDB) {
                if (product.getId() == idProdusCos) {
//                    produseCos.add(product);
                    CartProduct cartProduct = new CartProduct();
                    cartProduct.setCantitate(userSession.getCart().get(idProdusCos));
                    cartProduct.setId(product.getId());
                    cartProduct.setBrand(product.getBrand());
                    cartProduct.setNume(product.getNume());
                    cartProduct.setPret(product.getPret());
                    cartProduct.setPretTotal(userSession.getCart().get(idProdusCos) * product.getPret());
                    produseCos.add(cartProduct);
                }
            }
        }

        modelAndView.addObject("productList", produseCos);
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        userSession.setId(0);
        return new ModelAndView("index");
    }
}

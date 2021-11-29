package com.example.user.controller;


import com.example.user.VO.ResponseTemplateVO;
import com.example.user.entity.User;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")



public class  UserController {
    @Autowired
    private UserService userService;




    @GetMapping("/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/all/{id}")
    public User getById(@PathVariable int id) {
        return userService.getById(id);
    }




    @GetMapping("/cart-product")
    public ResponseTemplateVO getCartProduct(@RequestParam int userId){
        return  userService.getCartProducts(userId);
    }


    @GetMapping("/feedback")
    public ResponseTemplateVO getFeedback(@RequestParam int userId){
        return  userService.getFeedback(userId);
    }
}

package com.example.user.service.impl;

import com.example.user.VO.CartProduct;
import com.example.user.VO.Feedback;
import com.example.user.VO.ResponseTemplateVO;
import com.example.user.entity.User;
import com.example.user.repo.UserRepo;
import com.example.user.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RestTemplate restTemplate;


    public CartProduct getCartProductsById(int cartProductsId){
        return restTemplate.getForObject("http://cart-products-service/cart"+ cartProductsId, CartProduct.class);
    }


    @Autowired
    private UserRepo UserRepo;



    @Override
    public List<com.example.user.entity.User> findAll() {
        return UserRepo.findAll();
    }





    @Override
    public User getById(Integer id) {
        return UserRepo.getById(0);
    }








    @HystrixCommand(fallbackMethod = "getCartProductsFallback",
            threadPoolKey = "getSalesProducts",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="100"),
                    @HystrixProperty(name="maxQueueSize", value="50"),
            })
    @Override
    public ResponseTemplateVO getCartProducts(Integer userId) {

        ResponseTemplateVO vo = new ResponseTemplateVO();
        com.example.user.entity.User user = UserRepo.findById(userId).get();

        CartProduct cartProduct = restTemplate.getForObject("http://cart-products-service/cart/all/1" , CartProduct.class);

        vo.setCartProduct(cartProduct);

        return vo;
    }

    @Override
    public ResponseTemplateVO getFeedback(Integer userId) {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        com.example.user.entity.User user = UserRepo.findById(userId).get();

        Feedback feedback = restTemplate.getForObject("http://feedback-service/feedback/all/1" , Feedback.class);

        vo.setFeedback(feedback);

        return vo;
    }

    public ResponseTemplateVO getCartProductsFallback(Integer userId){
        System.out.println("here");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        com.example.user.entity.User user = UserRepo.findById(userId).get();
        com.example.user.entity.User a = new com.example.user.entity.User(0, " ", " ", " ", " ");
        CartProduct cartProduct = new CartProduct(0,"Error",0.0);
        vo.setCartProduct(cartProduct);
        vo.setUser(user);
        return vo;
    }
}

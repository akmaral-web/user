package com.example.user.service;


import com.example.user.VO.ResponseTemplateVO;
import com.example.user.entity.User;

import java.util.List;

public interface UserService {
    User getById(Integer id);
    List<User> findAll();
    ResponseTemplateVO getCartProducts(Integer userId);
    ResponseTemplateVO getFeedback(Integer userId);


}

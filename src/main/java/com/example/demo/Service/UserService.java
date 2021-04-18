package com.example.demo.Service;

import com.example.demo.model.Entity.User;


public interface UserService {
    User saveGithubUser(String code,String state);
}

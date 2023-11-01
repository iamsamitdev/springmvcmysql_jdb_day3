package com.itgenius.springmvcmysql_jdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itgenius.springmvcmysql_jdb.model.User;
import com.itgenius.springmvcmysql_jdb.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepositoy;

    // Get All User With Pagination
    public Page<User> getAllUser(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return userRepositoy.findAll(pageable);
    }

    // Save User
    public User save(User user){
        return userRepositoy.save(user);
    }

    // Get User By ID
    public Optional<User> findById(Integer id){
        return userRepositoy.findById(id);
    }

    // Delete User
    public void deleteById(User user){
        userRepositoy.delete(user);
    }
    
}

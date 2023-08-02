package com.backend.demo.service;

import com.backend.demo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserService extends CrudRepository<User,String> {
}

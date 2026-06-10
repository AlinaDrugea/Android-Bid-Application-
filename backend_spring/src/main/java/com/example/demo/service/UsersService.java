package com.example.demo.service;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.users;

public interface UsersService {
	users findByEmail(String email);
	users saveUsers(users users);
	String closeaccount(Long id_users);
	users login(String email, String password);
	users AddMoney(String email, BigDecimal balance);
	users Withdraw(String email, BigDecimal balance);

}

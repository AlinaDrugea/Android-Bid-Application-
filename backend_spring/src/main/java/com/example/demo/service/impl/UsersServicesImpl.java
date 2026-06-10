package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import com.example.demo.entity.users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UsersService;

import jakarta.transaction.Transactional;

import java.math.BigDecimal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UsersServicesImpl implements UsersService {
	private UsersRepository usersRepository;
	private BCryptPasswordEncoder passwordEncoder;
	
	public UsersServicesImpl(UsersRepository usersRepository)
	{
		super();
		this.usersRepository = usersRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	@Override
	public users findByEmail(String email) {
		return usersRepository.findByEmail(email);
	}
	@Override
	public users saveUsers(users user) {
		
		
		if(usersRepository.findByEmail(user.getEmail())!= null)
		{
			throw new RuntimeException("Acest email este deja inregistrat!");
		}
		
		String encodedPassword = passwordEncoder.encode(user.getPassw());
		user.setPassw(encodedPassword);
		
		if ("CUMPARATOR".equals(user.getRoles())) {
			user.setRoles("CUMPARATOR");
		}
		else {
			user.setRoles("VANZATOR");
		}
		
		
		return usersRepository.save(user);
	}
	@Override
	public String closeaccount(Long id_users) {
		usersRepository.deleteById(id_users);
		String message = "S-a sters contul!";
		return  message;
	}
	
	@Override
	public users login(String email, String password){
		users user = usersRepository.findByEmail(email);
		if (user != null && passwordEncoder.matches(password,user.getPassw())) {
			return user;
		}
		else
		return null;
	}
	
	@Override
	@Transactional
	public users AddMoney(String email , BigDecimal balance) {
		users user =  usersRepository.findByEmail(email);
		if(user == null) {
			return null;
		}
		if(balance.compareTo(BigDecimal.ZERO) < 0)
		{
			return null;
		}
		BigDecimal current_balance = user.getBalance().add(balance);
		user.setBalance(current_balance);
		return usersRepository.save(user);
	}
	
	@Override
	@Transactional
	public users Withdraw(String email , BigDecimal balance) {
		users user =  usersRepository.findByEmail(email);
		if(user == null) {
			return null;
		}
		if(balance.compareTo(BigDecimal.ZERO) < 0)
		{
			return null;
		}
		if(balance.compareTo(user.getBalance())>0)
		{
			return null;
		}
		BigDecimal current_balance = user.getBalance().subtract(balance);
		user.setBalance(current_balance);
		return usersRepository.save(user);
	}
	
	
	
	
}

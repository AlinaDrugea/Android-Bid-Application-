package com.example.demo.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	

	private UsersService usersService;
	public UserController(UsersService usersService) {
		super();
		this.usersService = usersService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody users user){
		try {
			users savedUser = usersService.saveUsers(user);
			return ResponseEntity.ok(savedUser);
		}
		catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody users user_log){
		users user = usersService.login(user_log.getEmail(), user_log.getPassw());
		if(user!=null) {
			return ResponseEntity.ok(user);
		}
		else {
			return ResponseEntity.status(401).body("Email sau parola incorecta!");
		}
	}
	
	@PostMapping("/add_balance/{balance}")
	public ResponseEntity<?> addBalance(@RequestBody users user_log,@PathVariable("balance") BigDecimal balance){
		if(user_log.getEmail() == null)
		{
			return ResponseEntity.badRequest().body("Email-ul lipseste!");
		}
		
		users user = usersService.AddMoney(user_log.getEmail(), balance);
		if(user!=null) {
			return ResponseEntity.ok(user);
		}
		else {
			return ResponseEntity.status(400).body("Nu s-a putut alimenta contul");
		}
	}
	
	@PostMapping("/withdraw/{balance}")
	public ResponseEntity<?> withdrawBalance(@RequestBody users user_log,@PathVariable("balance") BigDecimal balance){
		if(user_log.getEmail() == null)
		{
			return ResponseEntity.badRequest().body("Email-ul lipseste!");
		}
		
		users user = usersService.Withdraw(user_log.getEmail(), balance);
		if(user!=null) {
			return ResponseEntity.ok(user);
		}
		else {
			return ResponseEntity.status(400).body("Nu s-a putut retrage suma din cont");
		}
	}
	
	@GetMapping("get_balance/{email}")
	public ResponseEntity<?> get_balance(@PathVariable("email")String email){
		users user = usersService.findByEmail(email);
		if(user!=null)
		{

			return ResponseEntity.ok(user.getBalance());
		}
		else {
			return ResponseEntity.status(400).body("Nu s-a putut afla suma din cont");
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable Long id){
		String response = usersService.closeaccount(id);
		return ResponseEntity.ok(response);
	
	}
	
}

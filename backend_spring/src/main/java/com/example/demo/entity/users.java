package com.example.demo.entity;
import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "users")

public class users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iduser")
	private Long iduser;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "email", nullable = false , unique = true)
	private String email;
	
	@Column(name = "passw", nullable = false)
	private String passw;
	
	@Column(name = "roles", nullable = false)
	private String roles;
	
	@Column(name = "balance", nullable = false)
	private BigDecimal balance = BigDecimal.ZERO;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<anunturi> anunturi;
	
	@OneToMany(mappedBy = "utilizator", cascade = CascadeType.ALL, orphanRemoval = true )
	@JsonIgnore
	private Set<bids> bids;
	
	
	public users(){
		
	}	


	public users(String username, String email, String passw, String roles, BigDecimal balance , Set<anunturi> a, Set<bids> b)
	{
		super();
		this.username = username;
		this.email = email;
		this.passw = passw;
		this.roles = roles;
		this.balance = balance;
		this.anunturi = a;
		this.bids = b;
	}
	@JsonProperty("iduser")
	public Long getIduser() {
		return iduser;
	}
	
	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String name) {
		this.username = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getPassw() {
		return passw;
	}
	
	public void setPassw(String passw) {
		this.passw = passw;
	}
	
	public String getRoles() {
		return roles;
	}
	
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public Set<anunturi> getAnunt() {
		return this.anunturi;
	}
	
	public void setAnunt(Set<anunturi> anunturi) {
		this.anunturi = anunturi;
	}
	
	public Set<bids> getBid() {
		return this.bids;
	}
	
	public void setBid(Set<bids> bids) {
		this.bids = bids;
	}

}


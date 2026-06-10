package com.example.aplicatibid;

import java.math.BigDecimal;

public class User {
    private Long iduser;
    private String username;
    private String email;
    private String passw;
    private String roles;
    private BigDecimal balance;

    public User(){
    }
    public User(Long iduser, String username, String email, String passw, String roles, BigDecimal balance) {
        this.iduser = iduser;
        this.username = username;
        this.email = email;
        this.passw = passw;
        this.roles = roles;
        this.balance = balance;
    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


}

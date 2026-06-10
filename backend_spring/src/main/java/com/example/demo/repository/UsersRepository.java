package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.users;

@Repository
public interface UsersRepository extends JpaRepository<users, Long> {
	@Query("SELECT u FROM users u WHERE u.email = :email")
	users findByEmail(String email);
	@Query("SELECT u from users u WHERE u.username= :username")
	users findByUsername(String username);
}
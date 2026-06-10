package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.anunturi;
import java.util.List;

@Repository

public interface AnunturiRepository extends JpaRepository<anunturi, Long> {
	@Query("SELECT a FROM anunturi a WHERE a.user.iduser = :user")
	List<anunturi> findByUser_Iduser(Long user);
	
	@Query("SELECT a from anunturi a where lower(a.title) like lower(concat('%', :keyword ,'%')) AND a.status = 'ACTIV'")
	List<anunturi> Search(String keyword);
	@Query("SELECT a from anunturi a where a.id_anunt= :id_anunt")
	anunturi findByID(Long id_anunt);
}
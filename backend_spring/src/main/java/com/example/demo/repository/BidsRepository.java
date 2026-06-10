package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.entity.bids;
import com.example.demo.entity.anunturi;
import java.util.List;
@Repository
public interface BidsRepository extends JpaRepository<bids, Long> {

    @Query("SELECT b FROM bids b WHERE b.anunt.id_anunt = :idAnunt ORDER BY b.suma_licitata DESC LIMIT 1")
    bids findUltimulBid(@Param("idAnunt") Long idAnunt);

    @Query("SELECT b FROM bids b WHERE b.anunt.id_anunt = :idAnunt ORDER BY b.data_licitatie DESC")
    List<bids> findIstoricBids(@Param("idAnunt") Long idAnunt);
    
    @Query("SELECT DISTINCT b.anunt FROM bids b WHERE b.utilizator.iduser = :iduser")
    List<anunturi> findAnunturiLicitate(@Param("iduser") Long idUser);
    
    @Query(value = "SELECT * FROM bids WHERE id_anunt = :id_anunt ORDER BY suma_licitata DESC LIMIT 1", nativeQuery = true)
    bids findFirstByAnuntIdOrderBySumaDesc(@Param("id_anunt") Long anuntId);
    
    
    
}
package com.example.demo.service;

import com.example.demo.dto.BidsResponse;
import com.example.demo.entity.anunturi;
import com.example.demo.entity.bids;
import java.util.List;

import org.springframework.data.repository.query.Param;

public interface BidsService {
    
    BidsResponse liciteazaProdus(bids bid);
    bids getUltimulBid(Long id_anunt);
    List<bids> getIstoricBids(@Param("idAnunt") Long id_anunt);
    List<anunturi> findAnunturileUndeAiLicitat(@Param("iduser") Long idUser);

}
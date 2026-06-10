package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.demo.entity.anunturi;
import com.example.demo.entity.bids;
import com.example.demo.entity.users;
import com.example.demo.repository.BidsRepository;
import com.example.demo.repository.AnunturiRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.BidsService;
import com.example.demo.dto.BidsResponse;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BidsServiceImpl implements BidsService {
	
	private BidsRepository BidsRepository;
	private AnunturiRepository anunturiRepository;
	private UsersRepository usersRepository;
	
	public BidsServiceImpl(BidsRepository BidsRepository,AnunturiRepository anunturiRepository, UsersRepository usersRepository) {
		super();
		this.BidsRepository = BidsRepository;
		this.anunturiRepository = anunturiRepository; 
		this.usersRepository = usersRepository;
	}
	
	@Override
	@Transactional
	public BidsResponse liciteazaProdus(bids bid)
	{
		anunturi AnuntActual = anunturiRepository.findById(bid.getAnunt().getID_anunt())
								.orElseThrow(() -> new RuntimeException("Anuntul cu ID"+ bid.getAnunt().getID_anunt() + "nu a fost gasit!"));
		
		users UserActual = usersRepository.findById(bid.getUtilizator().getIduser())
				.orElseThrow(() -> new RuntimeException("Userul cu ID"+ bid.getUtilizator().getIduser() + "nu a fost gasit!"));
		bids Ultimulbid = getUltimulBid(AnuntActual.getID_anunt());
		
	
		if("VANZATOR".equals(UserActual.getRoles()))
			{throw new RuntimeException("Doar `Cumparatori` pot licita");}
		
		if(LocalDateTime.now().isBefore(AnuntActual.getStart_time()))
			{throw new RuntimeException("Licitatia nu a inceput inca!");}
		
		
		if(!"ACTIV".equals(AnuntActual.getStatus())) {
			throw new RuntimeException("Aceasta lictiatie este inchisa");
		}
	
		if(!AnuntActual.getStart_time().isBefore(LocalDateTime.now()) && "INACTIV".equals(AnuntActual.getStatus()))
		{
			AnuntActual.setStatus("ACTIV");
			anunturiRepository.save(AnuntActual);
		}
		
		
		if(AnuntActual.getEnd_time().isBefore(LocalDateTime.now())) {
			AnuntActual.setStatus("FINALIZAT");
			anunturiRepository.save(AnuntActual);
			throw new RuntimeException("Timpul a expirat chiar acum!");
		}
		
		if(bid.getSuma_licitata().compareTo(UserActual.getBalance()) > 0)
		{
			throw new RuntimeException("Fonduri insuficiente");
		}
		
		if(bid.getSuma_licitata().compareTo(AnuntActual.getMin_price())< 0)
		{throw new RuntimeException("Suma trebuie să fie cel puțin egală cu prețul de pornire!");}
		if(Ultimulbid != null) {
			if(bid.getSuma_licitata().compareTo(Ultimulbid.getSuma_licitata()) <= 0)
				{throw new RuntimeException("Suma oferita trebuie sa fie mai mare decat pretul actual!");}
			
			users FostLider = Ultimulbid.getUtilizator();
			FostLider.setBalance(FostLider.getBalance().add(Ultimulbid.getSuma_licitata()));
			usersRepository.save(FostLider);
			
		}
		
		
		UserActual.setBalance(UserActual.getBalance().subtract(bid.getSuma_licitata()));
		usersRepository.save(UserActual);
		bid.setData_licitatie(LocalDateTime.now());
		BidsRepository.save(bid);
		AnuntActual.setActual_price(bid.getSuma_licitata());
		anunturiRepository.save(AnuntActual);
		
		return new BidsResponse("Sunteti lider-ul lictiatiei!", UserActual.getBalance(), AnuntActual.getActual_price(),"ON_BID");
	}
	
	@Override
	public bids getUltimulBid(Long id_anunt) {
	    return BidsRepository.findUltimulBid(id_anunt);
	}

	
	@Override
	public List<bids> getIstoricBids(@Param("idAnunt") Long id_anunt) {
	    return BidsRepository.findIstoricBids(id_anunt);
	}
    
	@Override
	public  List<anunturi> findAnunturileUndeAiLicitat(@Param("iduser") Long idUser){
		return BidsRepository.findAnunturiLicitate(idUser);
	}
	
}

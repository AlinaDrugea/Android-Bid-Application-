package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.anunturi;
import com.example.demo.entity.users;
import com.example.demo.entity.bids;
import com.example.demo.repository.AnunturiRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.BidsRepository;
import com.example.demo.service.AnunturiService;



@Service
public class AnunturiServiceImpl implements AnunturiService {

	private AnunturiRepository AnunturiRepository;
	private UsersRepository usersRepository;
	private BidsRepository bidsRepository;
	
	public AnunturiServiceImpl(AnunturiRepository AnunturiRepository, UsersRepository usersRepository, BidsRepository bidsRepository)
	{
		super();
		this.AnunturiRepository = AnunturiRepository;
		this.usersRepository = usersRepository;
		this.bidsRepository = bidsRepository;
	}
	@Override
	public anunturi PublicaAnunt(anunturi anunt)
	{
		users userDb = usersRepository.findById(anunt.getuser().getIduser())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu există!"));

        if (!"VANZATOR".equals(userDb.getRoles())) {
            throw new RuntimeException("Doar vânzătorii pot publica anunțuri!");
        }
        if(!anunt.getStart_time().isBefore(LocalDateTime.now()))
        	{anunt.setStatus("INACTIV");}
        else {
        	anunt.setStatus("ACTIV");}
        anunt.setuser(userDb);
        return AnunturiRepository.save(anunt);
	}
	
	@Override
	public List<anunturi> SearchAnunt(String keyword)
	{
		List<anunturi> anunturi = AnunturiRepository.Search(keyword);
		return anunturi;
	}
	
	@Override
	public String StergeAnunt(Long id_anunt)
	{
		AnunturiRepository.deleteById(id_anunt);
		String message = "Anuntul a fost sters!";
		return message;			
	}
	
	@Override
	public List<anunturi> AfiseazaAnunturi()
	{
		List<anunturi> toate_anunturile = AnunturiRepository.findAll();
		LocalDateTime now = LocalDateTime.now();
		List<anunturi> active = new ArrayList<>();
		for(anunturi anunt: toate_anunturile)
		{
			if(anunt.getEnd_time().isBefore(now) && !"FINALIZAT".equals(anunt.getStatus())) {
				anunt.setStatus("FINALIZAT");
				AnunturiRepository.save(anunt);
			}
			else if(anunt.getStart_time().isBefore(LocalDateTime.now())&& "INACTIV".equals(anunt.getStatus()))
			{
				anunt.setStatus("ACTIV");
				AnunturiRepository.save(anunt);
			}
				
			if("ACTIV".equals(anunt.getStatus())) {
				active.add(anunt);
			}
		}
		return active;
	}
	
	@Override
	public List<anunturi> getAnunturibyUserID(Long id_user)
	{
		List<anunturi> anunturiUser =  AnunturiRepository.findByUser_Iduser(id_user);
		LocalDateTime now = LocalDateTime.now();
		for(anunturi anunt : anunturiUser) {
			if(anunt.getStart_time().isBefore(LocalDateTime.now()) && "INACTIV".equals(anunt.getStatus()))
			{
				anunt.setStatus("ACTIV");
				AnunturiRepository.save(anunt);
			}
			
			if(anunt.getEnd_time().isBefore(now) && !"FINALIZAT".equals(anunt.getStatus())) {
				anunt.setStatus("FINALIZAT");
				AnunturiRepository.save(anunt);
			}
		}
		return anunturiUser;
	}
	
	@Override
	public anunturi getAnuntById(Long id) {
		anunturi anunt = AnunturiRepository.findByID(id);
		if(anunt!=null)
		{
			bids UltimaLicitatie = bidsRepository.findFirstByAnuntIdOrderBySumaDesc(id);
			if(UltimaLicitatie!=null) {
				anunt.setUsernameLider(UltimaLicitatie.getUtilizator().getUsername());
			}
			else
			{
				anunt.setUsernameLider("Niciun Licitant!");
			}
		}
		return anunt;
	}
	
}

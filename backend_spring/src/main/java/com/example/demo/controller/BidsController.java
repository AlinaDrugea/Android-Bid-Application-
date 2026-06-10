package com.example.demo.controller;


import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.BidsResponse;
import com.example.demo.entity.anunturi;
import com.example.demo.entity.bids;
import com.example.demo.repository.BidsRepository;
import com.example.demo.service.BidsService;

@RestController
@RequestMapping("/api/bids")
public class BidsController {

	private BidsService bidsService;
	
	BidsController(BidsService bidsService){
		super();
		this.bidsService = bidsService;
	}
	
	@PostMapping("/bid")
	public ResponseEntity<?> liciteaza(@RequestBody bids bid) {
		try {
			BidsResponse message =  bidsService.liciteazaProdus(bid);
			return ResponseEntity.ok(message);
		}
		catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/istoric/{idAnunt}")
	public ResponseEntity<?> getistoric(@PathVariable("idAnunt") Long id_anunt){
	try {
		List<bids> istoric = bidsService.getIstoricBids(id_anunt);
        return ResponseEntity.ok(istoric);
    } catch (Exception e) {
    	e.printStackTrace();
        return ResponseEntity.internalServerError().body("Eroare la preluarea istoricului.");
    }
	
}

	@GetMapping("/istoric_user/{iduser}")
	public ResponseEntity<?> getuseristoric(@PathVariable("iduser") Long iduser){
		try {
			List<anunturi> lista = bidsService.findAnunturileUndeAiLicitat(iduser);
	        return ResponseEntity.ok(lista);
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body("Eroare la preluarea istoricului.");
	    }
	}
	
}

package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import com.example.demo.entity.anunturi;
import com.example.demo.entity.users;
import com.example.demo.repository.AnunturiRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.AnunturiService;

@RestController
@RequestMapping("/api/anunturi")
public class AnunturiController {
	private AnunturiService anunturiService;
	private UsersRepository usersRepository;
	
	public AnunturiController(AnunturiService anunturiService, UsersRepository usersRepository) {
		super();
		this.anunturiService = anunturiService;
		this.usersRepository = usersRepository;
	}
	
	@GetMapping("/pagina_principala")
	public ResponseEntity<?> prima_pagina(){
		try {
			List<anunturi> anunturi = anunturiService.AfiseazaAnunturi();
			return ResponseEntity.ok(anunturi);
		}
		catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<?> search_page(@PathVariable String keyword){
		try {
			List<anunturi> anunturi = anunturiService.SearchAnunt(keyword);
			return ResponseEntity.ok(anunturi);
		}
		catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> licitatiile_mele(@PathVariable Long id){
		try {
			List<anunturi> anunturi = anunturiService.getAnunturibyUserID(id);
			return ResponseEntity.ok(anunturi);
		}
		catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/publica")
	public ResponseEntity<?> publica_anunt(@RequestBody anunturi anunt){
		try {
			if("CUMPARATOR".equals(anunt.getuser().getRoles())) {
				return ResponseEntity.status(403).body("Doar vânzătorii pot publica anunțuri!");
			}
			else{
			anunturi anunt_de_publicat = anunturiService.PublicaAnunt(anunt);	
			return ResponseEntity.ok(anunt_de_publicat);
			}
		}
		catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/anunt/{id}")
	public ResponseEntity<?> getAnuntById(@PathVariable Long id,Principal principal	) {
	    try {
	    	anunturi anunt = anunturiService.getAnuntById(id);
	    	if (anunt == null) {
                return ResponseEntity.status(404).body("Anunțul nu a fost găsit!");
            }
	                      
	    	return ResponseEntity.ok(anunt);
	    }
	    catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{id_anunt}")
	public ResponseEntity<?> sterge_anunt(@PathVariable Long id_anunt){
		try {
			String message = anunturiService.StergeAnunt(id_anunt);
			return ResponseEntity.ok(message);
		}
		catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}	
}
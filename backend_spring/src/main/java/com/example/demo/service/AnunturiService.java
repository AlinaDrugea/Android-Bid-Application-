package com.example.demo.service;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.anunturi;

public interface AnunturiService {
	anunturi PublicaAnunt(anunturi anunt);
	String StergeAnunt(Long id_anunt);
	List<anunturi> AfiseazaAnunturi();
	List<anunturi> getAnunturibyUserID(Long id_user);
	List<anunturi> SearchAnunt(String keyword);
	anunturi getAnuntById(@PathVariable Long id);
}

package com.example.demo.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "bids")
public class bids {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_bid;
	
	@Column(name = "suma_licitata", nullable = false)
	private BigDecimal suma_licitata;
	@JsonFormat
	@Column(name = "data_licitatie", nullable = false)
	private LocalDateTime data_licitatie;
	
	@Column(name = "status_bid", nullable = false)
	private String status_bid;
		

	@ManyToOne
		@JoinColumn(name = "id_anunt")
		private anunturi anunt;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne
		@JoinColumn(name = "id_utilizator")
		private users utilizator;
	
	public bids() {
		
	}
	
	public bids(BigDecimal suma_licitata, LocalDateTime data_licitatie, String status_bid, anunturi anunt, users utilizator) {
			super();
			this.suma_licitata = suma_licitata;
			this.data_licitatie = data_licitatie;
			this.status_bid = status_bid;
			this.anunt = anunt;
			this.utilizator = utilizator;
		}
	
	
	public BigDecimal getSuma_licitata() {
		return suma_licitata;
	}
	
	public void setSuma_licitata(BigDecimal price) {
		this.suma_licitata = price;
	}
	
	public LocalDateTime getData_licitatie() {
		return data_licitatie;
	}
	
	public void setData_licitatie(LocalDateTime time) {
		this.data_licitatie = time;
	}
	
	public String getStatus_bid() {
		return status_bid;
	}
	
	public void setStatus_bid(String status) {
		this.status_bid = status;
	}
	
	public anunturi getAnunt() {
		return anunt;
	}
	
	public void setAnunt(anunturi anunt) {
		this.anunt = anunt;
	}
	
	public users getUtilizator() {
		return utilizator;
	}
	
	public void setUtilizator(users utilizator) {
		this.utilizator = utilizator;
	}
	public String getUsernameLider() {
	    if (this.utilizator != null) {
	        return this.utilizator.getUsername();
	    }
	    return "Anonim";
	}

}


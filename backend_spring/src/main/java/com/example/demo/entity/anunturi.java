package com.example.demo.entity;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "anunturi")
public class anunturi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id_anunt")
	private Long id_anunt;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "min_price", nullable =false)
	private BigDecimal min_price;
	
	@Column(name = "actual_price", nullable = false)
	private BigDecimal actual_price;
	
	@Column(name = "start_time")
	private LocalDateTime start_time;
	
	@Column(name = "end_time", nullable = false)
	private LocalDateTime end_time;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "status")
	private String status;
	
	@Transient
	private String usernameLider;

	
	@ManyToOne
		@JoinColumn(name = "id_user")
		private users user;
	
	
	@OneToMany(mappedBy="anunt", cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<bids> bids;
	
	public anunturi() {
		
	}
	
	public anunturi(String title, String description , BigDecimal min_price , BigDecimal actual_price , LocalDateTime start_time, LocalDateTime end_time, String image,String status, users user, Set<bids> bids)
	{
		super();
		this.title = title;
		this.description = description;
		this.min_price = min_price;
		this.actual_price = actual_price;
		this.start_time = start_time;
		this.end_time = end_time;
		this.image = image;
		this.user = user;
		this.bids = bids;
	}	
	

	public Long getID_anunt(){
			return id_anunt;
		}
	public void setID_anunt(Long id_anunt) {
			this.id_anunt = id_anunt;
		}
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String name) {
		this.title = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String name) {
		this.description = name;
	}
	
	public BigDecimal getMin_price() {
		return min_price;
	}
	
	public void setMin_price(BigDecimal price) {
		this.min_price = price;
	}
	
	public BigDecimal getActual_price() {
		return actual_price;
	}
	
	public void setActual_price(BigDecimal price) {
		this.actual_price = price;
	}
	
	public LocalDateTime getStart_time() {
		return start_time;
	}
	
	public void setStart_time(LocalDateTime time) {
		this.start_time = time;
	}
	
	public LocalDateTime getEnd_time() {
		return end_time;
	}
	
	public void setEnd_time(LocalDateTime time) {
		this.end_time = time;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String url) {
		this.image = url;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public users getuser() {
		return user;
	}
	
	public void setuser(users user) {
		this.user = user;
	}
	
	public Set<bids> getbids(){
		return bids;
	}
	
	public void setbids(Set<bids> bid) {
		this.bids = bid;
	}
	
	public String getUsernameLider() {
		return usernameLider;
	}
	
	public void setUsernameLider(String username) {
		this.usernameLider = username;
	}
	
}

	
	


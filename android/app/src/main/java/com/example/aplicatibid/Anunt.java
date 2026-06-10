package com.example.aplicatibid;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Anunt {
    private Long id_anunt;
    private String title;
    private String description;
    private BigDecimal min_price;
    private BigDecimal actual_price;
    private String start_time;
    private String end_time;
    private String image;
    private String status;

    private String usernameLider;

    private BigDecimal userBalance;
    private User user;



    public Anunt(Long id_anunt, String title, String description, BigDecimal min_price, BigDecimal actual_price, String start_time, String end_time, String image, String status, User user) {
        this.id_anunt = id_anunt;
        this.title = title;
        this.description = description;
        this.min_price = min_price;
        this.actual_price = actual_price;
        this.start_time = start_time;
        this.end_time = end_time;
        this.image = image;
        this.status = status;
        this.user = user;

    }

    public Long getId_anunt() {
        return id_anunt;
    }

    public void setId_anunt(Long id_anunt) {
        this.id_anunt = id_anunt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMin_price() {
        return min_price;
    }

    public void setMin_price(BigDecimal min_price) {
        this.min_price = min_price;
    }

    public BigDecimal getActual_price() {
        return actual_price;
    }

    public void setActual_price(BigDecimal actual_price) {
        this.actual_price = actual_price;
    }

    public String getStart_time() {
        return start_time;

    }

    public String getUsernameLider() {
        return usernameLider;
    }
    public BigDecimal getUserBalance() {
        return userBalance;
    }


    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public long getEndTimeInMillis() {
        try {
            // Presupunem că this.end_time este String-ul primit de la server (ex: "2026-05-12T22:00:00")
            LocalDateTime endTime = LocalDateTime.parse(this.end_time);
            // Convertim LocalDateTime în milisecunde raportate la System.currentTimeMillis()
            return endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public String getTimpRamas() {
        LocalDateTime acum = LocalDateTime.now();
        LocalDateTime end_time = LocalDateTime.parse(this.end_time);
        if (end_time == null || acum.isAfter(end_time)) {
            return "Încheiată";
        }
        Duration durata = Duration.between(acum, end_time);
        long ore = durata.toHours();
        long minute = durata.toMinutesPart();
        long secunde = durata.toSecondsPart();
        return String.format("%02d:%02d:%02d", ore, minute, secunde);
    }

}

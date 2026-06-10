package com.example.aplicatibid;

import java.math.BigDecimal;

public class Bids {
    private BigDecimal suma_licitata;
    private String data_licitatie;
    private String status_bid;

    private AnuntId anunt;
    private UtilizatorId utilizator;

    public Bids(BigDecimal suma_licitata, String data_licitatie, String status_bid, Long idAnunt, Long idUser) {

        this.suma_licitata = suma_licitata;
        this.data_licitatie = data_licitatie;
        this.status_bid = status_bid;
        this.anunt = new AnuntId(idAnunt);
        this.utilizator = new UtilizatorId(idUser);

    }
    public BigDecimal getSuma_licitata() {
        return suma_licitata;
    }
    public void setSuma_licitata(BigDecimal suma_licitata) {
        this.suma_licitata = suma_licitata;
    }
    public String getData_licitatie() {
        return data_licitatie;
    }
    public void setData_licitatie(String data_licitatie) {
        this.data_licitatie = data_licitatie;
    }
    public String getStatus_bid() {
        return status_bid;
    }
    public void setStatus_bid(String status_bid) {
        this.status_bid = status_bid;
    }


    public static class AnuntId{
        public Long id_anunt;
        public AnuntId(Long id_anunt) {
            this.id_anunt = id_anunt;
        }
    }

    public static class UtilizatorId{
        public Long iduser;
        public UtilizatorId(Long iduser) {
            this.iduser = iduser;
        }
    }
}

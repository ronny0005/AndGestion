package com.example.tron.andgestion.modele;

/**
 * Created by T.Ron on 12/06/2016.
 */
public class Entete {

    private String id;
    private String ref;
    private String entete;
    private String DO_Date;
    private String id_client;
    private String nouveau;
    private String statut;
    private String type_paiement;
    private String mtt_avance;
    private String latitude;
    private String longitude;
    private String totalTTC;

    public Entete(String ref, String entete, String DO_Date, String id_client, String nouveau, String statut, String type_paiement, String mtt_avance, String latitude, String longitude, String totalTTC) {
        this.ref = ref;
        this.entete = entete;
        this.DO_Date = DO_Date;
        this.id_client = id_client;
        this.nouveau = nouveau;
        this.statut = statut;
        this.type_paiement = type_paiement;
        this.mtt_avance = mtt_avance;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalTTC = totalTTC;
    }

    public Entete(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getEntete() {
        return entete;
    }

    public void setEntete(String entete) {
        this.entete = entete;
    }

    public String getDO_Date() {
        return DO_Date;
    }

    public void setDO_Date(String DO_Date) {
        this.DO_Date = DO_Date;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getNouveau() {
        return nouveau;
    }

    public void setNouveau(String nouveau) {
        this.nouveau = nouveau;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getType_paiement() {
        return type_paiement;
    }

    public void setType_paiement(String type_paiement) {
        this.type_paiement = type_paiement;
    }

    public String getMtt_avance() {
        return mtt_avance;
    }

    public void setMtt_avance(String mtt_avance) {
        this.mtt_avance = mtt_avance;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTotalTTC() {
        return totalTTC;
    }

    public void setTotalTTC(String totalTTC) {
        this.totalTTC = totalTTC;
    }

    @Override
    public String toString() {
        return "Entete{" +
                "id='" + id + '\'' +
                ", ref='" + ref + '\'' +
                ", entete='" + entete + '\'' +
                ", DO_Date='" + DO_Date + '\'' +
                ", id_client='" + id_client + '\'' +
                ", nouveau='" + nouveau + '\'' +
                ", statut='" + statut + '\'' +
                ", type_paiement='" + type_paiement + '\'' +
                ", mtt_avance='" + mtt_avance + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", totalTTC='" + totalTTC + '\'' +
                '}';
    }
}

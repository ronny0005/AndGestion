package com.example.tron.andgestion.Stock;

/**
 * Created by BORICE on 13/04/2016.
 */
public class BmqModele {

    private double PR;
    private double RECU;
    private double RETOUR;
    private double AVARI;
    private double VENDU;
    private double VALEUR;
    private double TVA;
    private double PRECOMPTE;
    private double REMISE;
    private double VENTE_TTC;
    private double MANQUANT;
    private double MARGE;
    private double AVANCE;
    private String AR_Ref,DL_Design;
    private int NBLIGNE;

    public BmqModele() {
    }

    public BmqModele(double PR, double RECU, double RETOUR, double AVARI, double VENDU, double VALEUR, double TVA, double PRECOMPTE, double REMISE, double COMPTANT_TTC, double MANQUANT, String AR_Ref, String DL_Design, int NBLIGNE,double marge,double avance) {
        this.PR = PR;
        this.RECU = RECU;
        this.RETOUR = RETOUR;
        this.AVARI = AVARI;
        this.VENDU = VENDU;
        this.VALEUR = VALEUR;
        this.TVA = TVA;
        this.PRECOMPTE = PRECOMPTE;
        this.REMISE = REMISE;
        this.VENTE_TTC = COMPTANT_TTC;
        this.MANQUANT = MANQUANT;
        this.AR_Ref = AR_Ref;
        this.DL_Design = DL_Design;
        this.NBLIGNE = NBLIGNE;
        this.setMARGE(marge);
        this.AVANCE = avance;
    }

    public double getPR() {
        return PR;
    }

    public void setPR(double PR) {
        this.PR = PR;
    }

    public double getRECU() {
        return RECU;
    }

    public void setRECU(double RECU) {
        this.RECU = RECU;
    }

    public double getRETOUR() {
        return RETOUR;
    }

    public void setRETOUR(double RETOUR) {
        this.RETOUR = RETOUR;
    }

    public double getAVARI() {
        return AVARI;
    }

    public void setAVARI(double AVARI) {
        this.AVARI = AVARI;
    }

    public double getVENDU() {
        return VENDU;
    }

    public void setVENDU(double VENDU) {
        this.VENDU = VENDU;
    }

    public double getVALEUR() {
        return VALEUR;
    }

    public void setVALEUR(double VALEUR) {
        this.VALEUR = VALEUR;
    }

    public double getTVA() {
        return TVA;
    }

    public void setTVA(double TVA) {
        this.TVA = TVA;
    }

    public double getPRECOMPTE() {
        return PRECOMPTE;
    }

    public void setPRECOMPTE(double PRECOMPTE) {
        this.PRECOMPTE = PRECOMPTE;
    }

    public double getREMISE() {
        return REMISE;
    }

    public void setREMISE(double REMISE) {
        this.REMISE = REMISE;
    }

    public double getVenteTTC() {
        return VENTE_TTC;
    }

    public void setVenteTTC(double COMPTANT_TTC) {
        this.VENTE_TTC = COMPTANT_TTC;
    }

    public double getMANQUANT() {
        return MANQUANT;
    }

    public void setMANQUANT(double MANQUANT) {
        this.MANQUANT = MANQUANT;
    }

    public String getAR_Ref() {
        return AR_Ref;
    }

    public void setAR_Ref(String AR_Ref) {
        this.AR_Ref = AR_Ref;
    }

    public String getDL_Design() {
        return DL_Design;
    }

    public void setDL_Design(String DL_Design) {
        this.DL_Design = DL_Design;
    }

    public int getNBLIGNE() {
        return NBLIGNE;
    }

    public void setNBLIGNE(int NBLIGNE) {
        this.NBLIGNE = NBLIGNE;
    }

    public double getMARGE() {
        return MARGE;
    }

    public void setMARGE(double MARGE) {
        this.MARGE = MARGE;
    }

    public double getAvance() {
        return AVANCE;
    }

    public void setAavance(double avance) {
        this.MARGE = avance;
    }



    @Override
    public String toString() {
        return "BmqModele{" +
                "PR=" + PR +
                ", RECU=" + RECU +
                ", RETOUR=" + RETOUR +
                ", AVARI=" + AVARI +
                ", VENDU=" + VENDU +
                ", VALEUR=" + VALEUR +
                ", TVA=" + TVA +
                ", PRECOMPTE=" + PRECOMPTE +
                ", REMISE=" + REMISE +
                ", COMPTANT_TTC=" + VENTE_TTC +
                ", MANQUANT=" + MANQUANT +
                ", MARGE=" + MARGE +
                ", AR_Ref='" + AR_Ref + '\'' +
                ", DL_Design='" + DL_Design + '\'' +
                ", NBLIGNE=" + NBLIGNE +
                '}';
    }
}

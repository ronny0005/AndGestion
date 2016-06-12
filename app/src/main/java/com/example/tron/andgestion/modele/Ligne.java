package com.example.tron.andgestion.modele;

/**
 * Created by T.Ron on 12/06/2016.
 */
public class Ligne {

    private String id;
    private String entete;
    private String DO_Date;
    private String AR_Ref;
    private String AR_Design;
    private String DL_Qte;
    private String DL_PrixUnitaire;
    private String DL_Taxe1;
    private String DL_Taxe2;
    private String DL_Taxe3;
    private String DL_MontantTTC;
    private String DL_Ligne;

    public Ligne(String id, String entete, String DO_Date, String AR_Ref, String AR_Design, String DL_Qte, String DL_PrixUnitaire, String DL_Taxe1, String DL_Taxe2, String DL_Taxe3, String DL_MontantTTC, String DL_Ligne) {
        this.id = id;
        this.entete = entete;
        this.DO_Date = DO_Date;
        this.AR_Ref = AR_Ref;
        this.AR_Design = AR_Design;
        this.DL_Qte = DL_Qte;
        this.DL_PrixUnitaire = DL_PrixUnitaire;
        this.DL_Taxe1 = DL_Taxe1;
        this.DL_Taxe2 = DL_Taxe2;
        this.DL_Taxe3 = DL_Taxe3;
        this.DL_MontantTTC = DL_MontantTTC;
        this.DL_Ligne = DL_Ligne;
    }

    public Ligne(String entete, String DO_Date, String AR_Ref, String AR_Design, String DL_Qte, String DL_PrixUnitaire, String DL_Taxe1, String DL_Taxe2, String DL_Taxe3, String DL_MontantTTC, String DL_Ligne) {
        this.entete = entete;
        this.DO_Date = DO_Date;
        this.AR_Ref = AR_Ref;
        this.AR_Design = AR_Design;
        this.DL_Qte = DL_Qte;
        this.DL_PrixUnitaire = DL_PrixUnitaire;
        this.DL_Taxe1 = DL_Taxe1;
        this.DL_Taxe2 = DL_Taxe2;
        this.DL_Taxe3 = DL_Taxe3;
        this.DL_MontantTTC = DL_MontantTTC;
        this.DL_Ligne = DL_Ligne;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAR_Ref() {
        return AR_Ref;
    }

    public void setAR_Ref(String AR_Ref) {
        this.AR_Ref = AR_Ref;
    }

    public String getAR_Design() {
        return AR_Design;
    }

    public void setAR_Design(String AR_Design) {
        this.AR_Design = AR_Design;
    }

    public String getDL_Qte() {
        return DL_Qte;
    }

    public void setDL_Qte(String DL_Qte) {
        this.DL_Qte = DL_Qte;
    }

    public String getDL_PrixUnitaire() {
        return DL_PrixUnitaire;
    }

    public void setDL_PrixUnitaire(String DL_PrixUnitaire) {
        this.DL_PrixUnitaire = DL_PrixUnitaire;
    }

    public String getDL_Taxe1() {
        return DL_Taxe1;
    }

    public void setDL_Taxe1(String DL_Taxe1) {
        this.DL_Taxe1 = DL_Taxe1;
    }

    public String getDL_Taxe2() {
        return DL_Taxe2;
    }

    public void setDL_Taxe2(String DL_Taxe2) {
        this.DL_Taxe2 = DL_Taxe2;
    }

    public String getDL_Taxe3() {
        return DL_Taxe3;
    }

    public void setDL_Taxe3(String DL_Taxe3) {
        this.DL_Taxe3 = DL_Taxe3;
    }

    public String getDL_MontantTTC() {
        return DL_MontantTTC;
    }

    public void setDL_MontantTTC(String DL_MontantTTC) {
        this.DL_MontantTTC = DL_MontantTTC;
    }

    public String getDL_Ligne() {
        return DL_Ligne;
    }

    public void setDL_Ligne(String DL_Ligne) {
        this.DL_Ligne = DL_Ligne;
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "id='" + id + '\'' +
                ", entete='" + entete + '\'' +
                ", DO_Date='" + DO_Date + '\'' +
                ", AR_Ref='" + AR_Ref + '\'' +
                ", AR_Design='" + AR_Design + '\'' +
                ", DL_Qte='" + DL_Qte + '\'' +
                ", DL_PrixUnitaire='" + DL_PrixUnitaire + '\'' +
                ", DL_Taxe1='" + DL_Taxe1 + '\'' +
                ", DL_Taxe2='" + DL_Taxe2 + '\'' +
                ", DL_Taxe3='" + DL_Taxe3 + '\'' +
                ", DL_MontantTTC='" + DL_MontantTTC + '\'' +
                ", DL_Ligne='" + DL_Ligne + '\'' +
                '}';
    }
}

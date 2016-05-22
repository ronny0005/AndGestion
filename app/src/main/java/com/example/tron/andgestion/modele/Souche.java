package com.example.tron.andgestion.modele;

/**
 * Created by T.Ron$ on 15/03/2016.
 */
public class Souche {
    private int id;
    private String jo_num;
    private String jo_numsituation;
    private String s_intitule;
    private int s_valide;
    private int cbindice;
    private int cbmarq;

    public Souche(String jo_num, String jo_numsituation, String s_intitule, int s_valide, int cbindice, int cbmarq) {
        this.jo_num = jo_num;
        this.jo_numsituation = jo_numsituation;
        this.s_intitule = s_intitule;
        this.s_valide = s_valide;
        this.cbindice = cbindice;
        this.cbmarq = cbmarq;
    }

    public String getJo_num() {
        return jo_num;
    }

    public void setJo_num(String jo_num) {
        this.jo_num = jo_num;
    }

    public String getJo_numsituation() {
        return jo_numsituation;
    }

    public void setJo_numsituation(String jo_numsituation) {
        this.jo_numsituation = jo_numsituation;
    }

    public String getS_intitule() {
        return s_intitule;
    }

    public void setS_intitule(String s_intitule) {
        this.s_intitule = s_intitule;
    }

    public int getS_valide() {
        return s_valide;
    }

    public void setS_valide(int s_valide) {
        this.s_valide = s_valide;
    }

    public int getCbindice() {
        return cbindice;
    }

    public void setCbindice(int cbindice) {
        this.cbindice = cbindice;
    }

    public int getCbmarq() {
        return cbmarq;
    }

    public void setCbmarq(int cbmarq) {
        this.cbmarq = cbmarq;
    }
}

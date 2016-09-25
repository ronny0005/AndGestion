package com.example.tron.andgestion.modele;

import java.io.Serializable;

/**
 * Created by T.Ron on 12/06/2016.
 */
public class QteStock implements Cloneable, Serializable{

    private String AS_MontSto,AR_Ref;
    private int DE_No,AS_QteSto;
    public QteStock() {
    }

    public QteStock(String AS_MontSto, int DE_No, String AR_Ref, int AS_QteSto) {
        this.AS_MontSto = AS_MontSto;
        this.DE_No = DE_No;
        this.AR_Ref = AR_Ref;
        this.AS_QteSto = AS_QteSto;
    }

    public String getAS_MontSto() {
        return AS_MontSto;
    }

    public void setAS_MontSto(String AS_MontSto) {
        this.AS_MontSto = AS_MontSto;
    }

    public int getDE_No() {
        return DE_No;
    }

    public void setDE_No(int DE_No) {
        this.DE_No = DE_No;
    }

    public String getAR_Ref() {
        return AR_Ref;
    }

    public void setAR_Ref(String AR_Ref) {
        this.AR_Ref = AR_Ref;
    }

    public int getAS_QteSto() {
        return AS_QteSto;
    }

    public void setAS_QteSto(int AS_QteSto) {
        this.AS_QteSto = AS_QteSto;
    }

    @Override
    public String toString() {
        return "QteStock{" +
                "AS_MontSto='" + AS_MontSto + '\'' +
                ", DE_No='" + DE_No + '\'' +
                ", AR_Ref='" + AR_Ref + '\'' +
                ", AS_QteSto='" + AS_QteSto + '\'' +
                '}';
    }
}


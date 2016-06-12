package com.example.tron.andgestion.modele;

/**
 * Created by T.Ron on 12/06/2016.
 */
public class QteStock {

    private String AS_MontSto,DE_No,AR_Ref,AS_QteSto;

    public QteStock() {
    }

    public QteStock(String AS_MontSto, String DE_No, String AR_Ref, String AS_QteSto) {
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

    public String getDE_No() {
        return DE_No;
    }

    public void setDE_No(String DE_No) {
        this.DE_No = DE_No;
    }

    public String getAR_Ref() {
        return AR_Ref;
    }

    public void setAR_Ref(String AR_Ref) {
        this.AR_Ref = AR_Ref;
    }

    public String getAS_QteSto() {
        return AS_QteSto;
    }

    public void setAS_QteSto(String AS_QteSto) {
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


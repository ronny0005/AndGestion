package com.example.tron.andgestion.Stock;

/**
 * Created by BORICE on 26/03/2016.
 */
public class Stock {

    private String AR_Ref;
    private String AR_Design;
    private int DE_No;
    private double AS_MontantSto;
    private double AS_QteSto;


    public Stock() {
    }

    public Stock(String AR_Ref, String AR_Design, int DE_No, double AS_MontantSto, double AS_QteSto) {
        this.AR_Ref = AR_Ref;
        this.AR_Design = AR_Design;
        this.DE_No = DE_No;
        this.AS_MontantSto = AS_MontantSto;
        this.AS_QteSto = AS_QteSto;
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

    public int getDE_No() {
        return DE_No;
    }

    public void setDE_No(int DE_No) {
        this.DE_No = DE_No;
    }

    public double getAS_MontantSto() {
        return AS_MontantSto;
    }

    public void setAS_MontantSto(double AS_MontantSto) {
        this.AS_MontantSto = AS_MontantSto;
    }

    public double getAS_QteSto() {
        return AS_QteSto;
    }

    public void setAS_QteSto(double AS_QteSto) {
        this.AS_QteSto = AS_QteSto;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "AR_Ref='" + AR_Ref + '\'' +
                ", AR_Design='" + AR_Design + '\'' +
                ", DE_No=" + DE_No +
                ", AS_MontantSto=" + AS_MontantSto +
                ", AS_QteSto=" + AS_QteSto +
                '}';
    }
}

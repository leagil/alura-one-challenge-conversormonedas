package modelos;

public class ConvRateDouble {
    Double ars;
    Double brl;
    Double mxn;
    Double usd;
    public ConvRateDouble(ConversionRate conversionRate){
        this.ars = Double.parseDouble(conversionRate.ARS());
        this.brl = Double.parseDouble(conversionRate.BRL());
        this.mxn = Double.parseDouble(conversionRate.MXN());
        this.usd = Double.parseDouble(conversionRate.USD());
    }
    @Override
    public String toString(){
        return "ARS:" + this.ars.toString() +
                " - BRL:" + this.brl.toString() +
                " - MXN:" + this.mxn.toString() +
                " - USD:" + this.usd.toString();
    }

    public Double getArs() {
        return ars;
    }

    public Double getBrl() {
        return brl;
    }

    public Double getMxn() {
        return mxn;
    }

    public Double getUsd() {
        return usd;
    }
}

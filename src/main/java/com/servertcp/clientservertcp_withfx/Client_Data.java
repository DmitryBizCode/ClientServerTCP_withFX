package com.servertcp.clientservertcp_withfx;

public class Client_Data{
    private Integer ID;

    private Double Xs;
    private Double Ys;
    private Double Cs;
    private Double Res;
    public Client_Data(Integer ID,Double Xs,Double Ys,Double Cs,Double Res){
        this.ID = ID;
        this.Xs = Xs;
        this.Ys = Ys;
        this.Cs = Cs;
        this.Res = Res;
    }
    public Integer getID(){
        return ID;
    }
    public Double getXs(){
        return Xs;
    }
    public Double getYs(){
        return Ys;
    }
    public Double getCs(){
        return Cs;
    }
    public Double getRes(){
        return Res;
    }
    public void setID(Integer ID){
        this.ID = ID;
    }
    public void setXs(Double Xs){
        this.Xs = Xs;
    }
    public void setYs(Double Ys){
        this.Ys = Ys;
    }
    public void setCs(Double Cs){
        this.Cs = Cs;
    }
    public void setRes(Double Res){
        this.Res = Res;
    }
}

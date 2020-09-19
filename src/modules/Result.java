package modules;


import java.io.Serializable;

public class Result implements Serializable {
    private Double x;
    private Double y;
    private Double r;
    private boolean result;

    public Result(){

    }

    public Double getX(){
        return this.x;
    }

    public Double getY(){
        return this.y;
    }

    public Double getR(){
        return this.r;
    }

    public boolean isResult() {
        return result;
    }

    public void setX(Double x){
        this.x=x;
    }

    public void setY(Double y){
        this.y=y;
    }

    public void setR(Double r){
        this.r=r;
    }

    public void setResult(boolean res) {
        this.result=res;
    }


}

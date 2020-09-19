package modules;


import java.io.Serializable;

public class Result implements Serializable {
    private Double x;
    private Double y;
    private Double r;
    private boolean result;


    public Double getX(){
        return x;
    }

    public Double getY(){
        return y;
    }

    public Double getR(){
        return r;
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

    public void setResult(boolean result) {
        this.result=result;
    }


}

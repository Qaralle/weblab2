package modules;


import java.io.Serializable;

import static java.lang.Math.pow;

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

    public void checkTheArea() {
        if (x > 0 && y < 0) {
            this.result= false;
        } else if ((x <= 0 && y <= 0) && (x >= -r) && (y >=- r)) {
            this.result= true;
        } else if (x >= 0 && y >= 0 && (y <= -2*x + r)) {
            this.result= true;
        } else this.result=((x <= 0 && y >= 0) && (pow(x,2) + pow(y,2)<=pow(r/2,2)));
    }



}

package MM1;
import java.lang.Math;

class Customer {
    private double arriveTime;
    private double intervalTime;
    private int id;

    Customer(int id, double averageIntervalTime){
        this.id = id;
        this.intervalTime = getExponentialVar(averageIntervalTime);
    }

    private int getExponentialVar(double averageIntervalTime){
        double lambda = 1 / averageIntervalTime;
        double u = Math.random();
        int x = 0;
        double cdf = 0;

        while(u >= cdf){
            x += 1;
            cdf = 1 - Math.exp(-1.0 * lambda * x);
        }
        return x;
    }

    double getArriveTime(){ return arriveTime; }

    double getIntervalTime(){ return intervalTime; }

    public String toString(){
        return "custom " + id + " arrived at " + arriveTime;
    }

    void setArriveTime(double times){ arriveTime = times; }

    int getId(){ return id; }
}

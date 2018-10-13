package MM1;
import java.lang.Math;

class Customer {
    private double arriveTime;
    private double intervalTime;
    private int id;
    // private double serviceTime;
    // private double beginServiceTime;

    Customer(int id, double averageIntervalTime){
        this.id = id;
        this.intervalTime = getExponentialVar(averageIntervalTime);
        //this.arriveTime = getPoissonVar(averageTime);
    }

//    private double getPoissonVar(double lambda){
//        double L = Math.exp(-1.0 * lambda);
//        double k = 0;
//        double p = 1;
//        do{
//            k += 1;
//            double u = Math.random();
//            p *= u;
//        }while(p > L);
//        return k;
//    }

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
//        return String.format("customer %d arrived at %4f", id, arriveTime);
        return "custom " + id + " arrived at " + arriveTime;
    }

    // double getServiceTime(){ return serviceTime; }

    //double getBeginServiceTime(){ return beginServiceTime; }

    //void  setServiceTime(double time){ serviceTime = time; }

    void setArriveTime(double times){ arriveTime = times; }

    //void setBeginServiceTime(double beginServiceTime){this.beginServiceTime = beginServiceTime;}
}

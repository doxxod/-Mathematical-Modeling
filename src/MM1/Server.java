package MM1;

class Server {
    private double averageServiceTime;

    Server(double averageServiceTime){
        this.averageServiceTime = averageServiceTime;
    }

    int getExponentialVar(){
        double lambda = 1 / averageServiceTime;
        double u = Math.random();
        int x = 0;
        double cdf = 0;

        while(u >= cdf){
            x += 1;
            cdf = 1 - Math.exp(-1.0 * lambda * x);
        }
        return x;
    }
}

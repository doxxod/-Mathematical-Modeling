package MM1;

import java.util.LinkedList;

class Scheduler {
    private double clock;
    private double totalDelayTime;
    private int delayNumber;
    private double areaNumberInQueue;
    private double areaStatus;
    private LinkedList<Customer> FEL;
    private LinkedList<Customer> CEL;
    private Server server;
    private int customerNumber;

    Scheduler(LinkedList<Customer> FEL, double averageServiceTime, int customerNumber){
        this.clock = 0;
        this.FEL = FEL;
        this.CEL = new LinkedList<>();
        this.server = new Server(averageServiceTime);
        this.totalDelayTime = 0;
        this.delayNumber = 0;
        this.areaNumberInQueue = 0;
        this.areaStatus = 0;
        this.customerNumber = customerNumber;
    }

    void start(int queueMaxLength){
        int currentQueueNum = 0;
        while (customerNumber > 0){
            if (!CEL.isEmpty()){
                int serviceTime = server.getExponentialVar();
                Customer currentCustomer = CEL.pop();
                if (clock < currentCustomer.getArriveTime()) clock = currentCustomer.getArriveTime();
                currentQueueNum --; //the customer who received the service are depart from the queue
                double delayTime = clock - currentCustomer.getArriveTime();
                while(currentQueueNum < queueMaxLength){
                    if (!FEL.isEmpty() && FEL.peek().getArriveTime() <= clock) {
                        CEL.addLast(FEL.poll());
                        currentQueueNum++;
                    }
                    else if (currentQueueNum == 0 && !FEL.isEmpty()) {
                        Customer c = FEL.poll();
                        CEL.addLast(c);
//                        if (clock + serviceTime < c.getArriveTime())
//                            clock = c.getArriveTime();
                        currentQueueNum ++;
                    }
                    else break;
                }
                if (delayTime > 0){
                    areaNumberInQueue += delayTime * currentQueueNum;
                    delayNumber ++;
                }
                totalDelayTime += delayTime;
                clock += serviceTime;
                System.out.println(currentCustomer + "\nService Time: " + serviceTime +
                        "\nDelay Time: " + delayTime + "\nLeft at: " + clock + "\n");
                areaNumberInQueue += serviceTime * currentQueueNum;
                areaStatus += serviceTime;
                customerNumber --;
            }
            while(currentQueueNum < queueMaxLength){
                if (!FEL.isEmpty() && FEL.peek().getArriveTime() <= clock && currentQueueNum != 0){
                    CEL.addLast(FEL.poll());
                    currentQueueNum ++;
                }
                else if (currentQueueNum == 0 && !FEL.isEmpty()) {
                    Customer c = FEL.poll();
                    CEL.addLast(c);
                    if (clock < c.getArriveTime())
                        clock = c.getArriveTime();
                    currentQueueNum ++;
                }
                else break;
            }
        }
    }

    double getAverageDelayTime(){
        if (delayNumber == 0){ return 0; }
        return totalDelayTime / delayNumber;
    }

    double getAverageAreaNumberInQueue(){ return areaNumberInQueue / clock; }

    double getUtilisation(){ return areaStatus / clock; }
}

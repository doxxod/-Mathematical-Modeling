package MM1;

import java.util.LinkedList;
import java.util.PriorityQueue;

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
                        if (clock + serviceTime < c.getArriveTime())
                            clock = c.getArriveTime();
                        currentQueueNum ++;
                    }
                    else break;
                }
                if (delayTime > 0){
                    areaNumberInQueue += delayTime * currentQueueNum;
                    delayNumber ++;
                }
                totalDelayTime += delayTime;
                // System.out.println("arrival time: " + currentCustomer.getArriveTime() + " clock: " + clock);
                // System.out.println("delay time: " + delayTime + " delay number: " + delayNumber + " service time: " + serviceTime);
                clock += serviceTime;
                areaNumberInQueue += serviceTime * currentQueueNum;
                areaStatus += serviceTime;
                customerNumber --;
                // System.out.println(currentQueueNum + " people in the queue");
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

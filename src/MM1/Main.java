package MM1;

import java.util.LinkedList;
import java.util.Scanner;

class Main {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        System.out.println("Please input average interval time: ");
        double averageIntervalTime = Double.parseDouble(s.nextLine());

        System.out.println("Please input average service time: ");
        double averageServiceTIme = Double.parseDouble(s.nextLine());

        System.out.println("Please input queue's max length: ");
        int queueMaxLength = Integer.parseInt(s.nextLine());

        System.out.println(("Please input customer's number: "));
        int customerNumber = Integer.parseInt(s.nextLine());

        LinkedList<Customer> FEL = new LinkedList<>();
        double arrivalTime = 0;
        for(int i = 0; i < customerNumber; i ++){
            Customer c = new Customer(i, averageIntervalTime);
            arrivalTime += c.getIntervalTime();
            c.setArriveTime(arrivalTime);
            // System.out.println(c.toString());
            FEL.addLast(c);
        }

        System.out.println("Program start...");
        Scheduler scheduler = new Scheduler(FEL, averageServiceTIme, customerNumber);
        scheduler.start(queueMaxLength);

        System.out.printf("The average delay time: %f.", scheduler.getAverageDelayTime());
        System.out.printf("The average number of customers in queue:  %f.", scheduler.getAverageAreaNumberInQueue());
        System.out.printf("The utilisation of server: %f.", scheduler.getUtilisation());
    }
}
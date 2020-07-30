/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motorwaysimulation;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MotorWaySimulation {

   
    private static int noOfvehicles=5;
    private static Vehicle[] vehicles;
    public static void main(String[] args) {
      
        MotorWaySimulation sm=new MotorWaySimulation();
        vehicles=new Vehicle[5];
        for(int i=0;i<noOfvehicles;i++)
        {
          vehicles[i]=new Vehicle(i);
          Thread t=new Thread(vehicles[i]);
 
          t.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MotorWaySimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
          
          
        
        }
    }
    
}
class Vehicle implements Runnable{
    private int id;
    private static TollBooth toll=new TollBooth();
      Vehicle(int id)
      {
        this.id=id;
      }
    @Override
    public void run() {
        System.out.println("Vehicle"+(id+1)+"starts journey");
        Random ran=new Random();
        int rnd=ran.nextInt(100);
        travel(rnd);
        System.out.println("vehicle"+(id+1)+"arrives at the toll");
        toll.useToll(this);
        travel(rnd);
        System.out.println("vehicle"+(id+1)+"has crossed the bridge");
        
    }
    public int getVehicleId()
    {
      return this.id;
    }
    public void travel(int time)
    {
      int limit=500000;
      for(int j=0;j<time;j++)
      {
        for(int k=0;k<limit;k++)
        {//do nuffn here
        };
      }
    
    }
}
class TollBooth{
boolean inUse;
  TollBooth()
  {
    inUse=false;
  }
  public void useToll(Vehicle vehicle)
  {
    while(true)
    {
      if(inUse==false)
         {
             synchronized(this)
             {inUse=true;
                 System.out.println("vehicle"+(vehicle.getVehicleId()+1)+"enters tollbooth");
                 vehicle.travel(50);
                 System.out.println("vehicle"+(vehicle.getVehicleId()+1)+"exits booth");
                    inUse=false;
                    break;
             }
         }
     
    }
  }

}


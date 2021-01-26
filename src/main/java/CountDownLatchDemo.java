import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

  public static void main(String[] args) {

    CountDownLatch latch = new CountDownLatch(4);
    Passenger driver = new Passenger("Driver", 2000,latch);
    Passenger passenger1 = new Passenger("Passenger1", 5000,latch);
    Passenger passenger2 = new Passenger("Passenger2", 3000,latch);
    Passenger passenger3 = new Passenger("Passenger3", 1000,latch);

    Thread task = new Thread(driver);
    Thread task1 = new Thread(passenger1);
    Thread task2 = new Thread(passenger2);
    Thread task3 = new Thread(passenger3);
    task.start();
    task1.start();
    task2.start();
    task3.start();

    try {
      latch.await();
    } catch (InterruptedException e) {
      System.out.println("Exception in Latch Await hence throwing Exception " +e.getMessage());
    }

    System.out.println("EveryOne boarded let go brum brum........");
  }
}

class Passenger implements Runnable{

  private String passengerName;
  private int waitingTime;
  private CountDownLatch latch;


  Passenger(String name,int waitingTime, CountDownLatch latch)
  {
    this.passengerName = name;
    this.waitingTime = waitingTime;
    this.latch = latch;
  }

  @Override public void run() {

    try{
      Thread.sleep(waitingTime);
    } catch (InterruptedException ex){
      System.out.println("Awaited from sleep hence throwing Exception " +ex.getMessage());
    }
    System.out.println(passengerName +" Boarded to Car");
    latch.countDown();
  }
}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreExample {

  public static void main(String[] args) {
    ExecutorService service = Executors.newFixedThreadPool(2);
    AtomicInteger atomicInteger = new AtomicInteger(1);
    Semaphore even = new Semaphore(0);
    Semaphore odd = new Semaphore(1);

    service.execute(new EvenThreadSemaphore(atomicInteger,even,odd));
    service.execute(new OddThreadSemaphore(atomicInteger,even,odd));
    service.shutdown();
  }
}


class EvenThreadSemaphore implements Runnable{

  AtomicInteger atomicInteger;
  private Semaphore evenSemaphore;
  private Semaphore oddSemaphore;

  EvenThreadSemaphore(AtomicInteger atomicInteger,Semaphore evenSemaphore, Semaphore oddSemaphore){
    this.atomicInteger = atomicInteger;
    this.evenSemaphore = evenSemaphore;
    this.oddSemaphore = oddSemaphore;
  }

  @Override public void run() {

    while(atomicInteger.get() <= 20){
      try{
          evenSemaphore.acquire();
             System.out.println("Even" +" Thread : "+ atomicInteger.getAndIncrement());
          oddSemaphore.release();
    } catch (InterruptedException ex){
      ex.printStackTrace();
      }
    }

  }
}

class OddThreadSemaphore implements Runnable{

  AtomicInteger atomicInteger;
  private Semaphore evenSemaphore;
  private Semaphore oddSemaphore;

  OddThreadSemaphore(AtomicInteger atomicInteger,Semaphore evenSemaphore, Semaphore oddSemaphore){
    this.atomicInteger = atomicInteger;
    this.evenSemaphore = evenSemaphore;
    this.oddSemaphore = oddSemaphore;
  }
  @Override public void run() {

    while(atomicInteger.get() < 20){

      try {
        oddSemaphore.acquire();
          System.out.println("ODD" + " Thread : " + atomicInteger.getAndIncrement());
        evenSemaphore.release();
      } catch (InterruptedException ex){
        ex.printStackTrace();
      }
    }
  }
}
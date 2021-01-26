//ExecutorFramwork
//Thread odd print
//Thread Even Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class EvenThread implements Runnable{

  String threadName;
  int maxNumber;
  AtomicInteger atomicInteger;
  Object obj;

  EvenThread(String threadName,int maxNumber,AtomicInteger atomicInteger,Object obj){
    this.atomicInteger = atomicInteger;
    this.threadName = threadName;
    this.maxNumber = maxNumber;
    this.obj= obj;
  }

  @Override public void run() {

    while(atomicInteger.get() <= maxNumber){

      if(atomicInteger.get() %2 ==0){
        System.out.println(threadName +" Thread : "+ atomicInteger.getAndIncrement());
        synchronized (obj){
          obj.notify();
        }
      } else{
        synchronized (obj){
          try{
            obj.wait();
          } catch(InterruptedException ex){
            ex.printStackTrace();
          }
        }
      }
    }

  }
}

class OddThread implements Runnable{

  String threadName;
  int maxNumber;
  AtomicInteger atomicInteger;
  Object obj;

  OddThread(String threadName,int maxNumber,AtomicInteger atomicInteger,Object obj){
    this.atomicInteger = atomicInteger;
    this.threadName = threadName;
    this.maxNumber = maxNumber;
    this.obj = obj;
  }

  @Override public void run() {

    while(atomicInteger.get() <= maxNumber){

      if(atomicInteger.get() %2 !=0){
        System.out.println(threadName + " Thread : "+atomicInteger.getAndIncrement());
        synchronized (obj){
          obj.notify();
        }
      } else{
        synchronized (obj){
          try{
            obj.wait();
          } catch(InterruptedException ex){
            ex.printStackTrace();
          }
        }
      }
    }
  }
}

public class ExecutorFrameWorkTest{

  public static void main(String[] args) {

    AtomicInteger atomicInteger = new AtomicInteger(1);
    Object obj = new Object();
    EvenThread even = new EvenThread("EVEN",20,atomicInteger,obj);
    OddThread odd = new OddThread("Odd",20,atomicInteger,obj);
    Thread evenThread = new Thread(even);
    Thread oddThread = new Thread(odd);
    ExecutorService exec = Executors.newFixedThreadPool(2);
    exec.execute(evenThread);
    exec.execute(oddThread);
    exec.shutdown();
  }
}
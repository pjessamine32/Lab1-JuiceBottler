//implements runnable to use as thread.  worker threads.  needs run method.
//use pieces of plant
// run - getting orange, doing whatever, handing off.  while loop
//(run is method.  class that implements runnable, need run method)


	public class Worker implements Runnable {
		
		 	private final Thread thread;
		    private int orangesProvided;
		    private int orangesProcessed;
		    private volatile boolean timeToWork;
		    private volatile AssemblyLine pile;  //ME
		    private volatile AssemblyLine nextPile;  //ME
		    
		    
		    /**ME, CHANGED THREAD LINE TO POSITION**/
		    Worker(String position, AssemblyLine pile, AssemblyLine nextPile){     
		    	thread = new Thread(this, position);
		    	this.pile = pile;
		    	this.nextPile = nextPile;
		    	
		    }
	
	 public void startWorker() {
	        timeToWork = true;
	        thread.start();
	    }

	    public void stopWorker() {
	        timeToWork = false;
	    }

	    public void waitToStop() {
	        try {
	            thread.join();
	        } catch (InterruptedException e) {
	            System.err.println(thread.getName() + " stop malfunction");
	        }
	    }
	
    public void run() {
        System.out.print(Thread.currentThread().getName() + " Processing oranges. ");
        while (timeToWork) {
            orangesProvided++;   //not every worker is incrementing oranges.  move this
            //processEntireOrange(new Orange());
        	Orange orange = pile.getOrange();  //ME.  CHANGED DOWN TO LINE 47
        	orange.doWork();
        	orangesProvided++;
        	nextPile.addOrange(orange);

            System.out.print(".");
        }
        System.out.println("");
    }
	}



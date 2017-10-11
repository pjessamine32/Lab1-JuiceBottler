//'Factory' not a lot of changes need to be made to code
//provides the oranges


	public class Factory implements Runnable {
	    // How long do we want to run the juice processing
	    public static final long PROCESSING_TIME = 5 * 1000;

	    private static final int NUM_Factories = 3;
	    
	    private static final int NUM_AssemblyLines = 2;  //ME BUT CAN'T FIGURE OUT HOW TO USE IT
	    
	    private static final int NUM_Workers = 10;   //ME

	    public static void main(String[] args) {
	        // Startup the Factories
	        Factory[] factories = new Factory [NUM_Factories];
	        for (int i = 0; i < NUM_Factories; i++) {
	           factories[i] = new Factory();
	           factories[i].startFactory();
	        }

	        // Give the Factories time to do work
	        delay(PROCESSING_TIME, "Factory malfunction");

	        // Stop the Factory, and wait for it to shutdown
	        for (Factory p : factories) {
	           p.stopFactory();
	        }
	        for (Factory p : factories) {
	           p.waitToStop();
	        }

	        // Summarize the results
	        int totalFetched = 0;   //ME.  CHANGED FOR THE RESULTS OF EACH PROCESS
	        int totalPeeled = 0;
	        int totalSqueezed = 0;
	        int totalBottled = 0;
	        int totalProcessed = 0;
	        int totalWasted = 0;
	        for (Factory p : factories) {
	        	totalFetched += p.getFetchedOranges();
	        	totalPeeled += p.getPeeledOranges();
	            totalSqueezed += p.getSqueezedOranages();
	            totalBottled += p.getBottled();
	            totalWasted += p.getWaste();
	            totalProcessed += p.getProcessed();
	        }
	       //System.out.println("Total provided/processed = " + totalProvided + "/" + totalProcessed);
	       System.out.println("fetched " + totalFetched + " peeled " + totalPeeled + " squeezed " + totalSqueezed + 
	    		   " bottled " + totalBottled + " processed " + totalProcessed + " wasted " + totalWasted);  //ME
	        //System.out.println("Created " + totalBottles +
	                          // ", wasted " + totalWasted + " oranges");
	    }

	    

		



		private static void delay(long time, String errMsg) {
	        long sleepTime = Math.max(1, time);
	        try {
	            Thread.sleep(sleepTime);
	        } catch (InterruptedException e) {
	            System.err.println(errMsg);
	        }
	    }

	   public final int ORANGES_PER_BOTTLE = 3;

	    private final Thread thread;
	    private int orangesProvided;
	    private int orangesProcessed;
	    private volatile boolean timeToWork;

	    Factory() {   
	        orangesProvided = 0;
	        orangesProcessed = 0;
	        thread = new Thread(this, "Factory");
	        AssemblyLine assemblyline = new AssemblyLine();  //ME DOWN TO LINE 107
	        AssemblyLine fetched = new AssemblyLine();       //CREATES EACH ASSEMBLY LINE FOR PROCESSING
	        AssemblyLine peeled = new AssemblyLine();
	        AssemblyLine squeezed = new AssemblyLine();
	        AssemblyLine bottled = new AssemblyLine();
	        AssemblyLine processed = new AssemblyLine();
	        AssemblyLine finished = new AssemblyLine();
	        Worker[] workers = new Worker [NUM_Workers];
	        for (int i = 0; i < NUM_Workers; i++) {   //CREATES EACH WORKER & TELLS EACH IT'S ASSEMBLY LINE
	        	if (i == 0 || i == 1){
	        		workers[i] = new Worker("Fetcher", fetched, peeled);
	        		}
	        		else if (i == 2 || i == 3){
	        			workers[i] = new Worker("Peeler", peeled, squeezed);
	        		}
	        		else if (i == 4 || i == 5){
	        			workers[i] = new Worker("Squeezer", squeezed, bottled);
	        		}
	        		else if (i == 6 || i == 7){
	        			workers[i] = new Worker("Bottler", bottled, processed);
	        		}
	        		else if (i >= 8 && i <= 10 ){
	        			workers[i] = new Worker("Processor", processed, finished);
	        		}
	        		else {
	        			System.out.println("uh oh");
	        	}
	        	workers[i].startWorker(); 
	        }
	         
	        }
	        
//	        switch(Thread.currentThread().getName()){
//	        case "fetcher":
//	        }
	        
	    

	    public void startFactory() {
	        timeToWork = true;
	        thread.start();
	    }

	    public void stopFactory() {
	        timeToWork = false;
	    }

	    public void waitToStop() {
	        try {
	            thread.join();
	        } catch (InterruptedException e) {
	            System.err.println(thread.getName() + " stop malfunction");
	        }
	    }

//	    public void run() {
//	        System.out.print(Thread.currentThread().getName() + " Processing oranges");
//	        while (timeToWork) {
//	            processEntireOrange(new Orange());
//	            orangesProvided++;
//	            System.out.print(".");
//	        }
//	        System.out.println("");
//	    }
	    /**SUPPOSED TO CHANGE THIS TO TELL THE FACTORY WHAT TO DO BUT IDK...**/
	    public void run() {
	        System.out.print(Thread.currentThread().getName() + " Processing oranges ");
	        while (timeToWork) {
	            fetchOrange(new Orange());
	            orangesProvided++;
	            System.out.print(".");
	        }
	        System.out.println("");
	    }

	    public void fetchOrange(Orange o) {
	        while (o.getState() != Orange.State.Bottled) {
	            o.runProcess();
	        }
	        orangesProcessed++;
	    }

	    //public int getProvidedOranges() {
	    //    return orangesProvided;
	    //}
	    
	    public int getFetchedOranges() {  //I CHANGED DOWN TO LINE 189 TO RETURN ORANGES PROCESSED
	    	return orangesProcessed;
	    }

	    public int getPeeledOranges() {
	        return orangesProcessed;
	    }
	    
	    private int getSqueezedOranages() {
			return orangesProcessed;
		}

	    public int getBottled() {
	        return orangesProcessed / ORANGES_PER_BOTTLE;
	    }

	    public int getWaste() {
	        return orangesProcessed % ORANGES_PER_BOTTLE;
	    }
	    
	    private int getProcessed() {
			return orangesProcessed;
		}
	}




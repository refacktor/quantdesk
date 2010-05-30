package jmirror;

import java.net.UnknownHostException;
import java.util.Date;

public class JMirror {

    public static void main(String[] args) {
        System.out.println("JMirror started at " +
                           new Date(System.currentTimeMillis()));
        int rc = 0;
        
        try {
            run();        	
        }
        catch(Exception e) {
        	e.printStackTrace(System.err);
        	rc = -1;
        }

        System.out.println("JMirror finished at " +
                           new Date(System.currentTimeMillis()));
        
        System.exit(rc);
    }

	private static void run() throws UnknownHostException {
		boolean unfinished = true;
        MirrorThread mt = null;

        do {
            // 
            mt = new MirrorThread();
            mt.start();
            // 
            while (!mt.threadFinished()) {
                if (System.currentTimeMillis() - mt.getLastOperationTime() >
                    mt.getTimeout()) {
                    System.out.println("Timed out");
                    mt.interrupt();
                    mt.exit();
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {}
            }
            if (mt.finishedSuccessfully()) {
                unfinished = false;
            }
            else {
                System.out.println("JMirror restarted at " +
                                   new Date(System.currentTimeMillis()));
                mt = null;
                try {
                    Thread.sleep(60 * 1000); // wait for 60 seconds before restart
                } catch (InterruptedException ex) {}
            }
        } while (unfinished);
	}
}

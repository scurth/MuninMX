/*
 * MuninMX
 * Copyright (c) 2014
 * www.clavain.com
 * 
 */
package com.clavain.executors;

import static com.clavain.muninmxcd.logger;
import static com.clavain.muninmxcd.p;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.clavain.workers.MongoWorker;

/**
 *
 * @author enricokern
 */
public class MongoExecutor implements Runnable {

    @Override
    public void run() {
        try
        {
            while(true)
            {
                Thread.sleep(50);
                ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(p.getProperty("mongo.threads")));
                
                executor.execute(new MongoWorker());

                executor.shutdown();
                while (!executor.isTerminated()) {
                    Thread.sleep(50);
                }
                logger.info("MongoWorker Set finished. Restarting MongoWorkers.");
                System.gc();
            }
        } catch (Exception ex)
        {
            logger.fatal("Error in MongoExecutor: " + ex.getLocalizedMessage());
        }
    }
    
}
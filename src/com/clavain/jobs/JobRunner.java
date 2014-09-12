/*
 * MuninMX
 * Copyright (c) 2014
 * www.clavain.com
 * 
 */
package com.clavain.jobs;

import com.clavain.munin.MuninNode;
import static com.clavain.muninmxcd.logger;
import static com.clavain.utils.Generic.getMuninNode;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author enricokern
 */
public class JobRunner extends Thread implements Runnable {

    private int nodeId;
    private MuninNode mn;
    
    public JobRunner(int p_nid)
    {
        nodeId = p_nid;
    }
    
    @Override
    public void run() {
        mn = getMuninNode(nodeId);
        try{
            
            if(mn != null)
            {
              mn.run();
            }
            else
            {
              logger.error("Tried to Run job for NodeID: " + nodeId + " but this node is not in nodelist :("); 
            }  
        } 
        catch (ThreadDeath td)
        {
            mn = null;
            logger.info(mn.getHostname() + "Monitoring job stopped - Terminated - " + td.getMessage()); 
            return;
        }
        catch (Exception ex)
        {
            logger.info(mn.getHostname() + "Monitoring job stopped - Terminated");
            logger.error("JobRunner exception: " + ex.getLocalizedMessage());
        }

    }
    
    @Override  
    public void interrupt(){  
       try
       {  
           mn.getLastSocket().close();
           logger.info(mn.getHostname() + "Monitoring job called interrupt - closing socket");
       }  
        catch (IOException ex) {  
            Logger.getLogger(JobRunner.class.getName()).log(Level.SEVERE, null, ex);
        }       finally{  
         super.interrupt();  
       }  
    }      

}

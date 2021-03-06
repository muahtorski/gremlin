/***************************************************************
 * Program:      Gremlin
 * Description:  Basic scanning tool
 * Written by:   Don Franke
 * Comments:     IS6953 Summer 2006, UTSA
 * 
 * Class:        GoodWindowsExec
 * Comments:     Written by Michael C. Daconta for JavaWorld article
 ***************************************************************/

package gremlin;

import java.io.*;

class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    
    StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }
    
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
                System.out.println(type + ">" + line);    
            } catch (IOException ioe)
              {
                ioe.printStackTrace();  
              }
    }
}

public class GoodWindowsExec
{
    public void Execute(String inCommand)
    {
       
        try
        {            
            //String osName = System.getProperty("os.name" );
            String[] cmd = new String[3];

            //if( osName.equals( "Windows NT" ) )
            //{
                cmd[0] = "cmd.exe" ;
                cmd[1] = "/C" ;
                cmd[2] = inCommand;
            //}
            
            
            Runtime rt = Runtime.getRuntime();
            //System.out.println("Executing " + inCommand);
            Process proc = rt.exec(cmd);
            // any error message?
            StreamGobbler errorGobbler = new
                StreamGobbler(proc.getErrorStream(), "ERROR");            
            
            // any output?
            StreamGobbler outputGobbler = new
                StreamGobbler(proc.getInputStream(), "OUTPUT");
                
            // kick them off
            errorGobbler.start();
            outputGobbler.start();
                                    
            // any error???
            int exitVal = proc.waitFor();
            //System.out.println("ExitValue: " + exitVal);        
        } catch (Throwable t)
          {
            t.printStackTrace();
          }
    }
}
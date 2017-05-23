package com.rd.monitoring.proccesscounter;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProccessCounter {
	
	public static int getNumber(){
		
		int nOfProc = 0;
		
		try {
		    String line = "";
		    
		    // For Linux
		    //Process p = Runtime.getRuntime().exec("ps -e");
		    
		    Process p = Runtime.getRuntime().exec
		    	    (System.getenv("windir") +"\\system32\\"+"tasklist.exe");
		    
		    BufferedReader input =
		            new BufferedReader(new InputStreamReader(p.getInputStream()));
		    while ((line = input.readLine()) != null) {
		        nOfProc++;
		    }
		    input.close();
		} catch (Exception err) {
		    err.printStackTrace();
		}
		
		return nOfProc;
	}
}
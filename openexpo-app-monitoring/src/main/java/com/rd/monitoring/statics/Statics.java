package com.rd.monitoring.statics;

import java.util.ArrayList;
import java.util.List;

import com.rd.monitoring.beans.Parameter;

public class Statics {

	private static List<Parameter> list = new ArrayList<Parameter>();
	public static List<Integer> historicCPU = new ArrayList<Integer>();
	public static List<Long> historicPMSize = new ArrayList<Long>();
	public static List<Long> historicFreePM = new ArrayList<Long>();
	public static List<Long> historicUsablePartitionSize = new ArrayList<Long>();
	public static List<Integer> historicConnectedUsers = new ArrayList<Integer>();
	
	
	public static void insertOnList(String key, String value){
		
		// Delete FIXME
		if(list.size() > 0){
			for(int i=0;i<list.size();i++){
				Parameter p = list.get(i);
				if(p.getKey().equals(key)){
					list.remove(i);
				}
			}
		}
		
		Parameter pNew = new Parameter(key,value);
		if(key.equals("CPU Percentage")){
			if(historicCPU.size() > 50){
				historicCPU.remove(0);
			}
			historicCPU.add(Integer.valueOf(value));
			
		}
		if(key.equals("Active procceses")){
			if(historicConnectedUsers.size() > 50){
				historicConnectedUsers.remove(0);
			}
			historicConnectedUsers.add(Integer.valueOf(value));
		}
		if(key.equals("Free Partition Space")){
			if(historicPMSize.size() > 50){
				historicPMSize.remove(0);
			}
			historicPMSize.add(Long.valueOf(value));
		}
		if(key.equals("Free Physical Memory Size")){
			if(historicFreePM.size() > 50){
				historicFreePM.remove(0);
			}
			historicFreePM.add(Long.valueOf(value));
		}
		if(key.equals("Usable partition space")){
			if(historicUsablePartitionSize.size() > 50){
				historicUsablePartitionSize.remove(0);
			}
			historicUsablePartitionSize.add(Long.valueOf(value));
		}
		
		list.add(pNew);
		
	}
	
	public static List<Parameter> getList(){
		return list;
	}
}

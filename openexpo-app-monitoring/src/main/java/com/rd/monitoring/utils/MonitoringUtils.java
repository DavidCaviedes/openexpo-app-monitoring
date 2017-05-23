/* YOU CAN TRY THIS TOO */

package com.rd.monitoring.utils;

import java.io.File;
import java.lang.management.ManagementFactory;
// import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.management.RuntimeMXBean;
import java.io.*;
import java.net.*;
import java.util.*;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;

import com.rd.monitoring.proccesscounter.ProccessCounter;
import com.rd.monitoring.statics.Statics;
import com.sun.management.OperatingSystemMXBean;

import freemarker.log.Logger;

import java.lang.management.ManagementFactory;
import java.util.Random;

/**
 * Utils class for monitoring the server usage data.
 *
 */

public class MonitoringUtils {

	/**
	 * Method used for printing the memory used percentage
	 * 
	 * @param runtime
	 */
	public static void printMemoryUsage(Runtime runtime) {
		long total, free, used;
		int mb = 1024 * 1024;

		total = runtime.totalMemory();
		free = runtime.freeMemory();
		used = total - free;
	}

	
	/**
	 * Method used for logging
	 * @param message
	 */
	public static void log(Object message) {
	}

	
	/**
	 * Method used for calculate the percentage of CPU used
	 * @param cpuStartTime
	 * @param elapsedStartTime
	 * @param cpuCount
	 * @return
	 */
	public static int calculatePercentageCPU(long cpuStartTime, long elapsedStartTime, int cpuCount) {
		long end = System.nanoTime();
		long totalAvailCPUTime = cpuCount * (end - elapsedStartTime);
		long totalUsedCPUTime = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime() - cpuStartTime;

		float per = ((float) totalUsedCPUTime * 100) / (float) totalAvailCPUTime;
		log(per);
		return (int) per;
	}
	
	/**
	 * Aux method used for checking if the number passed as argument is a prime number
	 * @param n
	 * @return
	 */

	static boolean isPrime(int n) {
		// 2 is the smallest prime
		if (n <= 2) {
			return n == 2;
		}
		// even numbers other than 2 are not prime
		if (n % 2 == 0) {
			return false;
		}
		// check odd divisors from 3
		// to the square root of n
		for (int i = 3, end = (int) Math.sqrt(n); i <= end; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	
	/**
	 * Method used for print server usage data
	 * 
	 */
	public void printServerData() {
		int mb = 1024 * 1024;
		int gb = 1024 * 1024 * 1024;
		
		// Uso de memoria fisica
		com.sun.management.OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();

		com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory
				.getOperatingSystemMXBean();
		long physicalMemorySize = os.getTotalPhysicalMemorySize();
		long physicalfreeMemorySize = os.getFreePhysicalMemorySize();
		
		// Uso de disco
		File diskPartition = new File("C:");
		File diskPartition1 = new File("D:");
		File diskPartition2 = new File("E:");
		long totalCapacity = diskPartition.getTotalSpace() / gb;
		long totalCapacity1 = diskPartition1.getTotalSpace() / gb;
		double freePartitionSpace = diskPartition.getFreeSpace() / gb;
		double freePartitionSpace1 = diskPartition1.getFreeSpace() / gb;
		double freePartitionSpace2 = diskPartition2.getFreeSpace() / gb;
		double usablePatitionSpace = diskPartition.getUsableSpace() / gb;
		
		Statics.insertOnList("(P0) Free space", String.valueOf(freePartitionSpace));
		Statics.insertOnList("(P1) Free space", String.valueOf(freePartitionSpace1));
		Statics.insertOnList("(P2) Free space", String.valueOf(freePartitionSpace2));
		
		Long longPMF = (long) Math.round(physicalfreeMemorySize/mb);
		Statics.insertOnList("Free Physical Memory Size", String.valueOf(longPMF));
		Long longUsable = (long) Math.round(usablePatitionSpace);
		Statics.insertOnList("Usable partition space", String.valueOf(longUsable));
		Integer nOfProc = ProccessCounter.getNumber();
		Statics.insertOnList("Active procceses", String.valueOf(nOfProc));
		
		
		
		
		if (freePartitionSpace <= totalCapacity % 10 || freePartitionSpace1 <= totalCapacity1 % 10) {
		}

		Runtime runtime;
		byte[] bytes;
		
		runtime = Runtime.getRuntime();

		bytes = new byte[1024 * 1024];

		bytes = null;
		runtime.gc();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}

		for (int i = 0; i < 30; i++) {
			long start = System.nanoTime();
			int cpuCount = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
			Random random = new Random(start);
			int seed = Math.abs(random.nextInt());
			int primes = 10000;
			//
			long startCPUTime = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
			start = System.nanoTime();
			while (primes != 0) {
				if (isPrime(seed)) {
					primes--;
				}
				seed++;

			}
			float cpuPercent = calculatePercentageCPU(startCPUTime, start, cpuCount);
			int integerPercent = (int) Math.round(cpuPercent);
			Statics.insertOnList("CPU Percentage", String.valueOf(integerPercent));
			Statics.insertOnList("(P0) Free space", String.valueOf(freePartitionSpace));
			Statics.insertOnList("(P1) Free space", String.valueOf(freePartitionSpace1));
			Statics.insertOnList("(P2) Free space", String.valueOf(freePartitionSpace2));
			nOfProc = ProccessCounter.getNumber();
			Statics.insertOnList("Active procceses", String.valueOf(nOfProc));
			longPMF = (long) Math.round(physicalfreeMemorySize/mb);
			Statics.insertOnList("Free Physical Memory Size", String.valueOf(longPMF));
			longUsable = (long) Math.round(usablePatitionSpace);
			Statics.insertOnList("Usable partition space", String.valueOf(longUsable));
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}

		try {
			Thread.sleep(500);
		} catch (Exception ignored) {
		}
	}
}
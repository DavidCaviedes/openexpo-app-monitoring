/**
 * 
 */
package com.rd.monitoring.utils;

import com.rd.monitoring.constants.IConstantsOpenExpoMonitoring;


/**
 * @author David Caviedes
 *
 */
public class MessagesMonitoringUtils {

	/**
	 * @param key
	 * @return cadena correspondiente a la key pasada segun el bundle de este modulo
	 */
	public static String getString(String key) {
		
		return MessagesUtils.getString(IConstantsOpenExpoMonitoring.BUNDLE_NAME, key);
	}
	
}

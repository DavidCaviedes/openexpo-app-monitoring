/**
 * 
 */
package com.rd.monitoring.subapp;

import com.rd.monitoring.ui.IFactoriaElementosUI;

import info.magnolia.ui.api.message.MessageType;
import info.magnolia.ui.api.view.View;

/**
 * @author David Caviedes
 *
 */
public interface MonitoringMainSubappView extends View {

	void setListener(Listener listener);
	
	Listener getListener();
	
	IFactoriaElementosUI getFactoriaElementosUI();
	
	public interface Listener {

    	/**
    	 * Muestra un mensaje en Pulse Magnolia
    	 * 
    	 * @param messageType
    	 * @param subject
    	 * @param msg
    	 */
    	void sendAppMessage(MessageType messageType, String subject, String msg);
	}
	
}

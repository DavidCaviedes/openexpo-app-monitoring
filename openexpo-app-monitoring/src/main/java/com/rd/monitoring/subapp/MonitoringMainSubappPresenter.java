/**
 * 
 */
package com.rd.monitoring.subapp;

import javax.inject.Inject;

import info.magnolia.context.MgnlContext;
import info.magnolia.ui.api.app.SubAppContext;
import info.magnolia.ui.api.message.Message;
import info.magnolia.ui.api.message.MessageType;
import info.magnolia.ui.framework.app.BaseSubApp;

/**
 * @author David Caviedes
 *
 */
public class MonitoringMainSubappPresenter extends BaseSubApp<MonitoringMainSubappView> implements MonitoringMainSubappView.Listener {
	
	@Inject
	protected MonitoringMainSubappPresenter(SubAppContext subAppContext, MonitoringMainSubappView view) {
		super(subAppContext, view);
	}
	
	@Override
    protected void onSubAppStart() {
        getView().setListener(this);
        getView().getFactoriaElementosUI().loadParametersOnInit();
    }

	/* (non-Javadoc)
	 * @see com.rd.monitoring.subapp.MonitoringMainSubappView.Listener#sendAppMessage(info.magnolia.ui.api.message.MessageType, java.lang.String, java.lang.String)
	 */
	@Override
	public void sendAppMessage(MessageType messageType, String subject, String msg) {
		
		Message message = new Message();
		message.setType(messageType);
		message.setSubject(subject);
		message.setMessage(msg);
		
		getAppContext().sendUserMessage(MgnlContext.getUser().getName(), message);
	}


}

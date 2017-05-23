/**
 * 
 */
package com.rd.monitoring.subapp;

import javax.inject.Inject;

import com.rd.monitoring.ui.IFactoriaElementosUI;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * @author David Caviedes
 *
 */
public class MonitoringMainSubappViewImpl implements MonitoringMainSubappView {

	private static final long serialVersionUID = 1L;
	
	private VerticalLayout layout;
	private Listener listener;
	private IFactoriaElementosUI factoriaElementosUI;

	@Inject
	public MonitoringMainSubappViewImpl(IFactoriaElementosUI factoriaElementosUI) {
		
		this.factoriaElementosUI = factoriaElementosUI;
		
		this.layout = this.factoriaElementosUI.inicializarLayoutPrincipal(this);
	}

	/* (non-Javadoc)
	 * @see info.magnolia.ui.api.view.View#asVaadinComponent()
	 */
	@Override
	public Component asVaadinComponent() {
		return this.layout;
	}

	/* (non-Javadoc)
	 * @see com.rd.monitoring.subapp.MonitoringMainSubappView#setListener(com.rd.monitoring.subapp.MonitoringMainSubappView.Listener)
	 */
	@Override
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	/* (non-Javadoc)
	 * @see com.rd.monitoring.subapp.MonitoringMainSubappView#getListener()
	 */
	@Override
	public Listener getListener() {
		return this.listener;
	}

	@Override
	public IFactoriaElementosUI getFactoriaElementosUI() {
		return this.factoriaElementosUI;
	}

}

/**
 * 
 */
package com.rd.monitoring.ui;

import com.rd.monitoring.subapp.MonitoringMainSubappViewImpl;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * @author David Caviedes
 *
 */
public interface IFactoriaElementosUI {
	
	/**
	 * @param mainViewImpl
	 * @return layout con los componentes visuales que debe tener la vista principal de la app
	 */
	VerticalLayout inicializarLayoutPrincipal(MonitoringMainSubappViewImpl mainViewImpl);
	
	/**
	 * @return pestanya principal de la app
	 */
	TabSheet getTabSheet();
	
	/**
	 * Carga los datos y los agrega a la tabla de la vista principal
	 */
	void loadParametersOnInit();
	
	
}

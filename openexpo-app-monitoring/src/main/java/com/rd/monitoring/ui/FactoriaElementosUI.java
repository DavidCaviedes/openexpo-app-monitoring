package com.rd.monitoring.ui;

import com.jensjansson.pagedtable.PagedTable;
import com.rd.monitoring.beans.Parameter;
import com.rd.monitoring.charts.HighCharts;
import com.rd.monitoring.statics.Statics;
import com.rd.monitoring.subapp.MonitoringMainSubappViewImpl;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author David Caviedes
 *
 */
public class FactoriaElementosUI implements IFactoriaElementosUI {
	

	private MonitoringMainSubappViewImpl mainViewImpl;
	private TabSheet tabSheet;
	private PagedTable pt;
	
	/* (non-Javadoc)
	 * @see com.rd.monitoring.ui.IFactoriaElementosUI#inicializarLayoutPrincipal(com.rd.monitoring.subapp.MonitoringMainSubappViewImpl)
	 */
	@Override
	public VerticalLayout inicializarLayoutPrincipal(MonitoringMainSubappViewImpl mainViewImpl) {
		
		// This update UI each three second
		UI.getCurrent().setPollInterval( 3000 );
		
		VerticalLayout ret = new VerticalLayout();
		this.mainViewImpl = mainViewImpl;
		ret.setMargin(true);
		ret.setSpacing(true);
		ret.setWidth("100%");
		
		this.tabSheet = this.agregarTabsheet(ret);
        
		VerticalLayout tabDetalle = this.crearTabDetalle();
		VerticalLayout tabListado = this.crearTabListado(this.tabSheet, tabDetalle);
		
        agregarTabDetalle(this.tabSheet, tabDetalle);
        agregarTabListado(tabSheet, tabListado);
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.rd.monitoring.ui.IFactoriaElementosUI#getTabSheet()
	 */
	@Override
	public TabSheet getTabSheet() {
		
		return this.tabSheet;
	}
	
	@Override
	public void loadParametersOnInit(){

		BeanItemContainer<Parameter> beanItemContainer = new BeanItemContainer<Parameter>(Parameter.class, Statics.getList());
		this.pt.setContainerDataSource(beanItemContainer);

		setTableColumParameter(this.pt);
		
	}
	
	private void setTableColumParameter(Table table){
		table.setVisibleColumns("key", "value");
	}
	
	/**
	 * @param VerticalLayout layout
	 * @return TabSheet layout con un tabsheet nuevo agregado
	 */
	private TabSheet agregarTabsheet(VerticalLayout layout) {
		
		TabSheet ret = new TabSheet();
        layout.addComponent(ret);
        
        return ret;
	}

	/**
	 * @return VerticalLayout
	 */
	private VerticalLayout crearTabDetalle() {
		
		VerticalLayout ret = new VerticalLayout();
		
		ret.setMargin(true);
        ret.setSpacing(true);
        ret.setWidth("100%");
        
        HorizontalLayout hor = new HorizontalLayout();
        HorizontalLayout hor2 = new HorizontalLayout();
        
        HighCharts chart = new HighCharts();
        HighCharts chart2 = new HighCharts();
        HighCharts chart3 = new HighCharts();
        HighCharts chart4 = new HighCharts();
        
        // Consumo de CPU
        String arrayCPU = Statics.historicCPU.toString();
        chart.setHcjs("var options = { chart: {type: 'spline', backgroundColor: '#E0E0E0'},title: { text: 'CPU Usage' }, xAxis: { lineColor: '#000000', gridLineColor: '#000000'}, yAxis: { lineColor: '#000000', gridLineColor: '#000000'}, credits: {enabled: false},series: [{ name: 'Percentage of use', data: "+arrayCPU+"}] };");
        
        // Memoria fisica
        String arrayMemoriaFisica = Statics.historicConnectedUsers.toString();
        chart2.setHcjs("var options = { chart: {type: 'spline', backgroundColor: '#E0E0E0'},title: { text: 'Active procceses' }, xAxis: { lineColor: '#000000', gridLineColor: '#000000'}, yAxis: { lineColor: '#000000', gridLineColor: '#000000'}, credits: {enabled: false},series: [{ name: 'Percentage of use', data: "+arrayMemoriaFisica+"}] };");
        
        // Memoria fisica libre
        String arrayMemoriaFisicaLibre = Statics.historicFreePM.toString();
        chart3.setHcjs("var options = { chart: {type: 'spline', backgroundColor: '#E0E0E0'},title: { text: 'Physical free memory size' }, xAxis: { lineColor: '#000000', gridLineColor: '#000000'}, yAxis: { lineColor: '#000000', gridLineColor: '#000000'}, credits: {enabled: false},series: [{ name: 'Percentage of use', data: "+arrayMemoriaFisicaLibre+"}] };");
        
        // Memoria fisica libre
        String usableParticion = Statics.historicUsablePartitionSize.toString();
        chart4.setHcjs("var options = { chart: {type: 'spline', backgroundColor: '#E0E0E0'},title: { text: 'Usable partition size' }, xAxis: { lineColor: '#000000', gridLineColor: '#000000'}, yAxis: { lineColor: '#000000', gridLineColor: '#000000'}, credits: {enabled: false},series: [{ name: 'Percentage of use', data: "+usableParticion+"}] };");
        
        hor.addComponent(chart,0);
        hor.addComponent(chart2,1);
        ret.addComponent(hor);
        hor2.addComponent(chart3,0);
        hor2.addComponent(chart4,1);
        ret.addComponent(hor2);

		final Thread thread = new Thread() {
            @Override
            public void run() {
            	
            	while(true){
            		// Consumo de CPU
                    String arrayCPU = Statics.historicCPU.toString();
                    chart.setHcjs("var options = { chart: {type: 'spline', backgroundColor: '#E0E0E0'},title: { text: 'CPU Usage' }, xAxis: { lineColor: '#000000', gridLineColor: '#000000'}, yAxis: { lineColor: '#000000', gridLineColor: '#000000'}, credits: {enabled: false},series: [{ color: '#ff66ff', name: 'Percentage of use', data: "+arrayCPU+"}] };");
                    
                    // Memoria física
                    String arrayMemoriaFisica = Statics.historicConnectedUsers.toString();
                    chart2.setHcjs("var options = { chart: {type: 'spline', backgroundColor: '#E0E0E0'},title: { text: 'Active proccesses' }, xAxis: { lineColor: '#000000', gridLineColor: '#000000'}, yAxis: { lineColor: '#000000', gridLineColor: '#000000'}, credits: {enabled: false},series: [{ color: '#0066ff', name: 'Number', data: "+arrayMemoriaFisica+"}] };");
                    
                    // Memoria física libre
                    String arrayMemoriaFisicaLibre = Statics.historicFreePM.toString();
                    chart3.setHcjs("var options = { chart: {type: 'spline', backgroundColor: '#E0E0E0'},title: { text: 'Physical free memory size' }, xAxis: { lineColor: '#000000', gridLineColor: '#000000'}, yAxis: { lineColor: '#000000', gridLineColor: '#000000'}, credits: {enabled: false},series: [{ color: '#00cc00', name: 'Size (MB)', data: "+arrayMemoriaFisicaLibre+"}] };");
                    
                    // Memoria física libre
                    String usableParticion = Statics.historicUsablePartitionSize.toString();
                    chart4.setHcjs("var options = { chart: {type: 'spline', backgroundColor: '#E0E0E0'},title: { text: 'Usable partition size' }, xAxis: { lineColor: '#000000', gridLineColor: '#000000'}, yAxis: { lineColor: '#000000', gridLineColor: '#000000'}, credits: {enabled: false},series: [{ color: '#ff3300', name: 'Size (GB)', data: "+usableParticion+"}] };");
                    
					try {
				        //sending the actual Thread of execution to sleep X milliseconds
				        Thread.sleep(5000);
				    } catch(Exception e) {
				        System.out.println("Exception : "+e.getMessage());
				    }
					
            	}
            };
        };
        thread.start();
        
        return ret;
	}
	
	private PagedTable inicializarComponenteListado(final TabSheet tabSheet, final VerticalLayout tabDetalle) {
		
	PagedTable ret = new PagedTable("System parameters");
		
		ret.setSizeFull();

		ret.setColumnHeader("type", "Type");
		ret.setColumnHeader("percentage", "Percentage");
        
        ret.setColumnReorderingAllowed(true);
        
        ret.setImmediate(true);
        ret.setSelectable(true);
        ret.setAlwaysRecalculateColumnWidths(true);
       
        ret.setContainerDataSource(new BeanItemContainer<Parameter>(Parameter.class));
        
		return ret;
	}
	

	private VerticalLayout crearTabListado(TabSheet tabSheet2, VerticalLayout tabDetalle) {
		
        VerticalLayout ret = new VerticalLayout();
        
        ret.setMargin(true);
        ret.setSpacing(true);
        this.pt = this.inicializarComponenteListado(this.tabSheet, tabDetalle);
        
		final Thread thread = new Thread() {
            @Override
            public void run() {
            	
            	while(true){
            		
            		BeanItemContainer<Parameter> beanItemContainer = new BeanItemContainer<Parameter>(Parameter.class, Statics.getList());
    				pt.setContainerDataSource(beanItemContainer);
            		
					try {
				        //sending the actual Thread of execution to sleep X milliseconds
				        Thread.sleep(5000);
				    } catch(Exception e) {
				        System.out.println("Exception : "+e.getMessage());
				    }
					
            	}
            };
        };
        thread.start();
        
        ret.addComponent(this.pt);
        ret.addComponent(this.pt.createControls());
		
		return ret;
	}

	private void agregarTabListado(TabSheet tabSheet, VerticalLayout tabListado) {
		
        tabSheet.addTab(tabListado, "Detalles");
	}
	
	private void agregarTabDetalle(TabSheet tabSheet, VerticalLayout tabDetalle) {
		
        tabSheet.addTab(tabDetalle, "Gr\u00E1ficas");
	}
	
	
	
}

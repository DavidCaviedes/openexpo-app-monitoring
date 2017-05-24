![magnolia-logo](https://raw.githubusercontent.com/DavidCaviedes/openexpo-app-monitoring/master/openexpo-app-monitoring/src/main/resources/img/header.png)

# APP MONITORING

## Retos Digitales Magnolia Community 2017 [#RDMagnolia](https://www.magnolia-cms.com/about/news-events/events/rd-desarrolladores-2017.html) :rocket:

Este repositorio recoge un proyecto completo con la versión _Magnolia Community Edition (CE)_ y un módulo con la aplicación creada para participar en el **RETO VAADIN: Demuestra qué sabes de Vaadin en el Admin Central de Magnolia**.

## ¿Qué hace? :chart_with_upwards_trend:

La aplicación toma datos de la máquina dónde se ejecuta y los muestra por pantalla mediante gráficas y tablas usando VAADIN tal y como se puede observar en las capturas.

## ¿Cómo funciona? :bulb:

La aplicación utiliza el módulo scheduler propio de Magnolia para ejecutar un trabajo planificado cada más o menos, 30 segundos. Este trabajo se encarga de sondear los recursos usados por la aplicación y guardarlos en memoria.

Una vez almacenados dichos recursos, se introducen en gráficas interactivas usando Highcharts.js y son recogidos en una tabla creada exclusivamente usando Vaadin. Para conseguir el refresco tanto de la tabla como de la gráfica, hemos hecho uso del paralelismo en Java 7.

## Capturas :camera:

![Tabla](https://raw.githubusercontent.com/DavidCaviedes/openexpo-app-monitoring/master/openexpo-app-monitoring/src/main/resources/img/details.png)

![Grafica](https://raw.githubusercontent.com/DavidCaviedes/openexpo-app-monitoring/master/openexpo-app-monitoring/src/main/resources/img/graph.png)

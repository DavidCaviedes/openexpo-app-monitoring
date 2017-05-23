/**
 * 
 */
package com.rd.monitoring.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @author David
 *
 */
public interface IConstantsSymbols {

	String UNIX_PATH_SEPARATOR = "/";
	String DOMAINS_NUMBER_IDENTIFICATION_SEPARATOR = "_";
	String ROOT_PATH = "/";
	String PATH_SEPARATOR = "/";

	String COMMA = ",";
	String VERTICAL_BAR = "|";
	
	String TRUE = "true";
	String FALSE = "false";
	
	String ERROR_MESSAGE_SEPARATOR = "#error#";
	String ERROR_API_MESSAGE_SEPARATOR = "#errorAPI#";
	
	String SPAIN_COUNTRY_SHORT_NAME = "ES";
	
	List<String> TOW_SERVICE_REGIONS_NOT_PERMITTED = Arrays.asList("35", "38", "51", "52");
}

/**
 * 
 */
package com.rd.utils.messages;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rd.utils.constants.IConstantsOpenExpoUtilsModule;

import info.magnolia.cms.i18n.I18nContentSupport;
import info.magnolia.cms.i18n.I18nContentSupportFactory;
import info.magnolia.cms.i18n.Messages;
import info.magnolia.cms.i18n.MessagesManager;
import info.magnolia.cms.i18n.MessagesUtil;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.PropertyUtil;

/**
 * @author David Caviedes
 *
 */
public class MessagesUtils {

	private static final Logger log = LoggerFactory.getLogger(MessagesUtils.class);

	/**
	 * @param bundleName can be any file contained into classpath
	 * @param key referred into bundle file
	 * @param params to inject into bundle message
	 * @return string with final message
	 */
	public static String getString(String bundleName, String key, Object... params) {
		
		try {
			return MessageFormat.format(getString(bundleName, key), params);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * @param key referred into bundle file
	 * @return string message corresponding to key passed into default bundle
	 */
	public static String getString(String key) {
		
		return getString(IConstantsOpenExpoUtilsModule.BUNDLE_NAME, key);
	}
	
	/**
	 * @param bundleName can be any file contained into classpath
	 * @param key referred into bundle file
	 * @return string with final message
	 */
	public static String getString(String bundleName, String key) {
		
		Locale locale = I18nContentSupportFactory.getI18nSupport().getLocale();

        Messages messages = MessagesManager.getMessages(bundleName);

        // Si quisieramos sobreescribir el properties por defecto, lo indicariamos en esta variable
        String i18nBasename = null;
		messages = MessagesUtil.chain(MessagesManager.getMessages(i18nBasename , locale), messages);

        return messages.get(key);
	}
	
	/**
	 * @param node
	 * @param property
	 * @return internationalized property of node
	 */
	public String geti18nProperty(Node node, String property) {
		
		String pString = PropertyUtil.getString(node, property);

		I18nContentSupport i18contsuport = I18nContentSupportFactory.getI18nSupport();
		Property propertyi18n;

		try {
			propertyi18n = i18contsuport.getProperty(node, property, MgnlContext.getAggregationState().getLocale());

			if (propertyi18n != null) {
				pString = propertyi18n.getString();
			}

		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
		}

		if (pString == null) {
			pString = "";
		}
		
		return pString;
	}
	
}

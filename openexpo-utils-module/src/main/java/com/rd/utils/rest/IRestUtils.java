/**
 * 
 */
package com.rd.utils.rest;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

/**
 * @author David Caviedes
 *
 */
public interface IRestUtils {

	/**
	 * @param targetPath
	 * @return web target
	 */
	WebTarget getWebtarget(String targetPath);
	
	/**
	 * @param user
	 * @param pwd
	 * @return base64 token
	 */
	String getBase64Token(String user, String pwd);
	
	/**
	 * @param builder
	 * @param base64Token
	 * @return builder with desired header added
	 */
	Builder addBasicAuthorizationHeaderToBuilder(Builder builder, String base64Token);
	
	/**
	 * @param builder
	 * @param jsonObject
	 * @return service response
	 */
	Response invokeHttpJsonPost(Builder builder, JSONObject jsonObject);
	
}

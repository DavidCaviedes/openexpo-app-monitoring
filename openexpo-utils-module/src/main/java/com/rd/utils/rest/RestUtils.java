/**
 * 
 */
package com.rd.utils.rest;

import java.nio.charset.StandardCharsets;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

/**
 * @author David Caviedes
 *
 */
public class RestUtils implements IRestUtils {

	/* (non-Javadoc)
	 * @see com.rd.utils.rest.IRestUtils#getWebtarget(java.lang.String)
	 */
	@Override
	public WebTarget getWebtarget(String targetPath) {

		Client client = ClientBuilder.newBuilder().build();

		return client.target(targetPath);
	}

	/* (non-Javadoc)
	 * @see com.rd.utils.rest.IRestUtils#getBase64Token(java.lang.String, java.lang.String)
	 */
	@Override
	public String getBase64Token(String user, String pwd) {
		
		String token = user + ":" + pwd;
		return Base64.encodeBase64String(token.getBytes(StandardCharsets.UTF_8));
	}

	/* (non-Javadoc)
	 * @see com.rd.utils.rest.IRestUtils#addBasicAuthorizationHeaderToBuilder(javax.ws.rs.client.Invocation.Builder, java.lang.String)
	 */
	@Override
	public Builder addBasicAuthorizationHeaderToBuilder(Builder builder, String base64Token) {
		
		return builder.header("Authorization", "Basic " + base64Token);
	}

	/* (non-Javadoc)
	 * @see com.rd.utils.rest.IRestUtils#invokeHttpJsonPost(javax.ws.rs.client.Invocation.Builder, org.json.JSONObject)
	 */
	@Override
	public Response invokeHttpJsonPost(Builder builder, JSONObject jsonObject) {
		
		return builder.post(Entity.entity(jsonObject.toString(), ContentType.APPLICATION_JSON.toString()));
	}

}

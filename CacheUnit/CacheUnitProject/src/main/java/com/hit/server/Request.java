package com.hit.server;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author ���
 *
 *A class which represents the model of the request.
 *
 * @param <T>
 */

public class Request<T> implements Serializable {

	private static final long serialVersionUID = -4137284727528762260L;
	Map<String, String> headers;
	T body;
	
	public Request(Map<String, String> headers, T body){
		this.headers = headers;
		this.body = body;
	}
	
	public Map<String, String> getHeaders(){
		return headers;
		
	}

	public void setHeaders(Map<String, String> headers){
		this.headers = headers;
	}
	
	public T getBody(){
		return body;
		
	}
	
	public void setBody(T body){
		this.body = body;
	}
	
	@Override
	public String toString() {
		return "Request [headers=" + headers + ", body=" + body + "]";
	}

}

package com.ideassoft.portal;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * 
 */
@WebService(name = "IDataDealPortal", targetNamespace = "http://portal.ideassoft.com")
public interface IDataDealPortal {

	/**
	 * 
	 * @param movement
	 * @param data
	 * @param type
	 * @return returns java.lang.String
	 */
	@WebMethod
	@WebResult(name = "result", targetNamespace = "")
	@RequestWrapper(localName = "call", targetNamespace = "http://portal.ideassoft.com", className = "com.ideassoft.portal.Call")
	@ResponseWrapper(localName = "callResponse", targetNamespace = "http://portal.ideassoft.com", className = "com.ideassoft.portal.CallResponse")
	public String call(
			@WebParam(name = "type", targetNamespace = "http://portal.ideassoft.com") int type,
			@WebParam(name = "movement", targetNamespace = "http://portal.ideassoft.com") int movement,
			@WebParam(name = "data", targetNamespace = "http://portal.ideassoft.com") String data);

}

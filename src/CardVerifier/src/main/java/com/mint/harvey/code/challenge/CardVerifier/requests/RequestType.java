package com.mint.harvey.code.challenge.CardVerifier.requests;


/**
 * @Authur Harvey Imama
 *Interface defining all types of system requests
 */
public interface RequestType<T,U>  {
	
	 T sendRequest(U request);

}

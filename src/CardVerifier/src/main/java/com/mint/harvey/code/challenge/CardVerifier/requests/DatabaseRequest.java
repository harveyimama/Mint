package com.mint.harvey.code.challenge.CardVerifier.requests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mint.harvey.code.challenge.CardVerifier.data.BinEntity;
import com.mint.harvey.code.challenge.CardVerifier.data.BinRepository;
/**
 * @Authur Harvey Imama
 * Implements the sendRquest method of the inherited RequestType interface 
 * for database calls. Also extends other database request calls
 */
@Service
public class DatabaseRequest implements RequestType<Object, Object> {

	@Autowired
	BinRepository binRepo;

	@Override
	public Object sendRequest(Object request) {

		if (request instanceof String)
		{
			BinEntity entity = binRepo.findByBin((String) request);
			if(entity != null)
			{
			  entity.setNoOfSuccessfulCalls(entity.getNoOfSuccessfulCalls() + 1);
			  binRepo.save(entity);
			}
			return entity;
		}
		else
			return binRepo.save((BinEntity) request);
	}

	public List<BinEntity> sendRequest(final int start,final int limit) {
		
		return binRepo.findAllOffestLimit(start,limit); 
	}

}

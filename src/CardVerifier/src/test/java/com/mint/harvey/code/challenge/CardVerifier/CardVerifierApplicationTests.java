package com.mint.harvey.code.challenge.CardVerifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mint.harvey.code.challenge.CardVerifier.response.StatsResponse;
import com.mint.harvey.code.challenge.CardVerifier.response.VerificationResponse;

public class CardVerifierApplicationTests extends AbstractTest  {
	@Override
	   @BeforeEach
	   public void setUp() {
	      super.setUp();
	   }
	   
	   @Test
	   public void verifyCorrectBin() throws Exception {
	      String uri = "/card-scheme/verify/519908";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      VerificationResponse res = super.mapFromJson(content, VerificationResponse.class);
	      assertTrue(res.isSuccess());
	   }
	   
	   @Test
	   public void verifyWrongBin() throws Exception {
	      String uri = "/card-scheme/verify/001111";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      VerificationResponse res = super.mapFromJson(content, VerificationResponse.class);
	      assertTrue(!res.isSuccess());
	   }
	   
	   @Test
	   public void verifyLongBin() throws Exception {
	      String uri = "/card-scheme/verify/45717360";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      VerificationResponse res = super.mapFromJson(content, VerificationResponse.class);
	      assertTrue(res.isSuccess());
	   }
	   
	   @Test
	   public void getStats() throws Exception {
	      String uri = "/card-scheme/stats?start=1&limit=3";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      StatsResponse res = super.mapFromJson(content, StatsResponse.class);
	      assertTrue(res.getSuccess());
	   }
	   
	   @Test
	   public void getStatsWrongEntry() throws Exception {
	      String uri = "/card-scheme/stats?start=-1&limit=-3";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      StatsResponse res = super.mapFromJson(content, StatsResponse.class);
	      assertTrue(!res.getSuccess());
	   }
	   
	   @Test
	   public void getStatsNonIntegerEntry() throws Exception {
	      String uri = "/card-scheme/stats?start=A&limit=bgats";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(400, status);
	     
	   }

}

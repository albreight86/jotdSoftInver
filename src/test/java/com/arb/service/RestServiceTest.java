package com.arb.service;

import static org.mockito.Mockito.verify;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;

import com.arb.business.Business;
import com.arb.model.JokeOfTheDay;
import org.testng.annotations.BeforeMethod;

public class RestServiceTest
{
   private RestService rest;
   
   @Mock Business business;

   @BeforeMethod
   public void setup()
   {
      MockitoAnnotations.initMocks(this);
      rest = new RestService();
      rest.setBusiness(business);
   }

   @Test()
   public void testAddJoke()
   {
      MockitoAnnotations.initMocks(this);
      rest = new RestService();
      rest.setBusiness(business);
      JokeOfTheDay joke = new JokeOfTheDay("joke", "desc");
      rest.addJoke(joke);
      verify(business).addJoke("joke", rest.getStartOfTodayMs(), "desc");
   }
}

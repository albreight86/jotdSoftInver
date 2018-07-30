package com.arb.business;

import java.util.ArrayList;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.arb.db.JokeOfTheDaySch;
import com.arb.db.JotdRowMapper;

public class BusinessTest
{
   private Business business;
   
   @Mock JdbcTemplate jdbcMock;

   @BeforeMethod
   public void setup()
   {
      MockitoAnnotations.initMocks(this);
      business = new Business();
      business.setJdbcTemplate(jdbcMock);
   }

   @Test()
   public void testAddJoke()
   {
      when(jdbcMock.query(anyString(),any(Object[].class),isA(JotdRowMapper.class))).thenReturn(new ArrayList<JokeOfTheDaySch>());
      business.addJoke("", 0L, "");

      //This doesn't compile because query takes varargs for the prepared statement
      // Try using groovy for this.....might work
      //verify(jdbcMock).query(Business.SQL_ADD_JOKE_BY_DATE, "", new Timestamp(0));; 

   }

}

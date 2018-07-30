package com.arb.business;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.arb.db.JokeOfTheDaySch;
import com.arb.db.JotdRowMapper;
import com.arb.model.JokeOfTheDay;

@Component
public class Business
{
   private final static Logger log = org.apache.logging.log4j.LogManager.getLogger(Business.class);

   public final static String SQL_GET_ALL_JOKES = "select * from JOKES ";
   public final static String SQL_GET_JOKE_BY_DATE= "select * from JOKES where DATE = ?";
   public final static String SQL_DELETE_JOKE_BY_DATE= "delete from JOKES where DATE = ?";
   public final static String SQL_ADD_JOKE_BY_DATE= " insert into JOKES (joke, date, description) values (?,?,?)";
   public final static String SQL_UPDATE_JOKE_BY_DATE= "update jokes SET joke = ?, description = ? WHERE date = ?";

   private JdbcTemplate jdbc;

   @Resource
   public void setJdbcTemplate(JdbcTemplate jdbc)
   { this.jdbc = jdbc; }

   @PostConstruct
   public void init()
   { }

   public JokeOfTheDay retrieveJoke(long timeMs)
   {
      java.sql.Timestamp sqlTime = new java.sql.Timestamp(timeMs);
      log.info("sql time stamp: {}", sqlTime.toString());

      List<JokeOfTheDaySch> jokes = jdbc.query(SQL_GET_JOKE_BY_DATE, new Object[]{sqlTime}, new JotdRowMapper() );
      
      log.info("number of jokes returned: {}", jokes.size());

      if(jokes != null && ! jokes.isEmpty())
      {
         JokeOfTheDaySch jotdSch = jokes.get(0);
         log.info("retrieved joke {}, {}, {} ", jotdSch.getJoke(), jotdSch.getDate().getTime(), jotdSch.getDescription());
         return schToModel(jokes.get(0));
      }
      log.warn("No Joke For Today");
      
      return JokeOfTheDay.NO_JOKE_OF_THE_DAY;
   }

   public void addJoke(String joke, long timeMs, String description)
   {
      Timestamp sqlTime = new Timestamp(timeMs);
      log.info("sql time stamp: {}", sqlTime.toString());
      
      JokeOfTheDay jotd = retrieveJoke(timeMs);
      
      if( ! jotd.equals(JokeOfTheDay.NO_JOKE_OF_THE_DAY))
      {
         log.info("Updating joke having date: {}", timeMs);
         jdbc.update(SQL_UPDATE_JOKE_BY_DATE, joke, description, new Timestamp(timeMs));
         return;
      }

      log.info("Adding joke {}, {}, {} ", joke, timeMs, description);
      jdbc.update(SQL_ADD_JOKE_BY_DATE, joke, new Timestamp(timeMs), description);
   }

   public void deleteJoke(long timeMs)
   {
      java.sql.Timestamp sqlTime = new java.sql.Timestamp(timeMs);
      log.info("sql time stamp: {}", sqlTime.toString());

      log.info("Deleting joke {}", timeMs);
      
      int rowsDeleted = jdbc.update(SQL_DELETE_JOKE_BY_DATE, new Timestamp(timeMs));
      if(rowsDeleted == 0)
      {
         log.warn("no joke found to be deleted");
      }
      if(rowsDeleted > 1)
      {
         log.error("{} jokes deleted", rowsDeleted);
      }
   }
   
   /**
    * The is to separate the schema from the model so each
    * can change independently of the other
    */
   private JokeOfTheDay schToModel(JokeOfTheDaySch jotdSch)
   {
      return new JokeOfTheDay(jotdSch.getJoke(), 
                              jotdSch.getDescription());
   }
   
   /**
    * Used for development only
    */
   @Deprecated
   public List<JokeOfTheDay> retrieveAllJokes()
   {
      List<JokeOfTheDaySch> jokes = jdbc.query(SQL_GET_ALL_JOKES, new JotdRowMapper()); 
      log.info("number of jokes: {}", jokes.size());
      log.info("jokes: {}", jokes);
      
      return jokes.stream()
             .map(jokeSch -> {return schToModel(jokeSch);})
             .collect(Collectors.toList());
   }
}



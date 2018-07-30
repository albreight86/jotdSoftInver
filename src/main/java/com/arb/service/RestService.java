package com.arb.service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.annotations.GZIP;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.arb.business.Business;
import com.arb.model.JokeOfTheDay;

@Service("RestService")
@Path("")
@GZIP
public class RestService
{
   private static Logger log = org.apache.logging.log4j.LogManager.getLogger(RestService.class);

   private final static Response RESPONSE_OK = Response.ok().build();
   private final static Response RESPONSE_NO_CONTENT = Response.noContent().build();
   private final static Response RESPONSE_BAD_REQUEST = Response.status(Status.BAD_REQUEST).build();

   private Business business;

   @Resource
   public void setBusiness(Business business)
   { this.business = business; }

   public RestService()
   { }

   @PostConstruct
   public void init()
   { }

   @Path("/all")
   @GET
   @Produces({MediaType.APPLICATION_JSON})
   public Response allJokes()
   {
      log.info("allJokes");
      List<JokeOfTheDay> jokes = business.retrieveAllJokes();
      return Response.ok(jokes).build();
   }

   @Path("/joke")
   @GET
   @Produces({MediaType.APPLICATION_JSON})
   public Response getJoke()
   {
      long todayMs = getStartOfTodayMs();
      
      log.info("getJoke: date: {}", todayMs);

      JokeOfTheDay joke = business.retrieveJoke(todayMs);

      log.info("getJoke: {}", joke.toString());

      return Response.ok(joke).build();
   }

   @Path("/addJoke")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public Response addJoke(JokeOfTheDay jotd )
   {
      log.info("addJoke");

      if(jotd.getDescription() == null)
      {
         jotd.setDescription("");
      }

      log.info("addJoke: joke: {}, desc: {}", jotd.toString());

      long todayMs = getStartOfTodayMs();
      
      business.addJoke(jotd.getJoke(), todayMs, jotd.getDescription());

      return RESPONSE_OK;
   }

   @Path("/addJokePath")
   @POST
   @Produces({MediaType.APPLICATION_JSON})
   public Response addJokePath(@QueryParam("joke") String joke, 
                           @QueryParam("desc") String description)
   {
      log.info("addJoke");
      String desc="";

      if(description != null)
      {
         desc= description;
      }

      log.info("addJoke: joke: {}, desc: {}", joke, description);

      long todayMs = getStartOfTodayMs();
      
      business.addJoke(joke, todayMs, description);

      return RESPONSE_NO_CONTENT;
   }

   @Path("/deleteJoke")
   @POST
   public Response deleteJoke()
   {
      long todayMs = getStartOfTodayMs();

      log.info("Delete Joke: date: {}", todayMs);
      
      business.deleteJoke(todayMs);

      return RESPONSE_OK;
   }
   
   public final long getStartOfTodayMs()
   {
      Calendar today = Calendar.getInstance();
      today.setTimeInMillis(System.currentTimeMillis());
      today.set(Calendar.HOUR_OF_DAY, 0);
      today.set(Calendar.MINUTE, 0);
      today.set(Calendar.SECOND, 0);
      today.set(Calendar.MILLISECOND, 0);
      log.info("Start of today = {}", today.getTime());
      return today.getTimeInMillis();
   }
}




package com.arb.db;

import java.sql.Timestamp;

public class JokeOfTheDaySch
{
   private String joke;
   private Timestamp date;
   private String description;
   
   @Override
   public String toString()
   {
      return "joke: " + joke + " date: " + date + " desc: " + description;
   }
   

   public String getJoke()
   { return joke; }

   public void setJoke(String joke)
   { this.joke = joke; }

   public Timestamp getDate()
   { return date; }

   public void setDate(Timestamp date)
   { this.date = date; }

   public String getDescription()
   { return description; }

   public void setDescription(String description)
   { this.description = description; }
}


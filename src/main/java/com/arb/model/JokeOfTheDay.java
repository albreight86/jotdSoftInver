package com.arb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "JokeOfTheDay")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class JokeOfTheDay
{

   public final static JokeOfTheDay NO_JOKE_OF_THE_DAY = new JokeOfTheDay("No Joke", "No Desc");

   @XmlElement(name = "joke")
   private String joke;
   // @XmlElement(name = "date")
   // private Long date;
   @XmlElement(name = "description", nillable = true)
   private String description;

   public JokeOfTheDay()
   {
   }

   public JokeOfTheDay(String joke,
         // Long date,
         String description)
   {
      this.joke = joke;
      // this.date = date;
      this.description = description;
   }

   @Override
   public String toString()
   {
      return "JokeOfTheDay [joke=" + joke + ", description=" + description + "]";
   }

   public String getJoke()
   {
      return joke;
   }

   // public Long getDate()
   // {
   // return date;
   // }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String desc)
   {
      description = desc;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((description == null) ? 0 : description.hashCode());
      result = prime * result + ((joke == null) ? 0 : joke.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      JokeOfTheDay other = (JokeOfTheDay) obj;
      if (description == null)
      {
         if (other.description != null)
            return false;
      } else if (!description.equals(other.description))
         return false;
      if (joke == null)
      {
         if (other.joke != null)
            return false;
      } else if (!joke.equals(other.joke))
         return false;
      return true;
   }
}

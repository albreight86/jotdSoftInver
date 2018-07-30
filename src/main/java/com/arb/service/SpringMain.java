package com.arb.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain
{
   
   private static Logger log = LogManager.getLogger(SpringMain.class);

   public static void main (String[] args)
   {
      new SpringMain();
   }

   public SpringMain()
   {
      // open/read the application context file
      ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
      log.info("Started Spring Context");

   }

}

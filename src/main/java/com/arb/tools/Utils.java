package com.arb.tools;

import javax.sql.DataSource;

import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class Utils
{
   public static void main(String[] args)
   {
      Logger log = org.apache.logging.log4j.LogManager.getLogger(Utils.class);

      ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
      DataSource ds = (DataSource) applicationContext.getBean("dataSource");
      JdbcTemplate jt = new JdbcTemplate(ds);

      try
      {
         jt.execute("DROP TABLE IF EXISTS jokes");
         jt.execute("CREATE TABLE jokes (id serial primary key, "
                                     +  "joke varchar not null, "
                                     +  "date timestamp not null, "
                                     +  "description varchar)");
         jt.execute("ALTER TABLE jokes ADD CONSTRAINT constraint1 UNIQUE (date)");
      } catch (Exception ex)
      {
         log.error("problem with drop or create");
      }
   }
}
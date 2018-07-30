package com.arb.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.ext.Provider;

import org.springframework.jdbc.core.RowMapper;

public class JotdRowMapper  implements RowMapper<JokeOfTheDaySch>
{
   public JokeOfTheDaySch mapRow(ResultSet rs, int rowNum) throws SQLException
   {
      JokeOfTheDaySch jotd = new JokeOfTheDaySch();

      jotd.setJoke(rs.getString("joke"));
      jotd.setDescription(rs.getString("description"));
      jotd.setDate(rs.getTimestamp("date"));
      return jotd;
   }
}

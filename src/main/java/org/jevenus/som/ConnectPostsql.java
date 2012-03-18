// 	$Id: ConnectPostsql.java,v 1.2 2012/02/08 03:22:49 arch Exp arch $	

package org.jevenus.som;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class ConnectPostsql {
  private static final String user = "arch";
  private static final String passwd = "1";
  private static final String url = "jdbc:postgresql://localhost/us08county";

  public static final Logger LOG = Logger.getLogger(ConnectPostsql.class);
  
  public static void main(String[] args) {
    new ConnectPostsql().connectDB();
    LOG.info("conncetion successful!");
  }

  ConnectPostsql() {}

  public void connectDB() {

    Statement stmt = null;
    ResultSet rs = null;

    String mysql = "SELECT statefp, countyfp, countyns, cntyidfp, name, namelsad " +
                   "from tl_2008_us_county where statefp = '08' order by name";
    try {
      Connection con = getConn();
      stmt = con.createStatement();
      rs = stmt.executeQuery(mysql);
      while (rs.next()) {
        String statefp = rs.getString("statefp");
        String countyfp = rs.getString("countyfp");
        String countyns = rs.getString("countyns");
        String cntyidfp = rs.getString("cntyidfp");
        String name = rs.getString("name");
        String namelsad = rs.getString("namelsad");
        System.out.println(statefp + " " + countyfp + " " +
                           countyns + " " + cntyidfp + " " +
                           name + " " + namelsad);
      }
    } catch (SQLException  se) {
      // System.err.println("sql error");
      LOG.error("sql exception" + se);
    }
  }

  public static Connection getConn() {  // 使用静态工厂方法: 遵循ej的item1
                                        // consider static factory methods
                                        // instead of constructors
    Connection conn = null;
    try {
      conn =  DriverManager.getConnection(url, user, passwd);
    } catch (SQLException sqe) {
      // System.err.println("sql exeception!");
      LOG.error("sql exception" + sqe);
    }
    return conn;
  }

}

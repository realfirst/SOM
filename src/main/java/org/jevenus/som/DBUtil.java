/**
 * @file DBUtil.java
 * @brief implement a lot of util funtion for concise data process
 * @author dingje <dingje.gm@gmail.com>
 * @date Sat Feb 11 15:25:16 2012
 */

package org.jevenus.som;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class DBUtil {

  private Map<String, List<String>> attrsMap;
  private List<String> attrsList;
  private Map<String, Set<String>> nbsMap;
  private Set<String> nbsSet;
  private List<Integer> maxList;
  private List<Integer> minList;

  private static Connection conn;
  private static Statement attrsStmt;

  static final Logger logger = Logger.getLogger(DBUtil.class.getName());
  
  public DBUtil() {
    attrsMap = new TreeMap<>();
    attrsList = null;
    nbsMap = new TreeMap<>();
    nbsSet = null;
    maxList = new ArrayList<>();
    minList = new ArrayList<>();

    conn = ConnectPostsql.getConn();
    try {
      attrsStmt = conn.createStatement();
    } catch (SQLException sqle) {
      logger.info("sqlexception from attrsStmt" + sqle);
    }

  }

  private void queryNbs() {

    String nbsQuery = "SELECT t1.cntyidfp as cntyid , t2.cntyidfp as county_name from tl_2008_us_county t1,tl_2008_us_county t2 " +
                      "where st_touches(t1.the_geom, t2.the_geom)='T';";

    String cntyidfp = null;
    String cntyidfpPre = null;
    
    logger.info("" + nbsQuery);
    
    Statement nbStmt = null;
    try {
      nbStmt = conn.createStatement();
      ResultSet nbsRs = nbStmt.executeQuery(nbsQuery);

      nbsSet = new HashSet<>();
      while (nbsRs.next()) {

        cntyidfp = nbsRs.getString("cntyid");
        
        if (!cntyidfp.equals(cntyidfpPre) && cntyidfpPre != null) {
          nbsMap.put(cntyidfpPre, nbsSet);
          nbsSet = new HashSet<>();
        }

        nbsSet.add(nbsRs.getString("county_name"));
        cntyidfpPre = cntyidfp;
      }
      nbsMap.put(cntyidfp, nbsSet);
    } catch (SQLException sqle) {
      logger.info("sqlexception" + sqle);
      
    }
  }
  
  private void queryAttrs(List<String> selectAttrs) {

    String selectAttrsStr = null;
    StringBuilder sb = new StringBuilder();

    for (String attr : selectAttrs) {
      sb.append(attr + ", ");
    }

    int lastIndex = sb.lastIndexOf(",");
    selectAttrsStr = sb.toString().substring(0, lastIndex);
    String attrsQuery = "SELECT cntyidfp, " + selectAttrsStr + " from census2000;";
    
    // Statement attrsStmt = null;
    try {
      // attrsStmt = conn.createStatement();
      ResultSet attrsRs = attrsStmt.executeQuery(attrsQuery);

      String cntyidfp = null;
      
      while (attrsRs.next()) {
        cntyidfp = attrsRs.getString("cntyidfp");

        attrsList = new ArrayList<>(); // 一定要放在循环内，不然heap溢出
          
        for (String attr : selectAttrs) {
          attrsList.add(attrsRs.getString(attr));
        }
        // System.out.println(attrsList);

        attrsMap.put(cntyidfp, attrsList);
      }
    } catch (SQLException sqle) {
      logger.info("sqlexception in method queryAttrs()" + sqle);
    }

  }
  

  private void queryMaxAttrsVal(List<String> selectAttrs) {
    int lastIndex;
    StringBuilder maxQuerySB = new StringBuilder("select ");

    for (String attr : selectAttrs) {
      maxQuerySB.append("max(cast(" + attr + " as int)),");
    }
    lastIndex = maxQuerySB.lastIndexOf(",");
    String maxQuery = maxQuerySB.toString().substring(0, lastIndex) + " from census2000;";

    Statement maxAttrsValStmt = null;
    ResultSet maxAttrsValRS = null;
    try {
      maxAttrsValStmt = conn.createStatement();
      maxAttrsValRS = maxAttrsValStmt.executeQuery(maxQuery);

      while (maxAttrsValRS.next()) {
        for (int i = 1; i <= selectAttrs.size(); i++) {
          maxList.add(maxAttrsValRS.getInt(i));
        }
      }

    } catch (SQLException sqle) {
      logger.info("sqlexception from queryMaxAttrsVal" + sqle);
    }

  }

  private void queryMinAttrsVal(List<String> selectAttrs) {
    int lastIndex;
    StringBuilder minQuerySB = new StringBuilder("select ");

    for (String attr : selectAttrs) {
      minQuerySB.append("min(cast(" + attr + " as int)),");
    }
    lastIndex = minQuerySB.lastIndexOf(",");
    String minQuery = minQuerySB.toString().substring(0, lastIndex) + " from census2000;";

    Statement minAttrsValStmt = null;
    ResultSet minAttrsValRS = null;
    try {
      minAttrsValStmt = conn.createStatement();
      minAttrsValRS = minAttrsValStmt.executeQuery(minQuery);

      while (minAttrsValRS.next()) {
        for (int i = 1; i <= selectAttrs.size(); i++) {
          minList.add(minAttrsValRS.getInt(i));
        }
      }

    } catch (SQLException sqle) {
      logger.info("sqlexception from queryMinAttrsVal" + sqle);
    }

  }

  public Map<String, Pair> getLocation() {
    Map<String, Pair> locationMap = null;
    Connection conn = ConnectPostsql.getConn();
    String locationQuery = "SELECT cntyidfp, location from tl_2008_us_county order by cntyidfp;";
    Statement locationStmt = null;

    try {
      locationMap = new TreeMap<>();
      String cntyidfp = null;
      String location = null;
      
      int xStrBeginIndex;
      int xStrEndIndex;
      int yStrEndIndex;
      String xStr = null;
      String yStr = null;
      double x;
      double y;
      Pair pair = null;
      
      locationStmt = conn.createStatement();
      ResultSet locationRs = locationStmt.executeQuery(locationQuery);
      while (locationRs.next()) {
        cntyidfp = locationRs.getString("cntyidfp");
        location = locationRs.getString("location");
        // System.out.println(location);
        xStrBeginIndex = location.indexOf("(");
        xStrEndIndex = location.indexOf(" ");
        yStrEndIndex = location.indexOf(")");
        xStr = location.substring(xStrBeginIndex+1, xStrEndIndex);
        yStr = location.substring(xStrEndIndex+1, yStrEndIndex);
        x = Double.valueOf(xStr);
        y = Double.valueOf(yStr);
        pair = new Pair(x, y);
        locationMap.put(cntyidfp, pair);
      }

    } catch (SQLException sqle) {
      System.err.println("sql err from getLocation");
    }
    return (locationMap);
  }

  public class Pair {
    private double x;
    private double y;

    Pair(double x, double y) {
      this.x = x;
      this.y = y;
    }

    public double getX() {
      return x;
    }

    public double getY() {
      return y;
    }

    @Override
    public String toString() {
      return "(" + getX() + ", " + getY() + ")";
    }
  }
  
  public static List<String> getQueriedAttrs() {
    List<String> queriedAttrs = new ArrayList<>();
    queriedAttrs = Arrays.asList("famale", "male");
    return (queriedAttrs);
  }

  public Map<String, List<String>> getAttrVal() {
    queryAttrs(getQueriedAttrs());
    return (attrsMap);
  }

  public Map<String, Set<String>> getNbs() {
    queryNbs();
    return (nbsMap);
  }

  public List<Integer> getMinList() {
    queryMinAttrsVal(getQueriedAttrs());
    return (minList);
  }

  public List<Integer> getMaxList() {
    queryMaxAttrsVal(getQueriedAttrs());
    return (maxList);
  }
  
  public static void main(String[] args) {
    BasicConfigurator.configure();
    DBUtil du = new DBUtil();

    // Map<String, List<String>> tempMap = du.getAttrVal();
    // for (String cntyid : tempMap.keySet()) {
    // System.out.println(cntyid);
    // }
    
    /*
      System.out.println(du.getMaxList());
      System.out.println(du.getMinList());
      Map<String, Set<String>> tempMap = du.getNbs();
      for (String cntyid : tempMap.keySet()) {
      System.out.println(cntyid + " = " + tempMap.get(cntyid));
      }
    */
    
    Map<String, Set<String>> tempMap = du.getNbs();//du.getAttrVal();
    for (String cntyid : tempMap.keySet()) {
      System.out.println(cntyid + " = " + tempMap.get(cntyid));
    }
    
    // System.out.println(tempMap.size());
    // for (String cntyid : tempMap.keySet()) {
    // System.out.println(cntyid + " = " + tempMap.get(cntyid));
    // }
    // System.out.println(du.getNbs());
    // du.pareData(du.getQueriedAttrs());
    // System.out.println(du.getNbs());
    // System.out.println(du.getAttrVal());
    // System.out.println(du.getMinList());
    // System.out.println(du.getMaxList());
    // System.out.println(du.getLocation());
    // logger.info("location" + du.getLocation());
  }
}

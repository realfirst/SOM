/**
 * @file RDAJSlom.java
 * @brief calculate of outlier use real data set
 *        and use spatial ajecent method to get
 *        spatial neighbor
 * @author dingje <dingje.gm@gmail.com>
 * @date Wed Mar  7 18:44:47 2012
 */
package org.jevenus.som;

import java.util.List;


public class RDAJSlof extends Slof {

  public RDAJSlof() {
    DBUtil dbu = new DBUtil();
    nbsMap = dbu.getNbs();
    // int count = 0;
    // for (String cntyid : nbsMap.keySet()) {
      // if (nbsMap.get(cntyid).size() == 1) {
        // System.out.println(cntyid + "= " + nbsMap.get(cntyid));
        // count++;
      // }
    // }
    // System.out.println(count);
    attrsValMap = dbu.getAttrVal();

    Attribute attr = new Attribute();
    List<Integer> maxListVal = dbu.getMaxList();
    List<Integer> minListVal = dbu.getMinList();
    attrsMap = attr.getNormalizedAttrs(attrsValMap, maxListVal, minListVal);
    deta = 0.01;
  }

  public static void main(String[] args) {
    RDAJSlof rdajslof = new RDAJSlof();
    // rdajslof.runAlgo(rdajslof.getSlof());
    for (String cntyid : rdajslof.getSlof().keySet()) {
      System.out.println(cntyid + " = " + rdajslof.getSlof().get(cntyid));
    }
  }
}

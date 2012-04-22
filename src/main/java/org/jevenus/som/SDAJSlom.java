/**
 * @file SDAJSlom.java
 * @brief calculate of outlier via slom and
 *        get neighbor relationship through
 *        ajecent and use senthentic data set
 * @author dingje <dingje.gm@gmail.com>
 * @date Thu Mar  8 21:27:09 2012
 */

package org.jevenus.som;

import java.util.Map;
import java.util.TreeSet;

public class SDAJSlom extends Slom {

  public SDAJSlom() {
    SDUtil sdu = new SDUtil();

    nbsMap = sdu.getNbsMap();

    attrsMap = sdu.getNormalizedAttrsValue();
    attrsValMap = sdu.getMatrix();
  }

  public static void main(String[] args) {
    SDAJSlom sdajslom = new SDAJSlom();
    Map<String, Double> modifiedDistMap = sdajslom.getModfiedAvgDist();
    // System.out.println("********************");
    // for (String loc : new TreeSet<String>(modifiedDistMap.keySet())) {
      // System.out.println(loc + ": " + modifiedDistMap.get(loc));
      // System.out.printf("%s: %.2f\n", loc, modifiedDistMap.get(loc));
    // }
    // System.out.println("********************");
    // MapUtil.sortMapByValue(sdajslom.getSlom());
    sdajslom.runAlgo(sdajslom.getSlom());
  }
}

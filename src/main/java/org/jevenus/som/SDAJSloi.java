/**
 * @file SDAJSloi.java
 * @brief
 * @author dingje <dingje.gm@gmail.com>
 * @date Tue Mar 27 16:04:29 2012
 */
package org.jevenus.som;

public class SDAJSloi extends Sloi {

  public SDAJSloi() {
    SDUtil sdu = new SDUtil();
    nbsMap = sdu.getNbsMap();
    attrsMap = sdu.getNormalizedAttrsValue();
    attrsValMap = sdu.getMatrix();
  }

  public static void main(String[] args) {
    SDAJSloi sdajsloi = new SDAJSloi();
    // MapUtil.sortMapByValue(sdajsloi.getSloi());
    sdajsloi.runAlgo(sdajsloi.getSloi());
  }
  
}

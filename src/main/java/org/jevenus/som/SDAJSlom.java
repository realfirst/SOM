/**
 * @file SDAJSlom.java
 * @brief calculate of outlier via slom and
 *        get neighbor relationship through
 *        ajecent and use senthentic data set
 * @author dingje <dingje.gm@gmail.com>
 * @date Thu Mar  8 21:27:09 2012
 */

package org.jevenus.som;

public class SDAJSlom extends Slom {

  public SDAJSlom() {
    SDUtil sdu = new SDUtil();

    nbsMap = sdu.getNbsMap();

    attrsMap = sdu.getNormalizedAttrsValue();
    attrsValMap = sdu.getMatrix();
  }

  public static void main(String[] args) {
    SDAJSlom sdajslom = new SDAJSlom();
    // MapUtil.sortMapByValue(sdajslom.getSlom());
    sdajslom.runAlgo(sdajslom.getSlom());
  }
}

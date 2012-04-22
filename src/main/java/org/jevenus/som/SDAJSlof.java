/**
 * @file SDAJSlof.java
 * @brief
 * @author dingje <dingje.gm@gmail.com>
 * @date Thu Mar  8 22:24:01 2012
 */

package org.jevenus.som;

public class SDAJSlof extends Slof {

  public SDAJSlof() {
    SDUtil sdu = new SDUtil();
    attrsMap = sdu.getNormalizedAttrsValue();
    nbsMap = sdu.getNbsMap();
    attrsMap = sdu.getNormalizedAttrsValue();
    attrsValMap = sdu.getMatrix();
    
    deta = 0.01;

  }

  public static void main(String[] args) {
    SDAJSlof sdajslof = new SDAJSlof();
    System.out.println(sdajslof);
    // MapUtil.sortMapByValue(sdajslof.getSlof());
    sdajslof.runAlgo(sdajslof.getSlof());
  }
  
}

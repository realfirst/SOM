/**
 * @file SDDTSlof.java
 * @brief
 * @author dingje <dingje.gm@gmail.com>
 * @date Thu Mar  8 23:47:27 2012
 */

package org.jevenus.som;

public class SDDTSlof extends Slof {

  public SDDTSlof() {
    SDUtil sdu = new SDUtil();
    attrsMap = sdu.getNormalizedAttrsValue();

    SDDTNeighborhood sddt = new SDDTNeighborhood();
    nbsMap = sddt.getSDDtNb();
  }

  public static void main(String[] args) {
    SDDTSlof sddtslof = new SDDTSlof();
    MapUtil.sortMapByValue(sddtslof.getSlof());
  }
}

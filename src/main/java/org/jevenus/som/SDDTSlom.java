/**
 * @file SDDTSlom.java
 * @brief
 * @author dingje <dingje.gm@gmail.com>
 * @date Thu Mar  8 21:51:04 2012
 */

package org.jevenus.som;


public class SDDTSlom extends Slom {

  public SDDTSlom() {
    SDUtil sdu = new SDUtil();
    attrsMap = sdu.getNormalizedAttrsValue();
    attrsValMap = sdu.getMatrix();

    SDDTNeighborhood sddt = new SDDTNeighborhood();
    nbsMap = sddt.getSDDtNb();
  }

  public static void main(String[] args) {
    SDDTSlom sddtslom = new SDDTSlom();
    sddtslom.runAlgo(sddtslom.getSlom());
  }
}

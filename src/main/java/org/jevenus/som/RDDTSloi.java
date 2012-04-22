/**
 * @file RDDTSlom.java
 * @brief calculate of spatial outliter via slom
 *        and get spatial neighbor relationship
 *        through delauge method and use real data set
 *        to test the method's feasiblity.
 * @author dingje <dingje.gm@gmail.com>
 * @date Thu Mar  8 20:07:09 2012
 */
package org.jevenus.som;

import java.util.List;

public class RDDTSloi extends Sloi {

  public RDDTSloi() {
    DTNeighborhood dtnb = new DTNeighborhood();
    nbsMap = dtnb.getDtNb();

    DBUtil dbu = new DBUtil();
    attrsValMap = dbu.getAttrVal();

    Attribute attr = new Attribute();
    List<Integer> maxListVal = dbu.getMaxList();
    List<Integer> minListVal = dbu.getMinList();
    attrsMap = attr.getNormalizedAttrs(attrsValMap, maxListVal, minListVal);
  }

  public static void main(String[] args) {
    RDDTSloi rddtsloi = new RDDTSloi();
    rddtsloi.runAlgo(rddtsloi.getSloi());
    // for (String cntyid : rddtsloi.getSloi().keySet()) {
      // System.out.println(cntyid + " = " + rddtsloi.getSloi().get(cntyid));
    // }
  }
}



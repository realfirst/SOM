/**
 * @file Sloi.java
 * @brief
 * @author dingje <dingje.gm@gmail.com>
 * @date Tue Mar 27 10:11:09 2012
 */
package org.jevenus.som;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Sloi extends Sof {

  protected Map<String, Double> avgModifiedAvgDistMap ;
  // private Map<String, Double> modifiedAvgDist;

  // avgModifiedAvgDistMap = null;
  // modifiedAvgDistMap = getModfiedAvgDist();
  
  public Map<String, Double> getAvgModfiedAvgDist() { // 计算avg(N(o))
    avgModifiedAvgDistMap = new HashMap<>();
    modifiedAvgDistMap = getModfiedAvgDist();
    
    double avgModifiedAvgDist;
    double curModifiedDist;
    for (String cntyidfp : nbsMap.keySet()) {
      double maxModifiedDist = 0.0;
      double sumModifiedAvgDist = 0.0;
      Set<String> nbsSet = nbsMap.get(cntyidfp);
      for (String nb : nbsSet) {
        curModifiedDist = modifiedAvgDistMap.get(nb);
        sumModifiedAvgDist += curModifiedDist;
        
        if (curModifiedDist > maxModifiedDist) {
          maxModifiedDist = curModifiedDist;
        }
      }

      avgModifiedAvgDist = (sumModifiedAvgDist - maxModifiedDist) /
                           (nbsSet.size() -1);
      avgModifiedAvgDistMap.put(cntyidfp, avgModifiedAvgDist);
    }

    return avgModifiedAvgDistMap;
  }

  public Map<String, Double> getSloi() {
    Map<String, Double> sloiMap = new HashMap<>();

    Map<String, Double> minusMap = new HashMap<>();

    modifiedAvgDistMap = getModfiedAvgDist();
    avgModifiedAvgDistMap = getAvgModfiedAvgDist();

    double minus;
    double curModifiedAvgDist;
    double curAvgModifiedAvgDist;

    for (String cntyidfp : nbsMap.keySet()) {
      curModifiedAvgDist = modifiedAvgDistMap.get(cntyidfp);
      curAvgModifiedAvgDist = avgModifiedAvgDistMap.get(cntyidfp);
      minus = curModifiedAvgDist - curAvgModifiedAvgDist;
      minusMap.put(cntyidfp, minus);
    }

    double maxMinus = Collections.max(modifiedAvgDistMap.values());
    System.out.println("maxMinus" + maxMinus);
    double deta = Double.MIN_VALUE; // 1 / maxMinus;
    double sloi;
    
    for (String cntyidfp : nbsMap.keySet()) {
      double curMinus = minusMap.get(cntyidfp);
      Set<String> nbsSet = nbsMap.get(cntyidfp);
      double sumNbsMinus2 = 0.0;
      double maxNbsMinus2 = 0.0;
      for (String nb : nbsSet) {
        double curNbMinus2 = Math.pow(minusMap.get(nb), 2);
        sumNbsMinus2 += curNbMinus2;
        if (curNbMinus2 > maxNbsMinus2) {
          curNbMinus2 = maxNbsMinus2;
        }
      }
      double divider = Math.sqrt(sumNbsMinus2 - maxNbsMinus2) /
                       (nbsSet.size() - 1);
      sloi = (Math.abs(curMinus) + deta) / (divider + deta);
      sloiMap.put(cntyidfp, sloi);
    }

    return sloiMap;
  }

  
  
}

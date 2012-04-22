/**
 * @file Slom.java
 * @brief
 * @author dingje <dingje.gm@gmail.com>
 * @date Tue Apr  3 10:22:23 2012
 */
package org.jevenus.som;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Slom extends Sof {

  public Map<String, Double> getBeta() {
    Map<String, Double> betaMap = new HashMap<>();

    Map<String, Double> modifiedAvgDist = getModfiedAvgDist();

    Double beta = null;

    for (String nb : nbsMap.keySet()) {
      beta = 0.0;                       // step 1

      Set<String> nbsSetPlus = new HashSet<>(nbsMap.get(nb));
      nbsSetPlus.add(nb);

      // System.out.println(nb + " = " + nbsSetPlus);
      
      double modifiedAvgDistPlusSum = 0.0;
      for (String _nb : nbsSetPlus) {
        modifiedAvgDistPlusSum += modifiedAvgDist.get(_nb);
      }
      
      double avgModifiedAvgDistPlus = modifiedAvgDistPlusSum / nbsSetPlus.size();
      double currentModifiedAvgDist;
      for (String nbplus : nbsSetPlus) { // step 3
        currentModifiedAvgDist = modifiedAvgDist.get(nbplus);
        if (currentModifiedAvgDist > avgModifiedAvgDistPlus) {
          beta++;
        } else if (currentModifiedAvgDist < avgModifiedAvgDistPlus) {
          beta--;
        }
      }

      beta = Math.abs(beta);            // step 4
      if (nbsSetPlus.size() > 2) {
        beta = Math.max(beta, 1) / (nbsSetPlus.size() - 2); // step 5
      } else {
        beta = 1.0;
      }

      double avgModifiedAvgDistSum = 0.0;
      for (String nborig : nbsMap.get(nb)) {
        avgModifiedAvgDistSum += modifiedAvgDist.get(nborig);
      }
      double avgModifiedAvgDist = avgModifiedAvgDistSum / nbsMap.get(nb).size();

      beta = beta / (1 + avgModifiedAvgDist); // step 6
      betaMap.put(nb, beta);
    }
    
    return (betaMap);
  }
  
  public Map<String, Double> getSlom() {
    modifiedAvgDistMap = getModfiedAvgDist();
    Map<String, Double> betaMap = getBeta();

    Map<String, Double> slomMap = new HashMap<>();
    for (String cntyidfp : modifiedAvgDistMap.keySet()) {
      double lof = modifiedAvgDistMap.get(cntyidfp) * betaMap.get(cntyidfp);
      // double lof = modifiedAvgDistMap.get(cntyidfp);
      slomMap.put(cntyidfp, lof);
    }

    return (slomMap);
  }

}

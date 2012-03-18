/**
 * @file Slof.java
 * @brief
 * @author dingje <dingje.gm@gmail.com>
 * @date Sun Feb 19 14:05:16 2012
 */

package org.jevenus.som;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Slof extends Slom {

    protected double deta;

    public Map<String, Double> getSlof() {
        Map<String, Double> slofMap = new HashMap<>();

        Map<String, Double> modifiedAvgDistMap = getModfiedAvgDist();
        for (String cntyidfp : nbsMap.keySet()) {
            double slof = 0.0;
            double currentModifiedAvgDist = modifiedAvgDistMap.get(cntyidfp);
            Set<String> nbSet = nbsMap.get(cntyidfp);
            double sumNbDist = 0.0;
            for (String nb : nbSet) {
                sumNbDist += modifiedAvgDistMap.get(nb);
            }
            double avgNbDist = sumNbDist / nbSet.size();
            slof = (currentModifiedAvgDist + deta) / (avgNbDist + deta);
            slofMap.put(cntyidfp, slof);
        }

        return (slofMap);
    }
}

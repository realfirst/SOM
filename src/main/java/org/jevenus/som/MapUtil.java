/**
 * @file MapUtil.java
 * @brief
 * @author dingje <dingje.gm@gmail.com>
 * @date Mon Feb 13 16:32:26 2012
 */

package org.jevenus.som;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

public class MapUtil {
  public static HashMap<String, Double>
      sortHashMap(Map<String, Double> input) {
    Map<String, Double> tempMap = new HashMap<>();
    for (String key : input.keySet()) {
      tempMap.put(key, input.get(key));
    }

    List<String> mapKeys = new ArrayList<>(tempMap.keySet());
    List<Double> mapValues = new ArrayList<>(tempMap.values());
    HashMap<String, Double> sortedMap = new LinkedHashMap<>();
    TreeSet<Double> sortedSet = new TreeSet<>(mapValues);
    Object[] sortedArray = sortedSet.toArray();
    int size = sortedArray.length;
    for (int i = 0; i < size; i++) {
      sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])),
                    (Double) sortedArray[i]);
    }

    return (sortedMap);
  }


  public static List<Entry<String, Double>> sortMapByValue(
      Map<String, Double> inputMap) {
    List<Entry<String, Double>> arrayList = new ArrayList<Entry<String, Double>>(
        inputMap.entrySet());
    Collections.sort(arrayList, new Comparator<Entry<String, Double>>() {
        public int compare(Entry<String, Double> e1,
                           Entry<String, Double> e2) {
          return (e2.getValue()).compareTo(e1.getValue());
        }
      });
    // for (Entry<String, Double> entry : arrayList) {
      // System.out.println(entry.getKey() + "  " + entry.getValue());
    // }
    return arrayList;
  }

}

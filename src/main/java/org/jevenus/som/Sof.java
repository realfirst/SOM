/**
 * @file Slom.java
 * @brief implement of slom spatial outlier algorithm
 * @author dingje <dingje.gm@gmail.com>
 * @date Sat Feb 11 15:17:19 2012
 */

package org.jevenus.som;

import java.util.Map.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Sof {

  protected Map<String, Set<String>> nbsMap;
  protected Map<String, List<Double>> attrsMap;
  protected Map<String, List<String>> attrsValMap;
  protected Map<String, Double> modifiedAvgDistMap;
  
  /**
   * 计算两个非空间属性向量间的欧氏距离
   *
   * @return a <code>double</code> value
   */
  public double getAttrsDist(List<Double> la, List<Double> lb) { 
                                                               
    double sum = 0.0;
    if (la.size() != lb.size()) {
      return (sum);
    } else {
      for (int i = 0; i < la.size(); i++) {
        sum += Math.pow((la.get(i) - lb.get(i)), 2);
      }
    }
    // System.out.println(Math.sqrt(sum));
    return (Math.sqrt(sum));
  }

  public Map<String, Double> getModfiedAvgDist() {
    modifiedAvgDistMap = new HashMap<>();

    for (String cntyidfp : nbsMap.keySet()) { // 3136条记录，有3条记录(1500{1,3,7})的邻居集合是空
      double maxDist = 0.0;
      double distSum = 0.0;
      List<Double> la = attrsMap.get(cntyidfp);
      Set<String> nbsSet = nbsMap.get(cntyidfp);
      for (String nb : nbsSet) {
        List<Double> lb = attrsMap.get(nb);
        double pairVal = getAttrsDist(la, lb);
        if (pairVal > maxDist) {
          maxDist = pairVal;
        }
        distSum += pairVal;
      }

      double avgDist;
      if (nbsSet.size() > 1) {
        avgDist = (distSum - maxDist) / (nbsSet.size() - 1);
      } else {
        avgDist = 0;
      }
      modifiedAvgDistMap.put(cntyidfp, avgDist);
    }
    return (modifiedAvgDistMap);
  }


  public void runAlgo(Map<String, Double> inputMap) {
    List<Entry<String, Double>> resultList = MapUtil.sortMapByValue(inputMap);
    int cnt = 0;
    System.out.println("cntyidfp\tlof\t\tattrsVal");
    for (Entry<String, Double> entry : resultList) {
      cnt++;
      if (cnt > 10) {
        break ;
      }
      System.out.println("----------------------------------------------------------------------" + cnt);

      String cntyid = entry.getKey();
      // System.out.println(cntyid + "\t" + entry.getValue() + "\t" + attrsMap.get(cntyid));
      System.out.printf("%s\t%.4f\t", cntyid, entry.getValue());
      printList(attrsMap.get(cntyid));
      
      Set<String> nbsSet = nbsMap.get(cntyid);
      System.out.println("nbs");
      for (String nb : nbsSet) {
        // System.out.println(nb + "\t" + inputMap.get(nb) + "\t" +
        // attrsMap.get(nb));
        System.out.printf("%s\t%.4f\t", nb, inputMap.get(nb));
        printList(attrsMap.get(nb));
      }
    }
    System.out.println("----------------------------------------------------------------------");
  }

  private void printList(List<Double> list) {

    System.out.print("[");
    for (Double propVal : list) {
      System.out.printf("%.4f, ", propVal);
    }
    System.out.println("]");
  }
  // public static void main(String[] args) {
  // List<Double> la = Arrays.asList(1.0f, 2.0f);
  // List<Double> lb = Arrays.asList(2.0f, 3.0f);
  // Slom slom = new Slom();
  // System.out.println(slom.getModfiedAvgDist());
  // MapUtil.sortMapByValue(slom.getSlom(), slom.nbsMap, slom.matrixMap);
  // for (String cntyidfp : result.keySet()) {
  // System.out.println(cntyidfp + " = " + result.get(cntyidfp));
  // }
  // System.out.println(result);
  // System.out.println(slom.nbsMap);
  // System.out.println(slom.getModfiedAvgDist());
  // System.out.println(slom.getAttrsDist(la, lb));
  // System.out.println(slom.getAttrsDist());
  // }
}

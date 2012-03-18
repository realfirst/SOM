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

public abstract class Slom {

  protected Map<String, Set<String>> nbsMap;
  protected Map<String, List<Double>> attrsMap;
  protected Map<String, List<String>> attrsValMap;
  /*
  // for real dataset
  public Slom() {
  DBUtil dbu = new DBUtil();
  dbu.pareData(dbu.getQueriedAttrs());
  Map<String, List<String>> attrsValMap = dbu.getAttrVal();
  // nbsMap = dbu.getNbs();
  DTNeighborhood dtnb = new DTNeighborhood();
  nbsMap = dtnb.getDtNb();
  Attribute attr = new Attribute();
  List<Integer> maxListVal = dbu.getMaxList();
  List<Integer> minListVal = dbu.getMinList();
  attrsMap = attr.getNormalizedAttrs(attrsValMap, maxListVal, minListVal);
  }
  */
  
  // public Slom() {
  // SDUtil sdu = new SDUtil();
  // SDDTNeighborhood sddt = new SDDTNeighborhood(); // 合成数据德劳内三角邻居
    

  // nbsMap = sdu.getNbsMap();
  // nbsMap = sddt.getSDDtNb();

  // matrixMap = sdu.getMatrix();
  // attrsMap = sdu.getNormalizedAttrsValue();
  // }
  
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
    Map<String, Double> modifiedAvgDist = new HashMap<>();

    for (String cntyidfp : nbsMap.keySet()) { // 3136条记录，有3条记录(1500{1,3,7})的邻居集合是空
      double maxDist = 0.0;
      double distSum = 0.0;
      List<Double> la = attrsMap.get(cntyidfp);
      Set<String> nbSet = nbsMap.get(cntyidfp);
      for (String _nbSet : nbSet) {
        List<Double> lb = attrsMap.get(_nbSet);
        double pairVal = getAttrsDist(la, lb);
        if (pairVal > maxDist) {
          maxDist = pairVal;
        }
        distSum += pairVal;
      }

      double avgDist;
      if (nbSet.size() > 1) {
        avgDist = (distSum - maxDist) / (nbSet.size() - 1);
      } else {
        avgDist = 0;
      }
      modifiedAvgDist.put(cntyidfp, avgDist);
    }
    return (modifiedAvgDist);
  }

  public Map<String, Double> getBeta() {
    Map<String, Double> betaMap = new HashMap<>();

    Map<String, Double> modifiedAvgDist = getModfiedAvgDist();

    Double beta = null;

    for (String nb : nbsMap.keySet()) {
      beta = 0.0;                       // step 1

      Set<String> nbsSetPlus = nbsMap.get(nb);
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
    Map<String, Double> modifiedAvgDistMap = getModfiedAvgDist();
    Map<String, Double> betaMap = getBeta();

    Map<String, Double> slomMap = new HashMap<>();
    for (String cntyidfp : modifiedAvgDistMap.keySet()) {
      double lof = modifiedAvgDistMap.get(cntyidfp) * betaMap.get(cntyidfp);
      // double lof = modifiedAvgDistMap.get(cntyidfp);
      slomMap.put(cntyidfp, lof);
    }

    return (slomMap);
  }

  public void runAlgo(Map<String, Double> inputMap) {
    List<Entry<String, Double>> resultList = MapUtil.sortMapByValue(inputMap);
    int cnt = 0;
    System.out.println("cntyidfp\tlof");
    for (Entry<String, Double> entry : resultList) {
      cnt++;
      if (cnt > 10) {
        break ;
      }
      System.out.println("----------------------------------------------------------------------" + cnt);

      String cntyid = entry.getKey();
      System.out.println(cntyid + "\t" + entry.getValue());
    

      Set<String> nbsSet = nbsMap.get(cntyid);
      System.out.println("nbs\t" + nbsSet );
      System.out.print("val\t");
      for (String nb : nbsSet) {
        System.out.print(attrsValMap.get(nb) + "\t");
      }
      System.out.println();
    }
    System.out.println("----------------------------------------------------------------------");
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

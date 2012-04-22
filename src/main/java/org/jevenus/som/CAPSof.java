/**
 * @file CAPSof.java
 * @brief
 * @author dingje <dingje.gm@gmail.com>
 * @date Tue Apr  3 10:33:03 2012
 */
package org.jevenus.som;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CAPSof extends Slof {

  public CAPSof() {
    DTNeighborhood dtnb = new DTNeighborhood();
    nbsMap = dtnb.getDtNb();

    DBUtil dbu = new DBUtil();
    attrsValMap = dbu.getAttrVal();

    attrsMap = getNormalizedCapVal();
    deta = Double.MIN_VALUE;
  }

  // 将数据库中提取的属性字符转换为cap数值
  public Map<String, List<Double>> getCapVal() {
    Map<String, List<Double>> capValMap = new HashMap<>();
    List<Double> capValList = null;
    
    for (String cntyidfp : attrsValMap.keySet()) {
      List<String> attrValList = attrsValMap.get(cntyidfp);
      capValList = new ArrayList<>();
      
      double oneRace = valueOf(attrValList.get(0));
      double white = valueOf(attrValList.get(1));
      double baa = valueOf(attrValList.get(2));
      double aian = valueOf(attrValList.get(3));
      double asian = valueOf(attrValList.get(4));
      double ai = valueOf(attrValList.get(5));
      double chinese = valueOf(attrValList.get(6));
      double filipino = valueOf(attrValList.get(7));
      double japanese = valueOf(attrValList.get(8));
      double korean = valueOf(attrValList.get(9));
      double vietnamese = valueOf(attrValList.get(10));
      double otherAsian = valueOf(attrValList.get(11));
      double nhopi = valueOf(attrValList.get(12));
      double sor = valueOf(attrValList.get(13));

      capValList.add(white / oneRace);
      capValList.add(baa / oneRace);
      capValList.add(aian / oneRace);
      capValList.add(asian / oneRace);
      
      // if (asian == 0) {
        // for (int i = 0; i < 7; i++) {
          // capValList.add(0.0);
        // }
      // } else {
        // capValList.add(ai / asian);
        // capValList.add(chinese / asian);
        // capValList.add(filipino / asian);
        // capValList.add(japanese / asian);
        // capValList.add(korean / asian);
        // capValList.add(vietnamese / asian);
        // capValList.add(otherAsian / asian);
      // }
      capValList.add(nhopi / oneRace);
      capValList.add(sor / oneRace);
      
      capValMap.put(cntyidfp, capValList);
    }
    return (capValMap);
  }

  // 取每一维属性cap的最大值
  public List<Double> getMaxCapValList() {
    Map<String, List<Double>> capValMap = getCapVal();
    Double[] initCapVals = new Double[13];

    for (int i = 0; i < initCapVals.length; i++) {
      initCapVals[i] = Double.MIN_VALUE;
    }
    
    List<Double> maxCapValList = new ArrayList<>(Arrays.asList(initCapVals));

    List<Double> curCapValList = null;

    for (String cntyidfp : capValMap.keySet()) {
      curCapValList = capValMap.get(cntyidfp);
      for (int i = 0; i < curCapValList.size(); i++) {
        if (curCapValList.get(i) > maxCapValList.get(i)) {
          maxCapValList.set(i, curCapValList.get(i));
        }
      }
    }
    return maxCapValList;
  }

  // 取每一维属性cap的最小值
  public List<Double> getMinCapValList() {
    Map<String, List<Double>> capValMap = getCapVal();
    Double[] initCapVals = new Double[13];

    for (int i = 0; i < initCapVals.length; i++) {
      initCapVals[i] = Double.MAX_VALUE;
    }

    List<Double> minCapValList = new ArrayList<>(Arrays.asList(initCapVals));

    List<Double> curCapValList = null;

    for (String cntyidfp : capValMap.keySet()) {
      curCapValList = capValMap.get(cntyidfp);
      for (int i = 0; i < curCapValList.size(); i++) {
        if (curCapValList.get(i) < minCapValList.get(i)) {
          minCapValList.set(i, curCapValList.get(i));
        }
      }
    }

    return (minCapValList);
  }

  // 对每个对象的cap值进行规范化处理
  public Map<String, List<Double>> getNormalizedCapVal() {
    Map<String, List<Double>> normalizedCapValMap = new HashMap<>();

    Map<String, List<Double>> capValMap = getCapVal();
    List<Double> maxCapValList = getMaxCapValList();
    List<Double> minCapValList = getMinCapValList();
    List<Double> curCapValList = null;

    for (String cntyidfp : capValMap.keySet()) {
      curCapValList = capValMap.get(cntyidfp);
      for (int i = 0; i < curCapValList.size(); i++) {
        double maxCapVal = maxCapValList.get(i);
        double minCapVal = minCapValList.get(i);
        double curCapVal = curCapValList.get(i);
        double normalizedCapVal = (curCapVal - minCapVal) / (maxCapVal - minCapVal);
        curCapValList.set(i, normalizedCapVal);
      }
      normalizedCapValMap.put(cntyidfp, curCapValList);
    }

    return (normalizedCapValMap);
  }

  // 计算cap-dist
  
  private static double valueOf(String name) {
    return (Double.valueOf(name));
  }

  public static void main(String[] args) {
    CAPSof capsof = new CAPSof();
    // Map<String, List<Double>> testMap = capsof.getCapVal();
    // for (String cntyidfp : testMap.keySet()) {
      // System.out.println(cntyidfp + ": " + testMap.get(cntyidfp));
    // }
    capsof.runAlgo(capsof.getSlof());
  }
}

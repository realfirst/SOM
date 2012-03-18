/**
 * @file Attribute.java
 * @brief
 * @author dingje <dingje.gm@gmail.com>
 * @date Sun Feb 12 10:36:37 2012
 */

package org.jevenus.som;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attribute {

  public Attribute() {}

  public Map<String, List<Double>> getNormalizedAttrs(Map<String, List<String>> attrsMap,
                                                     List<Integer> maxValList,
                                                     List<Integer> minValList) {
    Map<String, List<Double>> normalizedAttrsMap = new HashMap<>();

    List<String> attrsList = null;
    for (String cntyidfp : attrsMap.keySet()) {
      attrsList = attrsMap.get(cntyidfp); // 获取一个空间对象的一组非空间属性值的字符串表示
      // System.out.println(cntyidfp);
      // System.out.println(attrsList);

      double attrVal;
      double divisor;
      List<Double> attrsListValue = new ArrayList<>();
      for (int i = 0; i < attrsList.size(); i++) {
        divisor = maxValList.get(i) - minValList.get(i);
        attrVal = (Double.valueOf(attrsList.get(i)) - minValList.get(i)) / divisor;
        attrsListValue.add(attrVal);
      }
      normalizedAttrsMap.put(cntyidfp, attrsListValue);
    }
    return (normalizedAttrsMap);
  }

  public static void main(String[] args) {
    Attribute att = new Attribute();
    // System.out.println(att.maxValList);
    DBUtil dbu = new DBUtil();
    dbu.getQueriedAttrs();
    Map<String, List<String>> attrsMap = dbu.getAttrVal();
    List<Integer> maxList = dbu.getMaxList();
    List<Integer> minList = dbu.getMinList();
    System.out.println(maxList);
    System.out.println(minList);
    System.out.println(att.getNormalizedAttrs(attrsMap, maxList, minList));
  }
  
}

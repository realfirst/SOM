/**
 * @file SDUtil.java
 * @brief
 * @author dingje <dingje.gm@gmail.com>
 * @date Tue Feb 21 14:15:55 2012
 */

package org.jevenus.som;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SDUtil {
    private Map<String, Set<String>> nbsMap;
    private Map<String, List<Double>> attsMap;
    private Map<String, List<String>> matrix; // attsValMap in slom

    public SDUtil() {
        nbsMap = null;
        attsMap = null;
    }

    public Map<String, List<String>> getMatrix() { // 从文件中读取输入数据，并按(key = 坐标，value = 数值字符串)的形式保存到Map数据结构中
        String line = null;
        Map<String, List<String>> matrix = new LinkedHashMap<>();
        String location = null;
        try {
            BufferedReader br =
                new BufferedReader(new FileReader("synthentic-dataset.in"));

            int i = 0;
            List<String> itemList = null;
            while ((line = br.readLine()) != null) {
                int j = 0;
                for (String item : line.split(" ")) {
                    location = "" + i + j;
          
                    itemList = new ArrayList<>();
                    itemList.add(item);
          
                    matrix.put(location, itemList);
                    j++;
                }
                i++;
            }

        } catch (IOException ioe) {
            System.err.println("IOException err");
        }

        return matrix;
    }
  
    public Map<String, Set<String>> getNbsMap() {
        nbsMap = new LinkedHashMap<>();
        Map<String, List<String>> matrix = getMatrix();
        String location = null;

        // System.out.println(matrix.keySet());
        for (String item : matrix.keySet()) {
            Set<String> nbsSet = new TreeSet<>();
            String row = item.substring(0,1);
            // System.out.println(row);
            String column = item.substring(1);
            // System.out.println(column);
            int irow = Integer.valueOf(row);
            int icolumn = Integer.valueOf(column);
            for (int i = irow-1; i <= irow+1; i++) {
                for (int j = icolumn-1; j <= icolumn+1; j++) {
                    location = "" + i + j;
                    if (!location.equals(item) && matrix.get(location) != null) {
                        nbsSet.add(location);
                    }
                }
            }
            // System.out.println(nbsSet);
            nbsMap.put(item, nbsSet);
        }

        return (nbsMap);
    }

    public Map<String, Set<String>> getNbsMap2() {
        nbsMap = new LinkedHashMap<>();
        Map<String, List<String>> matrix = getMatrix();
        String location = null;

        // System.out.println(matrix.keySet());
        for (String item : matrix.keySet()) {
            Set<String> nbsSet = new TreeSet<>();
            String row = item.substring(0,1);
            // System.out.println(row);
            String column = item.substring(1);
            // System.out.println(column);
            int irow = Integer.valueOf(row);
            int icolumn = Integer.valueOf(column);
            for (int i = irow-1; i <= irow+1; i++) {
                for (int j = icolumn-1; j <= icolumn+1; j++) {
                    if (i == irow-1 || i == irow+1) {
                        location = "" + i + icolumn;
                    } else if (i == irow && (j != icolumn)) {
                        location = "" + i + j;
                    }

                    if (matrix.get(location) != null) {
                        nbsSet.add(location);
                    }
                }
            }
            nbsMap.put(item, nbsSet);
        }

        return (nbsMap);
    }

    public Map<String, List<Double>> getNormalizedAttrsValue() {
        Map<String, List<Double>> normalizedAttrsValMap = new TreeMap<>();
        int min = 1000;
        int max = -1000;
        int value;
    
        Map<String, List<String>> matrix = getMatrix();
        // System.out.println(matrix);

        // get max and min
        String valStr = null;
        for (String item : matrix.keySet()) {
            valStr = matrix.get(item).get(0);
            if (valStr.startsWith("−")) {
                valStr = "-" + valStr.substring(1);
            }

            value = Integer.parseInt(valStr);
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }

        int deta = max - min;
        Double normolizedVal = null;
        List<Double> valList = null;
        for (String  item : matrix.keySet()) {
            valList = new ArrayList<>();
            valStr = matrix.get(item).get(0);
            if (valStr.startsWith("−")) {
                valStr = "-" + valStr.substring(1);
            }

            value = Integer.parseInt(valStr);
            normolizedVal = (double)(value - min) / deta;
            valList.add(normolizedVal);
            normalizedAttrsValMap.put(item, valList);
        }
        // System.out.println(min +" " + max);
    
        return (normalizedAttrsValMap);
    }

    public Map<String, Pair> getLocation() {
        Map<String, Pair> locMap = new TreeMap<>();

        Map<String, List<String>> matrix = getMatrix();

        double xPos = 0.0;
        double yPos = 0.0;
        for (String loc : matrix.keySet()) {
            Pair locPair = new Pair();
            xPos = Double.valueOf(loc.substring(0,1));
            locPair.setX(xPos);
            yPos = Double.valueOf(loc.substring(1));
            locPair.setY(yPos);
            locMap.put(loc, locPair);
        }
   
        return (locMap);
    }

    class Pair {
        private double x;
        private double y;

        public Pair() {
            this.x = 0.0;
            this.y = 0.0;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return (x);
        }

        public double getY() {
            return (y);
        }
    
        @Override
        public String toString() {
            return ("(" + x + "," + y +")");
        }
    }
    
    public static void main(String[] args) {
        SDUtil sd = new SDUtil();
        int i = 0;
        // for (String _item : sd.getNbsMap().keySet()) {
        // if (i % 10 == 0) {
        // System.out.println();
        // }
        // System.out.println(_item + " = " + sd.getNbsMap().get(_item));
        // i++;
        // }
        // System.out.println(sd.getMatrix());

        System.out.println(sd.getNbsMap2());
        // sd.getNbsMap();
        sd.getNormalizedAttrsValue();
        System.out.println(sd.getNormalizedAttrsValue());
        System.out.println(sd.getLocation());
    }
}

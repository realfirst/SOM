/**
 * @file DTNeighborhood.java
 * @brief return dt neighorhood Map<String, Set<String>>
 * @author dingje <dingje.gm@gmail.com>
 * @date Tue Feb 14 16:23:29 2012
 */

package org.jevenus.som;

import org.jevenus.som.SDUtil.Pair;
import com.vividsolutions.jts.geom.Coordinate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.geotools.graph.structure.Graph;
import org.geotools.graph.util.delaunay.DelaunayNode;
import org.geotools.graph.util.delaunay.DelaunayTriangulator;

public class SDDTNeighborhood {

  private Map<DelaunayNode, String> dtNodeCntyidMap;
  SDDTNeighborhood(){
    dtNodeCntyidMap = null;
  }

  private DelaunayNode[] initDelaunayNode() {

    SDUtil sdu = new SDUtil();
    int i = 0;
    Map<String, Pair> cntyLocationMap = sdu.getLocation();
    
    int len = cntyLocationMap.size();
    DelaunayNode[] dNArr = new DelaunayNode[len];
    
    // Map<String, DelaunayNode> cntyDtNodeMap = null;
    dtNodeCntyidMap = new HashMap<>();
    for (String point : cntyLocationMap.keySet()) {
      Coordinate coord = new Coordinate(cntyLocationMap.get(point).getX() + 0.5, cntyLocationMap.get(point).getY() + 0.5);
      DelaunayNode dn = new DelaunayNode();
      dn.setCoordinate(coord);

      dNArr[i++] = dn;
      dtNodeCntyidMap.put(dn, point);
    }

    return (dNArr);
  }

  public Map<String, Set<String>> getSDDtNb() {
    Map<DelaunayNode, String> reverseMap = null;
    Map<String, Set<String>> cnty2cntysMap = new TreeMap<>();

    DelaunayTriangulator dt = new DelaunayTriangulator();
    dt.setNodeArray(initDelaunayNode());
    Graph g = dt.getTriangulation();
    DelaunayNode[] dn = dt.getNodeArray();
    reverseMap = dtNodeCntyidMap;       // 只有等到 initDelaunayNode方法执行后再给 reverseMap 进行初始化
    
    Set<String> valSet = null;
    Iterator<DelaunayNode> it = null;
    for (int i = 0; i < dn.length; i++) {
      String key = reverseMap.get(dn[i]);
      // System.out.println(dn[i]+ "key");
      it = dn[i].getRelated();
      valSet = new HashSet<>();
      while (it.hasNext()) {
        valSet.add(reverseMap.get(it.next()));
        // System.out.println(it.next());
      }
      cnty2cntysMap.put(key, valSet);
      // System.out.println("********************************************************************" + i);
    }

    for (String key : cnty2cntysMap.keySet()) {
      System.out.println(key + " = " + cnty2cntysMap.get(key));
    }
    // System.out.println(dn);
    // System.out.println(g.getNodes());
    return (cnty2cntysMap);
  }
  public static void main(String[] args) {
    // new DTNeighborhood().initDelaunayNode();
    new SDDTNeighborhood().getSDDtNb();
    
  }
  
}

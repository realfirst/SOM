/**
 * @file DTNeighborhood.java
 * @brief return dt neighorhood Map<String, Set<String>>
 * @author dingje <dingje.gm@gmail.com>
 * @date Tue Feb 14 16:23:29 2012
 */

package org.jevenus.som;

import org.jevenus.som.DBUtil.Pair;
import com.vividsolutions.jts.geom.Coordinate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.geotools.graph.structure.Graph;
import org.geotools.graph.util.delaunay.DelaunayNode;
import org.geotools.graph.util.delaunay.DelaunayTriangulator;

public class DTNeighborhood {

    private Map<DelaunayNode, String> dtNodeCntyidMap;
  
    DTNeighborhood(){
        dtNodeCntyidMap = null;
    }

    private DelaunayNode[] initDelaunayNode() {

        DBUtil dbu = new DBUtil();
        int i = 0;
    
        Map<String, Pair> cntyLocationMap = dbu.getLocation();
    
        int len = cntyLocationMap.size();
        DelaunayNode[] dNArr = new DelaunayNode[len];
    
        // Map<String, DelaunayNode> cntyDtNodeMap = null;
        dtNodeCntyidMap = new HashMap<>();
        for (String point : cntyLocationMap.keySet()) {
            System.out.println(point);
            Coordinate coord = new Coordinate(cntyLocationMap.get(point).getX(), cntyLocationMap.get(point).getY());
            DelaunayNode dn = new DelaunayNode();
            dn.setCoordinate(coord);
            // System.out.println(dn);
            dNArr[i++] = dn;
            dtNodeCntyidMap.put(dn, point);
        }
        // System.out.println(cntyLocationMap);
        // System.out.println(dtNodeCntyidMap);
        // System.out.println(dNArr);
        return (dNArr);
    }

    public Map<String, Set<String>> getDtNb() {
        Map<DelaunayNode, String> reverseMap = null;
        Map<String, Set<String>> cnty2cntysMap = new HashMap<>();

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

        // for (String key : cnty2cntysMap.keySet()) {
        // System.out.println(key + " = " + cnty2cntysMap.get(key));
        // }
        // System.out.println(dn);
        // System.out.println(g.getNodes());
        return (cnty2cntysMap);
    }
  
    public static void main(String[] args) {
        // new DTNeighborhood().initDelaunayNode();
        new DTNeighborhood().getDtNb();
    
    }
}

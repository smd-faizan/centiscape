/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cytoscape.centiscape.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JPanel;
import org.cytoscape.centiscape.internal.Betweenness.BetweennessMethods;
import org.cytoscape.centiscape.internal.Betweenness.EdgeBetweenness;
import org.cytoscape.centiscape.internal.Betweenness.FinalResultBetweenness;
import org.cytoscape.centiscape.internal.Centroid.CentroidMethods;
import org.cytoscape.centiscape.internal.Centroid.FinalResultCentroid;
import org.cytoscape.centiscape.internal.Closeness.DirectedCloseness;
import org.cytoscape.centiscape.internal.Eccentricity.DirectedEccentricity;
import org.cytoscape.centiscape.internal.EigenVector.CalculateEigenVector;
import org.cytoscape.centiscape.internal.Radiality.DirectedRadiality;
import org.cytoscape.centiscape.internal.visualizer.ImplCentrality;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;

/**
 *
 * @author faizi
 */
public class CentiScaPeDirectedAlgorithm {

    public CyNetwork network;
    public boolean[] checkedCentralities;
    public String[] directedCentralities;
    public JPanel menu;
    public static boolean stop;
    public boolean openResultsPanel;
    public boolean displayBetweennessResults = false;
    public static TreeMap Stressmap = new TreeMap();
    public double Diameter = 0.0;
    public double totalDist;
    //dataStructures
    public Map<CyNode, Vector> singleShortestPaths;
    public Map<CyNode, Double> directedOutDegreeValues;
    public Map<CyNode, Double> directedInDegreeValues;
    public Map<CyNode, Double> directedClosenessValues;
    public Map<CyNode, Double> directedEccentrityValues;
    public Map<CyNode, Double> directedRadialityValues;
    public Map<CyNode, Double> directedStressValues;
    public Map<CyNode, Double> directedBridgingValues;
    public EdgeBetweenness edgeBetweenness;
    private static Vector BetweennessVectorResults;
    private static Vector CentroidVectorResults;
    private static Vector CentroidVectorofNodes;
    public double[][] adjacencyMatrixOfNetwork;
    public CentiScaPeCore centiscapecore;
    public Vector vectorResults = new Vector();
    public Boolean isWeighted;

    public static void stopAlgo() {
        stop = true;
    }

    public void executeCentralities(CyNetwork n, boolean[] c, String[] d, JPanel panel, CentiScaPeCore core) {
        stop = false;
        openResultsPanel = false;
        network = n;
        checkedCentralities = c;
        directedCentralities = d;
        CentiScaPeStartMenu menu = (CentiScaPeStartMenu) panel;
        isWeighted = menu.isWeighted;
        this.centiscapecore = core;
        System.out.println("Entered Directed algo");
        String networkname = network.getDefaultNetworkTable().getRow(network.getSUID()).get("name", String.class);
        int totalnodecount = network.getNodeCount();
        int totalEdgeCount = network.getEdgeCount();
        Long networksuid = network.getSUID();
        int nodeworked = 0;
        CyTable nodeTable = network.getDefaultNodeTable();
        CyTable networkTable = network.getDefaultNetworkTable();
        List<CyNode> nodeList = network.getNodeList();
        Vector CentiScaPeMultiShortestPathVector = null;
        if (checkedCentralities[7]) {
            displayBetweennessResults = true;
        } else {
            displayBetweennessResults = false;
        }
        if (checkedCentralities[10] && !checkedCentralities[7]) {
            checkedCentralities[7] = true;
        }
        //instantiate data stuctures
        if (checkedCentralities[1]) {
            totalDist = 0.0;
        }
        if (checkedCentralities[2]) {
            //Degree checkbox is selected
            directedOutDegreeValues = new HashMap<CyNode, Double>(totalnodecount);
            directedInDegreeValues = new HashMap<CyNode, Double>(totalnodecount);
            //openResultsPanel = false;
        }
        if (checkedCentralities[3]) {
            directedEccentrityValues = new HashMap<CyNode, Double>(totalnodecount);
        }
        if (checkedCentralities[4]) {
            directedRadialityValues = new HashMap<CyNode, Double>(totalnodecount);
        }
        if (checkedCentralities[5]) {
            directedClosenessValues = new HashMap<CyNode, Double>(totalnodecount);
        }
        if (checkedCentralities[6]) {
            directedStressValues = new HashMap<CyNode, Double>(totalnodecount);
        }
        if (checkedCentralities[7]) {
            BetweennessVectorResults = new Vector();
            for (Iterator i = nodeList.iterator(); i.hasNext();) {
                if (stop) {
                    return;
                }
                CyNode root = (CyNode) i.next();
                BetweennessVectorResults.add(new FinalResultBetweenness(root, 0));
            }
        }
        if (checkedCentralities[8]) {
            CentroidVectorResults = new Vector();
            CentroidVectorofNodes = new Vector();
            for (Iterator i = nodeList.iterator(); i.hasNext();) {
                if (stop) {
                    return;
                }
                CyNode root = (CyNode) i.next();
                CentroidVectorofNodes.addElement(root.getSUID());
            }
        }
        if (checkedCentralities[9]) {
            adjacencyMatrixOfNetwork = new double[totalnodecount][totalnodecount];
        }
        if (checkedCentralities[10]) {
            directedBridgingValues = new HashMap<CyNode, Double>(totalnodecount);
        }
        if (checkedCentralities[11]) {
            edgeBetweenness = new EdgeBetweenness(network);
        }
        // checked centralities :-
            /*
         * CheckedCentralities[0] = DiameterCheckBox.isSelected();
         * CheckedCentralities[1] = AverageDistanceCheckBox.isSelected();
         * CheckedCentralities[2] = DegreeCheckBox.isSelected();
         * CheckedCentralities[3] = EccentricityCheckBox.isSelected();
         * CheckedCentralities[4] = RadialityCheckBox.isSelected();
         * CheckedCentralities[5] = ClosenessCheckBox.isSelected();
         * CheckedCentralities[6] = StressCheckBox.isSelected();
         * CheckedCentralities[7] = BetweennessCheckBox.isSelected();
         * CheckedCentralities[8] = CentroidCheckBox.isSelected();
         * CheckedCentralities[9] = EigenVectorCheckBox.isSelected();
         */
        boolean StressisOn = true;
        boolean BetweennessisOn = true;
        Stressmap = new TreeMap();
        Stressmap.clear();
        for (CyNode root : nodeList) {
            Stressmap.put(root.getSUID(), new Double(0));
        }
        TreeMap inizializedmap = new TreeMap(Stressmap);
        int k = 0;
        singleShortestPaths = new HashMap<CyNode, Vector>();
        for (CyNode root : nodeList) {
            if (stop) {
                return;
            }
            CentiScaPeMultiShortestPathVector = new Vector();
            CentiScaPeMultiShortestPathVector = CentiScaPeMultiShortestPathTreeAlgorithm.ExecuteMultiShortestPathTreeAlgorithm(network,
                    root, StressisOn, BetweennessisOn, inizializedmap, true, isWeighted);
            Vector CentiScaPeSingleShortestPathVector = new Vector();
            Vector NodesFound = new Vector();
            CentiScaPeShortestPathList CurrentList;
            for (int j = 0; j < CentiScaPeMultiShortestPathVector.size(); j++) {
                CurrentList = (CentiScaPeShortestPathList) CentiScaPeMultiShortestPathVector.get(j);
                String nodename = ((CentiScaPeMultiSPath) CurrentList.getLast()).getName();
                if (!NodesFound.contains(nodename)) {
                    NodesFound.add(nodename);
                    CentiScaPeSingleShortestPathVector.add(CurrentList);
                }
            }
            double currentdiametervalue;
            currentdiametervalue = CalculateDiameter(CentiScaPeSingleShortestPathVector);
            if (Diameter < currentdiametervalue) {
                Diameter = currentdiametervalue;
            }
            if (checkedCentralities[7]) {
                BetweennessMethods.updateBetweenness(CentiScaPeMultiShortestPathVector, BetweennessVectorResults);
            }
            if (checkedCentralities[11]) {
                edgeBetweenness.updateEdgeBetweenness(CentiScaPeMultiShortestPathVector);
            }
            singleShortestPaths.put(root, CentiScaPeSingleShortestPathVector);
        }
        //execute each centrality
        for (Map.Entry<CyNode, Vector> element : singleShortestPaths.entrySet()) {
            if (stop) {
                return;
            }
            CyNode root = element.getKey();
            Vector CentiScaPeSingleShortestPathVector = element.getValue();
            if (checkedCentralities[1]) {
                CentiScaPeShortestPathList currentlist;
                for (int j = 0; j < CentiScaPeSingleShortestPathVector.size(); j++) {
                    currentlist = (CentiScaPeShortestPathList) CentiScaPeSingleShortestPathVector.elementAt(j);
                    totalDist = totalDist + ((CentiScaPeMultiSPath) currentlist.get(currentlist.size() - 1)).getCost();
                }
            }
            if (checkedCentralities[2]) {
                directedOutDegreeValues.put(root, (double) (network.getNeighborList(root, CyEdge.Type.OUTGOING).size()));
                directedInDegreeValues.put(root, (double) (network.getNeighborList(root, CyEdge.Type.INCOMING).size()));
            }
            if (checkedCentralities[3]) {
                directedEccentrityValues.put(root, DirectedEccentricity.execute(CentiScaPeSingleShortestPathVector));
            }
            if (checkedCentralities[4]) {
                directedRadialityValues.put(root, DirectedRadiality.execute(Diameter, totalnodecount, CentiScaPeSingleShortestPathVector));
            }
            if (checkedCentralities[5]) {
                directedClosenessValues.put(root, DirectedCloseness.execute(CentiScaPeSingleShortestPathVector));
            }
            if (checkedCentralities[8]) {
                CentroidMethods.updateCentroid(CentiScaPeSingleShortestPathVector, root, totalnodecount, CentroidVectorofNodes, CentroidVectorResults);
            }
            if (checkedCentralities[9]) {
                List<CyNode> neighbors = network.getNeighborList(root, CyEdge.Type.OUTGOING);
                for (CyNode neighbor : neighbors) {
                    adjacencyMatrixOfNetwork[k][nodeList.indexOf(neighbor)] = 1.0;
                }
                k++;
            }
            // increment node worked count    
            nodeworked++;
            CentiScaPeStartMenu.updatenodecounting(nodeworked, totalnodecount);
        }
        if (checkedCentralities[8]) {
            CentroidMethods.calculateCentroid(CentroidVectorResults, totalnodecount, CentroidVectorofNodes);
        }
        menu.endcalculus(totalnodecount);
        vectorResults.clear();
        //put in table
        if (checkedCentralities[0]) {
            putValuesinTable(network, directedCentralities[0], Diameter);
        }
        if (checkedCentralities[1]) {
            double average = totalDist / (totalnodecount * (totalnodecount - 1));
            putValuesinTable(network, directedCentralities[1], average);
        }
        if (checkedCentralities[2]) {
            putValuesinTable(network, directedCentralities[2], directedOutDegreeValues, vectorResults);
            putValuesinTable(network, "Directed In Degree", directedInDegreeValues, vectorResults);
        }
        if (checkedCentralities[3]) {
            putValuesinTable(network, directedCentralities[3], directedEccentrityValues, vectorResults);
        }
        if (checkedCentralities[4]) {
            putValuesinTable(network, directedCentralities[4], directedRadialityValues, vectorResults);
        }
        if (checkedCentralities[5]) {
            putValuesinTable(network, directedCentralities[5], directedClosenessValues, vectorResults);
        }
        if (checkedCentralities[6]) {
            Set stressSet = Stressmap.entrySet();
            for (Iterator i = stressSet.iterator(); i.hasNext();) {
                Map.Entry currentmapentry = (Map.Entry) i.next();
                long currentnodeSUID = (Long) currentmapentry.getKey();
                CyNode currentnode = network.getNode(currentnodeSUID);
                double currentstress = (double) (Double) (currentmapentry.getValue());
                directedStressValues.put(currentnode, currentstress);
            }
            putValuesinTable(network, directedCentralities[6], directedStressValues, vectorResults);
        }
        if (checkedCentralities[7] && displayBetweennessResults) {
            nodeTable.createColumn(directedCentralities[7], Double.class, false);
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
            for (Iterator i = BetweennessVectorResults.iterator(); i.hasNext();) {
                FinalResultBetweenness currentnodebetweenness = (FinalResultBetweenness) i.next();
                double currentbetweenness = currentnodebetweenness.getBetweenness();
                if (currentbetweenness < min) {
                    min = currentbetweenness;
                }
                if (currentbetweenness > max) {
                    max = currentbetweenness;
                }
                totalsum = totalsum + currentbetweenness;
                CyRow row = nodeTable.getRow(currentnodebetweenness.getNode().getSUID());
                row.set(directedCentralities[7], new Double(currentbetweenness));
            }
            networkTable.createColumn(directedCentralities[7] + " Max value", Double.class, false);
            networkTable.createColumn(directedCentralities[7] + " min value", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn(directedCentralities[7] + " mean value", Double.class, false);
            network.getRow(network).set(directedCentralities[7] + " Max value", new Double(max));
            network.getRow(network).set(directedCentralities[7] + " min value", new Double(min));
            network.getRow(network).set(directedCentralities[7] + " mean value", new Double(mean));
            ImplCentrality betweennessCentrality = new ImplCentrality(directedCentralities[7], true, mean, min, max);
            vectorResults.add(betweennessCentrality);
        }
        if (checkedCentralities[8]) {
            nodeTable.createColumn(directedCentralities[8], Double.class, false);
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
            for (Iterator i = CentroidVectorResults.iterator(); i.hasNext();) {
                FinalResultCentroid currentnodeCentroid = (FinalResultCentroid) i.next();
                double currentcentroid = currentnodeCentroid.getCentroid();
                if (currentcentroid < min) {
                    min = currentcentroid;
                }
                if (currentcentroid > max) {
                    max = currentcentroid;
                }
                totalsum = totalsum + currentcentroid;
                CyRow row = nodeTable.getRow(currentnodeCentroid.getNode().getSUID());
                row.set(directedCentralities[8], new Double(currentcentroid));
            }
            networkTable.createColumn(directedCentralities[8] + " Max value", Double.class, false);
            networkTable.createColumn(directedCentralities[8] + " min value", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn(directedCentralities[8] + " mean value", Double.class, false);
            network.getRow(network).set(directedCentralities[8] + " Max value", new Double(max));
            network.getRow(network).set(directedCentralities[8] + " min value", new Double(min));
            network.getRow(network).set(directedCentralities[8] + " mean value", new Double(mean));
            ImplCentrality centroidCentrality = new ImplCentrality(directedCentralities[8], true, mean, min, max);
            vectorResults.add(centroidCentrality);
        }
        if (checkedCentralities[9]) {
            CalculateEigenVector.executeAndWriteValues(adjacencyMatrixOfNetwork, network, nodeList, nodeTable, "Directed EigenVector", vectorResults);
        }
        if (checkedCentralities[10]) {
            nodeTable.createColumn(directedCentralities[10], Double.class, false);
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
            for (Iterator i = BetweennessVectorResults.iterator(); i.hasNext();) {
                FinalResultBetweenness currentnodebetweenness = (FinalResultBetweenness) i.next();
                double currentbetweenness = currentnodebetweenness.getBetweenness();
                CyNode root = currentnodebetweenness.getNode();
                List<CyNode> bridgingNeighborList = network.getNeighborList(root, CyEdge.Type.ANY);
                double bridgingCoefficient = 0;
                if (bridgingNeighborList.size() != 0) {
                    double BCNumerator = (1 / (double) (bridgingNeighborList.size()));
                    double BCDenominator = 0;
                    for (CyNode bridgingNeighbor : bridgingNeighborList) {
                        if (network.getNeighborList(bridgingNeighbor, CyEdge.Type.ANY).size() != 0) {
                            BCDenominator = BCDenominator + 1 / (double) (network.getNeighborList(bridgingNeighbor, CyEdge.Type.ANY).size());
                        }
                    }
                    if (BCDenominator != 0) {
                        bridgingCoefficient = BCNumerator / BCDenominator;
                    } else {
                        bridgingCoefficient = 0.0;
                    }
                }
                double bridgingCentrality = bridgingCoefficient * currentbetweenness;
                if (bridgingCentrality < min) {
                    min = bridgingCentrality;
                }
                if (bridgingCentrality > max) {
                    max = bridgingCentrality;
                }
                totalsum = totalsum + bridgingCentrality;
                CyRow row = nodeTable.getRow(currentnodebetweenness.getNode().getSUID());
                row.set(directedCentralities[10], new Double(bridgingCentrality));
            }
            networkTable.createColumn(directedCentralities[10] + " Max value", Double.class, false);
            networkTable.createColumn(directedCentralities[10] + " min value", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn(directedCentralities[10] + " mean value", Double.class, false);
            network.getRow(network).set(directedCentralities[10] + " Max value", new Double(max));
            network.getRow(network).set(directedCentralities[10] + " min value", new Double(min));
            network.getRow(network).set(directedCentralities[10] + " mean value", new Double(mean));
            ImplCentrality bridgingCentrality = new ImplCentrality(directedCentralities[10], true, mean, min, max);
            vectorResults.add(bridgingCentrality);
        }
        if (checkedCentralities[11]) {
            CyTable edgeTable = network.getDefaultEdgeTable();
            edgeTable.createColumn(directedCentralities[11], Double.class, false);
            Map<CyEdge, Double> values = edgeBetweenness.getEdgeBetweennessMap();
            Set<CyEdge> edges = values.keySet();
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
            Iterator it = edges.iterator();
            while (it.hasNext()) {
                CyEdge root = (CyEdge) it.next();
                currentvalue = values.get(root);
                if (currentvalue < min) {
                    min = currentvalue;
                }
                if (currentvalue > max) {
                    max = currentvalue;
                }
                totalsum = totalsum + currentvalue;
                CyRow row = edgeTable.getRow(root.getSUID());
                row.set(directedCentralities[11], new Double(currentvalue));
            }
            networkTable.createColumn(directedCentralities[11] + " Max value", Double.class, false);
            networkTable.createColumn(directedCentralities[11] + " min value", Double.class, false);
            double mean = totalsum / totalEdgeCount;
            networkTable.createColumn(directedCentralities[11] + " mean value", Double.class, false);
            network.getRow(network).set(directedCentralities[11] + " Max value", new Double(max));
            network.getRow(network).set(directedCentralities[11] + " min value", new Double(min));
            network.getRow(network).set(directedCentralities[11] + " mean value", new Double(mean));
            // for embending centrality in Results Panel -- These two lines are enough
            ImplCentrality edgeBetweennessCentrality = new ImplCentrality(directedCentralities[11], true, mean, min, max);
            vectorResults.add(edgeBetweennessCentrality);
        }
        centiscapecore.createCentiScaPeVisualizer();
        centiscapecore.getvisualizer().setEnabled(vectorResults);
    }

    public static void putValuesinTable(CyNetwork network, String heading, double result) {
        CyTable networkTable = network.getDefaultNetworkTable();
        networkTable.createColumn(heading, Double.class, false);
        network.getRow(network).set(heading, new Double(result));
    }

    public static void putValuesinTable(CyNetwork network, String heading, Map<CyNode, Double> values, Vector vectorResults) {
        CyTable nodeTable = network.getDefaultNodeTable();
        nodeTable.createColumn(heading, Double.class, false);
        Set<CyNode> nodes = values.keySet();
        double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
        Iterator it = nodes.iterator();
        while (it.hasNext()) {
            CyNode root = (CyNode) it.next();
            currentvalue = values.get(root);
            if (currentvalue < min) {
                min = currentvalue;
            }
            if (currentvalue > max) {
                max = currentvalue;
            }
            totalsum = totalsum + currentvalue;
            CyRow row = nodeTable.getRow(root.getSUID());
            row.set(heading, new Double(currentvalue));
        }
        CyTable networkTable = network.getDefaultNetworkTable();
        int totalnodecount = network.getNodeCount();
        networkTable.createColumn(heading + " Max value", Double.class, false);
        networkTable.createColumn(heading + " min value", Double.class, false);
        double mean = totalsum / totalnodecount;
        networkTable.createColumn(heading + " mean value", Double.class, false);
        network.getRow(network).set(heading + " Max value", new Double(max));
        network.getRow(network).set(heading + " min value", new Double(min));
        network.getRow(network).set(heading + " mean value", new Double(mean));
        // for embending centrality in Results Panel -- These two lines are enough
        ImplCentrality Centrality = new ImplCentrality(heading, true, mean, min, max);
        vectorResults.add(Centrality);
    }

    public double CalculateDiameter(Vector SingleShortestPathVector) {
        CentiScaPeShortestPathList currentdiameterlist;
        int currentmaxvalue = 0;
        double currentvalue = 0;
        int cost;
        for (int j = 0; j < SingleShortestPathVector.size(); j++) {
            currentdiameterlist = (CentiScaPeShortestPathList) SingleShortestPathVector.elementAt(j);
            cost = ((CentiScaPeMultiSPath) currentdiameterlist.getLast()).getCost();
            currentmaxvalue = Math.max(currentmaxvalue, cost);
        }
        if (currentmaxvalue > currentvalue) {
            currentvalue = ((double) currentmaxvalue);
        }
        return currentvalue;
    }
}

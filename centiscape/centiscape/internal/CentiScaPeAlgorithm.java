/*
 * CentiScaPeAlgorithm.java
 *
 * Created on 7 marzo 2007, 10.01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 *
 * @author scardoni
 */
package org.cytoscape.centiscape.internal;

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
import org.cytoscape.centiscape.internal.Closeness.ClosenessMethods;
import org.cytoscape.centiscape.internal.Closeness.FinalResultCloseness;
import org.cytoscape.centiscape.internal.Degree.FinalResultDegree;
import org.cytoscape.centiscape.internal.Eccentricity.EccentricityMethods;
import org.cytoscape.centiscape.internal.Eccentricity.FinalResultEccentricity;
import org.cytoscape.centiscape.internal.EigenVector.CalculateEigenVector;
import org.cytoscape.centiscape.internal.Radiality.FinalResultRadiality;
import org.cytoscape.centiscape.internal.Stress.FinalResultStress;
import org.cytoscape.centiscape.internal.visualizer.ImplCentrality;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;

public class CentiScaPeAlgorithm {

    private boolean stop = false;
    // one Vector for each centrality
    private Vector ClosenessVectorResults;
    private Vector EccentricityVectorResults;
    private static Vector StressVectorResults;
    private Vector RadialityVectorResults;
    private static Vector BetweennessVectorResults;
    private static Vector CentroidVectorResults;
    private static Vector CentroidVectorofNodes;
    private Vector DegreeVectorResults;
    public double[][] adjacencyMatrixOfNetwork;
    public EdgeBetweenness edgeBetweenness;
    private double Diameter = 0;
    private double average = 0;
    private double totaldist;
    // variables to verify the selected centralities
    private boolean StressisOn = false;
    private boolean ClosenessisOn = false;
    private boolean EccentricityisOn = false;
    private boolean RadialityisOn = false;
    private boolean DiameterisOn = false;
    private boolean DiameterisSelected = false;
    private boolean BetweennessisOn = false;
    private boolean CentroidisOn = false;
    private boolean DegreeisOn = false;
    private boolean AverageisOn = false;
    private boolean EigenVectorisOn = false;
    private boolean BridgingisOn = false;
    private boolean EdgeBetweennessisOn = false;
    private boolean doNotDisplayBetweenness = false;
    public static TreeMap Stressmap = new TreeMap();
    Vector vectorOfNodeAttributes = new Vector();
    Vector vectorOfNetworkAttributes = new Vector();
    public Vector VectorResults = new Vector();
    public boolean openResultPanel;
    public String networkname;
    public CyNetwork network;
    public CentiScaPeCore centiscapecore;
    public List<CyNode> nodeList;
    public Boolean isWeighted;

    /**
     * Creates a new instance of CentiScaPeAlgorithm
     */
    public CentiScaPeAlgorithm(CentiScaPeCore centiscapecore) {
        this.centiscapecore = centiscapecore;
    }

    public void ExecuteCentiScaPeAlgorithm(CyNetwork network, CyNetworkView view, JPanel c) {
        stop = false;
        this.network = network;
        openResultPanel = false;
        CentiScaPeStartMenu menustart = (CentiScaPeStartMenu) c;
        isWeighted = menustart.isWeighted;
        System.out.println("Entered Centiscape Algorithm " + network.toString());
        String networkname = network.getDefaultNetworkTable().getRow(network.getSUID()).get("name", String.class);
        int totalnodecount = network.getNodeCount();
        int totalEdgeCount = network.getEdgeCount();
        Long networksuid = network.getSUID();
        nodeList = network.getNodeList();
        int nodeworked = 0;
        Vector CentiScaPeMultiShortestPathVector = null;
        CyTable nodeTable = network.getDefaultNodeTable();
        CyTable networkTable = network.getDefaultNetworkTable();
        for (Iterator i = vectorOfNodeAttributes.iterator(); i.hasNext();) {
            String attributetoremove = (String) i.next();
            if (nodeTable.getColumn(attributetoremove) != null) {
                nodeTable.deleteColumn(attributetoremove);
            }
        }
        for (Iterator i = vectorOfNetworkAttributes.iterator(); i.hasNext();) {
            String attributetoremove = (String) i.next();
            if (networkTable.getColumn(attributetoremove) != null) {
                nodeTable.deleteColumn(attributetoremove);
            }
        }
        // Create the data structure for each selected centralities
        if (ClosenessisOn) {
            ClosenessVectorResults = new Vector();
            openResultPanel = true;
        }
        if (EccentricityisOn) {
            EccentricityVectorResults = new Vector();
            openResultPanel = true;
        }
        if (DegreeisOn) {
            DegreeVectorResults = new Vector();
            openResultPanel = true;
        }
        if (RadialityisOn) {
            RadialityVectorResults = new Vector();
            openResultPanel = true;
        }
        if (DiameterisOn) {
        }
        if (AverageisOn) {
            totaldist = 0;
        }
        if (EigenVectorisOn) {
            adjacencyMatrixOfNetwork = new double[totalnodecount][totalnodecount];
        }
        if (EdgeBetweennessisOn) {
            edgeBetweenness = new EdgeBetweenness(network);
        }
        if (BetweennessisOn || StressisOn || CentroidisOn) {
            openResultPanel = true;
            if (BetweennessisOn) {
                BetweennessVectorResults = new Vector();
            }
            if (StressisOn) {
                StressVectorResults = new Vector();
            }
            if (CentroidisOn) {
                CentroidVectorResults = new Vector();
                CentroidVectorofNodes = new Vector();
            }
            Stressmap.clear();
            for (Iterator i = network.getNodeList().iterator(); i.hasNext();) {
                if (stop) {
                    unselectallnodes(network);
                    return;
                }
                CyNode root = (CyNode) i.next();
                if (BetweennessisOn) {
                    BetweennessVectorResults.add(new FinalResultBetweenness(root, 0));
                }
                if (StressisOn || BetweennessisOn) {
                    Stressmap.put(root.getSUID(), new Double(0));
                }
                if (CentroidisOn) {
                    CentroidVectorofNodes.addElement(root.getSUID());
                }
            }
        }
        TreeMap inizializedmap = new TreeMap(Stressmap);
        Diameter = 0;
        // Start iteration on each node
        int k = 0;
        for (CyNode root : nodeList) {
            if (stop) {
                unselectallnodes(network);
                return;
            }
            nodeworked++;
            menustart.updatenodecounting(nodeworked, totalnodecount);
            if (EigenVectorisOn) {
                List<CyNode> neighbors = network.getNeighborList(root, CyEdge.Type.ANY);
                for (CyNode neighbor : neighbors) {
                    adjacencyMatrixOfNetwork[k][nodeList.indexOf(neighbor)] = 1.0;
                }
                k++;
            }
            // Execute the multi shortest path algorithm for node root and put the results on the
            // vector called ShortestPathVector
            CentiScaPeMultiShortestPathVector = CentiScaPeMultiShortestPathTreeAlgorithm.ExecuteMultiShortestPathTreeAlgorithm(network, root, StressisOn, BetweennessisOn, inizializedmap, false, isWeighted);
            // Create a Single Shortest Path Vector
            Vector CentiScaPeSingleShortestPathVector = new Vector();
            Vector NodesFound = new Vector();
            CentiScaPeShortestPathList CurrentList;
            for (int j = 0; j < CentiScaPeMultiShortestPathVector.size(); j++) {
                CurrentList = (CentiScaPeShortestPathList) CentiScaPeMultiShortestPathVector.get(j);
                //System.out.println(CurrentList.toString());
                String nodename = ((CentiScaPeMultiSPath) CurrentList.getLast()).getName();
                if (!NodesFound.contains(nodename)) {
                    NodesFound.add(nodename);
                    CentiScaPeSingleShortestPathVector.add(CurrentList);
                }
            }
            // Calculate each properties
            if (ClosenessisOn) {
                ClosenessVectorResults.add((FinalResultCloseness) ClosenessMethods.CalculateCloseness(CentiScaPeSingleShortestPathVector, root));
            }
            if (EccentricityisOn) {
                EccentricityVectorResults.add(EccentricityMethods.CalculateEccentricity(CentiScaPeSingleShortestPathVector, root));
            }
            if (DegreeisOn) {
                FinalResultDegree currentDegree = new FinalResultDegree(root, 0);
                double currentDegreevalue = network.getNeighborList(root, CyEdge.Type.ANY).size();
                currentDegree.update(currentDegreevalue);
                DegreeVectorResults.addElement(currentDegree);
            }
            if (RadialityisOn) {
                CentiScaPeShortestPathList currentlist;
                FinalResultRadiality currentRadiality = new FinalResultRadiality(root, 0);
                int distance;
                for (int j = 0; j < CentiScaPeSingleShortestPathVector.size(); j++) {
                    currentlist = (CentiScaPeShortestPathList) CentiScaPeSingleShortestPathVector.elementAt(j);
                    distance = ((CentiScaPeMultiSPath) currentlist.getLast()).getCost();
                    currentRadiality.updatesizevector(new Integer(distance));
                }
                RadialityVectorResults.add(currentRadiality);
            }
            if (DiameterisOn || DiameterisSelected) {
                double currentdiametervalue;
                currentdiametervalue = CalculateDiameter(CentiScaPeSingleShortestPathVector);
                if (Diameter < currentdiametervalue) {
                    Diameter = currentdiametervalue;
                }
            }
            if (AverageisOn) {
                CentiScaPeShortestPathList currentlist;
                for (int j = 0; j < CentiScaPeSingleShortestPathVector.size(); j++) {
                    currentlist = (CentiScaPeShortestPathList) CentiScaPeSingleShortestPathVector.elementAt(j);
                    totaldist = totaldist + ((CentiScaPeMultiSPath) currentlist.get(currentlist.size() - 1)).getCost();
                }
            }
            if (BetweennessisOn) {
                BetweennessMethods.updateBetweenness(CentiScaPeMultiShortestPathVector, BetweennessVectorResults);
            }
            if (EdgeBetweennessisOn) {
                edgeBetweenness.updateEdgeBetweenness(CentiScaPeMultiShortestPathVector);
            }
            if (StressisOn) {
            }
            if (CentroidisOn) {
                CentroidMethods.updateCentroid(CentiScaPeSingleShortestPathVector, root, totalnodecount, CentroidVectorofNodes, CentroidVectorResults);
            }
        }
        unselectallnodes(network);
        if (RadialityisOn) {
            for (Iterator i = RadialityVectorResults.iterator(); i.hasNext();) {
                if (stop) {
                    unselectallnodes(network);
                    return;
                }
                FinalResultRadiality currentRadiality;
                currentRadiality = (FinalResultRadiality) i.next();
                double Diametervalue = Diameter;
                int currentDist = 0;
                double currentRadialityvalue, parziale1, parziale2;
                for (int j = 0; j < currentRadiality.getVectorSize(); j++) {
                    currentDist = currentRadiality.getlistsizeat(j);
                    if (currentDist != 0) {
                        parziale1 = Diametervalue + 1 - currentDist;
                        parziale2 = totalnodecount - 1;
                        currentRadialityvalue = ((Diametervalue + 1 - currentDist));
                        currentRadiality.update(currentRadialityvalue);
                    }
                }
                currentRadiality.finalcalculus(totalnodecount - 1);
            }
        }
        if (CentroidisOn) {
            CentroidMethods.calculateCentroid(CentroidVectorResults, totalnodecount, CentroidVectorofNodes);
        }
        menustart.endcalculus(totalnodecount);
        VectorResults.clear();
        if (DiameterisSelected) {
            networkTable.createColumn("CentiScaPe Diameter", Double.class, false);
            network.getRow(network).set("CentiScaPe Diameter", new Double(Diameter));
            vectorOfNetworkAttributes.addElement("CentiScaPe Diameter");
        }
        if (AverageisOn) {
            average = totaldist / (totalnodecount * (totalnodecount - 1));
            networkTable.createColumn("CentiScaPe Average Distance", Double.class, false);
            network.getRow(network).set("CentiScaPe Average Distance", new Double(average));
            vectorOfNetworkAttributes.addElement("CentiScaPe Average Distance");
        }
        if (EccentricityisOn) {
            nodeTable.createColumn("CentiScaPe Eccentricity", Double.class, false);
            vectorOfNodeAttributes.addElement("CentiScaPe Eccentricity");
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
            for (Iterator i = EccentricityVectorResults.iterator(); i.hasNext();) {
                FinalResultEccentricity currentnodeeccentricity = (FinalResultEccentricity) i.next();
                double currenteccentricity = currentnodeeccentricity.geteccentricity();
                if (currenteccentricity < min) {
                    min = currenteccentricity;
                }
                if (currenteccentricity > max) {
                    max = currenteccentricity;
                }
                totalsum = totalsum + currenteccentricity;
                CyRow row = nodeTable.getRow(currentnodeeccentricity.getNode().getSUID());
                row.set("CentiScaPe Eccentricity", new Double(currenteccentricity));
            }
            networkTable.createColumn("CentiScaPe Eccentricity Max value", Double.class, false);
            networkTable.createColumn("CentiScaPe Eccentricity min value", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn("CentiScaPe Eccentricity mean value", Double.class, false);
            network.getRow(network).set("CentiScaPe Eccentricity Max value", new Double(max));
            network.getRow(network).set("CentiScaPe Eccentricity min value", new Double(min));
            network.getRow(network).set("CentiScaPe Eccentricity mean value", new Double(mean));
            vectorOfNetworkAttributes.addElement("CentiScaPe Eccentricity Max value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Eccentricity min value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Eccentricity mean value");
            ImplCentrality eccentricityCentrality = new ImplCentrality("CentiScaPe Eccentricity", true, mean, min, max);
            VectorResults.add(eccentricityCentrality);
        }
        if (ClosenessisOn) {
            nodeTable.createColumn("CentiScaPe Closeness", Double.class, false);
            vectorOfNodeAttributes.addElement("CentiScaPe Closeness");
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
            for (Iterator i = ClosenessVectorResults.iterator(); i.hasNext();) {
                FinalResultCloseness currentnodecloseness = (FinalResultCloseness) i.next();
                double currentcloseness = currentnodecloseness.getCloseness();
                if (currentcloseness < min) {
                    min = currentcloseness;
                }
                if (currentcloseness > max) {
                    max = currentcloseness;
                }
                totalsum = totalsum + currentcloseness;
                CyRow row = nodeTable.getRow(currentnodecloseness.getNode().getSUID());
                row.set("CentiScaPe Closeness", new Double(currentcloseness));
            }
            networkTable.createColumn("CentiScaPe Closeness Max value", Double.class, false);
            networkTable.createColumn("CentiScaPe Closeness min value", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn("CentiScaPe Closeness mean value", Double.class, false);
            network.getRow(network).set("CentiScaPe Closeness Max value", new Double(max));
            network.getRow(network).set("CentiScaPe Closeness min value", new Double(min));
            network.getRow(network).set("CentiScaPe Closeness mean value", new Double(mean));
            vectorOfNetworkAttributes.addElement("CentiScaPe Closeness Max value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Closeness min value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Closeness mean value");
            ImplCentrality closenessCentrality = new ImplCentrality("CentiScaPe Closeness", true, mean, min, max);
            VectorResults.add(closenessCentrality);
        }
        if (RadialityisOn) {
            nodeTable.createColumn("CentiScaPe Radiality", Double.class, false);
            vectorOfNodeAttributes.addElement("CentiScaPe Radiality");
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
            for (Iterator i = RadialityVectorResults.iterator(); i.hasNext();) {
                FinalResultRadiality currentnoderadiality = (FinalResultRadiality) i.next();
                double currentradiality = currentnoderadiality.getRadiality();
                if (currentradiality < min) {
                    min = currentradiality;
                }
                if (currentradiality > max) {
                    max = currentradiality;
                }
                totalsum = totalsum + currentradiality;
                CyRow row = nodeTable.getRow(currentnoderadiality.getNode().getSUID());
                row.set("CentiScaPe Radiality", new Double(currentradiality));
            }
            networkTable.createColumn("CentiScaPe Radiality Max value", Double.class, false);
            networkTable.createColumn("CentiScaPe Radiality min value", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn("CentiScaPe Radiality mean value", Double.class, false);
            network.getRow(network).set("CentiScaPe Radiality Max value", new Double(max));
            network.getRow(network).set("CentiScaPe Radiality min value", new Double(min));
            network.getRow(network).set("CentiScaPe Radiality mean value", new Double(mean));
            vectorOfNetworkAttributes.addElement("CentiScaPe Radiality Max value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Radiality min value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Radiality mean value");
            ImplCentrality radialityCentrality = new ImplCentrality("CentiScaPe Radiality", true, mean, min, max);
            VectorResults.add(radialityCentrality);
        }
        if (BetweennessisOn && doNotDisplayBetweenness == false) {
            nodeTable.createColumn("CentiScaPe Betweenness", Double.class, false);
            vectorOfNodeAttributes.addElement("CentiScaPe Betweenness");
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
                row.set("CentiScaPe Betweenness", new Double(currentbetweenness));
            }
            networkTable.createColumn("CentiScaPe Betweenness Max value", Double.class, false);
            networkTable.createColumn("CentiScaPe Betweenness min value", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn("CentiScaPe Betweenness mean value", Double.class, false);
            network.getRow(network).set("CentiScaPe Betweenness Max value", new Double(max));
            network.getRow(network).set("CentiScaPe Betweenness min value", new Double(min));
            network.getRow(network).set("CentiScaPe Betweenness mean value", new Double(mean));
            vectorOfNetworkAttributes.addElement("CentiScaPe Betweenness Max value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Betweenness min value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Betweenness mean value");
            ImplCentrality betweennessCentrality = new ImplCentrality("CentiScaPe Betweenness", true, mean, min, max);
            VectorResults.add(betweennessCentrality);
        }
        if (DegreeisOn) {
            nodeTable.createColumn("CentiScaPe Node Degree", Double.class, false);
            vectorOfNodeAttributes.addElement("CentiScaPe Node Degree");
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
            for (Iterator i = DegreeVectorResults.iterator(); i.hasNext();) {
                FinalResultDegree currentnodeDegree = (FinalResultDegree) i.next();
                double currentdegree = currentnodeDegree.getDegree();
                if (currentdegree < min) {
                    min = currentdegree;
                }
                if (currentdegree > max) {
                    max = currentdegree;
                }
                totalsum = totalsum + currentdegree;
                CyRow row = nodeTable.getRow(currentnodeDegree.getNode().getSUID());
                row.set("CentiScaPe Node Degree", new Double(currentdegree));
            }
            networkTable.createColumn("CentiScaPe Degree Max value", Double.class, false);
            networkTable.createColumn("CentiScaPe Degree min value", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn("CentiScaPe Degree mean value", Double.class, false);
            network.getRow(network).set("CentiScaPe Degree Max value", new Double(max));
            network.getRow(network).set("CentiScaPe Degree min value", new Double(min));
            network.getRow(network).set("CentiScaPe Degree mean value", new Double(mean));
            vectorOfNetworkAttributes.addElement("CentiScaPe Degree Max value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Degree min value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Degree mean value");
            ImplCentrality degreeCentrality = new ImplCentrality("CentiScaPe Node Degree", true, mean, min, max);
            VectorResults.add(degreeCentrality);
        }
        if (StressisOn) {
            nodeTable.createColumn("CentiScaPe Stress", Double.class, false);
            vectorOfNodeAttributes.addElement("CentiScaPe Stress");
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE, totalsum = 0, currentvalue;
            Set stressSet = Stressmap.entrySet();
            for (Iterator i = stressSet.iterator(); i.hasNext();) {
                Map.Entry currentmapentry = (Map.Entry) i.next();
                long currentnodeSUID = (Long) currentmapentry.getKey();
                CyNode currentnode = network.getNode(currentnodeSUID);
                double currentstress = (double) (Double) (currentmapentry.getValue());
                StressVectorResults.add(new FinalResultStress(currentnode, currentstress));
                if (currentstress < min) {
                    min = currentstress;
                }
                if (currentstress > max) {
                    max = currentstress;
                }
                totalsum = totalsum + currentstress;
                CyRow row = nodeTable.getRow(currentnodeSUID);
                row.set("CentiScaPe Stress", new Double(currentstress));
            }
            networkTable.createColumn("CentiScaPe Stress Max value", Double.class, false);
            networkTable.createColumn("CentiScaPe Stress min value", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn("CentiScaPe Stress mean value", Double.class, false);
            network.getRow(network).set("CentiScaPe Stress Max value", new Double(max));
            network.getRow(network).set("CentiScaPe Stress min value", new Double(min));
            network.getRow(network).set("CentiScaPe Stress mean value", new Double(mean));
            vectorOfNetworkAttributes.addElement("CentiScaPe Stress Max value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Stress min value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Stress mean value");
            ImplCentrality stressCentrality = new ImplCentrality("CentiScaPe Stress", true, mean, min, max);
            VectorResults.add(stressCentrality);
        }
        if (CentroidisOn) {
            nodeTable.createColumn("CentiScaPe Centroid", Double.class, false);
            vectorOfNodeAttributes.addElement("CentiScaPe Centroid");
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
                row.set("CentiScaPe Centroid", new Double(currentcentroid));
            }
            networkTable.createColumn("CentiScaPe Centroid Max value", Double.class, false);
            networkTable.createColumn("CentiScaPe Centroid min value", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn("CentiScaPe Centroid mean value", Double.class, false);
            network.getRow(network).set("CentiScaPe Centroid Max value", new Double(max));
            network.getRow(network).set("CentiScaPe Centroid min value", new Double(min));
            network.getRow(network).set("CentiScaPe Centroid mean value", new Double(mean));
            vectorOfNetworkAttributes.addElement("CentiScaPe Centroid Max value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Centroid min value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Centroid mean value");
            ImplCentrality centroidCentrality = new ImplCentrality("CentiScaPe Centroid", true, mean, min, max);
            VectorResults.add(centroidCentrality);
        }
        if (EigenVectorisOn) {
            vectorOfNodeAttributes.addElement("CentiScaPe EigenVector");
            vectorOfNetworkAttributes.addElement("CentiScaPe EigenVector Max value");
            vectorOfNetworkAttributes.addElement("CentiScaPe EigenVector min value");
            vectorOfNetworkAttributes.addElement("CentiScaPe EigenVector mean value");
            CalculateEigenVector.executeAndWriteValues(adjacencyMatrixOfNetwork, network, nodeList, nodeTable, "CentiScaPe EigenVector", VectorResults);
        }
        if (BridgingisOn) {
            nodeTable.createColumn("CentiScaPe Bridging", Double.class, false);
            vectorOfNodeAttributes.addElement("CentiScaPe Bridging");
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
                        BCDenominator = BCDenominator + 1 / (double) (network.getNeighborList(bridgingNeighbor, CyEdge.Type.ANY).size());
                    }
                    bridgingCoefficient = BCNumerator / BCDenominator;
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
                row.set("CentiScaPe Bridging", new Double(bridgingCentrality));
            }
            networkTable.createColumn("CentiScaPe Bridging Max value", Double.class, false);
            networkTable.createColumn("CentiScaPe Bridging min value", Double.class, false);
            double mean = totalsum / totalnodecount;
            networkTable.createColumn("CentiScaPe Bridging mean value", Double.class, false);
            network.getRow(network).set("CentiScaPe Bridging Max value", new Double(max));
            network.getRow(network).set("CentiScaPe Bridging min value", new Double(min));
            network.getRow(network).set("CentiScaPe Bridging mean value", new Double(mean));
            vectorOfNetworkAttributes.addElement("CentiScaPe Bridging Max value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Bridging min value");
            vectorOfNetworkAttributes.addElement("CentiScaPe Bridging mean value");
            ImplCentrality bridgingCentrality = new ImplCentrality("CentiScaPe Bridging", true, mean, min, max);
            VectorResults.add(bridgingCentrality);
        }
        if (EdgeBetweennessisOn) {
            CyTable edgeTable = network.getDefaultEdgeTable();
            edgeTable.createColumn("CentiScaPe Edge Betweenness", Double.class, false);
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
                row.set("CentiScaPe Edge Betweenness", new Double(currentvalue));
            }
            networkTable.createColumn("CentiScaPe Edge Betweenness" + " Max value", Double.class, false);
            networkTable.createColumn("CentiScaPe Edge Betweenness" + " min value", Double.class, false);
            double mean = totalsum / totalEdgeCount;
            networkTable.createColumn("CentiScaPe Edge Betweenness" + " mean value", Double.class, false);
            network.getRow(network).set("CentiScaPe Edge Betweenness" + " Max value", new Double(max));
            network.getRow(network).set("CentiScaPe Edge Betweenness" + " min value", new Double(min));
            network.getRow(network).set("CentiScaPe Edge Betweenness" + " mean value", new Double(mean));
            // for embending centrality in Results Panel -- These two lines are enough
            ImplCentrality edgeBetweennessCentrality = new ImplCentrality("CentiScaPe Edge Betweenness", true, mean, min, max);
            VectorResults.add(edgeBetweennessCentrality);
        }
        centiscapecore.createCentiScaPeVisualizer();
        centiscapecore.getvisualizer().setEnabled(VectorResults);
        if (openResultPanel) {
        } else {
        }
    }

    public void endalgorithm() {
        stop = true;
    }

    public void setChecked(boolean[] ison) {
        DiameterisOn = ison[4];
        DiameterisSelected = ison[0];
        AverageisOn = ison[1];
        DegreeisOn = ison[2];
        EccentricityisOn = ison[3];
        RadialityisOn = ison[4];
        ClosenessisOn = ison[5];
        StressisOn = ison[6];
        BetweennessisOn = ison[7];
        CentroidisOn = ison[8];
        EigenVectorisOn = ison[9];
        BridgingisOn = ison[10];
        EdgeBetweennessisOn = ison[11];
        if (BridgingisOn && BetweennessisOn == false) {
            BetweennessisOn = true;
            doNotDisplayBetweenness = true;
        }
        if (ison[7]) {
            doNotDisplayBetweenness = false;
        }
    }

    public void unselectallnodes(CyNetwork network) {
        for (Iterator i = network.getNodeList().iterator(); i.hasNext();) {
            CyNode tmpnode = (CyNode) i.next();
            CyRow row = network.getRow(tmpnode);
            row.set("selected", true);
        }
    }

    public double CalculateDiameter(Vector SingleShortestPathVector) {
        CentiScaPeShortestPathList currentdiameterlist;
        int currentmaxvalue = 0;
        double currentvalue = 0;
        int cost = 0;
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

    public String getName(CyNode currentnode, CyNetwork currentnetwork) {
        return currentnetwork.getRow(currentnode).get("name", String.class);
    }
}

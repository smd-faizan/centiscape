/*
 * SPathEcc.java
 *
 * Created on 17 gennaio 2007, 16.13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 *
 * @author scardoni
 */
package org.cytoscape.centiscape.internal.Eccentricity;

import org.cytoscape.model.CyNode;

public class SPathEcc {

    private CyNode node;
    private long nodesuid;
    private int cost;
    private SPathEcc predecessor;
    //private Vector nodelist;

    /**
     * Creates a new instance of SPathEcc
     */
    public SPathEcc(CyNode Node, SPathEcc Predecessor) {
        node = Node;
        nodesuid = node.getSUID();
        cost = 1;
        predecessor = Predecessor;
        //  nodelist = new Vector();
        //  nodelist.addElement(Node);
    }
    //creates an instance of Spath with empty vector 
    //cost should be set = 0 to create the root Spath

    public SPathEcc(CyNode Node, int n) {
        node = Node;
        nodesuid = Node.getSUID();
        cost = n;
        predecessor = null;
        // nodelist = new Vector();
    }

    public void add(CyNode Node) {
        cost++;
        // nodelist.addElement(Node);  

    }
    // set the cost to Cost

    public void setCost(int Cost) {
        cost = Cost;
    }
    // set the predecessor to newPredecessor

    public void setPredecessor(SPathEcc newPredecessor) {
        this.predecessor = newPredecessor;
    }

    public SPathEcc getPredecessor() {
        return this.predecessor;
    }

    // return the node name of the SPathEcc instance
    public CyNode getNode() {
        return this.node;
    }

    public int getCost() {
        return this.cost;
    }
    /*
     * public String getName() { return nodename;
      }
     */

    public String toString() {
        String PathString;
        PathString = "origine = " + node.getSUID() + " costo = " + cost;
        if (cost == 0) {
            PathString = PathString + " Root and Target are the same ";
        } else if (predecessor == null) {
            PathString = PathString + "cazzoooo ";
        }
        if (predecessor != null) {
            //{PathString = PathString + " " +((CyNode)nodelist.elementAt(0)).getIdentifier();}
            {
                PathString = PathString + "predecessore= " + predecessor.node.getSUID();
            }
        }
        return PathString;
    }
}

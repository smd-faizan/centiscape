/*
 * CentVisualOptions.java
 *
 * Created on 7 gennaio 2008, 18.19
 */

package org.cytoscape.centiscape.internal.visualizer;

import java.lang.Double;
import java.util.Collections;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import org.cytoscape.centiscape.internal.charts.CentPlotByNode;
import org.cytoscape.centiscape.internal.charts.CentScatterPlot;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;





/**
 *
 * @author  admini
 */
public class CentVisualOptions extends javax.swing.JPanel  {
    
    public class MyObservable extends Observable {

        public void doIt() {
            this.setChanged();
            notifyObservers();
        }
    }
    
    public MyObservable notifier;
    public boolean isAnd;
    public CyNetworkView currentview;
    public CyNetwork currentnetwork;
    public  Vector<CompCyNode> al;
    
    /** Creates new form CentVisualOptions */
    public CentVisualOptions(CyNetworkView currentview) {
        this.currentview = currentview;
        this.currentnetwork = currentview.getModel();
        initComponents();
        loadNodesList();
        isAnd=true;
        notifier=new MyObservable();
    }
    
    private class CompCyNode implements Comparable{
        public CyNode node;
        public String nodename;

        public CompCyNode(CyNode n){
            node=n;
            nodename = currentnetwork.getDefaultNodeTable().getRow(node.getSUID()).get("name", String.class);
        }
        

        public int compareTo(Object o) {
            CompCyNode o1=(CompCyNode) o;
         //   return(node.getSUID().compareTo(o1.node.getSUID()));
            CyTable currentnodetable = currentnetwork.getDefaultNodeTable();
       return(currentnodetable.getRow(node.getSUID()).get("name", String.class).compareTo(
           currentnodetable.getRow(o1.node.getSUID()).get("name", String.class)));     
        }
    }
    
    //sort CyNodes and load the "Plot By Node" combobox
    public  void loadNodesList(){
        plotByNodeSelector.removeAllItems();
        //CyNetwork currentnetwork = currentview.getModel();
        al=new Vector();
        for (Iterator i=currentnetwork.getNodeList().listIterator();i.hasNext();){
            CyNode el=(CyNode)i.next();
            al.add(new CompCyNode(el));
        }
        Collections.sort(al);
        for (int i =0;i<al.size(); i++) {
            plotByNodeSelector.addItem(((CompCyNode)al.get(i)).nodename);
      
        }
    }
    
    // populate centralities comboboxes 
    public void loadCentralities(Vector<Centrality> cents){
        xCombo.removeAllItems();
        yCombo.removeAllItems();
        
       /* for (Iterator<Centrality> it = cents.iterator(); it.hasNext();) {
            Centrality cent = (Centrality) it.next();
            xCombo.addItem(cent);
           
        } */
        // add numerical attributes too
         CyTable nodeTable = currentview.getModel().getDefaultNodeTable();
         for (Iterator i =  nodeTable.getColumns().iterator(); i.hasNext();) {
              CyColumn currentcolumn =  (CyColumn)i.next();
             //  System.out.println("la colonna current ?? "+ currentcolumn.getName());
             //   System.out.println("il primo tipo ?? "+ currentcolumn.getType());
                
             //    System.out.println("il secondo tipo ?? "+ Double.class );
              if (currentcolumn.getType().equals(Double.class) ||
                      currentcolumn.getType().equals(Long.class) ||
                     currentcolumn.getType().equals(Integer.class) )  {
              String currentattribute = currentcolumn.getName(); 
             //   System.out.println("la colonna current secondo ?? "+ currentcolumn.getName());
               yCombo.addItem(currentattribute);
                xCombo.addItem(currentattribute);
          }
                   
         }
         CyTable edgeTable = currentview.getModel().getDefaultEdgeTable();
         for (Iterator i =  edgeTable.getColumns().iterator(); i.hasNext();) {
              CyColumn currentcolumn =  (CyColumn)i.next();
             //  System.out.println("la colonna current ?? "+ currentcolumn.getName());
             //   System.out.println("il primo tipo ?? "+ currentcolumn.getType());
                
             //    System.out.println("il secondo tipo ?? "+ Double.class );
              if (currentcolumn.getType().equals(Double.class) ||
                      currentcolumn.getType().equals(Long.class) ||
                     currentcolumn.getType().equals(Integer.class) )  {
              String currentattribute = currentcolumn.getName(); 
             //   System.out.println("la colonna current secondo ?? "+ currentcolumn.getName());
               yCombo.addItem(currentattribute);
                xCombo.addItem(currentattribute);
          }
                   
         }
    
       //   CyAttributes attrs = Cytoscape.getNodeAttributes();
/*
        System.out.println("carico altri attributi");
       String[] names=attrs.getAttributeNames();
        for (int i = 0; i < names.length; i++) {
            String string = names[i];
            System.out.println("la stringa names ??"+names[i]);
            if ((attrs.getType(string)==attrs.TYPE_FLOATING) || (attrs.getType(string)==attrs.TYPE_INTEGER) )
            {
                System.out.println("la stringa names ?? dentro"+names[i]);
                yCombo.addItem(string);
                xCombo.addItem(string);
            }
        }*/
    }
    
    public void addObserver(Observer obs){
        notifier.addObserver(obs);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ANDORButtonGroup = new javax.swing.ButtonGroup();
        andButton = new javax.swing.JRadioButton();
        orButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        plottByNodeButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        plotByNodeSelector = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        xCombo = new javax.swing.JComboBox();
        yCombo = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        plotByCentrality = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Options and views"));
        setMaximumSize(new java.awt.Dimension(500, 270));

        ANDORButtonGroup.add(andButton);
        andButton.setSelected(true);
        andButton.setText("AND");
        andButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                andButtonActionPerformed(evt);
            }
        });

        ANDORButtonGroup.add(orButton);
        orButton.setText("OR");
        orButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("plot by node");

        plottByNodeButton.setText("plot");
        plottByNodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plottByNodeButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("filter type");

        plotByNodeSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotByNodeSelectorActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("plot by centralities");

        xCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xComboActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("horizontal axis");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("vertical axis");

        plotByCentrality.setText("plot");
        plotByCentrality.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotByCentralityActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .add(29, 29, 29)
                        .add(andButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(orButton))
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(plotByNodeSelector, 0, 153, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(plottByNodeButton)
                        .add(4, 4, 4)))
                .addContainerGap())
            .add(jSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
            .add(jSeparator2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel3)
                .addContainerGap(209, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel5)
                    .add(jLabel4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(yCombo, 0, 201, Short.MAX_VALUE)
                    .add(xCombo, 0, 201, Short.MAX_VALUE))
                .addContainerGap())
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(plotByCentrality, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(orButton)
                    .add(jLabel2)
                    .add(andButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(plottByNodeButton)
                    .add(plotByNodeSelector, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel3)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel4))
                    .add(layout.createSequentialGroup()
                        .add(7, 7, 7)
                        .add(xCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(yCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(plotByCentrality)
                .addContainerGap(13, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void andButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_andButtonActionPerformed
        // TODO add your handling code here:
        isAnd=true;
        notifier.doIt();
        
}//GEN-LAST:event_andButtonActionPerformed

    private void orButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orButtonActionPerformed
        // TODO add your handling code here:
        isAnd=false;
        notifier.doIt();
    }//GEN-LAST:event_orButtonActionPerformed

    private void plottByNodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plottByNodeButtonActionPerformed
        // TODO add your handling code here:
         String nodename=(String)plotByNodeSelector.getSelectedItem();
         CyNode c;
         for (int i =0;i<al.size(); i++) {
            if (((CompCyNode)al.get(i)).nodename.equals((String)plotByNodeSelector.getSelectedItem())) {
                c = (CyNode)((CompCyNode)al.get(i)).node;
                  CentPlotByNode pbn=new CentPlotByNode(c,currentnetwork);
        pbn.setSize(700,400);
        pbn.setVisible(true);
        return;
            }
     
        }
      //  CyNode c=(CyNode)plotByNodeSelector.getSelectedItem();
      
    }//GEN-LAST:event_plottByNodeButtonActionPerformed

    private void plotByCentralityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotByCentralityActionPerformed
        // TODO add your handling code here:
      //  CentScatterPlot scatter=new CentScatterPlot((Centrality)xCombo.getSelectedItem(),(String)yCombo.getSelectedItem());
       CentScatterPlot scatter=new CentScatterPlot((String)xCombo.getSelectedItem(),(String)yCombo.getSelectedItem(),currentnetwork);
      
        
        scatter.setSize(700,600);
        scatter.setVisible(true);
       
    }//GEN-LAST:event_plotByCentralityActionPerformed

    private void plotByNodeSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotByNodeSelectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_plotByNodeSelectorActionPerformed

    private void xComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xComboActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup ANDORButtonGroup;
    private javax.swing.JRadioButton andButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JRadioButton orButton;
    private javax.swing.JButton plotByCentrality;
    private javax.swing.JComboBox plotByNodeSelector;
    private javax.swing.JButton plottByNodeButton;
    private javax.swing.JComboBox xCombo;
    private javax.swing.JComboBox yCombo;
    // End of variables declaration//GEN-END:variables
    
}

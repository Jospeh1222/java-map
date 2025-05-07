package test;

import data.LocationSearch;
import data.RoutingData;
import data.RoutingService;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import waypoint.EventWaypoint;
import waypoint.MyWaypoint;
import waypoint.WaypointRender;

public class Main extends javax.swing.JFrame {

    private final Set<MyWaypoint> waypoints = new HashSet<>();
    private List<RoutingData> routingData = new ArrayList<>();
    private final List<GeoPosition> allWaypointPositions = new ArrayList<>();// konumları tutan liste
    private EventWaypoint event;
    private Point mousePosition;
    private java.util.List<String> previousSearches = new ArrayList<>();
    private JPopupMenu searchSuggestions = new JPopupMenu();



    public Main() {
        initComponents();
        init();
    }

    private void init() {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition(39.232253141714885,35.2880859375);
        jXMapViewer.setAddressLocation(geo);
        jXMapViewer.setZoom(14);
        
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmdSearch.doClick();
        }
        });
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            showSuggestions();
        }

        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            showSuggestions();
        }

        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            showSuggestions();
        }
    });
        //  Create event mouse move
        MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
        jXMapViewer.addMouseListener(mm);
        jXMapViewer.addMouseMotionListener(mm);
        jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
        event = getEvent();
    }
    
    private void addWaypoint(MyWaypoint waypoint) {// sadece waypoint eklme 
        waypoints.add(waypoint);
        initWaypoint();
    }
    
    private void addWaypointWithDelete(MyWaypoint waypoint){//waypoint eklerken haritada önce varsa silme
        for (MyWaypoint d : waypoints) {
            jXMapViewer.remove(d.getButton());
            }
            Iterator<MyWaypoint> iter = waypoints.iterator();
                while (iter.hasNext()) {
                    if (iter.next().getPointType() == waypoint.getPointType()) {
                        iter.remove();
                }
            }
         waypoints.add(waypoint);
         initWaypoint();
    }

    private void initWaypoint() {
        WaypointPainter<MyWaypoint> wp = new WaypointRender();
        wp.setWaypoints(waypoints);
        jXMapViewer.setOverlayPainter(wp);
        for (MyWaypoint d : waypoints) {
            jXMapViewer.add(d.getButton());
        }
        //  Routing Data
        if (waypoints.size() == 2) {
            GeoPosition start = null;
            GeoPosition end = null;
            for (MyWaypoint w : waypoints) {
                if (w.getPointType() == MyWaypoint.PointType.START) {
                    start = w.getPosition();
                } else if (w.getPointType() == MyWaypoint.PointType.END) {
                    end = w.getPosition();
                }
            }
            if (start != null && end != null) {
                routingData = RoutingService.getInstance().routing(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude());
                System.out.println("Routing data size: " + routingData.size());
                for (RoutingData d : routingData) {
                System.out.println("Point list size: " + d.getPointList().size());
            }
            } else {
                routingData.clear();
            }
            jXMapViewer.setRoutingData(routingData);
        }
    }

    private void clearWaypoint() {//waypoint silme
        for (MyWaypoint d : waypoints) {
            jXMapViewer.remove(d.getButton());
        }
        routingData.clear();
        waypoints.clear();
        initWaypoint();
    }

    private EventWaypoint getEvent() {
        return new EventWaypoint() {
            @Override
            public void selected(MyWaypoint waypoint) {
                JOptionPane.showMessageDialog(Main.this, waypoint.getName());
            }
        };
    }

    //haritada önceden aradığın yerleri tekrar yazarken öneri barı
    private void showSuggestions() {
    String text = txtSearch.getText().toLowerCase();
    searchSuggestions.removeAll();
    
    if (text.isEmpty()) return;

    for (String suggestion : previousSearches) {
        if (suggestion.toLowerCase().startsWith(text)) {
            JMenuItem item = new JMenuItem(suggestion);
            item.addActionListener(e -> {
                txtSearch.setText(suggestion);
                searchSuggestions.setVisible(false);
            });
            searchSuggestions.add(item);
        }
    }

    if (searchSuggestions.getComponentCount() > 0) {
        searchSuggestions.show(txtSearch, 0, txtSearch.getHeight());
    } else {
        searchSuggestions.setVisible(false);
    }
    txtSearch.requestFocusInWindow();
}

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        AddWaypoint = new javax.swing.JMenuItem();
        jXMapViewer = new data.JXMapViewerCustom();
        cmdClear = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        cmdSearch = new javax.swing.JButton();
        ListLocations = new javax.swing.JButton();
        Clear_history = new javax.swing.JButton();

        AddWaypoint.setText("Add waypoint");
        AddWaypoint.setToolTipText("");
        AddWaypoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddWaypointActionPerformed(evt);
            }
        });
        jPopupMenu1.add(AddWaypoint);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jXMapViewer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXMapViewerMouseReleased(evt);
            }
        });

        cmdClear.setText("Clear Waypoint");
        cmdClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdClearActionPerformed(evt);
            }
        });

        cmdSearch.setText("Search");
        cmdSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSearchActionPerformed(evt);
            }
        });

        ListLocations.setText("Locations");
        ListLocations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListLocationsActionPerformed(evt);
            }
        });

        Clear_history.setText("Clear History");
        Clear_history.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Clear_historyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXMapViewerLayout = new javax.swing.GroupLayout(jXMapViewer);
        jXMapViewer.setLayout(jXMapViewerLayout);
        jXMapViewerLayout.setHorizontalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXMapViewerLayout.createSequentialGroup()
                .addComponent(cmdClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Clear_history)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(ListLocations, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmdSearch)
                .addContainerGap())
        );
        jXMapViewerLayout.setVerticalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXMapViewerLayout.createSequentialGroup()
                .addGroup(jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdClear)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdSearch)
                    .addComponent(ListLocations)
                    .addComponent(Clear_history))
                .addGap(0, 486, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXMapViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXMapViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //sadece haritada görünen waypointleri siler ama tutulan lokasyon bilgilerini ellemez
    private void cmdClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdClearActionPerformed
         clearWaypoint();
    }//GEN-LAST:event_cmdClearActionPerformed

    private void jXMapViewerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXMapViewerMouseReleased
        if (SwingUtilities.isRightMouseButton(evt)) {
            mousePosition = evt.getPoint();
            jPopupMenu1.show(jXMapViewer, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jXMapViewerMouseReleased

    //search bar 
    private void cmdSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSearchActionPerformed                                    
    String keyword = txtSearch.getText();
    if (keyword != null && !keyword.isEmpty()) {
        LocationSearch.SearchResult result = LocationSearch.search(keyword);
        if (result != null) {
            GeoPosition pos = new GeoPosition(result.latitude, result.longitude);
            MyWaypoint wayPoint = new MyWaypoint("waypoint", MyWaypoint.PointType.SEARCH, event, new GeoPosition(result.latitude, result.longitude));
            addWaypointWithDelete(wayPoint);
            allWaypointPositions.add(pos);
            jXMapViewer.setAddressLocation(pos);
            System.out.println("type:"+result.type);
            if (!previousSearches.contains(keyword)) {
                previousSearches.add(keyword);
            }
            if (result.type.equals("city") || result.type.equals("town") || result.type.equals("village")|| result.type.equals("administrative")) {
                jXMapViewer.setZoom(10);
            } else {
                jXMapViewer.setZoom(4);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Location not found!");
        }
    }

    }//GEN-LAST:event_cmdSearchActionPerformed

    //wapoint ekler sağ click ile
    private void AddWaypointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddWaypointActionPerformed
        GeoPosition geop = jXMapViewer.convertPointToGeoPosition(mousePosition);
        MyWaypoint wayPoint = new MyWaypoint("waypoint", MyWaypoint.PointType.VIA, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()));
        addWaypointWithDelete(wayPoint);
        allWaypointPositions.add(geop);
        System.out.println("x:"+geop.getLatitude()+" y:"+geop.getLongitude());
    }//GEN-LAST:event_AddWaypointActionPerformed

    //location tutan listeyi yazdırır
    private void ListLocationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListLocationsActionPerformed
        int i=1;
        for (GeoPosition pos : allWaypointPositions) {
        MyWaypoint wayPoint = new MyWaypoint("Location"+i, MyWaypoint.PointType.SEARCH, event, new GeoPosition(pos.getLatitude(), pos.getLongitude()));
        addWaypoint(wayPoint);
        i++;
    System.out.println("Lat: " + pos.getLatitude() + ", Lon: " + pos.getLongitude());
}
    }//GEN-LAST:event_ListLocationsActionPerformed

    //tuttuğu bütün lokasyonları ve waypointleri siler
    private void Clear_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Clear_historyActionPerformed
        allWaypointPositions.clear();
        clearWaypoint();
    }//GEN-LAST:event_Clear_historyActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
         try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AddWaypoint;
    private javax.swing.JButton Clear_history;
    private javax.swing.JButton ListLocations;
    private javax.swing.JButton cmdClear;
    private javax.swing.JButton cmdSearch;
    private javax.swing.JPopupMenu jPopupMenu1;
    private data.JXMapViewerCustom jXMapViewer;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}

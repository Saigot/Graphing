/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import javax.swing.JPanel;

/**
 *
 * @author michael
 */
public class MouseMovementListener implements MouseMotionListener{
    LineGraph Graph;
    JPanel Canvas;
    
    public MouseMovementListener(JPanel canvas, LineGraph g) {
        Graph = g;
        Canvas = canvas;
    }

    
    @Override
    public void mouseDragged(MouseEvent e) {
       
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Canvas.repaint();
        double x = Graph.GetRelativeX(e.getX(), Canvas.getWidth());
        double y = Graph.GetRelativeY(e.getY(), Canvas.getHeight());
        DecimalFormat d = new DecimalFormat("### ### ##0.###;-### ### ##0.###");
        String xs = d.format(x);
        
    }
    
}

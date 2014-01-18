/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author michael
 */
public class LineGraph extends Graph{
    public String raw = ""; //this cannot be used directly, it must be passed through a parser
                            //it represents raw input
    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public LineGraph() {
    }

    public LineGraph(double array[]) {
        PointArray = array;
    }
    //</editor-fold>

    @Override
    public void DrawInnards(Graphics2D g, int width, int height) {
        GetMaximas();
        DrawInnards(g, width, height, lowerX, lowerY, upperX, upperY);
    }

    @Override
    public void DrawInnards(Graphics2D g, int width, int height, double minX,
            double minY, double maxX, double maxY) {
        upperX = maxX;
        lowerX = minX;

        upperY = maxY;
        lowerY = minY;
        //debug, DrawInnards bounds
//        g.DrawInnards(XOffSet, YOffSet, width, YOffSet);
//        g.DrawInnards(XOffSet, height, width, height); //X
//
//        g.DrawInnards(XOffSet, YOffSet, XOffSet, height);
//        g.DrawInnards(width, YOffSet, width, height); //Y

        double Xscale = GetXScale(width);
        double Yscale = GetYScale(height);
        int centerX = getCenterX(width);
        int centerY = getCenterY(height);
        
        //drawGrid(g, width, height, minX, minY, maxX, maxY);
        
        g.setColor(LineColor);
        g.setStroke(new BasicStroke(curveThickness));
        //draws line from last point to current point
        for (int i = 2; i <= PointArray.length - 1; i += 2) {
            double x1 = ((PointArray[i - 2] * Xscale) + centerX);
            double y1 = ((centerY) - (PointArray[i - 1]) * Yscale);

            double x2 = ((PointArray[i] * Xscale) + centerX);
            double y2 = ((centerY) - (PointArray[i + 1]) * Yscale);

            if(Double.isNaN(y2)){
                continue;
            }
            if(Double.isNaN(y1)){
                continue;
            }
            if (y1 == Double.NaN) {
                i += 2;
                continue;
            } else if (y1 == Double.POSITIVE_INFINITY || y1 == Double.NEGATIVE_INFINITY) {
                continue;
            }

            if (y2 == Double.NaN) {
                i += 2;
                continue;
            } else if (y2 == Double.POSITIVE_INFINITY || y2 == Double.NEGATIVE_INFINITY) {
                if (i > 3) {
                    if ((PointArray[i - 1] - PointArray[i - 3] > 0)) {
                        y2 = YOffSet;
                    } else {
                        y2 = height;
                    }
                } else {
                    continue;
                }
            }
            if (x1 < XOffSet) {
                x1 = XOffSet;
            } else if (x1 > width) {
                continue;
            }
            if (x2 < XOffSet) {
                continue;
            } else if (x2 > width) {
                x2 = width;
            }

            if (y1 < YOffSet) {
                y1 = YOffSet;
            } else if (y1 > height) {
                continue;
            }
            if (y2 < YOffSet) {
                continue;
            } else if (y2 > height) {
                y2 = height;
            }
            g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }

    }
 
 
    
}

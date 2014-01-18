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
public class LineGraph{
    public String raw = ""; //this cannot be used directly, it must be passed through a parser
    private double PointArray[] = new double[]{0,0}; //the array used to 
    //represent the line, odd is x, even y
    private float Opacity = 0.5f; //a value between 0 and 1 that determines how opacate the graph is
    private boolean DrawArea = false; //whether or not to color area under graph
    private double lowerX = 0; //the minimum value of x
    private double lowerY = 0; //the min val of y
    private double upperX = 0; //the max x val
    private double upperY = 0; //the max y val
    private int XOffSet = 15; //where to start drawing from the right
    private int YOffSet =15; //where to start drawing from the top
    boolean showScale = true; 
    boolean showNumbers = true;
    boolean showGrid = true;
    int increments = 10;
    float curveThickness = 1f;
    private Color LineColor = Color.RED;
    private Color BackgroundColor = new JPanel().getBackground();
    private boolean drawMouseLines = true;
    private boolean error;
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public double[] GetPointArray(){
        return PointArray;
    } 
    public boolean IsErrors(){
        return error;
    }
    public void setErrors(boolean e){
        error = e;
    }
    public Color getBackColor(){
        return BackgroundColor;
    }
    public void setBackColor(Color c){
        BackgroundColor = c;
    }
    public void setShowNumbers(boolean b){
        showNumbers = b;
    }
    public double getMinX(){
        return lowerX;
    }
    public double getMinY(){
        return lowerY;
    }
    public double getMaxX(){
        return upperX;
    }
    public double getMaxY(){
        return upperY;
    }
    public boolean getShowNUmbers(){
        return showNumbers;
    }
    public void setLineColour(Color c){
        LineColor = c;
    }
    public Color getLineColour(){
        return LineColor;
    }
    public void DrawMouseLines(boolean b){
        drawMouseLines = b;
    }
    public boolean getDrawMouseLines(){
        return drawMouseLines;
    }
    public void setIncrements(int x){
        increments = x;
    }
    public int getIncrements(){
        return increments;
    }
    public void setCurveThickness(float x){
        curveThickness = x;
    }
    public float getCurveThickness(){
        return curveThickness;
    }
    public void setOffSet(int x, int y){
        XOffSet = x ;
        YOffSet = y ;
    }
    public int getOffSetX(){
        return XOffSet;
    }
    public int getOffSetY(){
        return YOffSet;
    }
    public void SetOpacity(float opacity) {
        Opacity = opacity;
    }

    public int getCenterX(int w){
        return (int) ((-lowerX * GetXScale(w)) + XOffSet);
    }
    public int getCenterY(int h){
        return (int) (h + (lowerY * GetYScale(h)));
    }
    public float GetOpacity() {
        return Opacity;
    }

    public void SetDrawArea(boolean a) {
        DrawArea = a;
    }

    public boolean GetDrawArea() {
        return DrawArea;
    }

    public double GetXScale(int Width) {
        double n;
        double range = upperX - lowerX;
        n = (Width - XOffSet) / range;
        return n;
    }

    public double GetYScale(int Height) {
        double n;
        double range = upperY - lowerY;
        n = (Height - YOffSet) / range;
        return n;
    }

    public void GetMaximas() {
        lowerX = 0;//PointArray[0];
        upperX = 0;//PointArray[0];

        lowerY = 0;//PointArray[1];
        upperY = 0;//PointArray[1];
        for (int i = 0; i <= (PointArray.length-1); i += 2) {
            if (Double.isInfinite(PointArray[i])) {// || Double.isNaN(PointArray[i])){
                continue;
            }
            if (PointArray[i] < lowerX) {
                lowerX = PointArray[i];
            } else if (PointArray[i] > upperX) {
                upperX = PointArray[i];
            }
            if (Double.isInfinite(PointArray[i + 1])) {// || Double.isNaN(PointArray[i+1])){
                continue;
            }
            if (PointArray[i + 1] < lowerY) {
                lowerY = PointArray[i + 1];
            } else if (PointArray[i + 1] > upperY) {
                upperY = PointArray[i + 1];
            }
        }
    }

    public double GetY(double X) {
        for (int i = 2; i <= (PointArray.length - 1); i += 2) {
            if (PointArray[i] == X) {
                return PointArray[i + 1];
            }
        }
        return -1;
    }

    public int GetXIndex(double X) {
        for (int i = 2; i <= (PointArray.length - 1); i += 2) {
            if (PointArray[i] == X) {
                return i;
            }
        }
        return -1;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public LineGraph() {
    }

    public LineGraph(double array[]) {
        PointArray = array;
    }
    //</editor-fold>

    public void AddPoint(double x, double y, int index) {
        if (index > PointArray.length - 1) {
            index = PointArray.length;
        } else if (index < 0) {
            index = 0;
        }
        double temp[] = new double[PointArray.length + 2];
        for (int i = 0; i <= temp.length - 1; i++) {
            if (i > index + 1) {
                temp[i] = PointArray[i - 2];
            } else if (i < index) {
                temp[i] = PointArray[i];
            } else if (i == index) {
                temp[i] = x;
            } else {
                temp[i] = y;
            }
        }
        PointArray = temp;
    }

    public void RemovePoint(int index) {
        if (index > PointArray.length - 1 || index < 0) {
            return;
        }
        double temp[] = new double[PointArray.length - 2];
        for (int i = 0; i <= PointArray.length - 1; i += 2) {
            if (i / 2 == index) {
                continue;
            }
            temp[i] = PointArray[i];
            temp[i + 1] = PointArray[i + 1];
        }
    }

    public JPanel drawToJPanel(int width, int height) {
        width = ((width-XOffSet)/increments)*increments + XOffSet; //shearing anything not %increments
        height = ((height-YOffSet)/increments)*increments+YOffSet;
        JPanel p = new GraphPanel(this, width, height);
        p.setPreferredSize(new Dimension(width+XOffSet,height+YOffSet));
        p.setSize(width*2, height);
        return p;
    }

    public JPanel drawToJPanel(int width, int height, double minX,
            double minY, double maxX, double maxY) {
        width = ((width-XOffSet)/increments)*increments + XOffSet; //shearing anything not %increments
        height = ((height-YOffSet)/increments)*increments+YOffSet;
        GraphPanel p = new GraphPanel(this, width, height,
                minX, minY, maxX, maxY);
        p.setPreferredSize(new Dimension(width+XOffSet,height+YOffSet));
        p.setSize(width*2, height);
        return p;
    }

    public void drawLine(Graphics2D g, int width, int height) {
        GetMaximas();
        drawLine(g, width, height, lowerX, lowerY, upperX, upperY);
    }

    public void drawLine(Graphics2D g, int width, int height, double minX,
            double minY, double maxX, double maxY) {
        upperX = maxX;
        lowerX = minX;

        upperY = maxY;
        lowerY = minY;
        //debug, drawLine bounds
//        g.drawLine(XOffSet, YOffSet, width, YOffSet);
//        g.drawLine(XOffSet, height, width, height); //X
//
//        g.drawLine(XOffSet, YOffSet, XOffSet, height);
//        g.drawLine(width, YOffSet, width, height); //Y

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
    
    public void drawGrid(Graphics2D g, int width, int height){
        GetMaximas();
        drawGrid(g, width, height, lowerX, lowerY, upperX, upperY);
    }
    public void drawGrid(Graphics2D g, int width, int height, double minX,
            double minY, double maxX, double maxY){
         g.setColor(Color.BLACK);
         Color gridC; //= new Color(Color.white.getRGB() - BackgroundColor.getRGB());
         Color LightGridC;
         double lumin = BackgroundColor.getRed()*0.2126 + BackgroundColor.getGreen()* 0.7152 +
                 BackgroundColor.getBlue()*0.0722;
         if(lumin > 255/2d){
             gridC = Color.black;
             LightGridC = Color.GRAY;
         }else{
             gridC = Color.white;
             LightGridC = Color.lightGray;
         }
         g.setColor(gridC);
        upperX = maxX;
        lowerX = minX;

        upperY = maxY;
        lowerY = minY;
        //debug, drawLine bounds
//        g.drawLine(XOffSet, YOffSet, width, YOffSet);
//        g.drawLine(XOffSet, height, width, height); //X
//
//        g.drawLine(XOffSet, YOffSet, XOffSet, height);
//        g.drawLine(width, YOffSet, width, height); //Y
      //  g.drawLine(30, YOffSet, 30, 0);
        //g.drawLine(XOffSet, 30, 0, 30);
        double Xscale = GetXScale(width);
        double Yscale = GetYScale(height);
        int centerX = getCenterX(width);
        int centerY = getCenterY(height);//-lowerY*Yscale);

        // drawLine axis
        g.drawLine(XOffSet, centerY, width, centerY); //X axis
        g.drawLine(centerX, YOffSet, centerX, height); //Y axis
        
        
        //draws grid, numbers and increment lines
        int fontSizeX = (width/(increments*4));
        int fontSizeY = (height/increments);
        if(fontSizeX > 15){
            fontSizeX = 15;
        }
        if(fontSizeY > 15){
            fontSizeY = 15;
        }
        //x-axis
        int t = (int) ((width) * 0.01);
        g.setFont(new Font("MonoType", Font.PLAIN, (fontSizeX)));
        FontMetrics font = g.getFontMetrics();
        for (double i = XOffSet; i <= width; i += ((width-XOffSet) / increments)) {
            if (t == 0) {
                t = 1;
            }
            if(showGrid && i != centerX){
                g.setColor(LightGridC);
                g.drawLine((int) i, YOffSet, (int) i, height);
                g.setColor(gridC);
            }
            if(showScale){
            g.drawLine((int) i, (centerY + t), (int) i, (centerY - t));
            }
            
            if (showNumbers) {
                double d = Math.round(100 * ((((width - XOffSet) / Xscale) / (upperX - lowerX)
                        * (((i - XOffSet) / Xscale))) + lowerX));
                String o = Double.toString(
                         d / 100);
                int textWidth = font.stringWidth(o);
                g.drawString(o, (int) i - textWidth / 2, (centerY + t + font.getHeight()));
            }
        }
        //y axis
        t = (int) (height * 0.01);
        g.setFont(new Font("MonoType", Font.PLAIN, (fontSizeY)));
        font = g.getFontMetrics();
        for (double i = YOffSet; i <= height; i += ((height-YOffSet) / increments)) {
            if (t == 0) {
                t = 1;
            }
            if(showGrid && i != centerY){
                g.setColor(LightGridC);
                g.drawLine(XOffSet, (int) i, width, (int) i);
                g.setColor(gridC);
            }
            if(showScale){
            g.drawLine(centerX - t, (int) i, centerX + t, (int) i);
            }
            
            if (showNumbers) {
                double d =Math.round(100 * ((((upperY - lowerY) / ((height - YOffSet) / Yscale)
                        * (height -(i)) / Yscale) + lowerY)));
                String o = Double.toString(
                         d/ 100);
                int textWidth = font.stringWidth(o);
                g.drawString(o, centerX - t - textWidth, 
                        (int) ((i + font.getHeight() / 2)));
            }
        }
    }
    
   
    
    public void drawArea(Graphics2D g, int width, int height){
        GetMaximas();
        drawArea(g, width, height, lowerX, lowerY, upperX, upperY);
    }
    public void drawArea(Graphics2D g, int width, int height, double minX,
            double minY, double maxX, double maxY){
        upperX = maxX;
        lowerX = minX;

        upperY = maxY;
        lowerY = minY;
        //debug, drawLine bounds
//        g.drawLine(XOffSet, YOffSet, width, YOffSet);
//        g.drawLine(XOffSet, height, width, height); //X
//
//        g.drawLine(XOffSet, YOffSet, XOffSet, height);
//        g.drawLine(width, YOffSet, width, height); //Y

        double Xscale = GetXScale(width);
        double Yscale = GetYScale(height);
        int centerX = getCenterX(width);
        int centerY = getCenterY(height);
        
        //drawGrid(g, width, height, minX, minY, maxX, maxY);
        
        //create a polygon for every area between two zeros
        g.setColor(LineColor);
        ArrayList<Polygon> p = new ArrayList<>();
        p.add(new Polygon());
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                        Opacity));
        boolean isNeg = PointArray[1] < 0;
        p.get(0).addPoint(XOffSet, getCenterY(height));
        for(int i = 0; i <= PointArray.length-2; i+=2){
            double x = ((PointArray[i] * Xscale) + centerX);
            double y = ((centerY) - (PointArray[i + 1]) * Yscale);
            if(x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY){
                continue;
            }
            if(y == Double.POSITIVE_INFINITY || y == Double.NEGATIVE_INFINITY){
                y = height;
            }
            p.get(p.size()-1).addPoint((int)x,(int)y);
        }
        p.get(p.size()-1).addPoint(width, getCenterY(height));
        
       for(int i = 0; i <= p.size()-1; i++){
           g.fill(p.get(i));
       }
       g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
                                                       
//        Polygon p = new Polygon();
//        p.addPoint(width, height);
//        p.addPoint(XOffSet, height);
//        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
//                                                        Opacity));
//        for(int i = 0; i <= PointArray.length-2; i+=2){
//            double x = ((PointArray[i] * Xscale) + centerX);
//            double y = ((centerY) - (PointArray[i + 1]) * Yscale);
//            if(x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY){
//                continue;
//            }
//            if(y == Double.POSITIVE_INFINITY || y == Double.NEGATIVE_INFINITY){
//                y = height;
//            }
//            p.addPoint((int)x,(int)y);
//        }
//        
//        
//       g.fill(p);
//       g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
//                                                        1));
       drawLine(g, width, height, minX, minY, maxX, maxY);
    }
    
    public double GetRelativeX(int abs, int width){
        double scale = GetXScale(width);
//        int center = (int) ((-lowerX * scale) + XOffSet);
//        int delta = (abs)-(center-XOffSet);
//        return delta/scale;
        return ((((width) / scale) / (upperX - lowerX)
                        * (((abs - XOffSet) / scale))) + lowerX);
    }
    
    public double GetRelativeY(int abs, int height){
        double scale = GetYScale(height);
//        int center = (int) (height + (lowerY * scale));
//        int delta = (center-YOffSet/2)-abs;
//        return delta/scale;
        return ((((upperY - lowerY) / ((height-YOffSet*2) / scale)
                        * (height -(abs+YOffSet)) / scale) + lowerY));
    }
    
}

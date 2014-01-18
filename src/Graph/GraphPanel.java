package Graph;


import Graph.LineGraph;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GraphPanel extends JPanel {

    LineGraph g;
    public ArrayList<LineGraph> l = new ArrayList<>();
    int w;
    int h;
    double minX;
    double minY;
    double maxX;
    double maxY;
    boolean useBounds;

    GraphPanel(LineGraph G, int W, int H, double MinX, double MinY,
            double MaxX, double MaxY) {
        g = G;
        w = W;
        h = H;
        minX = MinX;
        maxX = MaxX;
        minY = MinY;
        maxY = MaxY;
        useBounds = true;
        this.setBackground(G.getBackColor());
    }

    GraphPanel(LineGraph G, int W, int H) {
        g = G;
        w = W;
        h = H;
        minX = 0;
        maxX = 0;
        minY = 0;
        maxY = 0;
        useBounds = false;
        this.setBackground(G.getBackColor());
    }

    @Override
    public void paintComponent(Graphics gr) {
        Graphics2D graphics = (Graphics2D)gr;
        super.paintComponent(gr);
        if(g.IsErrors()){
            return;
        }
        PointerInfo a = MouseInfo.getPointerInfo();
        if (g.getDrawMouseLines()) {
            int x = (int) (a.getLocation().getX() - this.getLocationOnScreen().getX());
            int y = (int) (a.getLocation().getY() - this.getLocationOnScreen().getY());
            if (x <= this.getWidth()-g.getOffSetX() && x >= g.getOffSetX() 
                    && y <= this.getHeight()-g.getOffSetY() && y >= g.getOffSetY()) {
                graphics.setColor(Color.black);
                graphics.drawLine(g.getOffSetX(), y, this.getWidth()-g.getOffSetX(), y);
                graphics.drawLine(x, g.getOffSetY(), x, this.getHeight()-g.getOffSetY());
            }
            
        }
        
        if (useBounds) {
            if (g.GetDrawArea()) {
                g.drawGrid(graphics, w, h, minX, minY, maxX, maxY);
                g.drawArea(graphics, w, h, minX, minY, maxX, maxY);
            } else {
                g.drawGrid(graphics, w, h, minX, minY, maxX, maxY);
                g.drawLine(graphics, w, h, minX, minY, maxX, maxY);
            }
        } else {
            if (g.GetDrawArea()) {
                g.drawGrid(graphics, w, h);
                g.drawArea(graphics, w, h);
            } else {
                g.drawGrid(graphics, w, h);
                g.drawLine(graphics, w, h);
            }
        }
        
        for(LineGraph line:l){
            if(line == null){
                continue;
            }
            if(line.GetDrawArea()){
                line.drawArea(graphics, w, h,g.getMinX(),g.getMinY(),g.getMaxX(),g.getMaxY());
            }else{
                line.drawLine(graphics, w, h,g.getMinX(),g.getMinY(),g.getMaxX(),g.getMaxY());
            }
        }
    }
}
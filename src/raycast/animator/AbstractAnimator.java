package raycast.animator;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import raycast.CanvasMap;
import raycast.entity.FpsCounter;
import utility.Point;

import java.util.Objects;

public abstract class AbstractAnimator extends AnimationTimer {

    protected CanvasMap map;
    protected Point mouse;
    private FpsCounter fps;

    public AbstractAnimator() {

        this.mouse = new Point();
        intersectResult = new double[4];
        fps = new FpsCounter(10, 25).setFill(Color.WHITE).setStroke(Color.YELLOW).setWidth(1);

    }

    public void setCanvas(CanvasMap map) {
        this.map = map;
    }

    public void mouseDragged(MouseEvent e) {
        mouse.set(e.getX(), e.getY());
    }


    public void mouseMoved(MouseEvent e) {
        mouse.set(e.getX(), e.getY());
    }

    public void handle(long now) {

        GraphicsContext gc = map.gc();

        if (map.getDrawFPS()) {
            fps.calculateFPS(now);
        }

        handle(map.gc(), now);

        if (map.getDrawShapeJoints() || map.getDrawBounds()) {

            for (int i = 0; i < map.shapes().size(); i++) {

                if (map.getDrawBounds()) {
                    map.shapes().get(i).getBounds();
                }

                if (map.getDrawShapeJoints()) {
                    map.shapes().get(i).drawCorners(gc);
                }

            }
        }

        if (map.getDrawFPS()) {
            fps.draw(gc);
        }

        if(map.getDrawLightSource()) {
            gc.setFill(Color.MAGENTA);
            gc.fillOval(mouse.x()-5, mouse.y()-5,10,10);
        }

    }

    protected abstract void handle(GraphicsContext gc, long now);

    protected double[] intersectResult;

    public double[] intersect() {
        Objects.requireNonNull(intersectResult, "intersectResult array must be initilized in the constructor.");
        if (intersectResult.length != 4) {
            throw new IllegalStateException("intersectResult must have length of 4");
        }
        return intersectResult;
    }

    public boolean getIntersection(double rsx, double rsy, double rex, double rey, double ssx, double ssy, double sex, double sey) {

        double qpx = rsx - ssx;
        double qpy = rsy - ssy;

        double rx = rex - rsx;
        double ry = rey - rsy;
        double sx = sex - ssx;
        double sy = sey - ssy;

        double qps = qpx * sy - sx * qpy;
        double qpr = qpx * ry - rx * qpy;

        double rs = rx * sy - sx * ry;

        double rayScaler = -qps / rs;
        double segmentScaler = -qpr / rs;

        intersectResult[0] = rsx + rx * rayScaler;
        intersectResult[1] = rsy + ry * rayScaler;
        intersectResult[2] = rayScaler;
        intersectResult[3] = segmentScaler;

        return rs != 0 && rayScaler >= 0 && segmentScaler >= 0 && segmentScaler <= 1;
    }

    public void clearAndFill(GraphicsContext gc, Color background) {

        gc.setFill(background);
        gc.clearRect(0, 0, map.w(), map.h());
        gc.fillRect(0, 0, map.w(), map.h());

    }

}

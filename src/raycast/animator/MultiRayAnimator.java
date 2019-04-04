package raycast.animator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import raycast.entity.geometry.PolyShape;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class MultiRayAnimator extends AbstractAnimator {

    private double[] intersectPoint = new double[4];

    @Override
    protected void handle(GraphicsContext gc, long now) {

        clearAndFill(gc, Color.BLACK);
        for (PolyShape s : map.shapes()) {
            s.draw(gc);
        }
        drawRays(gc, mouse.x(), mouse.y(), Color.WHITE);

    }

    public void drawLine(GraphicsContext gc, Color color, double sx, double sy, double ex, double ey) {

        gc.setLineWidth(2);
        gc.setStroke(color);
        gc.setFill(Color.MAGENTA);
        gc.strokeLine(sx, sy, ex, ey);

        if (map.getDrawIntersectPoint()) {
            gc.fillOval(ex - 5, ey - 5, 10, 10);
        }

    }

    public void drawRays(GraphicsContext gc, double startX, double startY, Color lightColor) {

        double endX;
        double endY;
        double rayIncrementer = 360d / map.getRayCount();

        for (double rayAngel = 0; rayAngel < 360; rayAngel += rayIncrementer) {

            endX = cos(Math.toRadians(rayAngel));
            endY = sin(Math.toRadians(rayAngel));

            for (PolyShape s : map.shapes()) {

                for (int i = 0, j = s.pointCount() - 1; i < s.pointCount(); i++, j = i - 1) {

                    if(getIntersection(
                            startX, startY,
                            startX + endX, startY + endY,
                            s.pX(i), s.pY(i), s.pX(j), s.pY(j)
                    )){

                        if(intersectPoint[2] > intersectResult[2]){
                            System.arraycopy(intersectResult, 0, intersectPoint, 0, intersectPoint.length);
                        }

                    }

                }

            }

            drawLine(gc, lightColor, startX, startY, intersectPoint[0], intersectPoint[1]);
            intersectPoint[2] = Double.MAX_VALUE;

        }


    }

    public String toString() {
        return "Multiray Animator";
    }

}

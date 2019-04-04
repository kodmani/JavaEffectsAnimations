package raycast.entity.geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import raycast.entity.DrawableObject;
import utility.Point;
import utility.RandUtil;

public class PolyShape implements DrawableObject<PolyShape> {

    private int pointCount;
    private double[][] points;
    private double minX, minY, maxX, maxY, strokeWidth;
    private Color fill;
    private Color stroke;
    private RectangleBounds bounds;

    public PolyShape() {

        strokeWidth = 1;
        fill = null;
        stroke = Color.BLACK;

    }

    public void drawCorners(GraphicsContext gc) {

        gc.setFill(Color.WHITE);

        for (int i = 0; i < pointCount; i++) {

            gc.fillText(Integer.toString(i), points[0][i] - 5, points[1][i] - 5);
            gc.fillOval(points[0][i] - 5, points[1][i] - 5, 10, 10);

        }

    }

    @Override
    public void draw(GraphicsContext gc) {

        gc.setLineWidth(strokeWidth);

        if (stroke != null) {

            gc.setStroke(stroke);
            gc.strokePolygon(points[0], points[1], pointCount);

        }

        if (fill != null) {

            gc.setFill(fill);
            gc.fillPolygon(points[0], points[1], pointCount);

        }

    }

    public double pX(int index) {
        return points[0][index];
    }

    public double pY(int index) {
        return points[1][index];
    }

    public PolyShape setPoints(double... nums) {

        minX = maxX = nums[0];
        minY = maxY = nums[1];

        pointCount = nums.length / 2;

        points = new double[2][pointCount];

        for (int i = 0, j = 0; i < nums.length; j++, i += 2) {

            updateMinMax(nums[i], nums[i + 1]);
            points[0][j] = nums[i];
            points[1][j] = nums[i + 1];

        }

        bounds = new RectangleBounds(minX, minY, maxX - minX, maxY - minY);

        return this;
    }

    public void updateMinMax(double x, double y) {

        if (x < minX) {
            x = minX;
        } else if (x > maxX) {
            x = maxX;
        }
        if (y < minY) {
            y = minY;
        } else if (y > maxY) {
            y = maxY;
        }

    }

    public int pointCount() {
        return pointCount;
    }

    public double[][] points() {
        return points;
    }

    public RectangleBounds getBounds() {
        return bounds;
    }

    @Override
    public PolyShape setFill(Color color) {
        fill = color;
        return this;
    }

    @Override
    public PolyShape setStroke(Color color) {
        stroke = color;
        return this;
    }

    @Override
    public PolyShape setWidth(double width) {
        strokeWidth = width;
        return this;
    }

    @Override
    public Color getFill() {
        return fill;
    }

    @Override
    public Color getStroke() {
        return stroke;
    }

    @Override
    public double getWidth() {
        return strokeWidth;
    }

    public PolyShape randomize(double x, double y, double size, int minPoints, int maxPoints) {

        pointCount = RandUtil.getInt(minPoints, maxPoints);
        Point center = new Point(x, y);
        double[] angle = new double[pointCount];
        points = new double[2][pointCount];
        for (int i = 0; i < pointCount; i++) {
            Point p = center.randomPoint(size);
            ;
            angle[i] = center.angle(p);
            points[0][i] = p.x();
            points[1][i] = p.y();
            updateMinMax(p.x(), p.y());

        }

        for (int i = 0; i < pointCount; i++) {
            for (int j = 1; j < pointCount - i; j++) {
                if (angle[j - 1] > angle[j]) {
                    double tempa = angle[j - 1];
                    angle[j - 1] = angle[j];
                    angle[j] = tempa;

                    double tempx = points[0][j - 1];
                    double tempy = points[1][j - 1];
                    points[0][j - 1] = points[0][j];
                    points[0][j] = tempx;
                    points[1][j - 1] = points[1][j];
                    points[1][j] = tempy;
                }
            }
        }

        this.bounds = new RectangleBounds(minX, minY, maxX - minX, maxY - minY);

        return this;
    }


}
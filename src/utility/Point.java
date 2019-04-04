package utility;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Objects;

public class Point {

    private DoubleProperty x, y;

    public Point() {
        this(new SimpleDoubleProperty(), new SimpleDoubleProperty());
    }

    private Point(DoubleProperty x, DoubleProperty y) {
        Objects.requireNonNull(x, "x property cannot be null");
        Objects.requireNonNull(y, "y property cannot be null");
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y) {
        this(new SimpleDoubleProperty(x), new SimpleDoubleProperty(y));
    }

    public void translate(double dx, double dy) {
        x(x() + dx);
        y(y() + dy);
    }

    public void set(double x, double y) {
        x(x);
        y(y);
    }

    public double x() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public double y() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public Point x(double x) {
        this.x.set(x);
        return this;
    }

    public Point y(double y) {
        this.y.set(y);
        return this;
    }

    public double angle(Point p) {
        return Math.atan2(y() - p.y(), x() - p.x());
    }

    public double[] toArray() {
        return new double[]{x(), y()};
    }

    public Point randomPoint(double maxRadius) {
        double angle = RandUtil.getDouble(360);
        double randR = RandUtil.getDouble(maxRadius);
        double dx = x() + randR * Math.cos(Math.toRadians(angle));
        double dy = y() - randR * Math.sin(Math.toRadians(angle));
        return new Point(dx, dy);
    }

    public Point randomPoint(double maxDx, double maxDy) {
        double dx = x() + RandUtil.getDouble(maxDx) * RandUtil.getPosNeg();
        double dy = y() + RandUtil.getDouble(maxDy) * RandUtil.getPosNeg();
        return new Point(dx, dy);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Double.hashCode((x == null) ? 0 : x());
        result = prime * result + Double.hashCode((y == null) ? 0 : y());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Point))
            return false;
        Point other = (Point) obj;
        if (x == null) {
            if (other.x != null)
                return false;
        } else if (x != other.x)
            return false;
        if (y == null) {
            if (other.y != null)
                return false;
        } else if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(" + x() + "," + y() + ")";
    }

    public static Point random(double w, double h, double margin) {
        return random(w, h, margin, margin);
    }

    public static Point random(double w, double h, double marginX, double marginY) {
        return new Point()
                .x(RandUtil.getDouble(marginX, w - marginX))
                .y(RandUtil.getDouble(marginY, h - marginY));
    }

}

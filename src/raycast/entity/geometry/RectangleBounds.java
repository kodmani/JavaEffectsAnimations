package raycast.entity.geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import raycast.entity.DrawableObject;
import utility.Point;

public class RectangleBounds implements DrawableObject<RectangleBounds> {

    private Point start, dimension;
    private Color fill, stroke;
    private double strokeWidth;

    public RectangleBounds() {
        start = new Point();
        dimension = new Point();
        fill = Color.LIGHTPINK;
        stroke = Color.RED;
        strokeWidth = 2;
    }

    public RectangleBounds(double x, double y, double w, double h) {
        this();
        start.set(x, y);
        dimension.set(w, h);
    }

    public void translate(double dx, double dy) {
        start.translate(dx, dy);
    }

    public Point pos() {
        return start;
    }

    public RectangleBounds pos(double x, double y) {
        start.set(x, y);
        return this;
    }

    public Point dimension() {
        return dimension;
    }

    public RectangleBounds dimension(double w, double h) {
        dimension.set(w, h);
        return this;
    }

    public double x() {
        return start.x();
    }

    public double centerX() {
        return start.x() + w() / 2;
    }

    public double y() {
        return start.y();
    }

    public double centerY() {
        return start.y() + h() / 2;
    }

    public double h() {
        return dimension.y();
    }

    public double hPos() {
        return h() + y();
    }

    public double w() {
        return dimension.x();
    }

    public double wPos() {
        return w() + x();
    }

    public boolean intersects(RectangleBounds b) {
        return intersects(b.x(), b.y(), b.w(), b.h());
    }

    public boolean intersects(double x, double y, double w, double h) {
        return !(x > wPos() || y > hPos() || x() > x + w || y() > y + h);
    }

    public boolean contains(RectangleBounds rect) {
        return contains(rect.x(), rect.y(), rect.w(), rect.h());
    }

    public boolean contains(double x, double y, double w, double h) {

        return x >= x() && x + w <= wPos() && y >= y() && y + h <= hPos();
    }

    public boolean contains(Point p) {
        return contains(p.x(), p.y());
    }

    public boolean contains(double x, double y) {
        return x >= x() && x <= wPos() && y >= y() && y <= hPos();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Double.hashCode((start == null) ? 0 : x());
        result = prime * result + Double.hashCode((start == null) ? 0 : y());
        result = prime * result + Double.hashCode((dimension == null) ? 0 : w());
        result = prime * result + Double.hashCode((dimension == null) ? 0 : h());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof RectangleBounds))
            return false;
        RectangleBounds other = (RectangleBounds) obj;
        if (dimension == null) {
            if (other.dimension != null)
                return false;
        } else if (!dimension.equals(other.dimension))
            return false;
        if (start == null) {
            if (other.start != null)
                return false;
        } else if (!start.equals(other.start))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("start:%s, size:%s", start, dimension);
    }

    @Override
    public RectangleBounds setFill(Color color) {
        fill = color;
        return this;
    }

    @Override
    public RectangleBounds setStroke(Color color) {
        stroke = color;
        return this;
    }

    @Override
    public RectangleBounds setWidth(double width) {
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

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(stroke);
        gc.setLineWidth(strokeWidth);
        gc.strokeRect(x(), y(), w(), h());
    }
}

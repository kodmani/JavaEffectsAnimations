package raycast.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class FpsCounter implements DrawableObject<FpsCounter> {

    public static final double ONE_SECOND = 1000000000L;
    public static final double HALF_SECOND = ONE_SECOND / 2F;

    private Font fpsFont;
    private String fpsDisplay;
    private int frameCount;
    private double lastTime;
    private double strokeWidth;
    private Color fill;
    private Color stroke;
    private double x, y;

    public FpsCounter(double x, double y) {

        setPos(x, y);
        Font.font(Font.getDefault().getFamily(), FontWeight.BLACK, 24);

    }

    public void calculateFPS(long now) {

        if (now - lastTime > HALF_SECOND) {
            fpsDisplay = Integer.toString(frameCount * 2);
            frameCount = 0;
            lastTime = now;
        }

        frameCount++;
    }

    public FpsCounter setFont(Font font) {
        fpsFont = font;
        return this;
    }

    public FpsCounter setPos(double x, double y) {

        this.x = x;
        this.y = y;
        return this;

    }

    @Override
    public void draw(GraphicsContext gc) {

        Font temp = gc.getFont();
        gc.setFont(fpsFont);
        gc.setFill(fill);
        gc.fillText(fpsDisplay, x, y);
        gc.setStroke(stroke);
        gc.setLineWidth(strokeWidth);
        gc.strokeText(fpsDisplay, x, y);
        gc.setFont(temp);

    }

    @Override
    public FpsCounter setFill(Color color) {
        fill = color;
        return this;
    }

    @Override
    public FpsCounter setStroke(Color color) {
        stroke = color;
        return this;
    }

    @Override
    public FpsCounter setWidth(double width) {
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

}

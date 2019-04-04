package raycast;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import raycast.animator.AbstractAnimator;
import raycast.entity.geometry.PolyShape;

import java.util.ArrayList;
import java.util.List;

public class CanvasMap {

    private static Canvas board;
    private AbstractAnimator animator;
    private IntegerProperty rayCount;
    private BooleanProperty drawLightSource;
    private BooleanProperty drawIntersectPoint;
    private BooleanProperty drawShapeJoints;
    private BooleanProperty drawSectors;
    private BooleanProperty drawBounds;
    private BooleanProperty drawFPS;
    private List<PolyShape> shapes;

    public CanvasMap() {

        shapes = new ArrayList<PolyShape>(20);
        board = new Canvas();
        rayCount = new SimpleIntegerProperty();
        drawLightSource = new SimpleBooleanProperty();
        drawIntersectPoint = new SimpleBooleanProperty();
        drawShapeJoints = new SimpleBooleanProperty();
        drawSectors = new SimpleBooleanProperty();
        drawBounds = new SimpleBooleanProperty();
        drawFPS = new SimpleBooleanProperty();

    }

    public boolean getDrawLightSource() {
        return drawLightSource.get();
    }

    public BooleanProperty drawLightSourceProperty() {
        return drawLightSource;
    }

    public boolean getDrawIntersectPoint() {
        return drawIntersectPoint.get();
    }

    public BooleanProperty drawIntersectPointProperty() {
        return drawIntersectPoint;
    }

    public boolean getDrawShapeJoints() {
        return drawShapeJoints.get();
    }

    public BooleanProperty drawShapeJointsProperty() {
        return drawShapeJoints;
    }

    public boolean getDrawSectors() {
        return drawSectors.get();
    }

    public BooleanProperty drawSectorsProperty() {
        return drawSectors;
    }

    public BooleanProperty drawBoundsProperty() {
        return drawBounds;
    }

    public boolean getDrawBounds() {
        return drawBounds.get();
    }

    public boolean getDrawFPS() {
        return drawFPS.get();
    }

    public BooleanProperty drawFPSProperty() {
        return drawFPS;
    }

    public AbstractAnimator getAnimator() {
        return animator;
    }

    public IntegerProperty rayCountProperty() {
        return rayCount;
    }

    public int getRayCount() {
        return rayCount.get();
    }

    public CanvasMap setAnimator(AbstractAnimator newAnimator) {

        if (animator != null) {
            stop();
            removeMouseEvents();
        }

        this.animator = newAnimator;
        start();
        registerMouseEvents();
        return this;

    }

    public void registerMouseEvents() {
        board.addEventHandler(MouseEvent.MOUSE_MOVED, (e) -> animator.mouseMoved(e));
        board.addEventHandler(MouseEvent.MOUSE_DRAGGED, animator::mouseDragged);
    }

    public void removeMouseEvents() {
        board.removeEventHandler(MouseEvent.MOUSE_MOVED, (e) -> animator.mouseMoved(e));
        board.removeEventHandler(MouseEvent.MOUSE_DRAGGED, animator::mouseDragged);
    }

    public <E extends Event> void addEventHandler(EventType<E> event, EventHandler<E> handler) {
        board.addEventHandler(event, handler);
    }

    public <E extends Event> void removeEventHandler(EventType<E> event, EventHandler<E> handler) {
        board.removeEventHandler(event, handler);
    }

    public void start() {
        animator.start();
    }

    public void stop() {
        animator.stop();
    }

    public Canvas getCanvas() {
        return board;
    }

    public GraphicsContext gc() {
        return board.getGraphicsContext2D();
    }

    public double h() {
        return board.getHeight();
    }

    public double w() {
        return board.getWidth();
    }

    public List<PolyShape> shapes() {
        return shapes;
    }

    public void addSampleShapes() {

//		PolyShape ps1 = new PolyShape().setPoints(
//				100, 40, //0
//				40, 100, //1
//				180, 140 //2
//				).setFill(Color.GREEN);
//		
//		PolyShape ps2 = new PolyShape().setPoints(
//				150, 350, //0
//				50, 430, //1
//				200, 600, //2
//				200, 450 //3
//				).setFill(Color.BLUE);
//		
//		PolyShape ps3 = new PolyShape().setPoints(
//				550, 400, //0 
//				480, 430, //1
//				600, 450, //2
//				650, 400, //3
//				600, 350 //4
//				).setFill(Color.RED);
//		
//		PolyShape ps4 = new PolyShape().setPoints(
//				550, 100, //0 
//				480, 130, //1
//				600, 150, //2
//				650, 100 //3
//				).setFill(Color.PURPLE);

        PolyShape shape1 = new PolyShape().randomize(100, 100, 100, 6, 7)
                .setFill(Color.PINK).setStroke(Color.GREY).setWidth(5);

        PolyShape shape2 = new PolyShape().randomize(500, 300, 100, 6, 7)
                .setFill(Color.LIGHTGREEN).setStroke(Color.GREY).setWidth(5);

        PolyShape shape3 = new PolyShape().randomize(500, 500, 100, 6, 7)
                .setFill(Color.ORANGE).setStroke(Color.GREY).setWidth(5);

        PolyShape shape4 = new PolyShape().randomize(200, 300, 100, 6, 7)
                .setFill(Color.YELLOW).setStroke(Color.GREY).setWidth(5);


//		shapes.add(ps1);
//		shapes.add(ps2);
//		shapes.add(ps3);
//		shapes.add(ps4);

        shapes.add(shape1);
        shapes.add(shape2);
        shapes.add(shape3);
        shapes.add(shape4);

    }

}

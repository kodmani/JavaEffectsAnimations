package raycast.animator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StaticShapes extends AbstractAnimator {

    public static final Color BACKGROUND = Color.PINK;

    @Override
    public void handle(GraphicsContext gc, long now) {

        clearAndFill(gc, BACKGROUND.brighter());

        for (int i = 0; i < map.shapes().size(); i++) {
            map.shapes().get(i).draw(gc);
        }

    }

    public String toString() {
        return "Static Shapes";
    }


}

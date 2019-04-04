package raycast.animator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TextAnimator extends AbstractAnimator {

	@Override
	public void handle(GraphicsContext gc, long now) {
		
		gc.save();
		Font.font( gc.getFont().getFamily(), FontWeight.BLACK, 50);
		gc.setFont(Font.font("Futura", 60));
		gc.setFill(javafx.scene.paint.Color.BLACK);
		gc.fillText("CST 8288 - Ray Cast", mouse.x(), mouse.y());
		gc.setStroke(javafx.scene.paint.Color.RED);
		gc.strokeText("CST 8288 - Ray Cast", mouse.x(), mouse.y());
		gc.restore();
		
	}

	@Override
	public String toString() {
		return "Text Animator";
	}
}

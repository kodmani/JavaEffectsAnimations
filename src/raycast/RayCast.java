package raycast;


import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import raycast.animator.AbstractAnimator;
import raycast.animator.MultiRayAnimator;
import raycast.animator.StaticShapes;
import raycast.animator.TextAnimator;

public class RayCast extends Application {

	private double width = 700, height = 700;
	private String title = "RayCast";
	private Color background = Color.LIGHTPINK;
	private BorderPane root;
	private CanvasMap board;
	private ChoiceBox<AbstractAnimator> animatorsBox;
	private ObservableList<AbstractAnimator> animators;

	@Override
	public void init() throws Exception {

		animators = FXCollections.observableArrayList(new TextAnimator(), new StaticShapes(), new MultiRayAnimator());

		board = new CanvasMap();

		ToolBar statusBar = createStatusBar();
		ToolBar optionsBar = createOptionsBar();

		root = new BorderPane();

		root.setTop(optionsBar);

		root.setCenter(board.getCanvas());

		root.setBottom(statusBar);

		board.getCanvas().widthProperty().bind(root.widthProperty());

		board.getCanvas().heightProperty().bind(root.heightProperty()
				.subtract(statusBar.heightProperty())
				.subtract(optionsBar.heightProperty()));

		for (AbstractAnimator a : animators) {
			a.setCanvas(board);
		}
		board.addSampleShapes();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Scene scene = new Scene(root, width, height, background);
		primaryStage.setScene(scene);
		primaryStage.setTitle(title);
		primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if (KeyCode.ESCAPE == event.getCode()) {
				primaryStage.hide();
			}
		});

		primaryStage.show();

		animatorsBox.getSelectionModel().select(2);

	}

	@Override
	public void stop() throws Exception {
		board.stop();
	}

	public ToolBar createOptionsBar() {
		
		Button startB = createButton("start", a->board.start());
		
		Button stopB = createButton("stop", b->board.stop());
		
		Pane filler1 = new Pane();
		Pane filler2 = new Pane();
		
		HBox.setHgrow(filler1, Priority.ALWAYS);
		
		HBox.setHgrow(filler2, Priority.ALWAYS);
		
		Spinner<Integer> rayCount = new Spinner<>(1, Integer.MAX_VALUE, 360*3);
		
		rayCount.setEditable(true);
		
		rayCount.setMaxWidth(100);
		
		board.rayCountProperty().bind(rayCount.valueProperty());
		
		CheckMenuItem fps = createCheckMenuItem("fps", true, board.drawFPSProperty());
		CheckMenuItem intersects = createCheckMenuItem("intersects", false, board.drawIntersectPointProperty());
		CheckMenuItem lights = createCheckMenuItem("lights", false, board.drawLightSourceProperty());
		CheckMenuItem joints = createCheckMenuItem("joints", false, board.drawShapeJointsProperty());
		CheckMenuItem bounds = createCheckMenuItem("bounds", false, board.drawBoundsProperty());
		CheckMenuItem sectors = createCheckMenuItem("sectors", false, board.drawSectorsProperty());
		
		MenuButton menuButton = new MenuButton("options", null, fps, intersects, lights, joints, bounds, sectors);
		
		animatorsBox = new ChoiceBox<AbstractAnimator>(animators);

		animatorsBox.getSelectionModel().selectedItemProperty().addListener((v,o,n)->board.setAnimator(n));
		
		ToolBar toolBar = new ToolBar(
				startB, stopB, filler1, rayCount, 
				menuButton, filler2, new Label( "Animators "), animatorsBox
				);
		
		return toolBar;

	}


	public ToolBar createStatusBar() {
		
		Label mouseCoordLabel = new Label("0,0");
		Label dragCoordLabel = new Label("0,0");

		board.addEventHandler(MouseEvent.MOUSE_MOVED, e->{
			mouseCoordLabel.setText(e.getX() + "   " + e.getY());
		});
		
		board.addEventHandler(MouseEvent.MOUSE_DRAGGED,e->{
			dragCoordLabel.setText(e.getX() + "   " + e.getY());
		});

		ToolBar toolBar = new ToolBar(new Label("Mouse:"), mouseCoordLabel, new Label("Drag: "), dragCoordLabel);
		
		return toolBar;

	}

	public CheckMenuItem createCheckMenuItem(String text, boolean isSelected, BooleanProperty binding) {
		
		CheckMenuItem txt = new CheckMenuItem(text);
		
		binding.bind(txt.selectedProperty());
		
		txt.setSelected(isSelected);
		
		return txt;
	}

	
	public Button createButton(String text, EventHandler<ActionEvent> action) {

		Button button = new Button(text);
		button.setOnAction(action);
		return button;

	}

	public static void main(String[] args) {
		launch(args);
	}

}

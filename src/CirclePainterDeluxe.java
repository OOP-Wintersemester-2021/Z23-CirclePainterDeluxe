import config.Settings;
import config.Theme;
import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.events.MousePressedEvent;
import de.ur.mi.oop.graphics.Circle;
import de.ur.mi.oop.graphics.Label;
import de.ur.mi.oop.launcher.GraphicsAppLauncher;
import de.ur.mi.oop.utils.DebugInfo;

import java.util.ArrayList;

public class CirclePainterDeluxe extends GraphicsApp implements MovingCircle.MovingCircleListener {

    private ArrayList<MovingCircle> circles;
    private ArrayList<MovingCircle> circlesToBeRemoved;
    private Label infoLabel;

    @Override
    public void initialize() {
        setCanvasSize((int) Settings.WINDOW_WIDTH, (int) Settings.WINDOW_HEIGHT);
        circles = new ArrayList<>();
        circlesToBeRemoved = new ArrayList<>();
        infoLabel = new Label(24, 24, "", Theme.RED);
        infoLabel.setFont("Arial Rounded MT Bold");
        infoLabel.setFontSize(14);
    }

    @Override
    public void draw() {
        drawBackground(Theme.GREY);
        updateCircles();
        clearCircles();
        drawCircles();
        infoLabel.draw();
        infoLabel.setText("circles in scene: " + circles.size());
    }

    private void updateCircles() {
        for(MovingCircle circle: circles) {
            circle.update(getWidth(), getHeight());
        }
    }

    private void clearCircles() {
        circles.removeAll(circlesToBeRemoved);
        circlesToBeRemoved.clear();
    }

    private void drawCircles() {
        for(MovingCircle circle: circles) {
            circle.draw();
        }
    }


    @Override
    public void onMousePressed(MousePressedEvent event) {
        circles.add(new MovingCircle(event.getXPos(), event.getYPos(), this));
    }

    @Override
    public void hasLeftScene(MovingCircle circle) {
        circlesToBeRemoved.add(circle);
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch("CirclePainterDeluxe");
    }

}

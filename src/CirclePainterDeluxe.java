import config.Settings;
import config.Theme;
import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.launcher.GraphicsAppLauncher;

public class CirclePainterDeluxe extends GraphicsApp {


    @Override
    public void initialize() {
        setCanvasSize((int) Settings.WINDOW_WIDTH, (int) Settings.WINDOW_HEIGHT);
    }

    @Override
    public void draw() {
        drawBackground(Theme.GREY);
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch("CirclePainterDeluxe");
    }
}

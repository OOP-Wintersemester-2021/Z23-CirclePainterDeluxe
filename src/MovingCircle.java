import config.Theme;
import de.ur.mi.oop.graphics.Circle;

public class MovingCircle {

    private final float CIRCLE_RADIUS = 20;
    private final float MIN_SPEED = 1;
    private final float MAX_SPEED = 4;

    private final Circle shape;
    private final float xVelocity;
    private final float yVelocity;
    private final MovingCircleListener listener;

    public MovingCircle(float x, float y, MovingCircleListener listener) {
        this.shape = new Circle(x, y, CIRCLE_RADIUS, Theme.YELLOW);
        this.xVelocity = createRandomVelocity();
        this.yVelocity = createRandomVelocity();
        this.listener = listener;
    }

    private float createRandomVelocity() {
        float v = (float) (MIN_SPEED + Math.random() * (MAX_SPEED - MIN_SPEED));
        if(Math.random() > 0.5) {
            return -v;
        }
        return v;
    }

    public void update(float sceneWidth, float sceneHeight) {
        shape.move(xVelocity, yVelocity);
        if(shape.getLeftBorder() > sceneWidth || shape.getTopBorder() > sceneHeight || shape.getRightBorder() < 0 || shape.getBottomBorder() < 0) {
            listener.hasLeftScene(this);
        }
    }

    public void draw() {
        shape.draw();
    }

    public interface MovingCircleListener {

        void hasLeftScene(MovingCircle circle);

    }
}

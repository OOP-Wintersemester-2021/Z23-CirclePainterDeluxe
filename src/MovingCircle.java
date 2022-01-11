import config.Theme;
import de.ur.mi.oop.graphics.Circle;

/**
 * Diese Klasse repräsentiert einen Kreis, der sich mit zufälliger Geschwindigkeit über
 * die Zeichenfläche bewegt. Über eine einfache Listener-Schnittstelle informiert der
 * Kreis selbstständig einen registrierten Listener (Observer), sobald er sich durch
 * die regelmäßig stattfindende Bewegung (siehe update-Methode) vollständig aus dem sichtbaren
 * Bereich der Zeichenfläche entfernt hat.
 */
public class MovingCircle {

    private final float CIRCLE_RADIUS = 20; // Radius der Kreise
    private final float MIN_SPEED = 1; // Minimale Geschwindigkeit (absoluter Wert)
    private final float MAX_SPEED = 4; // Maximale Geschwindigkeit (absoluter Wert)

    private final Circle shape; // Circle zur Darstellung des Kreises
    private final float xVelocity; // Geschwindigkeit des Kreises auf der X-Achse (Pixel per Frame)
    private final float yVelocity; // Geschwindigkeit des Kreises auf der Y-Achse (Pixel per Frame)
    private final MovingCircleListener listener; // Listener, der informiert werden soll, wenn Kreis Zeichenfläche verlässt

    /**
     * Konstruktor für neue "Moving Circles". Als Parameter wird die Bildschirmposition übergeben,
     * an der der neue Kreis initial angezeigt werden soll. Zusätzlich wird ein Listener übergeben,
     * der vom Kreis informiert wird, sobald sich dieser später aus dem Bildschirm heraus bewegt. Alle
     * weiteren Eigenschaften werden zufällige im Konstruktor bestimmt.
     * @param x Initiale Position des Kreises auf der X-Achse
     * @param y Initiale Position des Kreises auf der Y-Achse
     * @param listener Listener, der über das Verlassen der Zeichenfläche informiert werden soll
     */
    public MovingCircle(float x, float y, MovingCircleListener listener) {
        this.shape = new Circle(x, y, CIRCLE_RADIUS, Theme.YELLOW);
        this.xVelocity = createRandomVelocity();
        this.yVelocity = createRandomVelocity();
        this.listener = listener;
    }

    /**
     * Diese Methode gibt eine zufällige Geschwindigkeit für die Bewegung des Kreises auf einer der Achsen zurück.
     * Dabei wird zuerst ein zufälliger, absoluter Wert, auf Basis der Grenzwerte in MIN_SPEED und MAX_SPEED bestimmt.
     * Im Anschluss wird, ebenfalls zufällig, die Bewegungsrichtung festgelegt, in dem der absolute Wert mit einer
     * Wahrscheinlichkeit von p = 0.5 mit einem negativen Vorzeichen versehen wird.
     * @return Die zufällig bestimmte Geschwindigkeit (inkl. Bewegungsrichtung)
     */
    private float createRandomVelocity() {
        float v = (float) (MIN_SPEED + Math.random() * (MAX_SPEED - MIN_SPEED));
        if(Math.random() > 0.5) {
            return -v;
        }
        return v;
    }

    /**
     * Diese Methode bewegt den Kreis auf Basis der im Konstruktor festgelegten Geschwindigkeiten. Falls der Kreis
     * nach der Bewegung die sichtbare Zeichenfläche verlassen hat, wird der im Konstruktor gespeicherte Listener
     * informiert. Grundlage für diese Prüfung sind die Dimensionen der Zeichenfläche, die als Parameter an diese
     * Methode übergeben werden.
     * @param sceneWidth Breite der Zeichenfläche in Pixel, auf der der Kreis sich bewegt
     * @param sceneHeight Höhe der Zeichenfläche in Pixel, auf der der Kreis sich bewegt
     */
    public void update(float sceneWidth, float sceneHeight) {
        shape.move(xVelocity, yVelocity);
        /*
         * Hier prüfen wir anhand der jeweiligen Grenzbereiche des Kreises (Boundingbox), ob dieser sich noch
         * vollständig in der Zeichenfläche befindet, die über die übergebenen Parameter definiert wird.
         */
        if(shape.getLeftBorder() > sceneWidth || shape.getTopBorder() > sceneHeight || shape.getRightBorder() < 0 || shape.getBottomBorder() < 0) {
            listener.hasLeftScene(this); // Kommunikation mit dem Listener, falls Kreis die Zeichenfläche verlassen haben sollte
        }
    }

    public void draw() {
        shape.draw();
    }

    /**
     * Interface, mit dem sich der Listener gegenüber dem MovingCircle ausgibt. Klassen und Interfaces können, wie hier,
     * auch als (öffentlicher) Teil einer bereits bestehenden Klasse definiert werden. Häufig wird dies, statt der Definition
     * in einer separaten Datei, verwendet, wenn es sich um sehr übersichtliche Interfaces handelt, die in einer direkten
     * Beziehung zur umschließenden Klasse stehen. Das ist hier auch der Fall: Das Interface definiert nur eine Methode und bezieht
     * sich explizit auf die Klasse MovingCircle.
     */
    public interface MovingCircleListener {

        void hasLeftScene(MovingCircle circle);

    }
}

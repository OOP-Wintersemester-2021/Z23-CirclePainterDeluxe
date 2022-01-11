import config.Settings;
import config.Theme;
import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.events.MousePressedEvent;
import de.ur.mi.oop.graphics.Circle;
import de.ur.mi.oop.graphics.Label;
import de.ur.mi.oop.launcher.GraphicsAppLauncher;
import de.ur.mi.oop.utils.DebugInfo;

import java.util.ArrayList;

/**
 * In diesem Beispiel wird eine Liste sich bewegender Kreise verwaltet. Bei Mausklicks werden an der
 * Position des Mauszeigers neue Kreise erstellt, die im Rahmen des Draw-Loops bewegt und gezeichnet werden.
 * Verlässt ein Kreis dabei die Zeichenfläche, wird er entfernt.
 */
public class CirclePainterDeluxe extends GraphicsApp implements MovingCircle.MovingCircleListener {

    private ArrayList<MovingCircle> circles; // Liste aller darzustellenden Kreise
    private ArrayList<MovingCircle> circlesToBeRemoved; // Liste der Kreise, die vor dem nächsten Zeichnen gelöscht werden sollen
    private Label infoLabel; // Label, in dem die aktuelle Anzahl an sichtbaren Kreisen angezeigt wird

    @Override
    public void initialize() {
        setCanvasSize((int) Settings.WINDOW_WIDTH, (int) Settings.WINDOW_HEIGHT);
        circles = new ArrayList<>(); // Erstellen einer neuen, leeren ArrayList für die Kreise
        circlesToBeRemoved = new ArrayList<>(); // Erstellen einer neuen, leeren ArrayList für die zu löschenden Kreise
        infoLabel = new Label(24, 24, "", Theme.RED);
        infoLabel.setFont("Arial Rounded MT Bold");
        infoLabel.setFontSize(14);
    }

    @Override
    public void draw() {
        drawBackground(Theme.GREY);
        updateCircles(); // Alle Kreise werden bewegt (Falls ein Kreis dabei den Bildschirm verlässt, erfahren wir das in "hasLeftScene")
        clearCircles(); // Entfernen aller Kreise, die durch updateCircles den Bildschirm verlassen haben
        drawCircles(); // Zeichnen aller verbleibenden Kreise
        infoLabel.setText("circles in scene: " + circles.size());
        infoLabel.draw();
    }

    private void updateCircles() {
        for(MovingCircle circle: circles) {
            circle.update(getWidth(), getHeight());
        }
    }

    private void clearCircles() {
        /*
         * Hier machen wir vollen Gebrauch von den sehr einfachen Methoden der ArrayList:
         * - Wir entfernen alle Kreise aus circles, die auch in circlesToBeRemoved gespeichert sind
         * - Im Anschluss leeren wir die circleToBeRemoved-Liste, damit wird im nächsten Frame hier erneut die zu löschenden Kreise sammeln können
         */
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
        /*
         * Beim Klick mit der Maus wird ein neuer Kreis an der Position des Mauszeigers erstellt und in der ArrayList gespeichert.
         * Dabei gibt sich die GraphicsApp-Klasse als Listener aus (deshalb implementieren wir in dieser Klasse auch
         * das Interface MovingCircle.MovingCircleListener).
         */
        circles.add(new MovingCircle(event.getXPos(), event.getYPos(), this));
    }

    @Override
    public void hasLeftScene(MovingCircle circle) {
        /*
         * Wenn uns ein Kreis mitteil, dass er den Bildschirm verlassen hat, merken wir uns das, in dem wir den Kreis in einer
         * separaten Liste speichern. Das müssen wir deshalb machen, da wir nicht gleichzeitig über eine ArrayList iterieren
         * können (siehe udpateCircles) und Inhalte aus dieser entfernen können. Der Aufruf dieser Methode aus einer der
         * MovingCircle-Instanzen kann nur aus der for-Schleife in updateCircles ausgelöst werden, in der für jeden Kreis
         * die update-Methode ausgelöst wird. Statt den Kreis direkt zu löschen - was einen Fehler verursachen würde - merken
         * wir uns alle zu löschenden Kreise und entfernen diese erst, wenn die Iteration über die Liste abgeschlossen ist. Das
         * passiert dann nach dem Ende der Methode "updateCircles" in einer separaten Methode "clearCircles".
         */
        circlesToBeRemoved.add(circle);
    }

    public static void main(String[] args) {
        GraphicsAppLauncher.launch("CirclePainterDeluxe");
    }

}

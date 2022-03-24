import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ML extends MouseAdapter implements MouseListener {
    private boolean isPressed;
    private double x, y;

    public ML() {
        this.isPressed = false;
        this.x = 0;
        this.y = 0;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isPressed = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public double getMouseX(){ return x; }

    public double getMouseY(){
        return y;
    }

    public boolean isMousePressed(){
        return isPressed;
    }
}

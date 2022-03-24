import java.awt.event.KeyEvent;

public class PlayerControl {
    private final Rect rect;
    private final KL keyListener;

    public PlayerControl(Rect rect, KL keyListener) {
        this.rect = rect;
        this.keyListener = keyListener;
    }

    public void update(double dt){
        if(keyListener.isKeyPressed(KeyEvent.VK_Q)){
                rect.y -= 250 * dt;
        }
        else if(keyListener.isKeyPressed(KeyEvent.VK_A)){
                rect.y += 250 * dt;
        }
    }
}

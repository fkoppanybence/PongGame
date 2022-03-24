import java.awt.event.KeyEvent;

public class PlayerControl2 {
    private final Rect rect;
    private final KL keyListener;

    public PlayerControl2(Rect rect, KL keyListener) {
        this.rect = rect;
        this.keyListener = keyListener;
    }

    public void update(double dt){
        if(keyListener.isKeyPressed(KeyEvent.VK_UP)){
            rect.y -= 500 * dt;
        }
        else if(keyListener.isKeyPressed(KeyEvent.VK_DOWN)){
            rect.y += 500 * dt;
        }
    }
}

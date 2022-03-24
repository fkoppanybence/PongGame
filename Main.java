import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {
    public static int state = 0;
    public static Thread mainThread,musicThread;
    public static MainMenu menu;
    public static Window window;
    public static Music music;

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        menu = new MainMenu();
        mainThread = new Thread(menu);
        mainThread.start();
        music = new Music();
        musicThread = new Thread(music);
        musicThread.start();
    }

    public static void changeState(int newState){
        if(newState == 1 && state == 0){
            menu.stop();
            window = new Window(0,0);
            mainThread = new Thread(window);
            mainThread.start();
        }else if(newState == 0 && state == 1){
            window.stop();
            menu = new MainMenu();
            mainThread = new Thread(menu);
            mainThread.start();
        }else if(newState == 2 && state == 1){
            window.stop();
        }else if(newState == 2 && state == 0){
            menu.stop();
        }else if(newState == 4){
            int x = menu.getLeftScore();
            int y = menu.getRightScore();
            menu.stop();
            window = new Window(x,y);
            mainThread = new Thread(window);
            mainThread.start();
            newState = 1;
        }
        state = newState;
    }
}

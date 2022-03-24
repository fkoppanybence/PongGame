import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Music implements Runnable{
    private Clip clip;
    private File file;
    public Music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Random random = new Random();
        int randomInt = random.nextInt(9);
        if(randomInt == 0){

        }else if(randomInt == 1){
            file = new File("/home/koppany/Downloads/Daði Freyr – 10 Years (Official Audio).wav");
        }else if(randomInt == 2){
            file = new File("/home/koppany/Downloads/Daði Freyr – Think About Things (Lyric Video – English).wav");
        }else if(randomInt == 3){
            file = new File("/home/koppany/Downloads/Wolf Alice - Smile (Lyrics).wav");
        }else if(randomInt == 4){
            file = new File("/home/koppany/Downloads/I Don't Wanna Talk (I Just Wanna Dance).wav");
        }else if(randomInt == 5){
            file = new File("/home/koppany/Downloads/FOALS - The Runner [Official Music Video].wav");
        }else if(randomInt == 6){
            file = new File("/home/koppany/Downloads/Twenty One Pilots - Level of Concern (Audio).wav");
        }else{
            file = new File("/home/koppany/Downloads/FOALS - Wake Me Up [Official Music Video].wav");
        }
        Scanner scanner = new Scanner(System.in);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
    @Override
    public void run() {}
}
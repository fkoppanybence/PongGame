import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.stream.Stream;

import static java.awt.Font.PLAIN;

public class MainMenu extends JFrame implements Runnable{

    private final Graphics2D g2;
    private final Text startGame, exitGame, pongGame, loadGame;
    private final ML mouseListener;
    private boolean isRunning = true;
    private boolean load = false;
    private int leftScore,rightScore;


    public MainMenu(){
        this.setSize(800,600);
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        g2 = (Graphics2D) this.getGraphics();

        mouseListener = new ML();
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        KL keyListener = new KL();
        this.addKeyListener(keyListener);

        startGame = new Text("Start Game", new Font("Times New Roman", PLAIN, 40), 280,300, Color.WHITE);
        loadGame = new Text("Load Game", new Font("Times New Roman", PLAIN, 40), 280,350, Color.WHITE);
        exitGame = new Text("Quit", new Font("Times New Roman", PLAIN, 40), 280,400, Color.WHITE);
        pongGame = new Text("Pong Game", new Font("Times New Roman", PLAIN, 110), 90,150, Color.WHITE);
    }

    public void update(double dt){
        Image dbImage = createImage(getWidth(),getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage,0,0,this);

        //ha a Start Game-re klikkelek, nyit ej uj ablakot, amiben kezdodik a jatek
        if(mouseListener.getMouseX() > startGame.getX() && mouseListener.getMouseX() < startGame.getX() + startGame.getW() && mouseListener.getMouseY() > startGame.getY() - startGame.getH()/2 && mouseListener.getMouseY() < startGame.getY() + startGame.getH() / 2){
            startGame.setColor(new Color(108, 102, 102));
            if(mouseListener.isMousePressed()){
                Main.changeState(1);
            }
        } else{
            startGame.setColor(Color.WHITE);
        }


        //ha a Load Game-re klikkelek, filebol be tudok tolteni egy elozo jatekot
        if(mouseListener.getMouseX() > loadGame.getX() && mouseListener.getMouseX() < loadGame.getX() + loadGame.getW() && mouseListener.getMouseY() > loadGame.getY() - loadGame.getH()/2 && mouseListener.getMouseY() < loadGame.getY() + loadGame.getH() / 2){
            loadGame.setColor(new Color(108, 102, 102));
            if(mouseListener.isMousePressed()) {

                final JFileChooser Load = new JFileChooser();
                Load.setApproveButtonText("Load");
                int actionDialog = Load.showOpenDialog(null);
                File fileName = new File(Load.getSelectedFile() + ".txt");
                try {
                    String textLine;
                    FileReader fr = new FileReader(Load.getSelectedFile());
                    BufferedReader reader = new BufferedReader(fr);
                    load = true;
                    String fileData = reader.readLine();
                    int[] data = Stream.of(fileData.split(",")).mapToInt(Integer::parseInt).toArray();

                    leftScore = data[0];
                    rightScore = data[1];

                } catch (IOException ignored) {
                }
                if(load){
                    Main.changeState(4);
                }else{
                    loadGame.setColor(Color.WHITE);
                }
            }
        }
        else{
            loadGame.setColor(Color.WHITE);
        }

        //ha Exit-re klikkelek, kilep a jatekbol
        if(mouseListener.getMouseX() > exitGame.getX() && mouseListener.getMouseX() < exitGame.getX() + exitGame.getW() && mouseListener.getMouseY() > exitGame.getY() - exitGame.getH()/2 && mouseListener.getMouseY() < exitGame.getY() + exitGame.getH() / 2){
            exitGame.setColor(new Color(108, 102, 102));
            if(mouseListener.isMousePressed()){
                Main.changeState(2);
            }
        }
        else{
            exitGame.setColor(Color.WHITE);
        }

    }

    //kirajzolom a gombjaimat
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(26, 49, 8));
        g2.fillRect(0,0,800,600);
        loadGame.draw(g2);
        startGame.draw(g2);
        exitGame.draw(g2);
        pongGame.draw(g2);
    }

    public void stop(){
        isRunning = false;
    }

    public int getLeftScore() {
        return leftScore;
    }

    public int getRightScore() {
        return rightScore;
    }

    @Override
    public void run() {
        double lastFrameTime = 0.0;
        while (isRunning) {
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;
            update(deltaTime);
        }
        this.dispose();
    }
}

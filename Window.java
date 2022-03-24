import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.awt.Font.PLAIN;

public class Window extends JFrame implements Runnable{

    private final Graphics2D g2;
    private Rect player, player2, ball;
    private PlayerControl playerControl;
    private PlayerControl2 playerControl2;
    private Ball ball1;
    private Text leftScoreText, rightScoreText,exitGame,saveGame,resumeGame;
    private boolean isRunning = true;
    private int left, right;
    private KL keyListener;

    private final Text startGame, whiteWinner, blueWinner;
    private final ML mouseListener;

    public Window(int left, int right){
        this.setSize(800,600);
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.left = left;
        this.right = right;

        g2 = (Graphics2D) this.getGraphics();

        //listenekeret adok hozza
        keyListener = new KL();
        this.addKeyListener(keyListener);

        mouseListener = new ML();
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);


        //letrehozom a jatekosokat meg a labdat
        player = new Rect(0,250,20,100,Color.WHITE);
        player2 = new Rect(780,250,20,100, Color.BLUE);
        ball = new Rect(400,300,20,20,Color.YELLOW);
        playerControl = new PlayerControl(player, keyListener);
        playerControl2 = new PlayerControl2(player2, keyListener);
        ball1 = new Ball(ball, player, player2,leftScoreText, rightScoreText,left,right);

        //a jatek allasanak szamolasa
        leftScoreText = new Text(left, new Font("Times New Roman", PLAIN, 40), 50,80);
        rightScoreText = new Text(right, new Font("Times New Roman", PLAIN, 40), 700,80);

        //Resume vagy Game Over eseten ezeket a szovegeket iratom ki
        startGame = new Text("New Game", new Font("Times New Roman", PLAIN, 40), 260,350, Color.WHITE);
        whiteWinner = new Text("Congratulations! White player won!", new Font("Times New Roman", PLAIN, 40), 35,250, Color.WHITE);
        blueWinner = new Text("Congratulations! Blue player won!", new Font("Times New Roman", PLAIN, 40), 35,200, Color.WHITE);
        exitGame = new Text("Quit", new Font("Times New Roman", PLAIN, 40), 260,400, Color.WHITE);
        saveGame = new Text("Save Game", new Font("Times New Roman", PLAIN, 40), 260,250, Color.WHITE);
        resumeGame = new Text("Resume Game", new Font("Times New Roman", PLAIN, 40), 260,300, Color.WHITE);
    }

    public void update(double dt){

        //az allapot, amiben meg egyik versenyzo sem nyert, es fut a program
        if(ball1.getWinner()==0){
            Image dbImage = createImage(getWidth(), getHeight());
            Graphics dbg = dbImage.getGraphics();
            this.draw(dbg);
            g2.drawImage(dbImage, 0, 0, this);
            playerControl.update(dt);
            playerControl2.update(dt);
            ball1.update(dt);
            if(keyListener.isKeyPressed(KeyEvent.VK_P)){
                ball1.setWinner(5);
            }
        }

        //az allapot, amiben valamelyik versenyzo nyert
        else if(ball1.getWinner()==1 || ball1.getWinner()==2){
            Image dbImage = createImage(getWidth(), getHeight());
            Graphics dbg = dbImage.getGraphics();
            this.draw2(dbg);
            g2.drawImage(dbImage, 0, 0, this);

            if(mouseListener.getMouseX() > startGame.getX() && mouseListener.getMouseX() < startGame.getX() + startGame.getW() && mouseListener.getMouseY() > startGame.getY() - startGame.getH()/2 && mouseListener.getMouseY() < startGame.getY() + startGame.getH() / 2){
                startGame.setColor(new Color(108, 102, 102));
                if(mouseListener.isMousePressed()){
                    leftScoreText = new Text(0, new Font("Times New Roman", PLAIN, 40), 50,80);
                    rightScoreText = new Text(0, new Font("Times New Roman", PLAIN, 40), 700,80);

                    ball1 = new Ball(ball, player, player2,leftScoreText, rightScoreText,left,right);
                    ball1.setWinner(0);
                }
            } else{
                startGame.setColor(Color.WHITE);
            }

            if(mouseListener.getMouseX() > exitGame.getX() && mouseListener.getMouseX() < exitGame.getX() + exitGame.getW() && mouseListener.getMouseY() > exitGame.getY() - exitGame.getH()/2 && mouseListener.getMouseY() < exitGame.getY() + exitGame.getH() / 2){
                exitGame.setColor(new Color(108, 102, 102));
                if(mouseListener.isMousePressed()){
                    Main.changeState(2);
                }
            } else{
                exitGame.setColor(Color.WHITE);
            }
        }

        else if(ball1.getWinner() == 3){
            ball1 = new Ball(ball, player, player2,leftScoreText, rightScoreText,left,right);
            ball1.setWinner(0);
        }

        //az az allapot, amikor szunetel a jatek
        else if(ball1.getWinner() == 5){
            Image dbImage = createImage(getWidth(), getHeight());
            Graphics dbg = dbImage.getGraphics();
            this.draw3(dbg);
            g2.drawImage(dbImage, 0, 0, this);

            if(mouseListener.getMouseX() > startGame.getX() && mouseListener.getMouseX() < startGame.getX() + startGame.getW() && mouseListener.getMouseY() > startGame.getY() - startGame.getH()/2 && mouseListener.getMouseY() < startGame.getY() + startGame.getH() / 2){
                startGame.setColor(new Color(108, 102, 102));
                if(mouseListener.isMousePressed()){
                    leftScoreText = new Text(0, new Font("Times New Roman", PLAIN, 40), 50,80);
                    rightScoreText = new Text(0, new Font("Times New Roman", PLAIN, 40), 700,80);
                    ball1 = new Ball(ball, player, player2,leftScoreText, rightScoreText,left,right);
                    ball1.setWinner(0);
                }
            } else{
                startGame.setColor(Color.WHITE);
            }

            if(mouseListener.getMouseX() > saveGame.getX() && mouseListener.getMouseX() < saveGame.getX() + saveGame.getW() && mouseListener.getMouseY() > saveGame.getY() - saveGame.getH()/2 && mouseListener.getMouseY() < saveGame.getY() + saveGame.getH() / 2){
                saveGame.setColor(new Color(108, 102, 102));
                if(mouseListener.isMousePressed()){
                    final JFileChooser SaveAs = new JFileChooser();
                    SaveAs.setApproveButtonText("Save");
                    int actionDialog = SaveAs.showOpenDialog(null);
                    File fileName = new File(SaveAs.getSelectedFile() + ".txt");
                    try {
                        BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName));
                        outFile.write(ball1.getLeftScore() + "," + ball1.getRightScore());
                        outFile.close();
                    } catch (IOException ignored) {}
                }
            } else{
                saveGame.setColor(Color.WHITE);
            }

            if(mouseListener.getMouseX() > resumeGame.getX() && mouseListener.getMouseX() < resumeGame.getX() + resumeGame.getW() && mouseListener.getMouseY() > resumeGame.getY() - resumeGame.getH()/2 && mouseListener.getMouseY() < resumeGame.getY() + resumeGame.getH() / 2){
                resumeGame.setColor(new Color(108, 102, 102));
                if(mouseListener.isMousePressed()){
                     ball1.setWinner(0);
                }
            } else{
                resumeGame.setColor(Color.WHITE);
            }

            if(mouseListener.getMouseX() > exitGame.getX() && mouseListener.getMouseX() < exitGame.getX() + exitGame.getW() && mouseListener.getMouseY() > exitGame.getY() - exitGame.getH()/2 && mouseListener.getMouseY() < exitGame.getY() + exitGame.getH() / 2){
                exitGame.setColor(new Color(108, 102, 102));
                if(mouseListener.isMousePressed()){
                    Main.changeState(2);
                }
            } else{
                exitGame.setColor(Color.WHITE);
            }
        }
    }

    //annak az allapotnak a kirajzolasa, amikor megy a jatek
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(26, 49, 8));
        g2.fillRect(0,0,800,600);
        rightScoreText.draw(g2);
        leftScoreText.draw(g2);
        ball.draw(g2);
        player.draw(g2);
        player2.draw(g2);
    }

    //annak az allapotnak a kirajzolasa, amikor az egyik jatekos nyert
    public void draw2(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(26, 49, 8));
        g2.fillRect(0,0,800,600);
        if(ball1.getWinner()==1){
            whiteWinner.draw(g2);
        } else{
            blueWinner.draw(g2);
        }
        startGame.draw(g2);
        exitGame.draw(g2);
    }

    //annak az allapotnak a kirajzolasa, amikor szunetel a jatek
    public void draw3(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(26, 49, 8));
        g2.fillRect(0,0,800,600);
        startGame.draw(g2);
        exitGame.draw(g2);
        resumeGame.draw(g2);
        saveGame.draw(g2);
    }

    public void stop(){
        isRunning = false;
    }

    @Override
    public void run() {
        double lastFrameTime = 0.0;
        while (isRunning) {
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;
            update(deltaTime);
            try{
                Thread.sleep(30);
            }
            catch (Exception e){}
        }
        this.dispose();
    }
}

import java.awt.*;

public class Ball {
    private final Rect rect;
    private final Rect leftpaddle, rightpaddle;
    private final Text leftScoreText;
    private final Text rightScoreText;
    private int leftScore,rightScore;
    private double vx,vy;
    private int winner;
    private double speed = 2;

    public Ball(Rect rect, Rect leftpaddle, Rect rightpaddle, Text rightScoreText, Text leftScoreText, int left, int right) {
        this.rect = rect;
        this.leftpaddle = leftpaddle;
        this.rightpaddle = rightpaddle;
        this.leftScoreText = leftScoreText;
        this.rightScoreText = rightScoreText;
        this.vx = -100;
        this.vy = -100;
        this.leftScore = left;
        this.rightScore = right;
        this.winner = 3;
        this.rect.x = 300;
        this.rect.y = 400;
    }

    public void update(double dt){
        if(vx < 0){
            //visszapattan a jatekosrol
            if((rect.x <= (leftpaddle.x + leftpaddle.width)) && (rect.x + rect.width >= leftpaddle.x) && (rect.y >= leftpaddle.y) && (rect.y <= leftpaddle.y + leftpaddle.height)){
                vx *= -1;
            }
            //kimegy a labda
            else if(rect.x + rect.width < leftpaddle.x){
                leftScoreText.setText("" + (Integer.parseInt(leftScoreText.getText()) + 1));
                leftScore++;
                if(leftScore == 5){
                    winner = 2;
                }
                speed = speed + 0.1;
                rect.x = 400;
                rect.y = 300;
                vy = 100;
                vx = +100;
            }
        }
        else if (vx > 0){
            //visszapattan a jatekosrol
            if(rect.x + rect.width >= rightpaddle.x && rect.x <= rightpaddle.x + rightpaddle.width && rect.y >= rightpaddle.y && rect.y <= rightpaddle.y + rightpaddle.height){
                vx *= -1;
            }
            //kimegy a labda
            else if(rect.x + rect.width > rightpaddle.x + rightpaddle.width){
                rightScoreText.setText("" + (Integer.parseInt(rightScoreText.getText()) + 1));
                rightScore++;
                speed = speed + 0.1;
                if(rightScore == 5){
                    winner = 1;
                }
                rect.x = 400;
                rect.y = 300;
                vy = 100;
                vx = -100;
            }
        }

        //visszapattan a falrol
        if(vy > 0){
            if(rect.y + rect.height > 600){
                vy *= -1;            }
        }else if(vy < 0){
            if(this.rect.y <= 40){
                vy *= -1;
            }
        }

        rect.x += vx * speed * dt;
        rect.y += vy * speed * dt;
    }

    public int getLeftScore() {
        return leftScore;
    }

    public int getRightScore() {
        return rightScore;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getWinner() {
        return winner;
    }
}

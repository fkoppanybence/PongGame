import java.awt.*;

public class Text {
    private String text;
    private final Font font;
    private final double x,y;
    private double w, h;
    private Color color;

    public Text(String text, Font font, double x, double y, Color color) {
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
        this.w = font.getSize() * text.length();
        this.h = font.getSize();
        this.color = Color.WHITE;
    }

    public Text(int text, Font font, double x, double y) {
        this.text = "" + text;
        this.font = font;
        this.x = x;
        this.y = y;
        this.color = Color.WHITE;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void draw(Graphics2D g2){
        g2.setColor(color);
        g2.setFont(font);
        g2.drawString(text, (float)x, (float)y);
    }
}

import java.awt.*;

public class MenuPainterClass implements MenuPaint {



    @Override
    public void paint(Graphics2D g2d, String text, Rectangle bounds, boolean isChoosen, boolean isClicked) {
        FontMetrics fm = g2d.getFontMetrics();
        if (isChoosen) {
            giveColorBackground(g2d, bounds, Color.BLUE, Color.WHITE);
        } else if (isClicked) {
            giveColorBackground(g2d, bounds, Color.ORANGE, Color.BLACK);
        } else {
            giveColorBackground(g2d, bounds, Color.GREEN, Color.LIGHT_GRAY);
        }
        int x = bounds.x + ((bounds.width - fm.stringWidth(text)) / 2);
        int y = bounds.y + ((bounds.height - fm.getHeight()) / 2) + fm.getAscent();
        g2d.setColor(isChoosen ? Color.WHITE : Color.LIGHT_GRAY);
        g2d.drawString(text, x, y);
    }

    public void giveColorBackground(Graphics2D g2d, Rectangle bounds, Color background,Color front){
        g2d.setColor(background);
        g2d.fill(bounds);
        g2d.setColor(front);
        g2d.draw(bounds);
    }

    @Override
    public Dimension getSize(Graphics2D g2d, String text) {
        return g2d.getFontMetrics().getStringBounds(text, g2d).getBounds().getSize();
    }
}

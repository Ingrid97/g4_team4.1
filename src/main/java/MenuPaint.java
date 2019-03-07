import java.awt.*;

public interface MenuPaint {
    public void paint(Graphics2D g2d, String text, Rectangle bounds, boolean isChoosen, boolean isClicked);

    public Dimension getSize(Graphics2D g2d, String text);
    }


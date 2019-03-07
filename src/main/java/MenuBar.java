/*import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuBar extends JFrame {

    //Creating GUI parameters
    private String clickedItem;
    private List<String> menuItem;
    private String selectMenuItem;


    private MenuPainterClass painter;
    private Map<String, Rectangle> menuBounds;

    public MenuBar(){
        setBackground(Color.BLACK);
        painter = new MenuPainterClass();
        menuItem = new ArrayList<>(20);
        menuItem.add("Start");
        menuItem.add("Exit");
        selectMenuItem = menuItem.get(0);

        MouseAdapter mouse = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String click = null;
                for(String text: menuItem) {
                    Rectangle bounds = menuBounds.get(text);
                    if (bounds.contains(e.getPoint())) {
                        click = text;
                        break;

                    }
                }
                    if( click != null && !click.equals(selectMenuItem)){
                        selectMenuItem = click;
                        repaint();
                    }

            }

            public void moveMouse(MouseEvent e){
                clickedItem = null;
                for(String text: menuItem){
                    Rectangle bounds = menuBounds.get(text);
                    if(bounds.contains(e.getPoint())){
                        clickedItem = text;
                        repaint();
                        break;
                    }
                }


            }
        };
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        InputMap inn = getInputMap();
        ActionMap out = getActionMap();


    }
}
*/
//TODO this class does not work yet because GameState class is not done

/* public class MenuBar extends JFrame {

    //Creating GUI parameters
    private String clickedItem;
    private List<String> menuItem;
    private String selectMenuItem;
    private int delta;



    private MenuPaint painter;
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

        InputMap inn = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap out = getActionMap();

        inn.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "arrowGoingDown");
        inn.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "arrowGoingUp");

        out.put("arrowDown", new GameState(1));
        out.put("arrowUp", new GameState(-1));




    }
    public Dimension getSize(){
        return new Dimension(640, 640);
    }

    protected void giveColorBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        if (menuBounds == null) {
            menuBounds = new HashMap<>(menuItem.size());
            int width = 0;
            int height = 0;
            for (String text : menuItem) {
                Dimension dim = painter.getSize(g2d, text);
                width = Math.max(width, dim.width);
                height = Math.max(height, dim.height);
            }

            int x = (getWidth() - (width + 10)) / 2;

            int totalHeight = (height + 10) * menuItem.size();
            totalHeight += 5 * (menuItem.size() - 1);

            int y = (getHeight() - totalHeight) / 2;

            for (String text : menuItem) {
                menuBounds.put(text, new Rectangle(x, y, width + 10, height + 10));
                y += height + 10 + 5;
            }

        }
        for (String text : menuItem) {
            Rectangle bounds = menuBounds.get(text);
            boolean isSelected = text.equals(selectMenuItem);
            boolean isFocused = text.equals(clickedItem);
            painter.paint(g2d, text, bounds, isSelected, isFocused);
        }
        g2d.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        int index = menuItem.indexOf(selectMenuItem);
        if (index < 0) {
            selectMenuItem = menuItem.get(0);
        }
        index += delta;
        if (index < 0) {
            selectMenuItem = menuItem.get(menuItem.size() - 1);
        } else if (index >= menuItem.size()) {
            selectMenuItem = menuItem.get(0);
        } else {
            selectMenuItem = menuItem.get(index);
        }
        repaint();
    }



0

*/
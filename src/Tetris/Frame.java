package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Monika
 */

public class Frame extends JPanel implements ActionListener{
    boolean falling = false, begin = false;
    int width = 10, height = 20;
    Timer timer;
    int linesRemoved = 0;
    int X = 0, Y = 0;
    int totalFull = 0;
    int cross = width * height;
    TetrisShapes liveShape;
    TetrisShapes.Tetri[] box;
    JLabel infoText;

    public Frame(Game valI) {
        timer = new Timer(200, this);
        addMouseListener(new userInput());
        liveShape = new TetrisShapes();
        infoText = valI.sendText();
        box = new TetrisShapes.Tetri[cross];
    }

    public void begin() {
        begin = true;
        falling = false;
        fillSpaces();
        createTetri();
        timer.start();
    }

    public TetrisShapes.Tetri thisShape(int x, int y) {
        return box[x + (y * width)];
    }

    private void fillSpaces() {
        int coord=0;
        while (coord<cross){
            box[coord] = TetrisShapes.Tetri.None;
            coord++;
        }
    }

    private void fallingPiece() {
        int position = 0;
        while (position < 4) {
            int coordX = X + liveShape.x(position);
            int coordY = Y - liveShape.y(position);
            box[(coordY * width) + coordX] = liveShape.findShape();
            position++;
        }
        remLine();
        createTetri();
    }

    public void createTetri() {
        liveShape.randomShape();
        if (!move(liveShape, width/2, height-1+liveShape.minimumY()-1)) {
            timer.stop();
            liveShape.placeShape(TetrisShapes.Tetri.None);
            JDialog.setDefaultLookAndFeelDecorated(true);
            int playerResp=JOptionPane.showConfirmDialog(this, "GAME OVER!" + "\n" +
                            "Your Score Was:   " + " "+ linesRemoved + "\n " + "Would you like to play again?", "GAME OVER",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (playerResp==JOptionPane.YES_OPTION){
                linesRemoved=0;
                infoText.setText("   Score:    0      ");
                begin();
            }else{
                System.exit(0);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        int posY=Y-1;
        if (!move(liveShape, X, posY)) {
            for(int a=Y; a>0;a-- ){
                if (!move(liveShape, X, posY))
                    break;
                }
                fallingPiece();
            }
        }

    public int boxWidth() {
        int sizeWidth= getSize().width / width;
        return sizeWidth;
    }

    public int boxHeight() {
        int sizeHeight=getSize().height / height;
        return sizeHeight;
    }

    private void square(Graphics g, int x, int y, TetrisShapes.Tetri colorFor) {
        Color[] colors = {Color.BLACK, Color.RED, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.BLUE, Color.ORANGE};
        Color color = colors[colorFor.ordinal()];
        int bHeight=boxHeight()-3;
        int bWidth=boxWidth()-1;
        g.setColor(color);
        g.fillRect(x + 2, y + 1, bWidth, boxHeight() - 2);
        g.drawLine(x, y + boxHeight() - 2, x, y);
        g.drawLine(x, y, x + bWidth, y);
        g.setColor(color.darker());
        g.drawLine(x, y + bHeight, x + bWidth, y + bHeight);
        g.drawLine(x + bWidth, y + bHeight, x + bWidth, y + 1);
    }

    public void paint(Graphics g) {
        super.paint(g);
        double size = getHeight();
        int heightMult=(int) size-height*boxHeight();
        int boxTop = heightMult;

        for (int i=0; i<height*width; i++){
            TetrisShapes.Tetri tetri = thisShape(i%width, height - (i/width)-1);
            if (tetri != TetrisShapes.Tetri.None) {
              square(g, i%width * boxWidth(), boxTop + i/width * boxHeight(), tetri);
             }
        }
        if (liveShape.findShape() != TetrisShapes.Tetri.None) {
            int currPosition=0;

            while (currPosition<4){
                int shapeForX=liveShape.x(currPosition), shapeForY=liveShape.y(currPosition);
                int x = X + shapeForX, y = Y - shapeForY;
                square(g, x * boxWidth(), (height - y - 1) * boxHeight(), liveShape.findShape());
                currPosition++;
            }
        }
    }

    private boolean move(TetrisShapes newPiece, int newX, int newY) {
        int val=0;
        while (val<4){
            int x = newX + newPiece.x(val);
            int y = newY - newPiece.y(val);

            if(x>=width){
                return false;
            }
            else if (x<0 || y<0){
                return false;
            }
            else if (thisShape(x, y) != TetrisShapes.Tetri.None) {
                return false;
            }
            val++;
            repaint();
            liveShape = newPiece;
        }
        X = newX;
        Y = newY;
        return true;
    }

    private void remLine() {
        for (int i = height - 1; i >= 0; i--)  {
            boolean filled = true;
            for (int j = 0; j < width; j++) {
                if (thisShape(j, i) == TetrisShapes.Tetri.None) {
                    filled = false;
                    break;
                }
            }
            if (filled) {
                ++totalFull;
                int a=height-1;
                int b=width;
                for (int k = i; k < a; k++) {
                    for (int j = 0; j < b; j++) {
                        box[(k * b) + j] = thisShape(j, k + 1);
                    }
                }
                if (totalFull > 0) {
                    linesRemoved += (totalFull * 10);
                    infoText.setText("Score:   " + String.valueOf(linesRemoved));
                    repaint();
                }
            }

        }
    }

    class userInput extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {
            int mouseClicks= e.getButton();
            if (begin==false){
                return;
            } else if (liveShape.findShape() == TetrisShapes.Tetri.None){
                return;
            }
            switch(mouseClicks){
                case MouseEvent.BUTTON1:
                    move(liveShape, X-1, Y);
                    break;
                case MouseEvent.BUTTON3:
                    move(liveShape, X+1, Y);
                    break;
                case MouseEvent.BUTTON2:
                    move(liveShape.rightRot(), X, Y);
                    break;
            }
        }
    }

}

package Tetris;

/**
 * Created by Monika on 26/11/2016.
 */
public class TetrisShapes {

    public enum Tetri {
        None(new int[][]
                {{0, 0}, {0, 0},
                        {0, 0}, {0, 0}}),
        SShape(new int[][]
                {{0, -1}, {0, 0},
                        {-1, 0}, {-1, 1}}),
        ZShape(new int[][]
                {{0, -1}, {0, 0},
                        {1, 0}, {1, 1}}),
        IShape(new int[][]
                {{0, -1}, {0, 0},
                        {0, 1}, {0, 2}}),
        TShape(new int[][]
                {{-1, 0}, {0, 0},
                        {1, 0}, {0, 1}}),
        OShape(new int[][]
                {{0, 0}, {1, 0},
                        {0, 1}, {1, 1}}),
        LShape(new int[][]
                {{-1, -1},
                        {0, -1},
                        {0, 0}, {0, 1}}),
        FShape(new int[][]
                {{1, -1}, {0, -1},
                        {0, 0}, {0, 1}});


        int[][] pieceCoords;
        Tetri(int[][] cood) {
            pieceCoords=cood;
        }
    }

    Tetri specificShape;

    Tetri findShape() {
        return specificShape;
    }

    int[][] pieceCoords;

    int x(int xCo) {
        return pieceCoords[xCo][0];
    }

    int y(int xCo) {
        return pieceCoords[xCo][1];
    }

    TetrisShapes() {
        pieceCoords = new int[4][2];
    }

    public void placeShape(Tetri createShape) {
        for (int i = 0; i < 4 * 2; i++) {
            pieceCoords[i / 2][i % 2] = createShape.pieceCoords[i / 2][i % 2];
        }
        specificShape = createShape;
    }

    public int minimumY() {
        int startXPosition = 0;
        int coordVal = pieceCoords[2][0];
        while (startXPosition < 4) {
            coordVal = Math.min(coordVal, pieceCoords[startXPosition][1]);
            startXPosition++;
        }
        return coordVal;
    }

    public void randomShape() {
        Tetri[] shapeRetrieved = Tetri.values();
        int random1 = 1 + (int) (Math.random() * 7);
        placeShape(shapeRetrieved[random1]);
    }

    public void forX(int index, int x) {
        pieceCoords[index][0] = x;
    }

    public void forY(int index, int y) {
        pieceCoords[index][1] = y;
    }

    public TetrisShapes rightRot() {
        TetrisShapes degreeNinty = new TetrisShapes();
        degreeNinty.specificShape = specificShape;
        int i = 0;
        if (specificShape == Tetri.OShape) {
            return this;
        }
        while (i < 4) {
            degreeNinty.forX(i, -y(i));
            degreeNinty.forY(i, x(i));
            i++;
        }
        return degreeNinty;
    }
}
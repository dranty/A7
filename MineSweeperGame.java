import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import java.time.Instant;
import java.util.Random;

@SuppressWarnings("unused")
public class MineSweeperGame extends Canvas {
        cell[][] cells;
        uiStats boardUI;
    
    
        class uiStats {
            int time; // upperRightUI data that will store the time spent.
            faceStates face; // middleUI data that will store the facial expression of the emoji.
            int totalMines; // upperleftUI data that will store the total # of mines. (can be -'d if the user plants a flag.)
        }
            
        class cell {
            boolean gameOverState; // Indicates whether all mines should be revealed if true.
            visualCellState vCState; // This will contain the data for the visual state of the mine. 
            internalCellState iCState; // this is the data that will contain the internal state of the mine (0, hasMine, or noMine).
            int adjacencyVal; // this number will appear if the cell is close to a mine visually
    
    
        }  
    
        enum visualCellState { unOpened, opened, flagged }
    
        enum internalCellState { noMine, hasMine, safe } // By "Safe" it means the tile is near a mine, this will be the tile that has the num
    
        enum faceStates { smiling, dead, shocked, win }   
    
        enum difficulty { easy, medium, hard }
        
        public static void main(String[] args) {
            MineSweeperGame game = new MineSweeperGame();
    }

        public MineSweeperGame() {
            // cell stuff

            cells = new cell[3][3]; // placeholder values, see below
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; i < cells.length; j++) { // all cells are standard but initialized.
                    cells[i][j] = new cell();
                    cells[i][j].gameOverState = false;
                    cells[i][j].vCState = visualCellState.unOpened;
                    cells[i][j].iCState = internalCellState.noMine;
                    cells[i][j].adjacencyVal = 0;
                }
            }
            mineMapGenerator(cells); // places the mines.
            
            // ui stuff
            
            // int totalMines = mineBucket;
        }

        void mineMapGenerator(cell[][] cells) {
            Random rand = new Random();
            int mineBucket = 0; // returns the total amount of mines.
            for (int i = 0; i < cells.length; i++) { // 3 is a placeholder value, find perm. solution for finding size dynamically. (difficulty)
                for (int j = 0; j < cells.length; j ++) {
                    int randomMineGen = rand.nextInt(1); // will generate a random int between 1 and 0, if the int is 1 a m, if not, the space will be noMine
                    if (randomMineGen == 1) {
                        cells[i][j].iCState = internalCellState.hasMine;
                        mineBucket++;
                    }  
                    
                }
            }
        }


}

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.management.monitor.GaugeMonitor;
import javax.swing.JFrame;
import java.time.Instant;
import java.util.Random;

@SuppressWarnings("unused")
public class MineSweeperGame extends Canvas {
        cell[][] cells;
        uiStats boardUI;
        gameVariables gameVariables;

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
        
        class gameVariables {
            difficulty gameDifficulty;
        }
    
        enum visualCellState { unOpened, opened, flagged }
    
        enum internalCellState { noMine, hasMine, safe } // By "Safe" it means the tile is near a mine, this will be the tile that has the num
    
        enum faceStates { smiling, dead, shocked, win }   
    
        enum difficulty { easy, medium, hard }
        
        public static void main(String[] args) {
            MineSweeperGame game = new MineSweeperGame();
            game.printBoard();
    }
        public MineSweeperGame() {
            faceStates face = faceStates.smiling;
            difficulty gameDifficulty = difficulty.medium; // this will change depends on user input
            if (gameDifficulty == difficulty.easy) {
                cells = new cell[9][9]; 
            }
            if (gameDifficulty == difficulty.medium) {
                cells = new cell[16][16]; 
            }
            if (gameDifficulty == difficulty.hard) {
                cells = new cell[16][30];
            }

            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) { // all cells are standard but initialized.
                    cells[i][j] = new cell();
                    cells[i][j].gameOverState = false;
                    cells[i][j].vCState = visualCellState.unOpened;
                    cells[i][j].iCState = internalCellState.noMine;
                    cells[i][j].adjacencyVal = 0;
                }
            }
            
            int totalMines = mineMapGenerator(cells, gameDifficulty); // works

            System.out.println("Mode: " + gameDifficulty); // debug
            System.out.println("Total Mapped Mines: " + totalMines); // debug
        }

        public int mineMapGenerator(cell[][] cells, difficulty gameDifficulty) { // works
            Random rand = new Random();
            int mineChance = 0;
            if (gameDifficulty == difficulty.easy) {
                mineChance++;
                mineChance++;
                mineChance++;
                mineChance++;
            }
            if (gameDifficulty == difficulty.medium) {
                mineChance++;
                mineChance++;
                mineChance++;
            }
            if (gameDifficulty == difficulty.hard) {
                mineChance++;
                mineChance++;
            }
            
            int mineBucket = 0; // returns the total amount of mines.
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    int randomMineGen = rand.nextInt(mineChance); // will generate a random int between 1 and 0, if the int is 1 a m, if not, the space will be noMine
                    if (randomMineGen == 1) {
                        cells[i][j].iCState = internalCellState.hasMine;
                        mineBucket++;
                    }
                }
            }
            System.out.println("Mine Chance: " + mineChance);
                return mineBucket;
        }

        public void printBoard() {
            System.out.println("Minesweeper Board:");
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    if (cells[i][j].iCState == internalCellState.hasMine) {
                        System.out.print("* ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }
        }
}

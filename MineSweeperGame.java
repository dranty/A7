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
            visualCellState vCState; // This will contain the data for the visual state of the mine. 
            internalCellState iCState; // this is the data that will contain the internal state of the mine (0, hasMine, or noMine).
            int adjacencyVal; // this number will appear if the cell is close to a mine visually, doesnt need to be there
        } 
        
        class gameVariables {
            difficulty gameDifficulty; // Indicates how big the grid should be and the amount of mines on a grid.
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
                    cells[i][j].vCState = visualCellState.unOpened;
                    cells[i][j].iCState = internalCellState.noMine;
                }
            }
            
            int totalMines = mineMapGenerator(cells, gameDifficulty); // works
            adjacencyCalculator(cells);
            firstClick(cells);
            

            System.out.println("Mode: " + gameDifficulty); // debug
            System.out.println("Total Mapped Mines: " + totalMines); // debug
        }

        public int mineMapGenerator(cell[][] cells, difficulty gameDifficulty) { // works
            Random rand = new Random();
            int mineChance = 2;
            if (gameDifficulty == difficulty.easy) {
                mineChance++;
                mineChance++;
                mineChance++;
                mineChance++;
            }
            if (gameDifficulty == difficulty.medium) {
                mineChance++;
                mineChance++;

            }
            if (gameDifficulty == difficulty.hard) {
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

        public void adjacencyCalculator(cell[][] cells) { // this function will be call recursively checkNeighhbors on all open cells. also add if the ic is a mine skip this function.
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    cells[i][j].adjacencyVal = checkNeighbors(i, j); // works fine but can be buggy (displays 0 even though there is a minewhen there are lots of mines, not sure why. 
                }
            }
        }

        public void clickDetection() { // implement click detection through here. 
            int pos1 = 3;
            int pos2 = 2;
        }


        public class CellLocations {
            cell topRight;
            cell middleTop;
            cell topLeft;
            cell middleLeft;
            cell middleRight;
            cell bottomLeft;
            cell bottomMiddle;
            cell bottomRight; 

                public void CellCords(cell[][] cells, int pos1, int pos2) {
                    topRight = cells[pos1 - 1][pos2 - 1];
                    middleTop = cells[pos1 - 1][pos2];
                    topLeft = cells[pos1 - 1][pos2 + 1];
                    middleLeft = cells[pos1][pos2 - 1];
                    middleRight = cells[pos1][pos2 + 1];
                    bottomLeft = cells[pos1 + 1][pos2 - 1];
                    bottomMiddle = cells[pos1 + 1][pos2];
                    bottomRight = cells[pos1 + 1][pos2 + 1];
                }     
        }



        public int checkNeighbors(int pos1, int pos2) throws IndexOutOfBoundsException { // this will check the corresponding neighbors to the cell, if one of the neighbors is a mine, increment by 1. ** also see notes for solution 
            int adjacencyVal = 0;
                        if (pos1 - 1 <= -1 || pos2 - 1 <= -1) {
                            } else { 
                                if (cells[pos1 - 1][pos2 - 1].iCState == internalCellState.hasMine) { // top right
                                adjacencyVal++;
                            }
                        }
                        
                        if (pos1 - 1 <= -1) {
                            } else {  
                                if (topRight.iCState == internalCellState.hasMine) { // middle top
                                adjacencyVal++;
                                }
                            }
                
                        if (pos1 - 1 <= -1 || pos2 + 1 > cells.length - 1) {
                            } else { 
                                if (cells[pos1 - 1][pos2 + 1].iCState == internalCellState.hasMine) { // top left
                                adjacencyVal++;
                                }
                            }
                
                        if (pos2 - 1 <= -1) {
                            } else { 
                                if (cells[pos1][pos2 - 1].iCState == internalCellState.hasMine) { // middle left
                                adjacencyVal++;
                                }
                            }
                                  
                        if (pos2 + 1 >= cells.length - 1) {
                            } else { 
                                if (cells[pos1][pos2 + 1].iCState == internalCellState.hasMine) { // middle right
                                adjacencyVal++;
                                }
                            }

                        if (pos1 + 1 > cells.length - 1 || pos2 - 1 <= -1) {
                            } else { 
                                if (cells[pos1 + 1][pos2 - 1].iCState == internalCellState.hasMine) { // bottom left
                                   adjacencyVal++;
                                }
                            }
                    
                        if (pos1 + 1 > cells.length - 1) {
                            } else { 
                                if (cells[pos1 + 1][pos2].iCState == internalCellState.hasMine) { // bottom middle
                                    adjacencyVal++;
                                }
                            }
                            
                        if (pos1 + 1 > cells.length - 1 || pos2 + 1 > cells.length - 1) {
                            } else {
                                if (cells[pos1 + 1][pos2 + 1].iCState == internalCellState.hasMine) { // bottom right
                                    adjacencyVal++;
                            }
                        }
                    return adjacencyVal;
                }

            public boolean isValid(cell[][] cells, int X, int Y) {
                
            }

            public int getClick() { // this method will get where in the grid the player clicked and store it for later use. 
                int X = 1;
                int Y = 2;
                return X;
            }
        
            public void firstClick(cell[][] cells) { // upon first click, the 9x9 area around the click location will be a noMine & will be revealed.
                int X = 3;
                int Y = 3;

                cells[X][Y].iCState = internalCellState.noMine;
                cells[X - 1][Y - 1].iCState = internalCellState.noMine;
                cells[X - 1][Y].iCState = internalCellState.noMine;
                cells[X - 1][Y + 1].iCState = internalCellState.noMine;
                cells[X][Y - 1].iCState = internalCellState.noMine;
                cells[X][Y + 1].iCState = internalCellState.noMine;
                cells[X + 1][Y - 1].iCState = internalCellState.noMine;
                cells[X + 1][Y].iCState = internalCellState.noMine;
                cells[X + 1][Y + 1].iCState = internalCellState.noMine;

                // reveal logic here TO DO!!!
            }

            // public void recursiveReveal(cell[][] cells, int X, int Y) {
            //     /* This method will be called when the first click is done, it will check each square around the firstClick square.
            //      * In total it will check 8 squares, which will all be called recursively (using the neighbors method.)
            //      * If the square has no mines neighboring mines, itself will be revealed and will reveal the nearest square that is not a mine. 
            //      * 
            //      */
                
            //     checkNeighbors(X, Y);
                


            // }

            public void printBoard() {
                System.out.println("Minesweeper Board:");
                for (int i = 0; i < cells.length; i++) {
                    for (int j = 0; j < cells[i].length; j++) {
                        if (i == 3 && j == 3) {
                            System.out.print("+ ");
                        } else if (cells[i][j].iCState == internalCellState.hasMine) {
                            System.out.print("* ");
                        } else {
                            System.out.print(cells[i][j].adjacencyVal + " ");
                        }
                    }
                    System.out.println();
                }
            }
}

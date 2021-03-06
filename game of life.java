import java.io.; import java.util.;
// Lynn  Long 
public class GameofLifeApp {

private Board currentBoard;
private int num_generations;
private Scanner infile;
private PrintWriter outfile;

public GameofLifeApp() {
    try {
        // Open the input/output streams.
        infile = new Scanner(new FileReader("INPUT2.TXT"));
        outfile = new PrintWriter(new FileWriter("OUTPUT.TXT"));

    } catch (IOException exc) {
        // Exit if a stream cannot be opened.
        System.out.println(exc);
        System.exit(1);
    }
    this.readInputData();
    //currentBoard.printBoard();
    this.playgame();
}

public void readInputData() {
// read the data from the text and stored sin the appropriate places int width, height;

    width = infile.nextInt();
    height = infile.nextInt();
    num_generations = infile.nextInt();


    System.out.println("num of generations:  " + num_generations);
    System.out.println(" The generations are in the outputfile");
//System.out.println(""+width); //System.out.println(""+height); //System.out.println(""+Gen);

    currentBoard = new Board(width, height);
    currentBoard.createInitialBoard(infile);

}

public void playgame() {
    for (int i = 0; i < num_generations; i++) {
        // For each generation,
        // ask all life cells to count their living neighbors
        // then ask all life cells to update their states
        // for the new generation.
        // print the new board
        currentBoard.printBoard(outfile);
        outfile.println();
        outfile.println();
        outfile.println();
        currentBoard.updateBoard();
    }

    outfile.flush();
    outfile.close();

}

public static void main(String[] args) {
    new GameofLifeApp();
}
}

import java.io.; import java.util.;

public class Board {

private LifeCell[][] board;    // Board of life cells.
private int boardHeight, boardWidth;

// Constructor for objects of class Board
public Board(int width, int height) {
    //   creates a board with lifecell objects
    boardHeight = height;
    boardWidth = width;
    board = new LifeCell[width+2][height+2];

    // buffer the board w 2 extra rows and 2 extra cols
    for (int i = 0; i < boardWidth + 2; i++) {
        for (int j = 0; j < boardHeight + 2; j++) {
            board[i][j] = new LifeCell(board, i, j);
        }
    }
}

public void createInitialBoard(Scanner infile) {
    //for each particular pair read in, ask life cell to toggle?????
    // define in  life cell class
    //  GameofLifeApp readInputData  = new GameofLifeApp();
    int r;
    int c;
    while (infile.hasNextInt()) {
        r = infile.nextInt();
        c = infile.nextInt();
        // System.out.println(""+r+c);
        board[r][c].toggle();
    }
    infile.close();
}

public void updateBoard() {
    // first ask all life cells to count their living neighbors.
    // It should then ask all life cells to toggle
    // for the new generation.
    for (int i = 1; i < boardHeight + 1; i++) {
        for (int j = 1; j < boardWidth + 1; j++) {
            board[i][j].countNeighbors();
        }
    }

    for (int i = 1; i < boardHeight + 1; i++) {
        for (int j = 1; j < boardWidth + 1; j++) {
            board[i][j].updateState();
        }
    }


}

public void printBoard(PrintWriter out) {
    //  prints the board with the appropriate characters +-|
    //  used to show the boundaries.  Where appropriate,
    //  Ask each cell to print itself
    out.println("+-+-+-+-+-+-+-+-+");
    for (int i = 1; i < boardHeight + 1; i++) {
        out.print("|");
        for (int j = 1; j < boardWidth + 1; j++) {

            board[i][j].printcell(out);
            out.print("|");

        }
        //    System.out.print(" |");
        out.println();
        out.println("+-+-+-+-+-+-+-+-+");
    }
    //System.out.println("+-+-+-+-+-+-+-+-+");
}
}

import java.io.*;

public class LifeCell {

private LifeCell[][] board; // A reference to the board array.
private boolean alive;      // Stores the state of the cell.
private int row, col;       // Position of the cell on the board.
private int count;          // Stores number of living neighbors.

public LifeCell(LifeCell[][] b, int r, int c) {
    // Initialize the life cell as dead.  Store the reference
    // to the board array and the board position passed as
    // arguments.  Initialize the neighbor count to zero.
    board = b;
    count = 0;
    alive = false;
    row = r;
    col = c;
}

public void countNeighbors() {
    // reset count
    count = 0;
    // Set "count" to the number of living neighbors of this cell.
    //if (row != 0 && col != 0 && row != board[0].length && col != board[0].length) {
        if (board[row - 1][col - 1].isAlive()) {
            count++;
            //System.out.println("ROW: " + row + " COL: " + col + " COUNT: " + count);
        }
        if (board[row - 1][col].isAlive()) {
            count++;
          //  System.out.println("ROW: " + row + " COL: " + col + " COUNT: " + count);
        }
        if (board[row - 1][col + 1].isAlive()) {
            count++;
            //System.out.println("ROW: " + row + " COL: " + col + " COUNT: " + count);
        }
        if (board[row][col - 1].isAlive()) {
            count++;
          //  System.out.println("ROW: " + row + " COL: " + col + " COUNT: " + count);
        }
        if (board[row][col + 1].isAlive()) {
            count++;
          //  System.out.println("ROW: " + row + " COL: " + col + " COUNT: " + count);
        }
        if (board[row + 1][col - 1].isAlive()) {
            count++;
           // System.out.println("ROW: " + row + " COL: " + col + " COUNT: " + count);
        }
        if (board[row + 1][col].isAlive()) {
            count++;
          //  System.out.println("ROW: " + row + " COL: " + col + " COUNT: " + count);
        }
        if (board[row + 1][col + 1].isAlive()) {
            count++;
            //System.out.println("ROW: " + row + " COL: " + col + " COUNT: " + count);
        }
    //System.out.println("ROW: " + row + " COL: " + col + " COUNT: " + count);
    //}
}

public void updateState() {
    // Examine "alive" and "count" to determine the state of this
    // cell in the next generation.  If the state changes in the
    // next generation, invoke "toggle" to update the state.
    if(isAlive() && (count == 2 || count == 3)){
        // stay alive
    }else if (isAlive() && (count < 2 || count > 3)) {
        toggle();
    } else if (!isAlive() && count == 3) {
        toggle();
    }

}

public void printcell(PrintWriter out) {
    // When alive, the cell is printed as a *; when dead a space
    if (alive == true) {
        out.print("*");
    } else {
        out.print(" ");
    }

}

public boolean isAlive() {
    return alive;
}

public void toggle() {
    alive = !alive;
}
}

txt file to read in 8 8 7 2 2 2 3 2 6 2 7 3 2 3 4 3 5 3 7 4 2 4 7 5 2 5 7 6 2 6 4 6 5 6 7 7 2 7 3 7 6 7 7

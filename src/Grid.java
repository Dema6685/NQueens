/**
 * Created by Dominic DeMaria(Dema6685) on 7/18/2017.
 * CS-203 10:15-11:50
 **/
import java.util.*;
public class Grid {
    private Queen [] placedQueens; //Array of all the queens placed in the puzzle
    public boolean solved = false;
    private boolean [][] isQueen; //2D-Array reping the grid showing true if queen there or false if not
    public int size;

    public Grid(int size)
    {
        placedQueens = new Queen[size];
        isQueen = new boolean[size][size];
        this.size = size; //The size of the grid counting from 0 -> size
        resetGrid();
    }//End of placedQueens function

    /**
     * Resets the grid to a blank grid
     * returns nothing
     */
    public void resetGrid()
    {

        for(int i = 0; i < placedQueens.length; i++)//Moves row
        {
            placedQueens[i] = null;
            for(int j = 0; j < placedQueens.length; j++)//Moves cols
            {
                isQueen[i][j] = false; //False since no queen anymore
            }//end of inner for
        }//end of outer for
    }//end of resetGrid


    public void setPlacedQueens(int type)
    {
        final int BRUTE = 1;
        final int REPAIR = 2;

        switch (type)
        {
            case BRUTE:
                for(int i=0; i < size; i++)
                {
                    placedQueens[i] = new Queen(0,i);
                    isQueen[0][i] = true;
                }
                break;

            case REPAIR:
                Random randomNumber = new Random();
                int randomInt;
                for(int i = 0; i < placedQueens.length; i++)
                {
                    randomInt = randomNumber.nextInt(placedQueens.length);
                    placedQueens[i] = new Queen(randomInt,i);
                    isQueen[randomInt][i] = true;
                }
                break;
            default:
                break;
        }

    }

    public long bruteForce()
    {
        long startTime = System.nanoTime();
        long endTime;

        setPlacedQueens(1);//Calling bruteForce setup

        endTime = bruteForce(0);

        return endTime - startTime;
    }

    private long bruteForce(int queen)
    {
        long endTime=0;
        if(queen == size - 1)
        {
            for(int i = 0; i < size; i++)
            {
                moveQueenRow(queen,i);
                if(checkPuzzle())
                    return System.nanoTime();
            }
            return System.nanoTime();
        }

        for(int i = 0; i < size; i++) {
            endTime = bruteForce(queen + 1);
            if(checkPuzzle())
                return endTime;
            moveQueenRow(queen,i);
        }
        return System.nanoTime();
    }

    public long repair()
    {

        long startTime = System.nanoTime();
        long endTime;

        setPlacedQueens(2);

        int indexWithMost = 0;
        int count = 0;
        while(!(checkPuzzle())) {

            if(count > size*size)
            {
                count = 0;
                resetGrid();
                setPlacedQueens(2);
            }

            for (int i = 0; i < placedQueens.length; i++)
            {
                placedQueens[i].setConflicts(checkNumConflicts(placedQueens[i]));
                if (placedQueens[i].getConflicts() > placedQueens[indexWithMost].getConflicts())
                    indexWithMost = i;
            }

            moveQueenRow(indexWithMost,findNewRow(placedQueens[indexWithMost]));

            count++;
        }//End of while

        endTime = System.nanoTime();

        return endTime - startTime;
    }

    public int findNewRow(Queen queen)
    {
        int newRow = 0;
        int lowestConflict = Integer.MAX_VALUE;
        Queen tempQueen = new Queen(0,queen.getCol());

        for(int i = 0; i < size; i++)
        {
            tempQueen.setRow(i);
            if(queen.getRow() == i)
            {}
            else
            {
                if(lowestConflict > countRow(tempQueen))
                {
                    lowestConflict = countRow(tempQueen);
                    newRow = tempQueen.getRow();
                }

            }
        }

        return newRow;
    }

    public boolean checkRow(Queen queen)
    {
        int numQueens = 0;

        for(int i = 0; i < isQueen.length; i++)
        {
            if(isQueen[queen.getRow()][i])
                numQueens++;
            if (numQueens > 1)
                return false;
        }

        return true;
    }//End of checkRow

    public boolean checkCol(Queen queen)
    {

        int numQueens = 0;
        for(int i = 0; i < isQueen.length; i++)
        {
            if(isQueen[i][queen.getCol()])
                numQueens++;
            if (numQueens > 1)
                return false;
        }
        return true;
    }//End of checkCol

    public boolean checkDiags(Queen queenToCheck)
    {
        //If it is in the top row
        if(queenToCheck.getRow() == 0)
        {
            for(int i=1; i<size; i++)
            {
                //check out of bounds
                if(queenToCheck.getCol() + i >= size || queenToCheck.getRow()+ i >= size)
                {}
                //Check diag to right
               else if(isQueen[queenToCheck.getRow()+i][queenToCheck.getCol()+i])
                   return false;
                //checks left diag
               if(queenToCheck.getCol() == 0)
               {}
               else if(queenToCheck.getCol() > 0)
               {
                   if(queenToCheck.getCol()-i < 0)
                   {}
                   else if(isQueen[queenToCheck.getRow()+i][queenToCheck.getCol()-i])
                       return false;
               }
            }
        }

        if(queenToCheck.getRow() != 0 ) {
            //if it is not in the top row
            for (int i = 1; i < size; i++) {
                //Checks upper left diag
                if (queenToCheck.getRow() - i < 0 || queenToCheck.getCol() - i < 0) {
                } else if (isQueen[queenToCheck.getRow() - i][queenToCheck.getCol() - 1])
                    return false;
                if (queenToCheck.getRow() + i >= size || queenToCheck.getCol() - i < 0) {
                }
                //checks the lower left diag
                else if (isQueen[queenToCheck.getRow() + i][queenToCheck.getCol() - i])
                    return false;

                if (queenToCheck.getRow() - i < 0 || queenToCheck.getCol() + i >= size) {
                }
                //check lower right
                else if (isQueen[queenToCheck.getRow() - i][queenToCheck.getCol() + i])
                    return false;
                if (queenToCheck.getRow() + i >= size || queenToCheck.getCol() + i >= size) {
                }
                //check upper right
                else if (isQueen[queenToCheck.getRow() + i][queenToCheck.getCol() + i])
                    return false;
            }
        }
            return true;
    }//end of checkDiags

    public int countRow(Queen queen) {
        int numQueens = 0;

        for (int i = 0; i < placedQueens.length; i++) {
            if (placedQueens[i].getRow() == queen.getRow())//counts all queens in that row
                numQueens++;
        }

        return numQueens - 1;//-1 so you dont count the queen u are checking//End of checkRow
    }

    public int countCol(Queen queen)
    {
        int numQueens = 0;
        for(int i = 0; i < placedQueens.length; i++)
        {
            if(placedQueens[i].getCol() == queen.getCol())//counts all queens in that col
                numQueens++;
        }
        return numQueens - 1;//-1 so you dont count the queen u are checking
    }//End of checkCol

    public int countDiags(Queen queenToCheck)
    {
        int numQueens = 0;
        //If it is in the top row
        if(queenToCheck.getRow() == 0)
        {
            for(int i=1; i<size; i++)
            {
                //check out of bounds
                if(queenToCheck.getCol() + i >= size || queenToCheck.getRow()+ i >= size)
                {}
                //Check diag to right
                else if(isQueen[queenToCheck.getRow()+i][queenToCheck.getCol()+i])
                    numQueens++;
                //checks left diag
                if(queenToCheck.getCol() == 0)
                {}
                else if(queenToCheck.getCol() > 0)
                {
                    if(queenToCheck.getCol()-i < 0)
                    {}
                    else if(isQueen[queenToCheck.getRow()+i][queenToCheck.getCol()-i])
                        numQueens++;
                }
            }
        }

        if(queenToCheck.getRow() != 0 ) {
            //if it is not in the top row
            for (int i = 1; i < size; i++) {
                //Checks upper left diag
                if (queenToCheck.getRow() - i < 0 || queenToCheck.getCol() - i < 0) {
                } else if (isQueen[queenToCheck.getRow() - i][queenToCheck.getCol() - 1])
                    numQueens++;
                if (queenToCheck.getRow() + i >= size || queenToCheck.getCol() - i < 0) {
                }
                //checks the lower left diag
                else if (isQueen[queenToCheck.getRow() + i][queenToCheck.getCol() - i])
                    numQueens++;

                if (queenToCheck.getRow() - i < 0 || queenToCheck.getCol() + i >= size) {
                }
                //check lower right
                else if (isQueen[queenToCheck.getRow() - i][queenToCheck.getCol() + i])
                    numQueens++;
                if (queenToCheck.getRow() + i >= size || queenToCheck.getCol() + i >= size) {
                }
                //check upper right
                else if (isQueen[queenToCheck.getRow() + i][queenToCheck.getCol() + i])
                    numQueens++;
            }
        }
        return numQueens;
    }//end of checkDiags


    /**checkNumConflicts
     * Checkts the number of conflicts a queen has
     * param queen: the queen that is being checked
     * return the number of conflicts the queen has
     */
    public int checkNumConflicts(Queen queen)
    {
        int numConflicts = 0;

        numConflicts += countRow(queen);
        //numConflicts += countCol(queen);//This will probably always give 0
        numConflicts += countDiags(queen);

        return numConflicts;
    }

    /**toString
     * Makes the puzzle as a string
     * returns the puzzle as a string
     */
    @Override
    public String toString() {
        String grid = "";
        for (int i = 0; i < size; i++)
        {
            grid += "\n";
            for (int j = 0; j < size; j++)
            {
                if(isQueen[i][j])
                {
                    grid += " Q ";
                }
                else
                {
                    grid += " _ ";
                }
            }
        }
        return grid;
    }

    /**checkPuzzle
     * checks the puzzle to see if it is solved
     * return: True if puzzle is good False if not
     */

    public boolean checkPuzzle()
    {
        for(int i = 0; i < size; i++) {
            if(!(checkRow(placedQueens[i])))
                return false;
            if(!(checkCol(placedQueens[i])))
                return false;
            if(!(checkDiags(placedQueens[i])))
                return false;
        }
        return true;
    }
    /**moveQueenRow
     * Moves the queen to a new row
     * param queen: to move, row: what row to move it to
     * return: none
     */
    public void moveQueenRow(int queen, int row)
    {
        Queen tempQueen = new Queen(placedQueens[queen].getRow(),placedQueens[queen].getCol());
        placedQueens[queen].setRow(row);
        isQueen[tempQueen.getRow()][tempQueen.getCol()] = false; //Pick up queen
        isQueen[placedQueens[queen].getRow()][placedQueens[queen].getCol()] = true; //Move it down
        tempQueen = null;
    }

    /**moveQueenCol
     * Moves the queen to a new col
     * param queen: to move,col: what col to move it to
     * return: none
     */
    public void moveQueenCol(int queen, int col)
    {
        Queen tempQueen = new Queen(placedQueens[queen].getRow(),placedQueens[queen].getCol());
        placedQueens[queen].setCol(col);
        isQueen[tempQueen.getRow()][tempQueen.getCol()] = false; //Pick up queen
        isQueen[placedQueens[queen].getRow()][placedQueens[queen].getCol()] = true; //Move it down
        tempQueen = null;
    }

}//End of placedQueens class

/**
 * Created by Dominic DeMaria(Dema6685) on 7/19/2017.
 * CS-203 10:15 -11:50
 **/
public class Queen {
    int row;
    int col;
    int conflicts; //number of conflicts queen has

    public Queen(int row, int col)
    {
        this.row = row;
        this.col = col;
        conflicts = 0;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getConflicts()
    {
        return conflicts;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setConflicts(int conflicts)
    {
        this.conflicts = conflicts;
    }





}

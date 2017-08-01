import java.util.Scanner;

/**
 Created by Dominic DeMaria (Dema6685) on 7/12/2017.
 CS-203 10:15 - 11:50
 **/


public class Main {

    /**
     * menu
     * param input
     * returns what command the user selected
     */
    public static int menu(Scanner input)
    {
        int userCommand; //The users command
        System.out.println("***************************************************");
        System.out.println("Current available commands:");
        System.out.println("1. --> Solve with brute force");
        System.out.println("2. --> Solve with iterative repair");
        System.out.println("3. --> Change puzzle size");
        System.out.println("0. --> Exit the program");
        System.out.println("***************************************************");
        System.out.print("Your choice --> ");

        //Checks for valid int input
        while(!input.hasNextInt())
        {
            input.next();
            System.out.println("Please enter a valid listed command!");
        }
        userCommand = input.nextInt();
        System.out.println();

        return userCommand;
    }//End of menu

    public static int sizeSelection(Scanner input)
    {
        int size;

        System.out.println("Please enter the grid size you would like: ");
        System.out.print("Size --> ");

        while(!input.hasNextInt())
        {
            System.out.println();
            input.next();
            System.out.println("Please enter a valid listed command!");
            System.out.print("Size --> ");
        }

        size = input.nextInt();

        System.out.println();

        return size;
    }//End of sizeSelection

    public static void main(String[] args) {
        int gridSize = 0;
        Scanner input = new Scanner(System.in);
        int userSelection = -1;
        Grid puzzle;

        //These are for the switch-case statement to act as a menu selection
        final int BRUTEFORCE = 1;
        final int REPAIR = 2;
        final int CHANGESIZE = 3;
        final int EXIT = 0;
        long runTime;


        System.out.println("Welcome to the N-Queens Puzzle");//Welcome line

        gridSize = sizeSelection(input);
        //Checking that size is a good size
        while(gridSize < 4)
        {
            System.out.println("Size must be greater than 3!");
            gridSize = sizeSelection(input);
        }

        //Some output to let the user know what the size is being set to
        System.out.println("Setting the grid size to " + gridSize +"....");
        puzzle = new Grid(gridSize);

        userSelection = menu(input);//Call the menu

        //While loop always running to exit user needs to call exit command in menu
        while(true) {
            switch (userSelection) {
                case BRUTEFORCE:
                    //Solve the puzzle brute force style
                    puzzle.resetGrid();

                    runTime = puzzle.bruteForce();
                    System.out.print("Result:");
                    System.out.println(puzzle);
                    System.out.println("Runtime: "+runTime +"\n");
                    break;

                case REPAIR:
                    //Solve the puzzle repair style
                    puzzle.resetGrid();
                    runTime = puzzle.repair();
                    System.out.print("Result:");
                    System.out.println(puzzle);
                    System.out.println("Runtime: "+runTime +"\n");
                    break;
                case CHANGESIZE:
                    System.out.println("Current size of puzzle is: " + gridSize);
                    do
                    {
                        gridSize = sizeSelection(input);
                        if(gridSize < 4)
                            System.out.println("Size must be greater than 3!");
                    }while(gridSize < 4);

                    puzzle = new Grid(gridSize);
                    System.out.println("New puzzle has been created with size: "+gridSize);
                    System.out.println();
                    break;

                case EXIT:
                    System.out.println("Exiting program Goodbye!");
                    System.exit(3);//End program
                    break;

                default:
                    break;
            }//End of switch
            userSelection = menu(input);//Call menu for next user selection
        }//End of while loop

    }//End of main
}

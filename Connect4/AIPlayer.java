// COURSE		: COMP 2150
// INSTRUCTOR	: Mike Domratzki
// ASSIGNMENT	: assignment 3
// QUESTION	: question #1
//
// PURPOSE : Respond as per to player move and and take turn by looking  its winning percentage
//
//
//-----------------------------------------

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class AIPlayer implements Player
{
    private  Status[][] board;//internal storage
    private GameLogic gl;
    private Random randomNumber;
    private Scanner ss;

    public AIPlayer()
    {
        randomNumber = new Random();
        ss=new Scanner(System.in);

    }


    /**
     * verifyCol - private helper method to determine if an integer is a valid
     * column that still has spots left.
     * @param col - integer (potential column number)
     * @return - is the column valid?
     */
    private boolean verifyCol(int col) {

        return (col >= 0 && col < board[0].length && board[0][col] == Status.NEITHER);
    }

    /**
     * lastMove - called to indicate the last move of the opponent. See the
     * assignment for more detail.
     * @param lastCol - column where the opponent played.
     */
    @Override
    public void lastMove(int lastCol)
    {

        if (lastCol != -1)
        {
            int lastPosn = drop(lastCol);
            board[lastPosn][lastCol] = Status.ONE; // this is the AI's move, so it's TWO.
        }

        int num = bestMove();

        int posn = drop(num);
        board[posn][num] = Status.TWO;
        gl.setAnswer(num); // tell the Gl class where the AI chose.

    }

    @Override
    public void gameOver(Status winner)
    {


        System.out.println("Game over!");
        if (winner == Status.NEITHER) {
            System.out.println("Game is a draw.");
        } else if (winner == Status.ONE) {
            System.out.println("You win.");
        } else {
            System.out.println("Computer wins.");
        }

    }

    @Override
    public void setInfo(int size, GameLogic gl)
    {

        this.gl=gl;
        board = new Status[size][size];
        for (Status[] s : board)
        {
            Arrays.fill(s, Status.NEITHER);
        }
        ConnectLogic temp = (ConnectLogic) gl;

    }

    /**
     * drop - a private helper method that finds the position of a marker
     * when it is dropped in a column.
     * @param col the column where the piece is dropped
     * @return the row where the piece lands
     */
    private int drop(  int col) {
        int posn = 0;
        while (posn < board.length && board[posn][col] == Status.NEITHER) {
            posn ++;
        }
        return posn-1;
    }



    //for score
    private int scorePosition(Status[][] tempboard)
    {
        int score =0;
        //horizontal

        for(int i=0 ; i<tempboard.length ; i++) {
            Status[] rowArray = new Status[tempboard[0].length];
            for (int j = 0; j < rowArray.length; j++) {
                rowArray[j] = tempboard[i][j];
            }

            for (int j = 0; j < tempboard[0].length - 3; j++) {
                int z = 0;
                Status[] window = new Status[4];
                for (int k = j; k < j + 4; k++) {
                    window[z] = rowArray[k];
                    z++;
                }
                score += evaluateWindow(window);

            }

        }

        //vertical
        for(int i=0 ; i<tempboard[0].length ; i++)
        {
            Status[] colArray = new Status[tempboard.length];
            for (int j=0 ; j<tempboard.length ; j++)
            {
                colArray[j] = tempboard[j][i];
            }

            for (int j=0 ; j< tempboard.length-3;j++) {

                int z = 0;
                Status[] window = new Status[4];
                for (int k = j; k < j + 4; k++) {
                    window[z] = colArray[k];
                    z++;
                }

                score+=evaluateWindow(window);

            }
        }

        //positive diagonal
        for(int i=0;i<board.length-3;i++) {
            for (int j = 0; j < board[0].length - 3; j++) {
                Status[] window = new Status[4];
                for (int z = 0; z < 4; z++) {
                    window[z] = board[i + z][j + z];
                }
                score+=evaluateWindow(window);
            }
        }

        //negative diagonal
        for(int i=0;i<board.length-3;i++) {
            for (int j = 0; j < board[0].length - 3; j++) {
                Status[] window = new Status[4];
                for (int z = 0; z < 4; z++) {
                    window[z] = board[i + 3 - z][j + z];
                }
                score+=evaluateWindow(window);
            }
        }
                return score;
    }

    private ArrayList<Integer> validLocations()
    {
        ArrayList<Integer> locations = new ArrayList<>();
        for(int i=0;i<board[0].length;i++)
        {
            if(board[0][i].toString().equals(Status.NEITHER.toString()))
            {
                locations.add(i);
            }
        }
        return locations;
    }
    int bestMove()
    {
        int bestscore=0;
        Random rand = new Random();


        ArrayList<Integer> locations = validLocations();
        int rr = rand.nextInt(locations.size());
        int bestcol =locations.get(rr) ;
        for(int i=0;i<locations.size();i++)
        {
            Status[][] tempboard = new Status[board.length][board[0].length];
            for(int j=0;j<board.length;j++)
            {
                tempboard[j]=Arrays.copyOf(board[j],board[j].length);
            }

            int posn = 0;
            while (posn < tempboard.length && tempboard[posn][locations.get(i)] == Status.NEITHER) {
                posn ++;
            }
            int row = posn-1;

            tempboard[row][locations.get(i)]=Status.TWO;
            int aa = scorePosition(tempboard);
               // System.out.println("score"+aa+"location "+locations.get(i)+"bestscore "+bestscore);
            if(aa>bestscore)
            {
                bestscore=aa;
                bestcol = locations.get(i);
            }

        }
        return bestcol;
    }



    //evaluate window

    int evaluateWindow(Status[] window)
    {
        int score=0;
        int num=0;
        int num1=0;
        int num2=0;
        for(int z=0;z<window.length;z++)
        {
            if(window[z].toString().equals(Status.TWO.toString()))
            {
                num++;
            }
            if(window[z].toString().equals(Status.ONE.toString()))
            {
                num1++;
            }
            if(window[z].toString().equals(Status.NEITHER.toString()))
            {
                num2++;
            }
        }

        if(num == 4)
        {
            score+=100;
        }
        else
        {
            if (num==3 && num2==1)
            {
                score+=10;
            }else
            {
                if(num==2 && num2==2)
                {
                    score+=5;
                }
            }
        }
        if(num1==3 && num2==1)
        {
            score-=80;
        }


        return score;
    }



}




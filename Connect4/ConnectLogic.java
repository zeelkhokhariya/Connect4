
// COURSE		: COMP 2150
// INSTRUCTOR	: Mike Domratzki
// ASSIGNMENT	: assignment 3
// QUESTION	: question #1
//
// PURPOSE : This will make all the logic for the winning the game for the AI
//           and human as well.
//         : make the alternate turn's without using the loop.
//         : Generate a random turn for the AI or Human.
//
//
//-----------------------------------------

import java.util.Arrays;
import java.util.Random;

public class ConnectLogic implements GameLogic
{
    //some private variables
    private int boardSize;
    private int selector;
    private Status[][] board;
    //private Objects
    private Player humanplayer;
    private Player computerPlayer;
    //constructor
    public ConnectLogic()
    {
        //intil all the variables and all the objects
        Random randomNumber = new Random();
        boardSize = randomNumber.nextInt(7)+6;
        selector = randomNumber.nextInt(2)+1;
        System.out.println("boardsize"+boardSize+" selector"+selector);
        humanplayer=new HumanPlayer();
        computerPlayer = new AIPlayer();
        board = new Status[boardSize][boardSize];
        for (Status[] s : board) {
            Arrays.fill(s, Status.NEITHER);
        }
        setPlayersBoardSize();

        playersTurn();
    }

    //Decide the turn of the player
    private void playersTurn()
    {
        //selector 1 is for the HUMAN
        //selector 2 is for the AI
        if(selector == 1)
        {
            //it will start the game by passing -1
            humanplayer.lastMove(-1);
        }
        else
        {
            //it will start the game by passing -1
            computerPlayer.lastMove(-1);
        }
    }

    //setting the info of the both player.
    private void setPlayersBoardSize()
    {
        //set the human's info
        humanplayer.setInfo(boardSize, this);
        //set the AI's info
        computerPlayer.setInfo(boardSize, this);
    }

    //this method will send the last move
    @Override
    public void setAnswer(int col)
    {
        //if the player is the Human then it will set the '0' there.
        if(selector==1)
        {
            //gives us the row of the last move
            int posn=drop(col);
            //set the human into the board
            board[posn][col]=Status.ONE;
            //checking for the winning
            if(iswinner(posn,col).equals(Status.ONE.toString()))
            {
                humanplayer.gameOver(Status.ONE);
                return;
            }
            //check for the game draw or not
            else
                {
                    if(iswinner(posn,col).equals(Status.NEITHER.toString()))
                    {
                        humanplayer.gameOver(Status.NEITHER);
                        return;
                    }
                    //if it's not draw then change the player turn to AI
                    else {
                        selector = 2;
                        computerPlayer.lastMove(col);
                    }
            }

        }
        else
        {
            //get the last move raw
            int posn=drop(col);
            //set the AI into the board
            board[posn][col]=Status.TWO;
            //check for the AI's winning conditions
            if(iswinner(posn,col).equals(Status.TWO.toString()))
            {//
                humanplayer.gameOver(Status.TWO);
                return;
            }
            //check for the AI's draw condition
            else
                {
                    if(iswinner(posn,col).equals(Status.NEITHER.toString()))
                    {
                        humanplayer.gameOver(Status.NEITHER);
                        return;
                    }
                    //if it's not draw then change player to Human
                    else {
                        selector = 1;
                        humanplayer.lastMove(col);
                    }
            }
        }
    }



    //Determining whether winner is Human
    private String iswinner(int posn,int col)
    {
        int row=posn;
        int coloumn = col;
        Status ss = board[row][coloumn];

        int count=0;
        //checking bottom
        for(int i=row,j=coloumn,z=0;z<4 && i<board[0].length;i++,z++)
        {
            if(board[i][j]==ss)
            {
                count++;
            }
            if(count==4)
            {
                return ss.toString();
            }
        }

        //checking left
        count=0;
        for(int i=row,j=coloumn,z=0;z<4 && j>=0;j--,z++)
        {
          // System.out.println("row:"+i+" col:"+j);
            if(board[i][j]==ss)
            {
                count++;
            }
            if(count==4)
            {
                return ss.toString();
            }
        }

        //checking left between
         count=0;
        for(int z=0, i=row,j=coloumn,j1=coloumn+1;z<5 && j>=0 && j1<board[0].length;z++)
        {
           if (board[i][j]==ss)
           {
               count++;
               j--;
           }
           if(board[i][j1]==ss)
           {
               count++;
               j1++;
           }

            if(count==4)
            {
                return ss.toString();
            }
        }

        //checking left bottom between
        count=0;
        for(int z=0, i=row,j=coloumn,j1=coloumn+1,i1=row-1;z<5 && j>=0 && i<board[0].length && i1>=0 && j1<board[0].length;z++)
        {
            if (board[i][j]==ss)
            {
                count++;
                j--;
                i++;
            }
            if(board[i1][j1]==ss)
            {
                count++;
                j1++;
                i1--;
            }

            if(count==4)
            {
                return ss.toString();
            }
        }

        //checking right bottom between
        count=0;
        for(int z=0, i=row,j=coloumn,j1=coloumn-1,i1=row-1;z<5 && j<board[0].length && i<board[0].length && i1>=0 && j1>=0;z++)
        {
            if (board[i][j]==ss)
            {
                count++;
                j++;
                i++;
            }
            if(board[i1][j1]==ss)
            {
                count++;
                j1--;
                i1--;
            }

            if(count==4)
            {
                return ss.toString();
            }
        }


        //check right
        count=0;
        for(int i=row,j=coloumn, z=0;z<4 &&j<board[0].length;j++,z++)
        {
            // System.out.println("row:"+i+" col:"+j);
            if(board[i][j]==ss)
            {
                count++;
            }
            if(count==4)
            {
                return ss.toString();
            }
        }

        //check left bottom
        count=0;
        for(int i=row,j=coloumn, z=0;z<4 && j>=0 && i<board[0].length;j--,z++,i++)
        {
            // System.out.println("row:"+i+" col:"+j);
            if(board[i][j]==ss)
            {
                count++;
            }
            if(count==4)
            {
                return ss.toString();
            }
        }

        //check right bottom
        count=0;
        for(int i=row,j=coloumn, z=0;z<4 && j<board[0].length && i<board[0].length;j++,z++,i++)
        {
            // System.out.println("row:"+i+" col:"+j);
            if(board[i][j]==ss)
            {
                count++;
            }
            if(count==4)
            {
                return ss.toString();
            }
        }

        //checking for draw
        count=0;
        for(int j=0;j<board[0].length;j++)
        {
            if(board[0][j]!=Status.NEITHER)
            {
             count++;
            }
            if(count == board[0].length)
            {
                return Status.NEITHER.toString();
            }
        }
        return "Nothing";
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
    //getter method for getting the Player
    public Player getHumanPLayer()
    {
        return humanplayer;
    }
}

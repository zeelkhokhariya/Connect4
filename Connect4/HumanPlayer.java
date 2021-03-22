//-----------------------------------------
// NAME		: Zeelkumar Khokhariya
// STUDENT NUMBER	: 7880619
// COURSE		: COMP 2150
// INSTRUCTOR	: Mike Domratzki
// ASSIGNMENT	: assignment 3
// QUESTION	: question #1
//
// PURPOSE : Humanplayer class will implements the Player and human class both and
//           we are making a connection with the UI interface here.
//         : Can and should be changeable
//
//
//-----------------------------------------
public class HumanPlayer implements Player, Human {

    //some private objects
    private UI HUI;
    private UI swingGui;
    private GameLogic gl;
    //constructor
    public HumanPlayer()
    {
        HUI = new TextUI();
        //swingGui = new SwingGUI();
    }

    //Override methods
    @Override
    public void setAnswer(int col)
    {
        gl.setAnswer(col);
    }

    @Override
    public void lastMove(int lastCol)
    {
        HUI.lastMove(lastCol);
       //swingGui.lastMove(lastCol);
    }

    @Override
    public void gameOver(Status winner)
    {
        HUI.gameOver(winner);
       // swingGui.gameOver(winner);
    }

    @Override
    public void setInfo(int size, GameLogic gl)
    {
        this.gl=gl;
        HUI.setInfo(this, size);
      //swingGui.setInfo(this,size);
    }
}

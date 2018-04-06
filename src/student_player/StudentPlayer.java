package student_player;

import boardgame.Move;
import tablut.TablutBoardState;
import tablut.TablutPlayer;

/** A player file submitted by a student. */
public class StudentPlayer extends TablutPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260745418");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    @Override
	public Move chooseMove(TablutBoardState boardState) {
    	Move move;
    	if(player_id == TablutBoardState.MUSCOVITE) {
    		move = Minimax.run(boardState, Minimax.PlayerType.MAX);
    	}
    	else{
    		move = Minimax.run(boardState, Minimax.PlayerType.MIN); 		
    	}
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...

        // Return your move to be processed by the server.
        return move;
    }
}
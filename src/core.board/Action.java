/**
 * 
 */
package core.board;

import static core.game.Move.index;


/**
 * @author ����
 *
 */
public class Action {
	PieceColor _whoseMove;
	char c;
	char r;
	int k;
	PieceColor[] _board;
	String actionType;
	
	public Action(char c, char r, Board _board,String actionType) {
		this.k=index(c, r);
		this._whoseMove = _board.whoseMove();
		this.actionType = actionType;
		this._board = _board.get_board();
	}
	//����
    protected void set() {
    	//assert validSquare(k);
		assert 'A' <= k && k <= 'A' + 18 ;
        _board[k] = _whoseMove;
    }
    //����
    protected void pick() {
    	
    }
    //����
    protected void kill() {
    	
    }
}

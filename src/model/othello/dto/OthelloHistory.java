/**
 * 
 */
package model.othello.dto;

import model.base.dto.Board;
import model.base.dto.History;
import model.base.dto.State;

/**
 * @author toshiki
 *
 */
public final class OthelloHistory extends History<Stone> {
  private State currentState;

  /**
   * @param board
   */
  public OthelloHistory(Board<Stone> board, State currentState) {
    super(board);
    this.currentState = currentState;
  }


  @Override
  public Board<Stone> getBoard() {
    return super.getBoard();
  }
  

  /**
   * @return the currentState
   */
  public State getCurrentState() {
    return currentState;
  }
}

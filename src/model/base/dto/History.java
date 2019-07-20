/**
 * (C) 2020 Toshiki.
 */
package model.base.dto;

/**
 * ボードの情報を履歴として扱うDTO.
 * <p>
 * BaseではBoardだけを扱っているため、継承して扱う
 * 
 * @author toshiki
 * @since 1.0
 */
public class History<T extends Cell> implements Cloneable {
  private Board<T> board;

  protected History(Board<T> board) {
    this.board = board.clone();
  }


  protected Board<T> getBoard() {
    return board.clone();
  }

  @Override
  protected History<T> clone() throws CloneNotSupportedException {
    return new History<T>(board.clone());
  }
}

/**
 * (C) 2020 Toshiki.
 */
package model.othello.dto;

import java.util.ArrayList;
import model.base.dto.Board;
import model.base.dto.Cell;
import model.base.dto.Coord;
import model.base.dto.State;

/**
 * ゲームのボード上を表すDTO.
 * <p>
 * 8 x 8 のボード上の情報を扱う.
 * 
 * @author toshiki
 * @since 1.0
 */
public class OthelloBoard extends Board<Stone> {
  private static final Integer BOARD_SIZE = 8;


  /**
   * 空のBoardを作成
   */
  public OthelloBoard() {
    super(BOARD_SIZE);
  }

  /**
   * 座標の位置の石を取得.
   * 
   * @param origin
   * @return 石が存在しない場合、又は範囲外の場合はNull
   */
  public Stone getStoneOf(Coord origin) {
    return super.getCellOf(origin);
  }

  /**
   * 石が置けるかどうかを判定.
   * 
   * @param origin
   * @return Trueは石を置くことが出来る
   */
  public Boolean isPutableStone(Coord origin) {
    return super.isPutable(origin);
  }

  /**
   * 石を置く.
   * 
   * 置けなかったら、例外を投げる IllegalArgumentException
   * 
   * @param origin
   * @param stone
   */
  public void putStoneTo(Coord origin, Stone stone) throws IllegalArgumentException {
    super.putCellTo(origin, stone);
  }


  /**
   * 引数のプレイヤーの数を数える
   * 
   * @param player
   * @return
   */
  public int countStoneOf(State surface) {
    ArrayList<ArrayList<Stone>> field = super.getField();
    int count = 0;

    for (ArrayList<Stone> within : field) {
      for (Stone s : within) {
        if (s.getState() == surface) {
          count++;
        }
      }
    }
    return count;
  }
}

/**
 * (C) 2020 Toshiki.
 */
package model.dto;

import model.player.Player;

/**
 * ゲームのボード上を表すDTO.
 * <p>
 * 8 x 8 のボード上の情報を扱う.
 * 
 * @author toshiki
 * @since 1.0
 */
public class Board implements Cloneable {
  private static final Integer MIN_SIZE = 0;
  private static final Integer MAX_SIZE = 7;

  private Stone field[][];

  
  /**
   * 空のBoardを作成
   */
  public Board() {
    this(new Stone[MAX_SIZE][MAX_SIZE]);
  }

  /**
   * パラメータで初期化
   * 
   * @param field
   */
  private Board(Stone field[][]) {
    this.field = field;
  }

  /**
   * 座標の位置の石を取得.
   * 
   * @param c
   * @return 石が存在しない場合、又は範囲外の場合はNull
   */
  public Stone getStoneOf(Coord c) {
    if (!isWithinRange(c)) {
      return null;
    }
    return field[c.getX()][c.getY()];
  }

  /**
   * 石が置けるかどうかを判定.
   * 
   * @param c
   * @return Trueは石を置くことが出来る
   */
  public Boolean isPutableStone(Coord c) {
    return isWithinRange(c) && field[c.getX()][c.getY()] == null;
  }

  /**
   * 石を置く.
   * 
   * 置けなかったら、例外を投げる
   * IllegalArgumentException
   * 
   * @param c
   * @param player
   */
  public void putStoneTo(Coord c, Player player) {
    if (!isPutableStone(c)) {
      throw new IllegalArgumentException("Out of range");
    }

    this.field[c.getX()][c.getY()] = new Stone(player);
  }

  /**
   * 引数のプレイヤーの数を数える
   * 
   * @param player
   * @return
   */
  public int countStoneOf(Player player) {
    int result = 0;

    for (int x = 0; x < MAX_SIZE; x++) {
      for (int y = 0; y < MAX_SIZE; y++) {
        Stone stone = field[x][y];
        if (stone != null && stone.getPlayer() == player) {
          result++;
        }
      }
    }
    return result;
  }

  /**
   * 自身の複製
   */
  @Override
  protected Board clone() {
    Stone field[][] = new Stone[MAX_SIZE][MAX_SIZE];

    for (int x = 0; x < MAX_SIZE; x++) {
      for (int y = 0; y < MAX_SIZE; y++) {
        field[x][y] = this.field[x][y].clone();
      }
    }

    return new Board(field);
  }
  
  /**
   * 引数の座標が範囲内かどうかを判定
   * 
   * @param c
   * @return 真偽
   */
  private static Boolean isWithinRange(Coord c) {
    int x = c.getX();
    int y = c.getY();
    return (MIN_SIZE <= x && x <= MAX_SIZE && MIN_SIZE <= y && y <= MAX_SIZE);
  }
}

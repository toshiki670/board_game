/**
 * (C) 2020 Toshiki.
 */
package model.base.dto;

import java.util.ArrayList;

/**
 * ゲームのボード上を表す共通DTO.
 * <p>
 * ボード上の情報を扱う.
 * 
 * @author toshiki
 * @param <T> Cellを継承したボード上の要素の型
 * @since 1.0
 */
public class Board<T extends Cell> implements Cloneable {
  private ArrayList<ArrayList<T>> field;
  private int horizontalBoardSize;
  private int verticalBoardSize;
  private final static int FIELD_MIN_SIZE = 0;



  /**
   * 指定したサイズでボードのサイズを指定.
   * 
   * @param boardSize
   */
  public Board(int boardSize) {
    this(boardSize, boardSize);
  }

  /**
   * 指定したサイズでボードのサイズを指定.
   * 
   * @param horizontalBoardSize
   * @param verticalBoardSize
   */
  public Board(int horizontalBoardSize, int verticalBoardSize) {
    this(Board.<T>getEmptyField(horizontalBoardSize, verticalBoardSize));
    this.horizontalBoardSize = horizontalBoardSize;
    this.verticalBoardSize = verticalBoardSize;
  }

  /**
   * パラメータで初期化
   * 
   * @param field
   */
  private Board(ArrayList<ArrayList<T>> field) {
    this.field = field;
  }

  /**
   * ボード上の情報を取得
   * 
   * @return field
   */
  public ArrayList<ArrayList<T>> getField() {
    return field;
  }

  /**
   * 座標の位置の要素を取得.
   * 
   * @param origin
   * @return 要素が存在しない場合、又は範囲外の場合はNull
   */
  public T getCellOf(Coord origin) {
    Coord c = Board.coordFix(origin);
    int h = c.getH();
    int v = c.getV();
    if (!isWithinRange(h, v)) {
      return null;
    }
    return field.get(h).get(v);
  }

  /**
   * 要素を入れる.
   * 
   * 置けなかったら、例外を投げる IllegalArgumentException
   * 
   * @param origin
   * @param element
   */
  public void putCellTo(Coord origin, T element) throws IllegalArgumentException {
    if (!isPutable(origin)) {
      throw new IllegalArgumentException("Out of range");
    }
    Coord c = Board.coordFix(origin);
    field.get(c.getH()).set(c.getV(), element);
  }

  /**
   * 要素が置けるかどうかを判定.
   * 
   * @param origin
   * @return Trueは要素を置くことが出来る
   */
  public Boolean isPutable(Coord origin) {
    Coord c = Board.coordFix(origin);
    int h = c.getH();
    int v = c.getV();
    return isWithinRange(h, v) && field.get(h).get(v) == null;
  }

  /**
   * 引数の座標が範囲内かどうかを判定.
   * 
   * @param h
   * @param v
   * @return 真偽
   */
  private Boolean isWithinRange(int h, int v) {
    return (FIELD_MIN_SIZE <= h && h <= horizontalBoardSize && FIELD_MIN_SIZE <= v
        && v <= verticalBoardSize);
  }

  /**
   * 座標修正.
   * <p>
   * 外部から入力される座標は1オリジンを想定しているため、<br>
   * 入力された座標を-1する.
   * 
   * @param origin
   * @return
   */
  private static Coord coordFix(Coord origin) {
    return origin.clone().moveTo(c -> {
      c.addH(-1);
      c.addV(-1);
    });
  }


  /**
   * BoardのFieldに対応した空の要素を返す.
   * 
   * @param <T>
   * @param horizontalBoardSize
   * @param verticalBoardSize
   * @return
   */
  @SuppressWarnings("serial")
  private static <T extends Cell> ArrayList<ArrayList<T>> getEmptyField(int horizontalBoardSize,
      int verticalBoardSize) {
    return new ArrayList<ArrayList<T>>() {
      {
        for (int h = FIELD_MIN_SIZE; h < horizontalBoardSize; h++) {
          add(new ArrayList<T>() {
            {
              for (int V = FIELD_MIN_SIZE; V < verticalBoardSize; V++) {
                add(null);
              }
            }
          });
        }
      }
    };
  }

  /**
   * 自身の複製.
   * <p>
   * TODO: 要テスト。
   */
  @Override
  public Board<T> clone() {
    ArrayList<ArrayList<T>> newField = new ArrayList<ArrayList<T>>();

    for (ArrayList<T> vertical : field) {
      ArrayList<T> newVertical = new ArrayList<T>();
      for (T cell : vertical) {
        newVertical.add(cell);
      }
      newField.add(newVertical);
    }
    return new Board<T>(newField);
  }
}

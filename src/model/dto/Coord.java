/**
 * (C) 2020 Toshiki.
 */
package model.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * 座標DTO
 * <p>
 * ボードゲームに最適化した座標を扱う.<br>
 * x は横方向の座標を表す.<br>
 * y は縦方向の座標を表す.<br>
 * 
 * @author Toshiki
 * @since 1.0
 */
public final class Coord {
  private int x;
  private int y;

  
  /**
   * 座標を初期化
   * 
   * @param x
   * @param y
   */
  public Coord(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * 座標が含まれるテキストを分解して初期化
   * 
   * @param both
   */
  public Coord(String both) {

  }

  /**
   * 文字列型の座標を整数に変換して初期化
   * 
   * @param x
   * @param y
   */
  public Coord(String x, String y) {}

  /**
   * 横方向の座標を取得
   * 
   * @return 横方向
   */
  public int getX() {
    return x;
  }

  /**
   * 縦方向の座標を取得
   * 
   * @return 縦方向
   */
  public int getY() {
    return y;
  }

  /**
   * 引数の座標に置き換える
   * 
   * @param coord
   * @return 自身を返す
   */
  private Coord replace(Coord coord) {
    this.x = coord.getX();
    this.y = coord.getY();
    return this;
  }

  /**
   * パラメータに与えられたラムダ式に基づき座標を移動
   * 
   * @param next ラムダ式
   * @return 自身を返す
   */
  public Coord moveTo(UnaryOperator<Coord> next) {
    return this.replace(next.apply(this));
  }


  /**
   * 上に一つ移動
   * 
   * @return ラムダ式
   */
  private static UnaryOperator<Coord> upper() {
    return c -> {
      return new Coord(c.getX(), c.getY() - 1);
    };
  }

  /**
   * 下に一つ移動
   * 
   * @return ラムダ式
   */
  private static UnaryOperator<Coord> lower() {
    return c -> {
      return new Coord(c.getX(), c.getY() + 1);
    };
  }

  /**
   * 左に一つ移動
   * 
   * @return ラムダ式
   */
  private static UnaryOperator<Coord> left() {
    return c -> {
      return new Coord(c.getX() - 1, c.getY());
    };
  }

  /**
   * 右に一つ移動
   * 
   * @return ラムダ式
   */
  private static UnaryOperator<Coord> right() {
    return c -> {
      return new Coord(c.getX() + 1, c.getY());
    };
  }

  /**
   * 左上に一つ移動
   * 
   * @return ラムダ式
   */
  private static UnaryOperator<Coord> upperLeft() {
    return c -> {
      return new Coord(c.getX() - 1, c.getY() - 1);
    };
  }

  /**
   * 右上に一つ移動
   * 
   * @return ラムダ式
   */
  private static UnaryOperator<Coord> upperRight() {
    return c -> {
      return new Coord(c.getX() + 1, c.getY() - 1);
    };
  }

  /**
   * 左下に一つ移動
   * 
   * @return ラムダ式
   */
  private static UnaryOperator<Coord> lowerLeft() {
    return c -> {
      return new Coord(c.getX() - 1, c.getY() + 1);
    };
  }

  /**
   * 右下に一つ移動
   * 
   * @return ラムダ式
   */
  private static UnaryOperator<Coord> lowerRight() {
    return c -> {
      return new Coord(c.getX() + 1, c.getY() + 1);
    };
  }
  
  /**
   * 鉢方向のリストを生成
   * 
   * @return ラムダ式のArrayList
   */
  @SuppressWarnings("serial")
  public static List<UnaryOperator<Coord>> getAround() {
    List<UnaryOperator<Coord>> around =
        Collections.unmodifiableList(new ArrayList<UnaryOperator<Coord>>() {
          {
            add(upper());
            add(lower());
            add(left());
            add(right());
            add(upperLeft());
            add(upperRight());
            add(lowerLeft());
            add(lowerRight());
          }
        });

    return around;
  }
}

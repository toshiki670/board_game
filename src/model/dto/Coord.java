/**
 * (C) 2020 Toshiki.
 */
package model.dto;

import java.util.function.Consumer;

/**
 * 座標DTO.
 * <p>
 * ボードゲームに最適化した座標を扱う.<br>
 * また、それぞれの座標は1を最初の数字として扱う.<br>
 * <ul>
 * <li>x は横方向の座標を表す.</li>
 * <li>y は縦方向の座標を表す.</li>
 * </ul>
 * 
 * @author Toshiki
 * @since 1.0
 */
public final class Coord implements Cloneable {
  private int x;
  private int y;

  
  /**
   * 座標を初期化.
   * 
   * @param x 1から始まる整数
   * @param y 1から始まる整数
   */
  public Coord(int x, int y) {
    this.x = x - 1;
    this.y = y - 1;
  }

  /**
   * 座標が含まれるテキストを分解して初期化.
   * 
   * @param both
   */
  public Coord(String both) {}

  /**
   * 文字列型の座標を整数に変換して初期化.
   * 
   * @param x
   * @param y
   */
  public Coord(String x, String y) {}

  /**
   * 横方向の座標に加算.
   * 
   * @param x the x to set
   */
  public void addX(int x) {
    this.x += x;
  }

  /**
   * 縦方向の座標に加算.
   * 
   * @param y the y to set
   */
  public void addY(int y) {
    this.y += y;
  }

  /**
   * 自身の複製
   */
  @Override
  public Coord clone() {
    // TODO Auto-generated method stub
    return new Coord(this.x, this.y);
  }

  /**
   * 横方向の座標を取得.
   * 
   * @return 横方向
   */
  public int getX() {
    return x;
  }
  
  /**
   * 縦方向の座標を取得.
   * 
   * @return 縦方向
   */
  public int getY() {
    return y;
  }

  /**
   * パラメータに与えられたラムダ式に基づき座標を移動.
   * 
   * @param next ラムダ式
   * @return 自身を返す
   */
  public Coord moveTo(Consumer<Coord> next) {
    next.accept(this);
    return this;
  }
}

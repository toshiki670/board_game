/**
 * (C) 2020 Toshiki.
 */
package model.base.dto;

import java.util.function.Consumer;

/**
 * 座標DTO.
 * <p>
 * ボードゲームに最適化した座標を扱う.<br>
 * また、それぞれの座標は1を最初の数字として扱う.<br>
 * <ul>
 * <li>horizontal は横方向の座標を表す.</li>
 * <li>vertical は縦方向の座標を表す.</li>
 * </ul>
 * 
 * @author Toshiki
 * @since 1.0
 */
public final class Coord implements Cloneable {
  private int horizontal;
  private int vertical;

  
  /**
   * 座標を初期化.
   * 
   * @param x 1から始まる整数
   * @param y 1から始まる整数
   */
  public Coord(int horizontal, int vertical) {
    this.horizontal = horizontal;
    this.vertical = vertical;
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
  public Coord(String horizontal, String vertical) {}

  /**
   * 横方向の座標に加算.
   * 
   * @param horizontal
   */
  public void addH(int horizontal) {
    this.horizontal += horizontal;
  }

  /**
   * 縦方向の座標に加算.
   * 
   * @param vertical
   */
  public void addV(int vertical) {
    this.vertical += vertical;
  }

  /**
   * 自身の複製
   */
  @Override
  public Coord clone() {
    // TODO Auto-generated method stub
    return new Coord(this.horizontal, this.vertical);
  }

  /**
   * 横方向の座標を取得.
   * 
   * @return 横方向
   */
  public int getH() {
    return horizontal;
  }
  
  /**
   * 縦方向の座標を取得.
   * 
   * @return 縦方向
   */
  public int getV() {
    return vertical;
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

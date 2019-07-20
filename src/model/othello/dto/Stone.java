/**
 * (C) 2020 Toshiki.
 */
package model.othello.dto;

import model.base.dto.Cell;
import model.base.dto.State;


/**
 * Cellを継承したOthello用の石
 * <p>
 * ボード上の情報を扱う.
 * 
 * @author toshiki
 * @since 1.0
 */
public final class Stone implements Cell {
  private State darkSurface;
  private State whiteSurface;
  private State currentSurface;


  /**
   * 石のインスタンスを生成
   * 
   * @param darkSurface
   * @param whiteSurface
   * @param currentSurface
   */
  public Stone(State darkSurface, State whiteSurface, State currentSurface) {
    this.darkSurface = darkSurface;
    this.whiteSurface = whiteSurface;
    this.currentSurface = currentSurface;
  }

  @Override
  public State getState() {
    return currentSurface;
  }

  /**
   * 石を反転
   */
  @Override
  public void callMethod() {
    if (currentSurface == darkSurface) {
      currentSurface = whiteSurface;
    }
    if (currentSurface == whiteSurface) {
      currentSurface = darkSurface;
    }
  }

  @Override
  protected Stone clone() {
    return new Stone(darkSurface, whiteSurface, currentSurface);
  }
}

/**
 * 
 */
package view.render.dto;

import java.util.ArrayList;

/**
 * @author toshiki
 *
 */
public final class RenderInfo {
  private CellCharHashMap cellChars;
  private ArrayList<String> horizontalGuidChar;
  private ArrayList<String> verticalGuidChar;
  
  /**
   * @param cellChars
   * @param horizontalGuidChar
   * @param verticalGuidChar
   */
  public RenderInfo(CellCharHashMap cellChars, ArrayList<String> horizontalGuidChar,
      ArrayList<String> verticalGuidChar) {
    super();
    this.cellChars = cellChars;
    this.horizontalGuidChar = horizontalGuidChar;
    this.verticalGuidChar = verticalGuidChar;
  }

  /**
   * @return the cellChars
   */
  public final CellCharHashMap getCellChars() {
    return cellChars;
  }

  /**
   * @return the horizontalGuidChar
   */
  public final ArrayList<String> getHorizontalGuidChar() {
    return horizontalGuidChar;
  }

  /**
   * @return the verticalGuidChar
   */
  public final ArrayList<String> getVerticalGuidChar() {
    return verticalGuidChar;
  }
}

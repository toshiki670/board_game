package view.render.cli;

import java.util.ArrayList;
import model.base.dto.Board;
import model.base.dto.Cell;
import view.render.dto.RenderInfo;

public final class BoardRender<T extends Cell, U extends Board<T>> implements CliRender {
  private static final String BLANK_CHAR = " ";

  private static final String UPPER_LEFT_BORDER = "";
  private static final String UPPER_RIGHT_BORDER = "";
  private static final String UNDER_LEFT_BORDER = "";
  private static final String UNDER_RIGHT_BORDER = "";

  private static final String HORIZONTAL_BORDER = "";
  private static final String VERTICAL_BORDER = "";

  private RenderInfo info;
  private U board;

  private String horizontalGuidBar;
  private int cellWidth;



  /**
   * @param info
   * @param board
   */
  public BoardRender(RenderInfo info) {
    super();
    this.cellWidth = getCellWidth(info);
    this.info = info;
    this.board = null;
    this.horizontalGuidBar = buildHorizontalGuidBar(info);
  }

  public void setBoard(U board) {
    this.board = board;
  }

  @Override
  public void render() {
    // TODO Auto-generated method stub

  }

  private int getCellWidth(RenderInfo data) {
    var maxLength = 0;

    for (var ch : data.getCellChars().values())
      maxLength = maxLength < ch.length() ? ch.length() : maxLength;
    for (var ch : data.getHorizontalGuidChar())
      maxLength = maxLength < ch.length() ? ch.length() : maxLength;
    if (maxLength < 1)
      return 0;

    return maxLength;
  }


  private String buildHorizontalGuidBar(RenderInfo data) {
    var hGuidBar = new StringBuilder();

    for (var ch : data.getHorizontalGuidChar()) {
      hGuidBar.append(getStringRepeat(ch, this.cellWidth));
    }

    return hGuidBar.toString();
  }


  private String getStringRepeat(String data, int count) {
    var result = new StringBuilder();

    result.append(data);
    while (result.toString().length() < count) {
      result.append(BLANK_CHAR);
    }

    return result.toString();
  }

}

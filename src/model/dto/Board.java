package model.dto;

import model.player.Player;

/**
 * @author toshiki
 *
 */
public class Board implements Cloneable {
  private static final Integer MIN_SIZE = 0;
  private static final Integer MAX_SIZE = 7;

  private Stone field[][];

  public Board() {
    this(new Stone[MAX_SIZE][MAX_SIZE]);
  }

  private Board(Stone field[][]) {
    this.field = field;
  }

  public Stone getStoneOf(Coord c) {
    if (!isWithinRange(c)) {
      return null;
    }
    return field[c.getX()][c.getY()];
  }

  public Boolean isPutableStone(Coord c) {
    return isWithinRange(c) && field[c.getX()][c.getY()] == null;
  }

  public void putStoneTo(Coord c, Player player) {
    if (!isPutableStone(c)) {
      throw new IllegalArgumentException("Out of range");
    }

    this.field[c.getX()][c.getY()] = new Stone(player);
  }

  /**
   * @deprecated このクラスに持たせるべき責務ではないため
   * @param c
   */
  public void turnOver(Coord c) {
    if (!isWithinRange(c) || this.field[c.getX()][c.getY()] == null) {
      throw new IllegalArgumentException("Out of range");
    }
    
    this.field[c.getX()][c.getY()].turnOver();
  }

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

  private Boolean isWithinRange(Coord c) {
    int x = c.getX();
    int y = c.getY();
    return (MIN_SIZE <= x && x <= MAX_SIZE && MIN_SIZE <= y && y <= MAX_SIZE);
  }

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
}

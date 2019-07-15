package model.dto;

import model.player.Player;

public class Board implements Cloneable {
  private static final Integer MIN_SIZE = 0;
  private static final Integer MAX_SIZE = 7;

  private Stone field[][];

  public Board() {
    this(new Stone[MAX_SIZE][MAX_SIZE]);
  }

  public Board(Stone field[][]) {
    this.field = field;
  }

  public Player getPlayerOf(int x, int y) {
    if (!isWithinRange(x, y)) {
      return null;
    }
    return field[x][y].getPlayer();
  }

  public Boolean isPutableStone(int x, int y) {
    return isWithinRange(x, y) && field[x][y] == null;
  }

  public void putStoneTo(int x, int y, Player player) {
    if (!isPutableStone(x, y)) {
      throw new IllegalArgumentException("Out of range");
    }

    this.field[x][y] = new Stone(player);
  }

  public void turnOver(int x, int y) {
    this.field[x][y].turnOver();
  }

  public int countStoneOf(Player player) {
    int result = 0;

    for (int x = 0; x < MAX_SIZE; x++) {
      for (int y = 0; y < MAX_SIZE; y++) {
        if (field[x][y].getPlayer() == player) {
          result++;
        }
      }
    }
    return result;
  }

  private Boolean isWithinRange(int x, int y) {
    return (MIN_SIZE <= x && x <= MAX_SIZE && MIN_SIZE <= y && y <= MAX_SIZE);
  }

  @Override
  protected Board clone() throws CloneNotSupportedException {
    Stone field[][] = new Stone[MAX_SIZE][MAX_SIZE];

    for (int x = 0; x < MAX_SIZE; x++) {
      for (int y = 0; y < MAX_SIZE; y++) {
        field[x][y] = (Stone) this.field[x][y].clone();
      }
    }

    return new Board(field);
  }
}

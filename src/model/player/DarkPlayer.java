package model.player;

public final class DarkPlayer implements Player {
  private static DarkPlayer instance;
  private String userName;

  private DarkPlayer() {
    this.userName = "Player1";
  }

  public static Player getInstance() {
    if (instance == null) {
      instance = new DarkPlayer();
    }
    return instance;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}

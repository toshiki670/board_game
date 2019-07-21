package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import model.base.dto.Coord;
import model.othello.logic.OthelloGame;

public class Main {

  public static void main(String[] args) throws NumberFormatException, IOException {
    // TODO Auto-generated method stub

    // show board
    // show help
    // show history {1}
    // set language Japanese
    // set dark[player] name
    // set white[player] name

    // put A1
    // put a1
    // put 1A
    // put 1a
    // put A 1
    // put a 1
    // put 1 A
    // put 1 a

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    
    OthelloGame game = OthelloGame.getInstance();

    if (!game.isEnd()) {
      
      game.getBoard().getField().forEach(System.out::println);
      
      int x = Integer.parseInt(br.readLine());
      int y = Integer.parseInt(br.readLine());

      
      game.putStone(new Coord(x, y));

      if (game.isStateOfPutPlace()) {
        game.commit();

      }

    }



  }

}

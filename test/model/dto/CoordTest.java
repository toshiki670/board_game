/**
 * 
 */
package model.dto;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.othello.dto.Coord;

/**
 * @author toshiki
 *
 */
class CoordTest {

  /**
   * @throws java.lang.Exception
   */
  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  /**
   * @throws java.lang.Exception
   */
  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {}

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {}

  /**
   * 取得した横方向の座標が引数に対して、1少ないこと
   */
  @Test
  void getX_0001() {
    Coord testInstance = new Coord(1, 8);

    assertEquals(0, testInstance.getX());
  }

  /**
   * 取得した縦方向の座標が引数に対して、1少ないこと
   */
  @Test
  void getY_0001() {
    Coord testInstance = new Coord(1, 8);

    assertEquals(7, testInstance.getY());
  }

  /**
   * Replaseの処理が参照渡しではないこと
   * 
   * @throws Exception
   */
  @Test
  void replace_0001() throws Exception {
    Coord testInstance = new Coord(1, 1);
    Coord replaceSource = new Coord(8, 8);
    Method replace = Coord.class.getDeclaredMethod("replace", Coord.class);
    replace.setAccessible(true);
    Field fieldX = Coord.class.getDeclaredField("x");
    Field fieldY = Coord.class.getDeclaredField("y");
    fieldX.setAccessible(true);
    fieldY.setAccessible(true);


    assertAll("テスト対象の初期値", () -> assertEquals(0, testInstance.getX()),
        () -> assertEquals(0, testInstance.getY())); 
    assertAll("置換え元の初期値", () -> assertEquals(7, replaceSource.getX()),
        () -> assertEquals(7, replaceSource.getY()));
    
    // privateなtestInstanceのメソッドを強制的に実行
    replace.invoke(testInstance, replaceSource);

    
    assertAll("テスト対象の置換え結果の確認", () -> assertEquals(7, testInstance.getX()),
        () -> assertEquals(7, testInstance.getY()));
    assertAll("置き換え対象の値が変わっていないこと", () -> assertEquals(7, replaceSource.getX()),
        () -> assertEquals(7, replaceSource.getY()));

    // privateなインスタンス変数に強制的に値を代入
    fieldX.set(replaceSource, 0);
    fieldY.set(replaceSource, 0);

    
    assertAll("テスト対象の置換え結果の確認", () -> assertEquals(7, testInstance.getX()),
        () -> assertEquals(7, testInstance.getY()));
    assertAll("置き換え対象の値が変わっていないこと", () -> assertEquals(0, replaceSource.getX()),
        () -> assertEquals(0, replaceSource.getY()));

  }
  
  @Test
  void moveTo_0001() throws Exception {
    Coord testInstance = new Coord(1, 100);
    Consumer<Coord> testLambda = c -> {
      c.addX(100);
      c.addY(-100);
    };
    
    assertAll("テスト対象の初期値", () -> assertEquals(0, testInstance.getX()),
        () -> assertEquals(99, testInstance.getY())); 
    
    testInstance.moveTo(testLambda);
    
    assertAll("テスト対象の移動後の確認", () -> assertEquals(100, testInstance.getX()),
        () -> assertEquals(-1, testInstance.getY())); 
    
  }

}

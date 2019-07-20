/**
 * 
 */
package model.base.dto;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.base.dto.Coord;

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
   * 取得した横方向の座標が引数に対して、変わらないこと
   */
  @Test
  void getH_0001() {
    Coord testInstance = new Coord(1, 8);

    assertEquals(1, testInstance.getH());
  }

  /**
   * 取得した縦方向の座標が引数に対して、変わらないこと
   */
  @Test
  void getV_0001() {
    Coord testInstance = new Coord(1, 8);

    assertEquals(8, testInstance.getV());
  }


  
  @Test
  void moveTo_0001() throws Exception {
    Coord testInstance = new Coord(1, 100);
    Consumer<Coord> testLambda = c -> {
      c.addH(100);
      c.addV(-100);
    };
    
    assertAll("テスト対象の初期値", () -> assertEquals(1, testInstance.getH()),
        () -> assertEquals(100, testInstance.getV())); 
    
    testInstance.moveTo(testLambda);
    
    assertAll("テスト対象の移動後の確認", () -> assertEquals(101, testInstance.getH()),
        () -> assertEquals(0, testInstance.getV())); 
    
  }

}

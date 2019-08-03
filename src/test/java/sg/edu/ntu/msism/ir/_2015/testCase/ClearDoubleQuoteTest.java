package sg.edu.ntu.msism.ir._2015.testCase;

import org.junit.jupiter.api.Test;
import sg.edu.ntu.msis.ir._2015.searchServer.OurSearchUtil;

public class ClearDoubleQuoteTest {

  @Test
  void test() {
    String s1 = "\"aaaa\"bbbbb";
    System.out.println(OurSearchUtil.cleanDoubleQuote(s1));
    String s2 = "\"\"";
    System.out.println(OurSearchUtil.cleanDoubleQuote(s2));
    String s3 = "\"aaaa bbbbb\"";
    System.out.println(OurSearchUtil.cleanDoubleQuote(s3));
    String s4 = "\"";
    System.out.println(OurSearchUtil.cleanDoubleQuote(s4));
  }
}

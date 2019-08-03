package sg.edu.ntu.msism.ir._2015.testCase;

import static java.lang.System.out;

import org.junit.jupiter.api.Test;
import sg.edu.ntu.msis.ir._2015.searchServer.OurSearchUtil;

public class AnalyzeKeywordTest {

  @Test
  void test() {
    String[] s1 = OurSearchUtil.analyzeKeyword("aaaa \"bbb cccc\" ddd");
    out.println("s1");
    for (String item : s1) {
      out.println(item);
    }

    String[] s2 = OurSearchUtil.analyzeKeyword("aaaa    \"bbb cccc\" ddd");
    out.println("s2");
    for (String value : s2) {
      out.println(value);
    }

    String[] s3 = OurSearchUtil.analyzeKeyword("aaaa    \"bbbcccc\" ddd");
    out.println("s3");
    for (String s : s3) {
      out.println(s);
    }

    String[] s4 = OurSearchUtil.analyzeKeyword("aaaa    \"bbbcccc\"ddd");
    out.println("s4");
    for (String s : s4) {
      out.println(s);
    }

    String[] s5 = OurSearchUtil.analyzeKeyword("aaaa    \"bbbcccc\"\" ddd");
    out.println("s5");
    for (String s : s5) {
      out.println(s);
    }

    String[] s6 = OurSearchUtil.analyzeKeyword("aaaa    \"bbbcccc\"\"ddd");
    out.println("s6");
    for (String s : s6) {
      out.println(s);
    }

    String[] s7 = OurSearchUtil.analyzeKeyword("aaaa    \"bbbcccc");
    out.println("s7");
    for (String s : s7) {
      out.println(s);
    }

    String[] s8 = OurSearchUtil.analyzeKeyword("\"firmly planting themselves\"");
    out.println("s8");
    for (String s : s8) {
      out.println(s);
    }

  }
}

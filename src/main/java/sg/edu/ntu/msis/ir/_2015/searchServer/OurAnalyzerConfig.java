/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.searchServer;

import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.ClassicTokenizer;

@Data
public class OurAnalyzerConfig {

  private List<String> stopWords = Arrays.asList("a", "an", "and", "are",
      "as", "at", "be", "but", "by", "for", "if", "in", "into", "is",
      "it", "no", "not", "of", "on", "or", "such", "that", "the",
      "their", "then", "there", "these", "they", "this", "to", "was",
      "will", "with");
  private Tokenizer source = new ClassicTokenizer();
  private boolean isLowerCase = false;
  private boolean isStop = false;
  private boolean isPorterStem = false;

  public OurAnalyzerConfig() {
  }

  public OurAnalyzerConfig(boolean isLowerCase, boolean isStop,
      boolean isPorterStem) {
    super();
    this.isLowerCase = isLowerCase;
    this.isStop = isStop;
    this.isPorterStem = isPorterStem;
  }
}

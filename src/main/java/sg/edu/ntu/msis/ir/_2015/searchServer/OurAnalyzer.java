/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.searchServer;

import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.util.CharArraySet;

public class OurAnalyzer extends Analyzer {

  private OurAnalyzerConfig analyzerConfig = null;

  public OurAnalyzer(OurAnalyzerConfig analyzerConfig) {
    super();
    this.analyzerConfig = analyzerConfig;
  }

  public OurAnalyzerConfig getAnalyzerConfig() {
    return analyzerConfig;
  }

  @Override
  protected TokenStreamComponents createComponents(String arg0) {
    Tokenizer source = analyzerConfig.getSource();
    TokenStream filter = new ClassicFilter(source);

    if (analyzerConfig.getIsStop()) {
      List<String> stopWords = analyzerConfig.getStopWords();
      CharArraySet stopSet = new CharArraySet(stopWords, false);
      filter = new StopFilter(filter, stopSet);
    }
    if (!analyzerConfig.getIsLowerCase()) {
      filter = new LowerCaseFilter(filter);
    }
    if (analyzerConfig.getIsPorterStem()) {
      filter = new PorterStemFilter(filter);
    }
    return new TokenStreamComponents(source, filter);
  }

}

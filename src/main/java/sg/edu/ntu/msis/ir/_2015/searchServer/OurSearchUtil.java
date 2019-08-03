/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.searchServer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.join.JoinUtil;
import org.apache.lucene.search.join.ScoreMode;
import org.apache.lucene.store.FSDirectory;
import sg.edu.ntu.msis.ir._2015.applicationServer.LogUtil;
import sg.edu.ntu.msis.ir._2015.applicationServer.StartupEngine;
import sg.edu.ntu.msis.ir._2015.webServer.model.SearchRequest;

public class OurSearchUtil {

  public static String FIELD_REVIEW_BUSINESS_ID = "business_id";

  public static String getYorN(boolean isTrue) {
    return isTrue ? "Y" : "N";
  }

  public static String getIndexLocation(String indexLocation,
      String indexName, boolean isLowerCase, boolean isStop,
      boolean isPorterStem) {
    Formatter formater = new Formatter(new StringBuilder(), Locale.US);
    try {
      return formater.format("%s//%s_%s%s_%s%s_%s%s", indexLocation,
          indexName, StartupEngine.INDEX_TYPES[0],
          OurSearchUtil.getYorN(isLowerCase),
          StartupEngine.INDEX_TYPES[1],
          OurSearchUtil.getYorN(isStop),
          StartupEngine.INDEX_TYPES[2],
          OurSearchUtil.getYorN(isPorterStem)).toString();
    } finally {
      formater.close();
    }
  }

  public static OurIndexSearcher getSearcher(SearchRequest searchRequest)
      throws IOException {
    String indexReviewFolder = OurSearchUtil.getIndexLocation(
        StartupEngine.PATH_INDEX_ROOT,//
        StartupEngine.INDEX_REVIEW,//
        searchRequest.getLowerCase(), //
        searchRequest.getStop(),//
        searchRequest.getPorterStem());
    LogUtil.out(String.format("indexReviewFolder: %s", indexReviewFolder));
    //
    String indexBusinessFolder = OurSearchUtil.getIndexLocation(
        StartupEngine.PATH_INDEX_ROOT,//
        StartupEngine.INDEX_BUSINESS,//
        searchRequest.getLowerCase(), //
        searchRequest.getStop(),//
        searchRequest.getPorterStem());
    LogUtil.out(String.format("indexBusinessFolder: %s",
        indexBusinessFolder));
    //
    IndexSearcher searcher0 = new IndexSearcher(new MultiReader(
        DirectoryReader.open(FSDirectory.open(FileSystems.getDefault()
            .getPath(indexReviewFolder))),
        DirectoryReader.open(FSDirectory.open(FileSystems.getDefault()
            .getPath(indexBusinessFolder)))));

    OurIndexSearcher searcher = new OurIndexSearcher(searcher0);
    return searcher;
  }

  public static String[] analyzeKeyword(String keyword) {
    List<String> keywords = new ArrayList<String>();
    if (keyword != null) {
      char[] charArray = keyword.toCharArray();
      List<Character> currentKeyword = new ArrayList<Character>();
      boolean isProcessingQuotes = false;
      for (int i = 0; i < charArray.length; i++) {
        if ("\"".equalsIgnoreCase(String.valueOf(charArray[i]))) {
          isProcessingQuotes = !isProcessingQuotes;
        }
        if ((" ".equalsIgnoreCase(String.valueOf(charArray[i])))
            && !isProcessingQuotes) {
          if (currentKeyword.isEmpty()) {
            continue;
          } else {
            String s = OurSearchUtil.wrapString(currentKeyword);
            s = OurSearchUtil.cleanDoubleQuote(s);
            if (s != null) {
              keywords.add(s);
            }
            currentKeyword.clear();
          }
        } else {
          currentKeyword.add(charArray[i]);
        }
        LogUtil.out(String.format("analyzeKeyword: %s %s", keywords,
            Arrays.asList(currentKeyword)));
      }
      String s = OurSearchUtil.wrapString(currentKeyword);
      s = cleanDoubleQuote(s);
      if (s != null) {
        keywords.add(s);
      }
      currentKeyword.clear();
      LogUtil.out(String.format("analyzeKeyword: %s %s", keywords,
          Arrays.asList(currentKeyword)));
    }
    return keywords.toArray(new String[]{});
  }

  public static String cleanDoubleQuote(String s) {
    if (s.contains("\"")) {
      if (s.length() > 2) {
        if (s.indexOf("\"") == 0) {
          s = s.substring(1);
          int i = s.lastIndexOf("\"");
          s = s.substring(0, i > 0 ? i : 0);
          if (s.length() == 0) {
            return null;
          } else {
            return s;
          }
        } else {
          return null;
        }
      } else {
        return null;
      }
    }
    return s;
  }

  private static String wrapString(List<Character> currentKeyword) {
    char[] result = new char[currentKeyword.size()];
    for (int j = 0; j < currentKeyword.size(); j++) {
      result[j] = currentKeyword.get(j);
    }
    return new String(result);
  }

  private static String[] breakWithQuotesString(String keyword) {
    return keyword.split(" ");
  }

  public static Query getQuery(SearchRequest searchRequest,
      OurIndexSearcher searcher) throws IOException {
    boolean isKeywordNull = null == searchRequest.getSearchQuery()
        || "".equalsIgnoreCase(searchRequest.getSearchQuery());
    boolean isLocationLatitudeNull = searchRequest.getLatitude1() == null;
    boolean isLocationLongitudeNull = searchRequest.getLongtitude1() == null;

    // final
    BooleanQuery queryFinal = new BooleanQuery();
    if (isKeywordNull && isLocationLatitudeNull && isLocationLongitudeNull) {
      return queryFinal;
    }

    BooleanQuery queryKeywords = null;
    if (!isKeywordNull) {
      // keyword
      queryKeywords = new BooleanQuery();
      String[] keywords = OurSearchUtil.analyzeKeyword(searchRequest
          .getSearchQuery());
      for (int i = 0; i < keywords.length; i++) {
        // "abc def" case
        if (keywords[i].contains(" ")) {
          String[] s = OurSearchUtil
              .breakWithQuotesString(keywords[i]);
          MultiPhraseQuery queryM = new MultiPhraseQuery();
          for (int j = 0; j < s.length; j++) {
            Term termM = new Term("text", s[j]);
            queryM.add(termM);
          }
          queryKeywords.add(queryM, BooleanClause.Occur.SHOULD);
        } else {
          try {
            // will match the search setting
            QueryParser qp = new QueryParser("text",
                new OurAnalyzer(new OurAnalyzerConfig(
                    searchRequest.getLowerCase(),
                    searchRequest.getStop(),
                    searchRequest.getPorterStem())));
            queryKeywords.add(qp.parse(keywords[i]),
                BooleanClause.Occur.SHOULD);
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
      }
    }

    BooleanQuery queryLocation = null;
    Query queryLocationFinal = null;
    if (!isLocationLatitudeNull || !isLocationLongitudeNull) {
      // location
      NumericRangeQuery<Double> queryLatitude = NumericRangeQuery
          .newDoubleRange("business_latitude",
              searchRequest.getLatitude1(),
              searchRequest.getLatitude2(), true, true);
      NumericRangeQuery<Double> queryLongitude = NumericRangeQuery
          .newDoubleRange("business_longitude",
              searchRequest.getLongtitude1(),
              searchRequest.getLongtitude2(), true, true);

      // combination
      queryLocation = new BooleanQuery();
      if (!isLocationLatitudeNull) {
        queryLocation.add(queryLatitude, BooleanClause.Occur.MUST);
      }
      if (!isLocationLongitudeNull) {
        queryLocation.add(queryLongitude, BooleanClause.Occur.MUST);
      }

      if (!isKeywordNull) {
        queryLocationFinal = JoinUtil.createJoinQuery("id2", true,
            OurSearchUtil.FIELD_REVIEW_BUSINESS_ID, queryLocation,
            searcher.getSearcher(), ScoreMode.Total);
      } else {
        queryLocationFinal = queryLocation;
      }

    }

    if (queryKeywords != null) {
      queryFinal.add(queryKeywords, BooleanClause.Occur.MUST);
    }
    if (queryLocationFinal != null) {
      queryFinal.add(queryLocationFinal, BooleanClause.Occur.MUST);
    }
    return queryFinal;
  }

}

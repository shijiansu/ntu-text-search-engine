/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.searchServer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import sg.edu.ntu.msis.ir._2015.applicationServer.LogUtil;
import sg.edu.ntu.msis.ir._2015.applicationServer.StartupEngine;
import sg.edu.ntu.msis.ir._2015.applicationServer.TimerUtil;
import sg.edu.ntu.msis.ir._2015.webServer.model.SearchRequest;
import sg.edu.ntu.msis.ir._2015.webServer.model.SearchResult;

public class OurSearchEngine {

  private static TimerUtil timer = new TimerUtil();

  public static void indexing(String sourceFolderPath,
      List<OurIndexerFieldDefiner> attributes, String indexFolderPath,
      OurAnalyzer analyzer) throws IOException {
    // remove old index folder
    File indexFolder = FileUtils.getFile(indexFolderPath);
    if (indexFolder.exists()) {
      FileUtils.deleteDirectory(indexFolder);
    }
    // build a lucene index
    // set writer
    OurIndexer indexer = new OurIndexer(indexFolderPath, analyzer);
    // set reader
    OurIndexerReader indexReader = new OurIndexerReaderJson();
    indexReader.setSourceFilePath(sourceFolderPath);
    indexReader.setAttributes(attributes);
    indexer.setIndexReader(indexReader);
    // build
    LogUtil.out(String.format("index for: %s", sourceFolderPath));
    timer.startTimer();
    indexer.buildIndex();
    timer.endTimer();
    LogUtil.out(String.format("time cost indexing (ms): %s", timer
        .getCostInMillisecond().toString()));
    indexer.close();
  }

  public static List<SearchResult> search(SearchRequest searchRequest,
      OurIndexSearcher searcher, Query query, int topN) {
    List<SearchResult> searchResultList = new ArrayList<SearchResult>();

    try {
      // perform search
      LogUtil.out(String.format("query: %s", query.toString()));

      timer.startTimer();
      TopDocs topDocs = searcher.getSearcher().search(query, topN);
      LogUtil.out(String.format(
          "count of results found: %s in top search %s",
          topDocs.totalHits, topN));
      searchRequest.setSearchResultFound(topDocs.totalHits);

      ScoreDoc[] hits = topDocs.scoreDocs;
      for (int i = 0; i < hits.length; i++) {
        Document record = searcher.getSearcher().doc(hits[i].doc);
        Explanation explain = searcher.getSearcher().explain(query,
            hits[i].doc);
        LogUtil.out(String.format("explain: %s", explain));

        SearchResult serachResult = new SearchResult();
        serachResult.setDocId(hits[i].doc);
        serachResult.setScore(hits[i].score);
        serachResult.setFields(new LinkedHashMap<String, String>());
        List<IndexableField> fields = record.getFields();
        for (IndexableField field : fields) {
          String fieldName = field.name();
          String fieldValue = record.get(fieldName);
          serachResult.getFields().put(fieldName, fieldValue);
          // get location detail
          if (OurSearchUtil.FIELD_REVIEW_BUSINESS_ID
              .equalsIgnoreCase(fieldName)) {
            secondarySearchForLocationDetail(searchRequest,
                fieldValue, serachResult);
          }
        }
        LogUtil.out(String.format("<<Output>> %s", serachResult));
        searchResultList.add(serachResult);
      }

      timer.endTimer();
      LogUtil.out(String.format("time cost querying (ms): %s", timer
          .getCostInMillisecond().toString()));
      searchRequest.setSearchCostTime(timer.getCostInMillisecond());
    } catch (Exception e) {
      LogUtil.error("Error occured when perform searching: "
          + e.getMessage());
      e.printStackTrace();
    }
    return searchResultList;
  }

  public static void secondarySearchForLocationDetail(
      SearchRequest searchRequest, String businessId,
      SearchResult serachResult) throws IOException {
    Term term = new Term("id1", businessId);

    TermQuery query = new TermQuery(term);
    String indexBusinessFolder = OurSearchUtil.getIndexLocation(
        StartupEngine.PATH_INDEX_ROOT,//
        StartupEngine.INDEX_BUSINESS,//
        searchRequest.getLowerCase(), //
        searchRequest.getStop(),//
        searchRequest.getPorterStem());
    OurIndexSearcher searcher = new OurIndexSearcher(indexBusinessFolder);

    TopDocs topDocs = searcher.getSearcher().search(query, 1);
    ScoreDoc[] hits = topDocs.scoreDocs;

    Document record = searcher.getSearcher().doc(hits[0].doc);
    if (record != null) {
      for (IndexableField field : record.getFields()) {
        String fieldName = field.name();
        String fieldValue = record.get(fieldName);
        if (!"id1".equalsIgnoreCase(fieldName)) {
          serachResult.getFields().put(fieldName, fieldValue);
        }
      }
    }
  }
}

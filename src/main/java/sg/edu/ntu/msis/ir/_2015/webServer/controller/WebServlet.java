/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.webServer.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.search.Query;
import sg.edu.ntu.msis.ir._2015.applicationServer.LogUtil;
import sg.edu.ntu.msis.ir._2015.searchServer.OurIndexSearcher;
import sg.edu.ntu.msis.ir._2015.searchServer.OurSearchEngine;
import sg.edu.ntu.msis.ir._2015.searchServer.OurSearchUtil;
import sg.edu.ntu.msis.ir._2015.webServer.model.SearchRequest;
import sg.edu.ntu.msis.ir._2015.webServer.model.SearchResult;

public class WebServlet extends HttpServlet {

  private static final long serialVersionUID = 4986594697911036536L;

  public WebServlet() {
    LogUtil.out("WebServlet() instancing...");
  }

  @Override
  protected void doGet(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    LogUtil.out("WebServlet() doGet()...");
    // reset
    request.setAttribute("model", new SearchRequest());
    request.getRequestDispatcher("/index.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    LogUtil.out("WebServlet() doPost()...");
    SearchRequest searchRequest = new SearchRequest();
    searchRequest
        .setLowerCase(Boolean.valueOf(request.getParameter("case")));
    searchRequest.setStop(Boolean.valueOf(request.getParameter("stop")));
    searchRequest.setPorterStem(Boolean.valueOf(request
        .getParameter("stem")));
    searchRequest.setSearchQuery(request.getParameter("keyword"));
    setTopN(request, searchRequest);
    setRangeForLocation(request, searchRequest);
    LogUtil.out(String.format("<<Input>> %s", searchRequest));
    //
    OurIndexSearcher searcher = OurSearchUtil.getSearcher(searchRequest);
    Query query = OurSearchUtil.getQuery(searchRequest, searcher);
    searchRequest.setLuceneQuery(query.toString());
    List<SearchResult> result = OurSearchEngine.search(searchRequest,
        searcher, query, searchRequest.getTopN());
    //
    searchRequest.setResults(result);
    request.setAttribute("model", searchRequest);
    request.getRequestDispatcher("/index.jsp").forward(request, response);
  }

  private void setTopN(HttpServletRequest request, SearchRequest searchRequest) {
    String topN = request.getParameter("topn");
    Integer topNInt = null;
    try {
      topNInt = Integer.valueOf(topN);
      if (topNInt > 100) {
        topNInt = 100;
      } else if (topNInt < 0) {
        topNInt = 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (topNInt == null) {
      topNInt = 10;
    }
    searchRequest.setTopN(topNInt);
  }

  private void setRangeForLocation(HttpServletRequest request,
      SearchRequest searchRequest) {
    // latitude
    String latitude = request.getParameter("latitude");
    Double latitudeDouble = null;
    try {
      latitudeDouble = Double.valueOf(latitude);
    } catch (Exception e) {
      e.printStackTrace();
    }
    String latitudeRange = request.getParameter("latitude_range");
    int latitudeRangeInt = 0;
    try {
      latitudeRangeInt = Integer.valueOf(latitudeRange);
      if (latitudeRangeInt > 10) {
        latitudeRangeInt = 10;
      } else if (latitudeRangeInt < 0) {
        latitudeRangeInt = 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    boolean isLatitudePlus = Boolean.parseBoolean(request
        .getParameter("latitude_plus"));

    // longitude
    String longitude = request.getParameter("longitude");
    Double longitudeDouble = null;
    try {
      longitudeDouble = Double.valueOf(longitude);
    } catch (Exception e) {
      e.printStackTrace();
    }
    String longitudeRange = request.getParameter("longitude_range");
    int longitudeRangeInt = 0;
    try {
      longitudeRangeInt = Integer.parseInt(longitudeRange);
      if (longitudeRangeInt > 10) {
        longitudeRangeInt = 10;
      } else if (longitudeRangeInt < 0) {
        longitudeRangeInt = 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    boolean isLongitudePlus = Boolean.parseBoolean(request
        .getParameter("longitude_plus"));
    // set
    if (latitudeDouble != null) {
      if (latitudeRangeInt <= 0) {
        searchRequest.setLatitude1(Double.valueOf((isLatitudePlus ? 1
            : -1) * latitudeDouble.intValue()));
        searchRequest.setLatitude2(Double.valueOf((isLatitudePlus ? 1
            : -1) * latitudeDouble.intValue() + 1));
      } else {
        searchRequest.setLatitude1(Double.valueOf((isLatitudePlus ? 1
            : -1) * latitudeDouble.intValue() - latitudeRangeInt));
        searchRequest.setLatitude2(Double.valueOf((isLatitudePlus ? 1
            : -1) * latitudeDouble.intValue() + latitudeRangeInt));
      }
    }
    if (longitudeDouble != null) {
      if (longitudeRangeInt <= 0) {
        searchRequest.setLongtitude1(Double
            .valueOf((isLongitudePlus ? 1 : -1)
                * longitudeDouble.intValue()));
        searchRequest.setLongtitude2(Double
            .valueOf((isLongitudePlus ? 1 : -1)
                * longitudeDouble.intValue() + 1));
      } else {
        searchRequest.setLongtitude1(Double
            .valueOf((isLongitudePlus ? 1 : -1)
                * longitudeDouble.intValue()
                - longitudeRangeInt));
        searchRequest.setLongtitude2(Double
            .valueOf((isLongitudePlus ? 1 : -1)
                * longitudeDouble.intValue()
                + longitudeRangeInt));
      }
    }
  }
}

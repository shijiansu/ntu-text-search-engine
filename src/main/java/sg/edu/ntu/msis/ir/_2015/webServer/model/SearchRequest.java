/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.webServer.model;

import java.util.List;
import lombok.Data;

@Data
public class SearchRequest {

  private boolean lowerCase = false;
  private boolean stop = false;
  private boolean porterStem = false;
  private Integer topN = null;
  //
  private String searchQuery = null;
  private Double latitude1 = null;
  private Double latitude2 = null;
  private Double longtitude1 = null;
  private Double longtitude2 = null;
  //
  private List<SearchResult> results = null;
  private String luceneQuery = null;
  //
  private Long searchCostTime = null;
  private Integer searchResultFound = null;

  @Override
  public String toString() {
    return "SearchRequest [lowerCase=" + lowerCase + ", stop=" + stop
        + ", porterStem=" + porterStem + ", topN=" + topN
        + ", searchQuery=" + searchQuery + ", latitude1=" + latitude1
        + ", latitude2=" + latitude2 + ", longtitude1=" + longtitude1
        + ", longtitude2=" + longtitude2 + ", results=" + results
        + ", luceneQuery=" + luceneQuery + ", searchCostTime="
        + searchCostTime + ", searchResultFound=" + searchResultFound
        + "]";
  }

}

/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.webServer.model;

import java.util.Map;
import lombok.Data;

@Data
public class SearchResult {

  private int docId;
  private float score;
  private Map<String, String> fields;

  @Override
  public String toString() {
    return "SearchResult [docId=" + docId + ", score=" + score
        + ", fields=" + fields + "]";
  }
}

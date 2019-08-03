/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.searchServer;

import lombok.Data;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;

@Data
public class OurIndexerFieldDefiner {

  private String attributeName;
  private String indexAttributeName;
  private Field field; // field type TextField StringField -> Field
  private Field.Store store;

  public OurIndexerFieldDefiner() {
    super();
  }

  public OurIndexerFieldDefiner(String attributeName, Field field, Store store) {
    super();
    this.attributeName = attributeName;
    this.indexAttributeName = attributeName;
    this.field = field;
    this.store = store;
  }

  public OurIndexerFieldDefiner(String attributeName,
      String indexAttributeName, Field field, Store store) {
    super();
    this.attributeName = attributeName;
    this.indexAttributeName = indexAttributeName;
    this.field = field;
    this.store = store;
  }
}

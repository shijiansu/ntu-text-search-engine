/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.searchServer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.DoubleDocValuesField;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatDocValuesField;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.util.BytesRef;

public abstract class OurIndexerReader {

  private String sourceFolderPath;
  private List<OurIndexerFieldDefiner> attributes = null;
  private List<Constructor<?>> constructors = null;

  public String getSourceFolderPath() {
    return sourceFolderPath;
  }

  public void setSourceFilePath(String sourceFolderPath) {
    this.sourceFolderPath = sourceFolderPath;
  }

  public List<OurIndexerFieldDefiner> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<OurIndexerFieldDefiner> attributes) {
    this.attributes = attributes;
    // set the constructor also
    constructors = new ArrayList<Constructor<?>>();
    for (OurIndexerFieldDefiner attribute : attributes) {
      constructors.add(this.getMatchedConstructor(attribute));
    }
  }

  public List<Constructor<?>> getConstructors() {
    return constructors;
  }

  public abstract void read(IndexWriter indexWriter);

  // get field from reflection
  public org.apache.lucene.document.Field getField(int iOfAttributes,
      Object value) {
    Object[] pars = getMatchedConstructorValues(
        this.getAttributes().get(iOfAttributes), value);
    try {
      return (org.apache.lucene.document.Field) getConstructors().get(
          iOfAttributes).newInstance(pars);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // to adapt different Lucene Field
  private Constructor<?> getMatchedConstructor(
      OurIndexerFieldDefiner attribute) {
    Constructor<?> constructor = null;
    //
    Field field = attribute.getField();
    try {
      if (field instanceof SortedDocValuesField) {
        Class<?>[] parTypes = new Class<?>[2];
        parTypes[0] = String.class;
        parTypes[1] = BytesRef.class;
        constructor = attribute.getField().getClass()
            .getConstructor(parTypes);
      } else if (field instanceof DoubleDocValuesField) {

      } else if (field instanceof DoubleField) {
        Class<?>[] parTypes = new Class<?>[3];
        parTypes[0] = String.class;
        parTypes[1] = double.class;
        parTypes[2] = Field.Store.class;
        constructor = attribute.getField().getClass()
            .getConstructor(parTypes);
      } else if (field instanceof FloatDocValuesField) {

      } else if (field instanceof FloatField) {

      } else if (field instanceof IntField) {

      } else if (field instanceof LongField) {

      } else if (field instanceof StoredField) {

      } else if (field instanceof StringField
          || field instanceof TextField) {
        Class<?>[] parTypes = new Class<?>[3];
        parTypes[0] = String.class;
        parTypes[1] = String.class;
        parTypes[2] = Field.Store.class;
        constructor = attribute.getField().getClass()
            .getConstructor(parTypes);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return constructor;
  }

  // to adapt different Lucene Field
  private Object[] getMatchedConstructorValues(
      OurIndexerFieldDefiner attribute, Object value) {
    Object[] pars = null;
    //
    Field field = attribute.getField();
    try {
      if (field instanceof SortedDocValuesField) {
        pars = new Object[2];
        pars[0] = attribute.getIndexAttributeName();
        // for Lucene 5. unexpected docvalues type NONE for field
        // 'business_id' (expected one of [BINARY, SORTED]). Use
        // UninvertingReader or index with docvalues.
        pars[1] = new BytesRef(String.valueOf(value));
      } else if (field instanceof DoubleDocValuesField) {

      } else if (field instanceof FloatDocValuesField) {

      } else if (field instanceof FloatField) {

      } else if (field instanceof IntField) {

      } else if (field instanceof LongField) {

      } else if (field instanceof StoredField) {

      } else if (field instanceof StringField
          || field instanceof TextField
          || field instanceof DoubleField) {
        pars = new Object[3];
        pars[0] = attribute.getIndexAttributeName();
        //
        // if(value instanceof Double){
        // // [Java Issue] double cannot be so precision
        // pars[1] = String.valueOf(value);
        // }else{
        pars[1] = value;
        // }
        // pars[1] = value;
        pars[2] = attribute.getStore();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return pars;
  }
}

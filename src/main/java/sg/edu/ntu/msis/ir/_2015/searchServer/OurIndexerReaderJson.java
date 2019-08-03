/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.searchServer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sg.edu.ntu.msis.ir._2015.applicationServer.LogUtil;

public class OurIndexerReaderJson extends OurIndexerReader {

  @Override
  public void read(IndexWriter indexWriter) {
    if (this.getAttributes() != null) {
      JSONParser jsonParser = new JSONParser();
      BufferedReader br = null;
      try {
        // Read JSON objects
        br = new BufferedReader(new FileReader(
            this.getSourceFolderPath()));

        // read the file
        String currentLine = null;
        while ((currentLine = br.readLine()) != null) {
          // System.out.println("line " + currentLine);
          JSONObject jsonLine = (JSONObject) jsonParser
              .parse(currentLine);

          // [Java Issue] double cannot be so precision
          // System.out.println(jsonLine.toJSONString());

          Document document = new Document();
          for (int i = 0; i < getAttributes().size(); i++) {
            String attributeName = getAttributes().get(i)
                .getAttributeName();
            Object value = jsonLine.get(attributeName);
            // System.out.println(attributeName + "=" + value);
            document.add(this.getField(i, value));
          }
          // index
          indexWriter.addDocument(document);
        }
      } catch (Exception e) {
        LogUtil.error("Error occured when reading the JSON: "
            + e.getMessage());
        e.printStackTrace();
      } finally {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}

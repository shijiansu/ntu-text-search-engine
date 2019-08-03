/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.searchServer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import sg.edu.ntu.msis.ir._2015.applicationServer.LogUtil;
import sg.edu.ntu.msis.ir._2015.applicationServer.StartupEngine;

public class OurIndexer {

  private IndexWriter indexWriter = null;
  private OurIndexerReader indexReader = null;

  public OurIndexer(String indexFolderPath, OurAnalyzer analyzer) {
    Path path = FileSystems.getDefault().getPath(indexFolderPath);
    Directory indexDirectory;
    try {
      indexDirectory = FSDirectory.open(path);
      // customized analyzer
      IndexWriterConfig config = new IndexWriterConfig(analyzer);
      indexWriter = new IndexWriter(indexDirectory, config);
    } catch (IOException e) {
      LogUtil.stopInfo("Unknown error occured at creating indexWriter!",
          e.getMessage());
      StartupEngine.stopEngine();
    }
  }

  public IndexWriter getIndexWriter(boolean create) throws IOException {
    return indexWriter;
  }

  public void setIndexReader(OurIndexerReader indexReader) {
    this.indexReader = indexReader;
  }

  public void close() throws IOException {
    if (indexWriter != null) {
      indexWriter.close();
    }
  }

  public void buildIndex() throws IOException {
    if (indexReader != null) {
      indexReader.read(indexWriter);
    }
  }
}

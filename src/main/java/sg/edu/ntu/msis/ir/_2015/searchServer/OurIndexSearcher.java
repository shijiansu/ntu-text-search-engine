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
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

public class OurIndexSearcher {

  private IndexSearcher searcher = null;

  public OurIndexSearcher(IndexSearcher searcher) {
    this.searcher = searcher;
  }

  public OurIndexSearcher(String indexFolderPath) throws IOException {
    Path path = FileSystems.getDefault().getPath(indexFolderPath);
    // to open a index folder as source to search
    searcher = new IndexSearcher(DirectoryReader.open(FSDirectory
        .open(path)));
  }

  public IndexSearcher getSearcher() {
    return searcher;
  }

  public void setSearcher(IndexSearcher searcher) {
    this.searcher = searcher;
  }

}

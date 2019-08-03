/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.applicationServer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import sg.edu.ntu.msis.ir._2015.searchServer.OurAnalyzer;
import sg.edu.ntu.msis.ir._2015.searchServer.OurAnalyzerConfig;
import sg.edu.ntu.msis.ir._2015.searchServer.OurIndexerFieldDefiner;
import sg.edu.ntu.msis.ir._2015.searchServer.OurSearchEngine;
import sg.edu.ntu.msis.ir._2015.searchServer.OurSearchUtil;
import sg.edu.ntu.msis.ir._2015.webServer.container.AppContextBuilder;
import sg.edu.ntu.msis.ir._2015.webServer.container.JettyServer;

public class StartupEngine {

  public static boolean ECLIPSE_RUNNING_MODE = false;

  // Change if execute for the real data set
  public static boolean TEST_MODE = true;

  public static String PATH_SOURCE_REVIEW = null;
  public static String PATH_SOURCE_BUSINESS = null;
  public static String PATH_INDEX_ROOT = null;
  public static boolean IS_CREATE_INDEX = true;
  public static int JETTY_PORT = 8585;

  public static String INDEX_REVIEW = "index_review";
  public static String INDEX_BUSINESS = "index_business";
  public static String[] INDEX_TYPES = new String[]{"case", "stop", "stem"};
  private static boolean[] CON_BOOLEAN = new boolean[]{true, false};

  public static void stopEngine() {
    System.exit(1);
  }

  public void startup(String[] args) {
    fixJettyJdkIssue();
    analyzeCommand(args);
    validateSourceFiles();
    startupWebServer();
    if (IS_CREATE_INDEX) {
      clearIndexFolder();
      buildBusinessIndex();
      buildReviewIndex();
    } else {
      LogUtil.out("re-create index is false...");
    }
    LogUtil.parameterInfo();
  }

  private void fixJettyJdkIssue() {
    LogUtil.out("fixJettyJdkIssue...");
    // to solve the jetty issue that shows need install JDK but now its JRE
    // http://sai1983.blogspot.sg/2013/08/solved-jetty-http-500-full-jdk-not-just.html
    System.setProperty("org.apache.jasper.compiler.disablejsr199", "true");
  }

  private void analyzeCommand(String[] args) {
    LogUtil.out("analyzeCommand...");
    if (args != null && args.length != 0) {
      for (int i = 0; i < args.length; i++) {
        LogUtil.out(String.format("command parameter: %s", args[i]));
      }

      // get parameter
      for (int i = 0; i < args.length; i++) {
        if (args[i].contains("=")) {
          String[] split = args[i].split("=");
          if (split.length == 2) {
            if ("sourcereview".equalsIgnoreCase(split[0])) {
              PATH_SOURCE_REVIEW = split[1];
            } else if ("sourcebusiness".equalsIgnoreCase(split[0])) {
              PATH_SOURCE_BUSINESS = split[1];
            } else if ("folderindex".equalsIgnoreCase(split[0])) {
              PATH_INDEX_ROOT = split[1];
            } else if ("iscreateindex".equalsIgnoreCase(split[0])) {
              try {
                IS_CREATE_INDEX = Boolean.valueOf(split[1]);
              } catch (Exception e) {
                LogUtil.stopInfo(
                    "Please provide a Boolean for iscreateindex in parameter!",
                    e.getMessage());
                StartupEngine.stopEngine();
              }
            } else if ("serverport".equalsIgnoreCase(split[0])) {
              try {
                JETTY_PORT = Integer.valueOf(split[1]);
              } catch (Exception e) {
                LogUtil.stopInfo(
                    "Please provide a Integer for serverport in parameter!",
                    null);
                StartupEngine.stopEngine();
              }
            } else if ("testmode".equalsIgnoreCase(split[0])) {
              TEST_MODE = Boolean.valueOf(split[1]);
            }
          } else {
            LogUtil.stopInfo(
                "Please don't use = in the parameter!", null);
            StartupEngine.stopEngine();
          }
        }
      }
      // execute at Eclipse
      if (TEST_MODE) {
        PATH_SOURCE_REVIEW = FileUtils.getFile(
            "data\\dataset\\yelp_academic_dataset_review.json")
            .getAbsolutePath();
        ;
        PATH_SOURCE_BUSINESS = FileUtils.getFile(
            "data\\dataset\\yelp_academic_dataset_business.json")
            .getAbsolutePath();
        PATH_INDEX_ROOT = FileUtils.getFile("test_data\\index")
            .getAbsolutePath();
        IS_CREATE_INDEX = true;
        LogUtil.out("using the testing mode for the application...");
      }
      // check any missing parameter
      StringBuilder errorMessage = new StringBuilder();
      if ("".equalsIgnoreCase(PATH_SOURCE_REVIEW)
          || PATH_SOURCE_REVIEW == null) {
        errorMessage.append("Review dataset file cannot be null;");
        errorMessage.append(System.lineSeparator());
      }
      if ("".equalsIgnoreCase(PATH_SOURCE_BUSINESS)
          || PATH_SOURCE_BUSINESS == null) {
        errorMessage.append("Business dataset file cannot be null;");
        errorMessage.append(System.lineSeparator());
      }
      if ("".equalsIgnoreCase(PATH_INDEX_ROOT) || PATH_INDEX_ROOT == null) {
        errorMessage.append("Index folder cannot be null;");
        errorMessage.append(System.lineSeparator());
      }
      if (!"".equalsIgnoreCase(errorMessage.toString())) {
        LogUtil.stopInfo(errorMessage.toString(), null);
        StartupEngine.stopEngine();
      }
    } else {
      LogUtil.stopInfo("Please provide suitable parameters!", null);
      StartupEngine.stopEngine();
    }
  }

  private void validateSourceFiles() {
    LogUtil.out("validateSourceFiles...");
    File sourceFileReviewFile = new File(PATH_SOURCE_REVIEW);
    if (!sourceFileReviewFile.exists()) {
      LogUtil.stopInfo("Review dataset file is not exist!", null);
      StartupEngine.stopEngine();
    }
    File sourceFileBusinessFile = new File(PATH_SOURCE_BUSINESS);
    if (!sourceFileBusinessFile.exists()) {
      LogUtil.stopInfo("Business dataset file is not exist!", null);
      StartupEngine.stopEngine();
    }
    File indexFolderRootFolder = new File(PATH_INDEX_ROOT);
    if (!indexFolderRootFolder.exists()) {
      try {
        indexFolderRootFolder.mkdirs();
      } catch (Exception e) {
        LogUtil.stopInfo(
            "Cannot create the index folder, please check the permission!",
            e.getMessage());
        StartupEngine.stopEngine();
      }
    } else if (!indexFolderRootFolder.isDirectory()) {
      LogUtil.stopInfo(
          "There is a file of same name with the index folder, please check your file system!",
          null);
      StartupEngine.stopEngine();
    }
    if (!indexFolderRootFolder.canWrite()
        || !indexFolderRootFolder.canExecute()) {
      LogUtil.stopInfo(
          "There is issue on permission of index folder, please check the permission!",
          null);
      StartupEngine.stopEngine();
    }
  }

  private void startupWebServer() {
    LogUtil.out("startupWebServer...");
    ContextHandlerCollection contexts = new ContextHandlerCollection();
    contexts.setHandlers(new Handler[]{new AppContextBuilder()
        .buildWebAppContext()});
    JettyServer jettyServer = new JettyServer(JETTY_PORT);
    jettyServer.setServerContext(contexts);
    try {
      jettyServer.startServer();
    } catch (Exception e) {
      LogUtil.stopInfo(
          "Failed to start the server, please check if the port conflict!",
          e.getMessage());
      StartupEngine.stopEngine();
    }
  }

  private void clearIndexFolder() {
    LogUtil.out("clearIndexFolder...");
    File indexFolderRootFolder = new File(PATH_INDEX_ROOT);
    if (indexFolderRootFolder.exists()) {
      try {
        indexFolderRootFolder.delete();
        indexFolderRootFolder.mkdirs();
      } catch (Exception e) {
        LogUtil.stopInfo(
            "Error occured when clearIndexFolder. "
                + "Check any running application using the same index folder!",
            e.getMessage());
        StartupEngine.stopEngine();
      }
    }
  }

  private void buildBusinessIndex() {
    LogUtil.out("buildBusinessIndex...");
    // it should be the same because it would affect by case, stop, stem
    final List<OurIndexerFieldDefiner> attributes2 = new ArrayList<OurIndexerFieldDefiner>();
    OurIndexerFieldDefiner attributes_dataset_business_business_id1 = new OurIndexerFieldDefiner(
        "business_id", "id1", new StringField("", "", Field.Store.YES),
        Field.Store.YES);
    OurIndexerFieldDefiner attributes_dataset_business_business_id2 = new OurIndexerFieldDefiner(
        "business_id", "id2", new SortedDocValuesField("", null), null);
    OurIndexerFieldDefiner attributes_dataset_business_latitude = new OurIndexerFieldDefiner(
        "latitude", "business_latitude", new DoubleField("", 0.0,
        Field.Store.YES), Field.Store.YES);
    OurIndexerFieldDefiner attributes_dataset_business_longitude = new OurIndexerFieldDefiner(
        "longitude", "business_longitude", new DoubleField("", 0.0,
        Field.Store.YES), Field.Store.YES);

    OurIndexerFieldDefiner attributes_dataset_business_name = new OurIndexerFieldDefiner(
        "name", "business_name", new StringField("", "",
        Field.Store.YES), Field.Store.YES);

    OurIndexerFieldDefiner attributes_dataset_business_fulladdress = new OurIndexerFieldDefiner(
        "full_address", "business_address", new StringField("", "",
        Field.Store.YES), Field.Store.YES);

    attributes2.add(attributes_dataset_business_business_id1);
    attributes2.add(attributes_dataset_business_business_id2);
    attributes2.add(attributes_dataset_business_latitude);
    attributes2.add(attributes_dataset_business_longitude);
    attributes2.add(attributes_dataset_business_name);
    attributes2.add(attributes_dataset_business_fulladdress);

    for (int i = 0; i < CON_BOOLEAN.length; i++) {
      for (int j = 0; j < CON_BOOLEAN.length; j++) {
        for (int k = 0; k < CON_BOOLEAN.length; k++) {

          LogUtil.out(String.format("%s=%s, %s=%s, %s=%s",
              INDEX_TYPES[0], CON_BOOLEAN[i], INDEX_TYPES[1],
              CON_BOOLEAN[j], INDEX_TYPES[2], CON_BOOLEAN[k]));

          try {
            OurSearchEngine.indexing(PATH_SOURCE_BUSINESS,//
                attributes2, //
                OurSearchUtil.getIndexLocation(PATH_INDEX_ROOT,
                    INDEX_BUSINESS, CON_BOOLEAN[i],
                    CON_BOOLEAN[j], CON_BOOLEAN[k]), //
                new OurAnalyzer(new OurAnalyzerConfig(
                    CON_BOOLEAN[i], CON_BOOLEAN[j],
                    CON_BOOLEAN[k])));
          } catch (IOException e) {
            LogUtil.stopInfo(
                "Error occured when buildBusinessIndex!",
                e.getMessage());
            StartupEngine.stopEngine();
          }
        }
      }
    }
  }

  private void buildReviewIndex() {
    LogUtil.out("buildReviewIndex...");

    final List<OurIndexerFieldDefiner> attributes = new ArrayList<OurIndexerFieldDefiner>();
    OurIndexerFieldDefiner attributes_dataset_review_business_id1 = new OurIndexerFieldDefiner(
        "business_id", OurSearchUtil.FIELD_REVIEW_BUSINESS_ID,
        new StringField("", "", Field.Store.YES), Field.Store.YES);
    OurIndexerFieldDefiner attributes_dataset_review_business_id2 = new OurIndexerFieldDefiner(
        "business_id", "review_business_id2", new SortedDocValuesField(
        "", null), null);
    OurIndexerFieldDefiner attributes_dataset_review_review = new OurIndexerFieldDefiner(
        "text", new TextField("", "", null), Field.Store.YES);

    attributes.add(attributes_dataset_review_business_id1);
    attributes.add(attributes_dataset_review_business_id2);
    attributes.add(attributes_dataset_review_review);

    for (int i = 0; i < CON_BOOLEAN.length; i++) {
      for (int j = 0; j < CON_BOOLEAN.length; j++) {
        for (int k = 0; k < CON_BOOLEAN.length; k++) {

          LogUtil.out(String.format("%s=%s, %s=%s, %s=%s",
              INDEX_TYPES[0], CON_BOOLEAN[i], INDEX_TYPES[1],
              CON_BOOLEAN[j], INDEX_TYPES[2], CON_BOOLEAN[k]));

          try {
            OurSearchEngine.indexing(PATH_SOURCE_REVIEW,//
                attributes, //
                OurSearchUtil.getIndexLocation(PATH_INDEX_ROOT,
                    INDEX_REVIEW, CON_BOOLEAN[i],
                    CON_BOOLEAN[j], CON_BOOLEAN[k]),//
                new OurAnalyzer(new OurAnalyzerConfig(
                    CON_BOOLEAN[i], CON_BOOLEAN[j],
                    CON_BOOLEAN[k])));
          } catch (IOException e) {
            LogUtil.stopInfo(
                "Error occured when buildReviewIndex!",
                e.getMessage());
            StartupEngine.stopEngine();
          }
        }
      }
    }
  }

}

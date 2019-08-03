/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.applicationServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {

  private static Logger LOG = LogManager.getLogger("[SYSTEM]");

  public static void out(String msg) {
    LOG.info("{}", msg);
  }

  public static void error(String msg) {
    LOG.error("{}", msg);
  }

  public static void parameterInfo() {
    out("***********************************************************");
    out("*                 <<APPLICATION STARTED>>                 *");
    out("* ECLIPSE_RUNNING_MODE=" + StartupEngine.ECLIPSE_RUNNING_MODE);
    out("* TEST_MODE=" + StartupEngine.TEST_MODE);
    out("* PATH_SOURCE_REVIEW=" + StartupEngine.PATH_SOURCE_REVIEW);
    out("* PATH_SOURCE_BUSINESS=" + StartupEngine.PATH_SOURCE_BUSINESS);
    out("* PATH_INDEX_ROOT=" + StartupEngine.PATH_INDEX_ROOT);
    out("* IS_CREATE_INDEX=" + StartupEngine.IS_CREATE_INDEX);
    out("* JETTY_PORT=" + StartupEngine.JETTY_PORT);
    out("***********************************************************");
  }

  public static void stopInfo(String msg, String cause) {
    error("***********************************************************");
    error("*                 <<APPLICATION IS STOP>>                 *");
    error(" * " + msg);
    if (cause != null) {
      error(" * " + cause);
    }
    error("***********************************************************");
  }
}

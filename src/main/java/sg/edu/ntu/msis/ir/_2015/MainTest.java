/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015;

import sg.edu.ntu.msis.ir._2015.applicationServer.StartupEngine;

public class MainTest {

  public static void main(String[] args) throws Exception {
    // the jsp path will be different
    StartupEngine.ECLIPSE_RUNNING_MODE = true;
    // need to pass command line parameter validation
    args = new String[1];
    args[0] = "testmode=true";
    new StartupEngine().startup(args);
  }
}

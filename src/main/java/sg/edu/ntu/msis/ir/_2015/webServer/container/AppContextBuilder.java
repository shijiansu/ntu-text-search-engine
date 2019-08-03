/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.webServer.container;

import java.io.IOException;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.core.io.ClassPathResource;
import sg.edu.ntu.msis.ir._2015.applicationServer.LogUtil;
import sg.edu.ntu.msis.ir._2015.applicationServer.StartupEngine;
import sg.edu.ntu.msis.ir._2015.webServer.controller.WebServlet;

public class AppContextBuilder {

  private WebAppContext context;

  public WebAppContext buildWebAppContext() {
    String viewPackagePath = "sg/edu/ntu/msis/ir/_2015/webServer/view";
    context = new WebAppContext();
    if (StartupEngine.ECLIPSE_RUNNING_MODE) {
      context.setResourceBase("./src/main/resources/" + viewPackagePath);
    } else {
      try {
        // use this way to read content in jar
        context.setResourceBase(new ClassPathResource(viewPackagePath)
            .getURI().toString());
      } catch (IOException e) {
        LogUtil.stopInfo("Error when starting the web server!",
            e.getMessage());
        StartupEngine.stopEngine();
      }
    }
    // access: http://localhost:8585/ntu-msis-ir
    context.setContextPath("/ntu-msis-ir");
    context.addServlet(new ServletHolder(new WebServlet()), "/search");
    return context;
  }
}

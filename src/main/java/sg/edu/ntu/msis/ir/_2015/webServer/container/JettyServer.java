/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.webServer.container;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class JettyServer {

  private Server server;

  public JettyServer(Integer runningPort) {
    server = new Server(runningPort);
  }

  public void setServerContext(ContextHandlerCollection contexts) {
    server.setHandler(contexts);
  }

  public void startServer() throws Exception {
    server.start();
  }

  public void stop() throws Exception {
    server.stop();
    server.join();
  }

  public boolean isStarted() {
    return server.isStarted();
  }

  public boolean isStopped() {
    return server.isStopped();
  }
}

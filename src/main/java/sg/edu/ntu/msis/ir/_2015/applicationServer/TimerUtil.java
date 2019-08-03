/**
 * Description: <br/>Copyright(C),2015 <br/>This program is protected by copyright laws. <br/>Data:
 * 2015-04-04
 *
 * @version 1.0
 */
package sg.edu.ntu.msis.ir._2015.applicationServer;

import java.util.Date;

public class TimerUtil {

  private long start = 0L;
  private long end = 0L;
  private Long costInMillisecond = 0L;

  public long getStart() {
    return start;
  }

  public void setStart(long start) {
    this.start = start;
  }

  public long getEnd() {
    return end;
  }

  public void setEnd(long end) {
    this.end = end;
  }

  public Long getCostInMillisecond() {
    return costInMillisecond;
  }

  public void setCostInMillisecond(Long costInMillisecond) {
    this.costInMillisecond = costInMillisecond;
  }

  public Date startTimer() {
    start = System.currentTimeMillis();
    return getTimeStamp(start);
  }

  public Date endTimer() {
    end = System.currentTimeMillis();
    costInMillisecond = calTime(start, end);
    return getTimeStamp(end);
  }

  public long getNow() {
    return System.currentTimeMillis();
  }

  public Long calTime(long start, long end) {
    return end - start;
  }

  public Date getTimeStamp(long timeStamp) {
    return new Date(start);
  }

}

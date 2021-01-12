package com.tianbo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.kechuang.core.mp.base.BaseEntity;

@Data
@TableName("ais_baowen_4")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AisBaowen4 extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private int pid;
  private String mmsi;
  private String longitude;
  private String latitude;
  private int utcYear;
  private int utcMonth;
  private int utcDay;
  private int utcHour;
  private int utcMinute;
  private int utcSec;

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public String getMmsi() {
    return mmsi;
  }

  public void setMmsi(String mmsi) {
    this.mmsi = mmsi;
  }


  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }


  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public int getUtcYear() {
    return utcYear;
  }

  public void setUtcYear(int utcYear) {
    this.utcYear = utcYear;
  }

  public int getUtcMonth() {
    return utcMonth;
  }

  public void setUtcMonth(int utcMonth) {
    this.utcMonth = utcMonth;
  }

  public int getUtcDay() {
    return utcDay;
  }

  public void setUtcDay(int utcDay) {
    this.utcDay = utcDay;
  }

  public int getUtcHour() {
    return utcHour;
  }

  public void setUtcHour(int utcHour) {
    this.utcHour = utcHour;
  }

  public int getUtcMinute() {
    return utcMinute;
  }

  public void setUtcMinute(int utcMinute) {
    this.utcMinute = utcMinute;
  }

  public int getUtcSec() {
    return utcSec;
  }

  public void setUtcSec(int utcSec) {
    this.utcSec = utcSec;
  }
}

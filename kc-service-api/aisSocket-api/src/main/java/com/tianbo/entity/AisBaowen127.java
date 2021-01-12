package com.tianbo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.kechuang.core.mp.base.BaseEntity;

@Data
@TableName("ais_baowen_1_27")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AisBaowen127 extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private int pid;
  private String mmsi;
  private int state;
  private String sog;
  private String longitude;
  private String latitude;
  private String cog;
  private String latency;


  public String getMmsi() {
    return mmsi;
  }

  public void setMmsi(String mmsi) {
    this.mmsi = mmsi;
  }

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getSog() {
    return sog;
  }

  public void setSog(String sog) {
    this.sog = sog;
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


  public String getCog() {
    return cog;
  }

  public void setCog(String cog) {
    this.cog = cog;
  }


  public String getLatency() {
    return latency;
  }

  public void setLatency(String latency) {
    this.latency = latency;
  }

}

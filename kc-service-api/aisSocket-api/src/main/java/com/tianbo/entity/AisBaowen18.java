package com.tianbo.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.kechuang.core.mp.base.BaseEntity;

@Data
@TableName("ais_baowen_18")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AisBaowen18 extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private int pid;
  private String mmsi;
  private String sog;
  private String longitude;
  private String latitude;
  private String cog;
  private String heading;
  private String timestamp;

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


  public String getHeading() {
    return heading;
  }

  public void setHeading(String heading) {
    this.heading = heading;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }
}

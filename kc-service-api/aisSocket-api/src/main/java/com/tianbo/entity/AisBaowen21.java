package com.tianbo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.kechuang.core.mp.base.BaseEntity;

@Data
@TableName("ais_baowen_21")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AisBaowen21 extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private int pid;
  private String mmsi;
  private String longitude;
  private String latitude;
  private int offposition;
  private int virtualaton;
  private String timestamp;
  private String name;
  private int type;
  private String loa;
  private String bm;


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



  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public int getOffposition() {
    return offposition;
  }

  public void setOffposition(int offposition) {
    this.offposition = offposition;
  }

  public int getVirtualaton() {
    return virtualaton;
  }

  public void setVirtualaton(int virtualaton) {
    this.virtualaton = virtualaton;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getLoa() {
    return loa;
  }

  public void setLoa(String loa) {
    this.loa = loa;
  }


  public String getBm() {
    return bm;
  }

  public void setBm(String bm) {
    this.bm = bm;
  }

}

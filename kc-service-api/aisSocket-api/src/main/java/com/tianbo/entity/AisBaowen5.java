package com.tianbo.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.kechuang.core.mp.base.BaseEntity;

@Data
@TableName("ais_baowen_5")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AisBaowen5 extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private int pid;
  private String mmsi;
  private String imo;
  private String callsign;
  private String name;
  private String type;
  private String loa;
  private String bm;
  private String eta;
  private String draught;
  private String dest;

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


  public String getImo() {
    return imo;
  }

  public void setImo(String imo) {
    this.imo = imo;
  }


  public String getCallsign() {
    return callsign;
  }

  public void setCallsign(String callsign) {
    this.callsign = callsign;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
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


  public String getEta() {
    return eta;
  }

  public void setEta(String eta) {
    this.eta = eta;
  }

  public String getDraught() {
    return draught;
  }

  public void setDraught(String draught) {
    this.draught = draught;
  }


  public String getDest() {
    return dest;
  }

  public void setDest(String dest) {
    this.dest = dest;
  }

}

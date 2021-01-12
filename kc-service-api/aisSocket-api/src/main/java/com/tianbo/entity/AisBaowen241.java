package com.tianbo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.kechuang.core.mp.base.BaseEntity;

@Data
@TableName("ais_baowen_24_1")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AisBaowen241 extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private int pid;
  private String mmsi;
  private int part24;
  private String name;

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public int getPart24() {
    return part24;
  }

  public void setPart24(int part24) {
    this.part24 = part24;
  }

  public String getMmsi() {
    return mmsi;
  }

  public void setMmsi(String mmsi) {
    this.mmsi = mmsi;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}

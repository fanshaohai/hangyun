package org.kechuang.common.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/*********************匿名内部类开始，可以提取出去******************************/

@Data
public class MultipleSheelPropety{

	private List<? extends BaseRowModel> data;

	public Sheet sheet;

	public List<? extends BaseRowModel> getData() {
		return data;
	}

	public void setData(List<? extends BaseRowModel> data) {
		this.data = data;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
}


/************************匿名内部类结束，可以提取出去***************************/

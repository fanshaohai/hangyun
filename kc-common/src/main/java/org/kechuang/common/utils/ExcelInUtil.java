package org.kechuang.common.utils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class ExcelInUtil {
	private final static String XLS = "xls";
	private final static String XLSX = "xlsx";

	/**
	 * 读取Excel到集合
	 * @param file 导入文件
	 * @return LinkedHashMap<String,Object>List
	 */
	public static List<LinkedHashMap<String,Object>> excelToMap(MultipartFile file) {
		// 定义要返回的MapList
		List<LinkedHashMap<String,Object>> mapList = new ArrayList<>();
		try {
			//检查文件
			checkFile(file);
			//获得Workbook工作薄对象
			Workbook wb = getWorkBook(file);
			//从第一行读
			int headerRowIndex = 0;
			// 读取一个工作表
			Sheet sheet = wb.getSheetAt(0);
			// 获取表头行
			Row headerRow = sheet.getRow(headerRowIndex);
			Iterator<Cell> iter = headerRow.iterator();
			List<String> excelFieldList = new ArrayList<>();
			while (iter.hasNext()) {
				Cell cell = iter.next();
				try {
					excelFieldList.add(cell.getStringCellValue());
				}catch (IllegalStateException illsEx){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					excelFieldList.add(cell.getStringCellValue());
				}
			}

			// 将列名和列号放入Map中,这样通过列名就可以拿到列号
			LinkedHashMap<Integer, String> colMap = new LinkedHashMap<>();
			for (int i = 0; i < excelFieldList.size(); i++) {
				colMap.put(i, excelFieldList.get(i));
			}
			// 填充数据
			for (int i = headerRowIndex + 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				Iterator<Cell> iter2 = row.iterator();
				// 新建要转换的对象
				LinkedHashMap<String,Object> map = new LinkedHashMap<>();
				int index = 0;
				//获取列值
				while (iter2.hasNext()) {
					Cell cell = iter2.next();
					//获取列对象
					Object content = null;
					try {
						content = getCellValue(cell);
					} catch (IllegalStateException e) {
						content = String.valueOf(cell.getStringCellValue());
					}
					//获取列名 并 转小写
//					String	enNormalName = colMap.get(index).toLowerCase();
					String	enNormalName = colMap.get(index);
					map.put(enNormalName,content);
					index++;
				}
				mapList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  mapList;
	}

	/**
	 *  判断文件是否为指定类型
	 * @param file 导入文件
	 * @throws IOException 文件流异常
	 */
	private static void checkFile(MultipartFile file) throws IOException{
		//判断文件是否存在
		if(null == file){
			throw new FileNotFoundException("文件不存在！");
		}
		//获得文件名
		String fileName = file.getOriginalFilename();
		//判断文件是否是excel文件
		if(!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)){
			throw new IOException(fileName + "不是excel文件");
		}
	}

	/**
	 * 对文件名进行解析，创建对应的 Workbook工作薄对象
	 * @param file 导入文件
	 * @return Workbook工作薄对象
	 */
	private static Workbook getWorkBook(MultipartFile file) {
		//获得文件名
		String fileName = file.getOriginalFilename();
		//创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			//获取excel文件的io流
			InputStream is = file.getInputStream();
			//根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if(fileName.endsWith(XLS)){
				//2003
				workbook = new HSSFWorkbook(is);
			}else if(fileName.endsWith(XLSX)){
				//2007
				workbook = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * 列值参数类型转换
	 * @param cell 列值
	 * @return Object对象
	 */
	private static Object getCellValue(Cell cell){
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cellValue = "";
		if(cell == null){
			return cellValue;
		}
		if (HSSFDateUtil.isCellDateFormatted(cell)) {
			return datetimeFormat.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));

		}
		//把数字当成String来读，避免出现1读成1.0的情况
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		//判断数据的类型
		switch (cell.getCellType()){
			//数字
			case Cell.CELL_TYPE_NUMERIC:
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			//字符串
			case Cell.CELL_TYPE_STRING:
				cellValue = String.valueOf(cell.getStringCellValue());
				break;
			//Boolean
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			//公式
			case Cell.CELL_TYPE_FORMULA:
				cellValue = String.valueOf(cell.getCellFormula());
				break;
			//空值
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			//故障
			case Cell.CELL_TYPE_ERROR:
				cellValue = "非法字符";
				break;
			default:
				cellValue = "未知类型";
				break;
		}
		return cellValue;
	}
}

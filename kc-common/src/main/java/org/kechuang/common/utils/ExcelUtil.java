package org.kechuang.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.alibaba.excel.EasyExcelFactory.write;

/**
 * @description:
 * @author: chenmingjian
 * @date: 19-3-18 16:16
 */
@Slf4j
public class ExcelUtil {

	private static Sheet initSheet;

	static {
		initSheet = new Sheet(1, 0);
		initSheet.setSheetName("sheet");
		//设置自适应宽度
		initSheet.setAutoWidth(Boolean.TRUE);
	}

	/**
	 * 读取少于1000行数据
	 * @param filePath 文件绝对路径
	 * @return
	 */
	public static List<Object> readLessThan1000Row(String filePath){
		return readLessThan1000RowBySheet(filePath,null);
	}

	/**
	 * 读小于1000行数据, 带样式
	 * filePath 文件绝对路径
	 * initSheet ：
	 *      sheetNo: sheet页码，默认为1
	 *      headLineMun: 从第几行开始读取数据，默认为0, 表示从第一行开始读取
	 *      clazz: 返回数据List<Object> 中Object的类名
	 */
	public static List<Object> readLessThan1000RowBySheet(String filePath, Sheet sheet){
		if(!StringUtils.hasText(filePath)){
			return null;
		}

		sheet = sheet != null ? sheet : initSheet;

		InputStream fileStream = null;
		try {
			fileStream = new FileInputStream(filePath);
			return EasyExcelFactory.read(fileStream, sheet);
		} catch (FileNotFoundException e) {
//         log.info("找不到文件或文件路径错误, 文件：{}", filePath);
			System.out.println("找不到文件或文件路径错误, 文件：{}"+filePath);
		}finally {
			try {
				if(fileStream != null){
					fileStream.close();
				}
			} catch (IOException e) {
//            log.info("excel文件读取失败, 失败原因：{}", e);
				System.out.println("excel文件读取失败, 失败原因："+e);
			}
		}
		return null;
	}

	/**
	 * 读大于1000行数据
	 * @param filePath 文件觉得路径
	 * @return
	 */
	public static List<Object> readMoreThan1000Row(String filePath){
		return readMoreThan1000RowBySheet(filePath,null);
	}

	/**
	 * 读大于1000行数据, 带样式
	 * @param filePath 文件觉得路径
	 * @return
	 */
	public static List<Object> readMoreThan1000RowBySheet(String filePath, Sheet sheet){
		if(!StringUtils.hasText(filePath)){
			return null;
		}

		sheet = sheet != null ? sheet : initSheet;

		InputStream fileStream = null;
		try {
			fileStream = new FileInputStream(filePath);
			ExcelListener excelListener = new ExcelListener();
			EasyExcelFactory.readBySax(fileStream, sheet, excelListener);
			return excelListener.getDatas();
		} catch (FileNotFoundException e) {
//         log.error("找不到文件或文件路径错误, 文件：{}", filePath);
			System.out.println("找不到文件或文件路径错误, 文件：："+filePath);
		}finally {
			try {
				if(fileStream != null){
					fileStream.close();
				}
			} catch (IOException e) {
//            log.error("excel文件读取失败, 失败原因：{}", e);
				System.out.println("excel文件读取失败, 失败原因："+e);
			}
		}
		return null;
	}

	/**
	 * 生成excle
	 * @param filePath  绝对路径, 如：/home/chenmingjian/Downloads/aaa.xlsx
	 * @param data 数据源
	 * @param head 表头
	 */
	public static void writeBySimple(String filePath, List<List<Object>> data, List<String> head){
		writeSimpleBySheet(filePath,data,head,null);
	}

	public static void writeBySimple(HttpServletResponse response, List<List<Object>> data, List<String> head){
		writeSimpleBySheet(response,data,head,null);
	}

	/**
	 * 生成excle
	 * @param filePath 绝对路径, 如：/home/chenmingjian/Downloads/aaa.xlsx
	 * @param data 数据源
	 * @param sheet excle页面样式
	 * @param head 表头
	 */
	public static void writeSimpleBySheet(String filePath, List<List<Object>> data, List<String> head, Sheet sheet){
		sheet = (sheet != null) ? sheet : initSheet;

		if(head != null){
			List<List<String>> list = new ArrayList<>();
			head.forEach(h -> list.add(Collections.singletonList(h)));
			sheet.setHead(list);

		}

		OutputStream outputStream = null;
		ExcelWriter writer = null;
		try {
			outputStream = new FileOutputStream(filePath);
			writer = EasyExcelFactory.getWriter(outputStream);
			writer.write1(data,sheet);
		} catch (FileNotFoundException e) {
			System.out.println("找不到文件或文件路径错误, 文件：："+filePath);

		}finally {
			try {
				if(writer != null){
					writer.finish();
				}

				if(outputStream != null){
					outputStream.close();
				}

			} catch (IOException e) {
				System.out.println("excel文件读取失败, 失败原因："+e);
			}
		}

	}

	public static void writeSimpleBySheet(HttpServletResponse response, List<List<Object>> data, List<String> head, Sheet sheet){
		sheet = (sheet != null) ? sheet : initSheet;

		if(head != null){
			List<List<String>> list = new ArrayList<>();
			head.forEach(h -> list.add(Collections.singletonList(h)));
			sheet.setHead(list);
		}

		OutputStream outputStream = null;
		ExcelWriter writer = null;
		try {
			outputStream = response.getOutputStream();
			writer = EasyExcelFactory.getWriter(outputStream);
			writer.write1(data,sheet);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(writer != null){
					writer.finish();
				}

				if(outputStream != null){
					outputStream.close();
				}

			} catch (IOException e) {
				System.out.println("excel文件读取失败, 失败原因："+e);
			}
		}

	}

	/**
	 * 生成excle
	 * @param filePath 绝对路径, 如：/home/chenmingjian/Downloads/aaa.xlsx
	 * @param data 数据源
	 */
	public static String writeWithTemplate(String filePath, List<? extends BaseRowModel> data){
		return writeWithTemplateAndSheet(filePath,data,null);
	}

	public static void writeWithTemplate(HttpServletResponse response, List<? extends BaseRowModel> data) {
		writeWithTemplateAndSheet(response,data,null);
	}

	public static String writeWithNoTemplate(List<? extends BaseRowModel> list, Sheet sheet,
											 HttpServletResponse response, String fileName){
		if(null == sheet){
			sheet = initSheet;
		}
		ExcelWriter writer = null;
		ServletOutputStream stream = null;
		try {
			stream = response.getOutputStream();
			writer = new ExcelWriter(stream, ExcelTypeEnum.XLSX, true);
			writer.write(list, sheet);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "ISO8859-1"));
			stream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.finish();
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "导出完成";

	}

	/**
	 * 生成excle
	 * @param filePath 绝对路径, 如：/home/chenmingjian/Downloads/aaa.xlsx
	 * @param data 数据源
	 * @param sheet excle页面样式
	 */
	public static String writeWithTemplateAndSheet(String filePath, List<? extends BaseRowModel> data, Sheet sheet){
		if(CollectionUtils.isEmpty(data)){
			return "导出数据为空";
		}

		sheet = (sheet != null) ? sheet : initSheet;
		sheet.setClazz(data.get(0).getClass());

		OutputStream outputStream = null;
		ExcelWriter writer = null;
		try {
			outputStream = new FileOutputStream(filePath);
			writer = EasyExcelFactory.getWriter(outputStream);
			writer.write(data,sheet);
			return "导出完成";
		} catch (FileNotFoundException e) {
			return "找不到文件或文件路径错误, 文件："+filePath;
		}finally {
			try {
				if(writer != null){
					writer.finish();
				}

				if(outputStream != null){
					outputStream.close();
				}
			} catch (IOException e) {
				return "excel文件读取失败, 失败原因："+e;
			}
		}
	}
	public static void writeWithTemplateAndSheet(HttpServletResponse response, List<? extends BaseRowModel> data, Sheet sheet){
		if(CollectionUtils.isEmpty(data)){
			return;
		}
		sheet = (sheet != null) ? sheet : initSheet;
		sheet.setClazz(data.get(0).getClass());
		OutputStream outputStream = null;
		ExcelWriter writer = null;
		try {
			/*设置样式*/
			HorizontalCellStyleStrategy horizontalCellStyleStrategy = getHorizontalCellStyleStrategy();
			outputStream = response.getOutputStream();
			writer = write().file(outputStream).autoCloseStream(Boolean.FALSE).convertAllFiled(Boolean.FALSE).registerWriteHandler(horizontalCellStyleStrategy).build();
			//	EasyExcelFactory.getWriter(outputStream);
			writer.write(data,sheet);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(writer != null){
					writer.finish();
				}

				if(outputStream != null){
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成多Sheet的excle
	 * @param filePath 绝对路径, 如：/home/chenmingjian/Downloads/aaa.xlsx
	 * @param multipleSheelPropetys
	 */
	public static void writeWithMultipleSheel(String filePath,List<MultipleSheelPropety> multipleSheelPropetys){
		if(CollectionUtils.isEmpty(multipleSheelPropetys)){
			return;
		}

		OutputStream outputStream = null;
		ExcelWriter writer = null;
		try {
			outputStream = new FileOutputStream(filePath);
			writer = EasyExcelFactory.getWriter(outputStream);
			for (MultipleSheelPropety multipleSheelPropety : multipleSheelPropetys) {
				Sheet sheet = multipleSheelPropety.getSheet() != null ? multipleSheelPropety.getSheet() : initSheet;
				if(!CollectionUtils.isEmpty(multipleSheelPropety.getData())){
					sheet.setClazz(multipleSheelPropety.getData().get(0).getClass());
				}
				writer.write(multipleSheelPropety.getData(), sheet);
			}

		} catch (FileNotFoundException e) {
			System.out.println("找不到文件或文件路径错误, 文件：："+filePath);
		}finally {
			try {
				if(writer != null){
					writer.finish();
				}

				if(outputStream != null){
					outputStream.close();
				}
			} catch (IOException e) {
				System.out.println("excel文件读取失败, 失败原因："+e);
			}
		}
	}

	public static Map<Integer, Integer> setColunmWidth(Integer[] widthArr){
		Map<Integer, Integer> columnWidthMap = new HashMap();
		for (int i = 0; i < widthArr.length; i++) {
			columnWidthMap.put(i, widthArr[i]);
		}
		return columnWidthMap;
	}

	private static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy() {
		WriteCellStyle headWriteCellStyle = new WriteCellStyle();
		WriteCellStyle cellStyle = new WriteCellStyle();
		cellStyle.setWrapped(true);
		/*垂直居中*/
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		/*水平居中*/
		cellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		return new HorizontalCellStyleStrategy(headWriteCellStyle, cellStyle);
	}

	/**
	 * 读小于1000行数据, 带样式
	 * filePath 文件绝对路径
	 * initSheet ：
	 *      sheetNo: sheet页码，默认为1
	 *      headLineMun: 从第几行开始读取数据，默认为0, 表示从第一行开始读取
	 *      clazz: 返回数据List<Object> 中Object的类名
	 */
	public static List<Object> readLessThan1000RowBySheet(byte[] buf, Sheet sheet){
		if(buf == null){
			return null;
		}

		sheet = sheet != null ? sheet : initSheet;

		InputStream fileStream = null;
		try {
			// fileStream = new FileInputStream(filePath);
			fileStream = new ByteArrayInputStream(buf);;
			return EasyExcelFactory.read(fileStream, sheet);
		} catch (Exception e) {
			e.printStackTrace();
//            log.info("找不到文件或文件路径错误, 文件：{}", filePath);
		}finally {
			try {
				if(fileStream != null){
					fileStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
//                log.info("excel文件读取失败, 失败原因：{}", e);
			}
		}
		return null;
	}

	/**
	 * 格式化Excel时间
	 * @param day
	 * @return yyyy-MM-dd
	 */
	public static String formatExcelDate(int day) {
		Calendar calendar1 = new GregorianCalendar(1900,0,-1);
		Date gregorianDate = calendar1.getTime();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(gregorianDate);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
		Date today = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = sdf.format(today);
		return result;

	}
}

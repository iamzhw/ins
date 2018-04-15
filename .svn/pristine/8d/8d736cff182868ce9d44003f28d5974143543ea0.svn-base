package com.axxreport.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil03 {

	private HSSFWorkbook workBook;

	public ExcelUtil03(InputStream input) throws IOException {
		POIFSFileSystem inputs = new POIFSFileSystem(input);
		workBook = new HSSFWorkbook(inputs);
	}

	public List<List<String>> getDatas(int num) {
		HSSFSheet sheet = workBook.getSheetAt(0);
		int size = sheet.getLastRowNum();
		if (size == 0) {
			return null;
		}
		List<List<String>> datas = new ArrayList<List<String>>();
		List<String> data = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		for (int i = 1; i <= size; i++) {
			row = sheet.getRow(i);
			data = new ArrayList<String>();
			for (int j = 0; j < num; j++) {
				cell = row.getCell(j);
				try {
					if (cell != null) {
						data.add(cell.getStringCellValue());
						// cell.getDateCellValue()
					} else {
						data.add("");
					}
				} catch (Exception e) {
					e.printStackTrace();
					data.add(new DecimalFormat("#").format(cell
							.getNumericCellValue()));
				}
			}
			datas.add(data);
		}
		return datas;
	}

	/**
	 * 指定sheet页，开始行数，结束列数
	 * 
	 * @param sheetNum
	 * @param rowNum
	 * @param num
	 * @return
	 */
	public List<List<String>> getDatas(int sheetNum, int rowNum, int num) {
		HSSFSheet sheet = workBook.getSheetAt(sheetNum);
		int size = sheet.getLastRowNum();
		if (size == 0) {
			return null;
		}
		List<List<String>> datas = new ArrayList<List<String>>();
		List<String> data = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = rowNum; i <= size; i++) {
			row = sheet.getRow(i);
			data = new ArrayList<String>();
			for (int j = 0; j < num; j++) {
				cell = row.getCell(j);
				try {
					if (cell != null) {
						data.add(cell.getStringCellValue());
					} else {
						data.add("");
					}
				} catch (Exception e) {
					if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)
						try {
							data.add(String.valueOf(cell.getStringCellValue()));
							continue;
						} catch (IllegalStateException e1) {
							data.add(String.valueOf(cell.getNumericCellValue()));
							continue;
						}
					if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
						data.add(String.valueOf(cell.getBooleanCellValue()));
						continue;
					}
					try {
						if (DateUtil.isCellDateFormatted(cell)) {
							Date theDate = cell.getDateCellValue();
							data.add(simpleDateFormat.format(theDate));
						} else {
							short format = cell.getCellStyle().getDataFormat();
							SimpleDateFormat sdf = null;
							if (format == 14 || format == 31 || format == 57
									|| format == 58) {
								// 日期
								sdf = new SimpleDateFormat("yyyy-MM-dd");
								Date date = DateUtil.getJavaDate(cell
										.getNumericCellValue());
								data.add(sdf.format(date));
								continue;
							}

							data.add(NumberToTextConverter.toText(cell
									.getNumericCellValue()));
						}
					} catch (Exception e2) {
						data.add("");
					}
				}
			}
			datas.add(data);
		}
		return datas;
	}

	/**
	 * 根据contentType下载文件。
	 * 
	 * @param response
	 *            响应
	 * @param File
	 *            要下载的文件源
	 * @param contentType
	 * @throws Exception
	 */
	public static void downFile(HttpServletResponse response, File file,
			String contentType) throws Exception {
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		java.io.FileInputStream fileInputStream = null;
		String destFileName = file.getPath();
		String shortFileName = FilenameUtils.getName(destFileName);// .getShortFileName(destFileName);
		try {
			// shortFileName =
			// java.net.URLEncoder.encode(shortFileName,"UTF-8");
			response.setContentType(contentType);
			response.setHeader("Content-disposition", "attachment;filename="
					+ new String((shortFileName).getBytes("gb2312"),
							"iso8859-1"));
			java.io.File filein = new java.io.File(destFileName);
			fileInputStream = new java.io.FileInputStream(
					filein);
			bis = new java.io.BufferedInputStream(fileInputStream);
			bos = new java.io.BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(fileInputStream != null){
				fileInputStream.close();
			}
			if (bis != null){
				bis.close();
			}
			if (bos != null){
				bos.close();
			}
		}
	}

	public static void downFile(HttpServletResponse response, File file,
			String fileName, String contentType) throws Exception {
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		java.io.FileInputStream fileInputStream = null;
		try {
			response.setContentType(contentType);
			response.setHeader("Content-disposition", "attachment;filename="
					+ new String((fileName).getBytes("gb2312"), "iso8859-1"));
			fileInputStream = new java.io.FileInputStream(
					file);
			bis = new java.io.BufferedInputStream(fileInputStream);
			bos = new java.io.BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(fileInputStream != null){
				fileInputStream.close();
			}
			if (bis != null){
				bis.close();
			}
			if (bos != null){
				bos.close();
			}
		}
	}

	/**
	 * 设置Cell的值
	 * 
	 * @param st
	 * @param rowIndex
	 *            行坐标
	 * @param columnIndex
	 *            列坐标
	 * @param value
	 *            值
	 */
	public static void setValue(HSSFSheet st, int rowIndex, int columnIndex,
			String value) {
		if (value == null || value.equals("null") || value.equals("NULL")) {
			value = "";
		}
		HSSFRow row = st.getRow(rowIndex);
		if (row == null) {
			row = st.createRow(rowIndex);
		}
		HSSFCell cell = row.getCell(columnIndex);
		if (cell == null) {
			cell = row.createCell(columnIndex);
		}
		// 注意：一定要设成这个，否则可能会出现乱码
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(value);
	}

	public String getSheetName(int sheetNum) {
		return workBook.getSheetAt(sheetNum).getSheetName();
	}
	
	
	 /** 

     * 导入Excel 

     * @param execelFile 

     */  

    public void impExcel(String execelFile){  

        try {  

            // 构造 Workbook 对象，execelFile 是传入文件路径(获得Excel工作区)  

            Workbook book = null;  

            try {  

                // Excel 2007获取方法  

                book = new XSSFWorkbook(new FileInputStream(execelFile));  

            } catch (Exception ex) {  

                // Excel 2003获取方法  

                book = new HSSFWorkbook(new FileInputStream(execelFile));  

            }  

              

            // 读取表格的第一个sheet页  

            Sheet sheet = book.getSheetAt(0);  

            // 定义 row、cell  

            Row row;  

            String cell;  

            // 总共有多少行,从0开始  

            int totalRows = sheet.getLastRowNum() ;  

            // 循环输出表格中的内容,首先循环取出行,再根据行循环取出列  

            for (int i = 1; i <= totalRows; i++) {  

                row = sheet.getRow(i);  

                // 处理空行  

                if(row == null){  

                    continue ;  

                }  

                // 总共有多少列,从0开始  

                int totalCells = row.getLastCellNum() ;  

                for (int j = row.getFirstCellNum(); j < totalCells; j++) {  

                    // 处理空列  

                    if(row.getCell(j) == null){  

                        continue ;  

                    }  

                    // 通过 row.getCell(j).toString() 获取单元格内容  

                    cell = row.getCell(j).toString();  

                    System.out.print(cell + "\t");  

                }  

                System.out.println("");  

            }  

        } catch (FileNotFoundException e) {  

            e.printStackTrace();  

        } catch (IOException e) {  

            e.printStackTrace();  

        }  

    }  

      

    public void expExcel(String expFilePath){  

        OutputStream os = null ;  

        Workbook book = null;  

        try {  

            // 输出流  

            os = new FileOutputStream(expFilePath);  

            // 创建工作区(97-2003)  

            book = new HSSFWorkbook();  

            // 创建第一个sheet页  

            Sheet sheet= book.createSheet("test");  

            // 生成第一行  

            Row row = sheet.createRow(0);  

            // 给第一行的第一列赋值  

            row.createCell(0).setCellValue("column1");  

            // 给第一行的第二列赋值  

            row.createCell(1).setCellValue("column2");  

            // 写文件  

            book.write(os);  

              

        } catch (FileNotFoundException e) {  

            e.printStackTrace();  

        } catch (IOException e) {  

            e.printStackTrace();  

        } finally {  

            // 关闭输出流  

            try {  
            	if(os != null){
            		os.close();  
            	}

            } catch (IOException e) {  

                e.printStackTrace();  

            }  

        }  

    }  

}  



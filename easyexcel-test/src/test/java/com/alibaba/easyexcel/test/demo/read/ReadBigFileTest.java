package com.alibaba.easyexcel.test.demo.read;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: yichengdong
 * @CreateDate: 2024/11/19 18:21
 * https://github.com/alibaba/easyexcel/issues/4041
 */
public class ReadBigFileTest {

	public static void main(String[] args) {
		String fileName = "D:/workspace/idea/easyexcel/easyexcel-test/target/test-classes/bigFile.xlsx";
		File file = new File(fileName);
		firstMap(file);
	}

	public static Map<String, String> firstMap(File file) {
		OPCPackage pkg = null;
		InputStream sheet = null;
		final Map<String, String> titleMap = new HashMap<>();
		try {
			pkg = OPCPackage.open(file, PackageAccess.READ);
			XSSFReader reader = new XSSFReader(pkg);
			sheet = reader.getSheet("rId1");
			XMLReader parser = XMLReaderFactory.createXMLReader();
			ReadOnlySharedStringsTable rosst = new ReadOnlySharedStringsTable(pkg);
			parser.setContentHandler(new XSSFSheetXMLHandler(reader.getStylesTable(),rosst,new XSSFSheetXMLHandler.SheetContentsHandler() {

				@Override
				public void cell(String index, String value, XSSFComment comment) {
					titleMap.put(index, value);
				}

				@Override
				public void startRow(int arg0) {
				}

				@Override
				public void endRow(int arg0) {
					throw new ArithmeticException();
				}

			},
					false));
			try {
				parser.parse(new InputSource(sheet));
			} catch(ArithmeticException e) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sheet.close();
			} catch (IOException e) {

			}
			try {
				pkg.close();
			} catch (IOException e) {

			}
			return titleMap;
		}
	}
}

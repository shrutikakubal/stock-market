package com.example.excel.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.excel.feign.CompanyClient;
import com.example.excel.model.StockDtls;


@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadExcelController {

	@Autowired
	CompanyClient companyClient;

	@RequestMapping(value = "/admin/uploadexcel", method = RequestMethod.POST)
	public ResponseEntity<?> uploadExcel(@RequestParam("excelsheet") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		StringBuilder buffer = new StringBuilder("upload Excel start\n");
		Workbook workbook = null;
		List<StockDtls> stockDtlsList = new ArrayList<>();
		try {
			String fileName = file.getOriginalFilename();
			InputStream inputStream = file.getInputStream();

			if (fileName.matches("^.+\\.(?i)(xls)$")) { // EXCEL2003
				workbook = new HSSFWorkbook(inputStream);
			}
			if (fileName.matches("^.+\\.(?i)(xlsx)$")) { // EXCEL2007
				workbook = new XSSFWorkbook(inputStream);
			}

			if (workbook != null) {
				Sheet sheet = workbook.getSheetAt(0);
				int allRowNum = sheet.getLastRowNum();
				if (allRowNum == 0) {
					buffer.append("excel sheet is blank\n");
				}

				for (int i = 1; i <= allRowNum; i++) {
					StockDtls stockDtls = new StockDtls();
					Row row = sheet.getRow(i);
					if (row != null) {
						Cell cell1 = row.getCell(0); // Company Code
						Cell cell2 = row.getCell(1); // stockExchange
						Cell cell3 = row.getCell(2); // BigDecimal currentPrice
						Cell cell4 = row.getCell(3); // LocalDateTime stockDate
						Cell cell5 = row.getCell(4); // LocalDateTime stockTime

						if (cell1 == null || cell2 == null || cell3 == null || cell4 == null || cell5 == null) {
							buffer.append("Row " + i + " has null data\n");
							break;
						}
						stockDtls.setCompanyCode(cell1.getStringCellValue().trim().replaceAll("\\u00A0", ""));
						stockDtls.setStockExchangeCode(cell2.getStringCellValue().trim());
						stockDtls.setCurrentPrice(new BigDecimal(cell3.getNumericCellValue()));

						String cell4Str = cell4.getDateCellValue().toInstant().atZone(ZoneId.systemDefault())
								.toLocalDate().toString();
						String cell5Str = cell5.getStringCellValue();
						String cell45Str = cell4Str.trim() + " " + cell5Str.trim();
						LocalDateTime stockDateTime = LocalDateTime.parse((CharSequence) cell45Str,
								DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
						stockDtls.setStockDateTime(stockDateTime);
						Map<String, String> params = new HashMap<>();
						params.put("companyCode", stockDtls.getCompanyCode());
						params.put("stockExchange", stockDtls.getStockExchangeCode());
						params.put("stockDateTime", cell45Str);
						StockDtls stockDtlsNew = companyClient.checkStock(params);

						if (stockDtlsNew == null) {
							stockDtlsList.add(stockDtls);
						}
					}
				}
				if (stockDtlsList.size() > 0) {
					companyClient.createManyStocks(stockDtlsList);
				}
				
			if (stockDtlsList.size() > 0) {
				List<Map<String, String>> consolidatedList = new ArrayList<Map<String, String>>();
				Map<String, List<StockDtls>> groupMap = stockDtlsList.stream()
						.collect(Collectors.groupingBy(StockDtls::getCompanyCode));
				for (Map.Entry<String, List<StockDtls>> entry : groupMap.entrySet()) {
					entry.getValue().sort(((o1, o2) -> o1.getStockDateTime().compareTo(o2.getStockDateTime())));
					Map<String, String> consolidated = new HashMap<>();
					consolidated.put("companyCode", entry.getKey());
					consolidated.put("stockExchangeCode", entry.getValue().get(0).getStockExchangeCode());
					consolidated.put("noOfRecords", Integer.toString(entry.getValue().size()));
					consolidated.put("fromDateTime", entry.getValue().get(0).getStockDateTime().toString());
					consolidated.put("toDateTime",
							entry.getValue().get(entry.getValue().size() - 1).getStockDateTime().toString());
					consolidatedList.add(consolidated);
				}

				return ResponseEntity.status(HttpStatus.CREATED).body(consolidatedList);
			}
			} else {
				buffer.append("File Format is Invalid\n");
				
			}
		} catch (Exception e) {
			
			buffer.append(e.getMessage());
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		buffer.append(" upload Excel done.");
		return ResponseEntity.ok().body(buffer);
	}
}

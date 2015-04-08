package ch.makery.address.model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser {
	final DataFormatter df = new DataFormatter();
	final SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
	final SimpleDateFormat format2 = new SimpleDateFormat("hh:mm");
	int rowsQ;
	int rowsCur=0;
	File file1;
	CreationHelper createHelper;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	CellStyle hlink_style;
	FileInputStream file;
	Iterator<Row> rowIterator;
	Cell cell;
	Row row;
	private ObservableList<Person> newList = FXCollections.observableArrayList();
	public ExcelParser(File file2) throws IOException{
		file1=file2;
		file = new FileInputStream(file2);
		workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(0);
        hlink_style = workbook.createCellStyle();
        Font hlink_font = workbook.createFont();
        hlink_font.setUnderline(Font.U_SINGLE);
        hlink_font.setColor(IndexedColors.BLUE.getIndex());
        hlink_style.setFont(hlink_font);
        createHelper = workbook.getCreationHelper();
        rowIterator = sheet.iterator();
        rowsQ=sheet.getPhysicalNumberOfRows();
        System.out.println("Total rows:" + rowsQ);
        System.out.println("Total columns:" + sheet.getRow(0).getPhysicalNumberOfCells());
	}
	

	public void moveOneRow(){
        row = rowIterator.next();
	}
	
	public Person readRow(Row row) throws UnsupportedEncodingException, IOException{
		String initTel=null;
		String theIP=null;
		String region=null;
		String promoCode=null;
		String whereFrom=null;
		String partnersMail=null;
		LocalDate queryDate=null;
		LocalTime queryTime=null;
		
        cell= row.getCell(0);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_NUMERIC){

        	initTel = new DecimalFormat("#.#######################").format(cell.getNumericCellValue());
        }
        cell = row.getCell(1);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_NUMERIC)queryDate = cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        cell = row.getCell(2);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
        	Instant instant = Instant.ofEpochMilli(cell.getDateCellValue().getTime());
        	queryTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
        }       
        cell= row.getCell(4);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)promoCode = cell.getStringCellValue();
        
        cell= row.getCell(9);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)region = cell.getStringCellValue();
        cell= row.getCell(3);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)theIP = cell.getStringCellValue();
        cell= row.getCell(6);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)whereFrom = cell.getStringCellValue();;
        System.out.println(initTel);
        Person person = new Person(initTel, theIP, region, promoCode, whereFrom, queryTime, queryDate, partnersMail);
		return person;
      
	}
	
	public ObservableList<Person> readNewBase(boolean isNew) {
        try
        {
        	while (rowIterator.hasNext()){
        	moveOneRow();
        	rowsCur++;
        	if(isNew)cell = row.getCell(0, Row.RETURN_BLANK_AS_NULL);
        	else cell = row.getCell(14, Row.RETURN_BLANK_AS_NULL);
            if (cell!=null) newList.add(readRow(row));
                
                        	}
        	return newList;
        }	
        catch (Exception e){e.printStackTrace();return null;}
        
    }
        public void saveFile() throws IOException{
            file.close();
            String name = file1.getName().substring(0,file1.getName().lastIndexOf(".xlsx"))+"+регионы.xlsx";
            
            String absolutePath = file1.getAbsolutePath();
    	    String filePath = absolutePath.
    	    	     substring(0,absolutePath.lastIndexOf(File.separator));
    	    
    	    System.out.println(filePath+"\\"+name);
            FileOutputStream fileOut = new FileOutputStream(filePath+"\\"+name);
            workbook.write(fileOut);
            fileOut.close();
    	}
        public static void main(String[] args) throws IOException {
        	ExcelParser job = new ExcelParser(new File("C:\\Users\\ƒмитрий\\git\\JavaFXCRM\\JavaFXCRM\\src\\база демо 26-28.12.14 - ƒима.xlsx"));
        	Person person=job.readNewBase(true).get(14);
        	System.out.println(person.getinitTel()+"   ||   "+person.getpromoCode());
        }
}

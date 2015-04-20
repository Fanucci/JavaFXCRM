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
import java.time.ZoneId;
import java.time.ZoneOffset;
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

import ch.makery.address.util.CSV;
import ch.makery.address.util.Utils;

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
	
	public Person readRow(Row row, boolean isNew) throws UnsupportedEncodingException, IOException{
		String initTel=null;
		String theIP=null;

		String promoCode=null;
		String whereFrom=null;
		String name1=null;
		String eMail=null;
		String comments=null;
		String status=null;
		LocalDate queryDate=null;
		String queryTime=null;
		LocalDateTime nextCall=null;
		String region=null;
		
		String diffTime=null;
        cell= row.getCell(0);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_NUMERIC){

        	initTel = new DecimalFormat("#.#######################").format(cell.getNumericCellValue());
        }
        cell = row.getCell(1);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_NUMERIC)queryDate = cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       
        cell = row.getCell(14);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
        	nextCall = LocalDateTime.ofInstant(Instant.ofEpochMilli(cell.getDateCellValue().getTime()), ZoneOffset.UTC);
        }    
        
        cell = row.getCell(7);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING){
        	queryTime = cell.getStringCellValue().substring(11,19);
        } 
        else{
        	cell = row.getCell(2);
        	if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
        	queryTime="[["+format2.format(cell.getDateCellValue())+"]]";
        	}}       	
        cell= row.getCell(4);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)promoCode = cell.getStringCellValue();
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_NUMERIC)promoCode = String.valueOf((int)cell.getNumericCellValue());
        cell= row.getCell(6);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)whereFrom = cell.getStringCellValue();
        
        cell= row.getCell(3);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)theIP = cell.getStringCellValue();
        cell= row.getCell(5);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)comments = cell.getStringCellValue();
        cell= row.getCell(12);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)comments = cell.getStringCellValue();
        cell= row.getCell(13);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)eMail = cell.getStringCellValue();
        cell= row.getCell(11);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)status = cell.getStringCellValue();
        CSV CSV=new CSV();
        //Region
        if(isNew)region= CSV.checkNumber(initTel);
        else{cell= row.getCell(9);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)region = cell.getStringCellValue();
        }
        //Time Difference
        if(isNew)diffTime=Utils.timeZone(region);
        else{cell= row.getCell(10);
        if(cell!=null&&cell.getCellType()==Cell.CELL_TYPE_STRING)diffTime = cell.getStringCellValue();
        }
        
        Person person = new Person(initTel, theIP, promoCode, whereFrom, queryTime,
        		queryDate, name1 ,comments, region, diffTime, nextCall, eMail, status);
		return person;
      
	}
	
    private void writeRow(Row row,Person p, boolean isNew) {
    	row.createCell(12).setCellValue(p.getcomments());
    	row.createCell(13).setCellValue(p.geteMail());
    	row.createCell(11).setCellValue(p.getstatus());
    	row.createCell(9).setCellValue(p.getregion());
    	row.createCell(10).setCellValue(p.getdiffTime());
	
}
	public ObservableList<Person> readNewBase(boolean isNew) throws UnsupportedEncodingException, IOException {

        	while (rowIterator.hasNext()){
        	moveOneRow();
        	rowsCur++;
        	cell = row.getCell(0, Row.RETURN_BLANK_AS_NULL);
            if (cell!=null) newList.add(readRow(row,isNew));
            }
        	return newList;
        	

    }
	
	public void writeNewBase(ObservableList<Person>LP, boolean isNew) throws IOException {
    	while (rowIterator.hasNext()){
    	moveOneRow();
    	Person PLine=LP.get(rowsCur);
    	rowsCur++;
    	cell = row.getCell(0, Row.RETURN_BLANK_AS_NULL);
        if (cell!=null) writeRow(row,PLine,isNew);
        }
    	saveFile();
	}



		public void saveFile() throws IOException{
            file.close();
            String name = file1.getName().substring(0,file1.getName().lastIndexOf(".xlsx"))+"+изменения.xlsx";
            
            String absolutePath = file1.getAbsolutePath();
    	    String filePath = absolutePath.
    	    	     substring(0,absolutePath.lastIndexOf(File.separator));
    	    
    	    System.out.println(filePath+"\\"+name);
            FileOutputStream fileOut = new FileOutputStream(filePath+"\\"+name);
            workbook.write(fileOut);
            fileOut.close();
    	}
        public static void main(String[] args) throws IOException {
        	ExcelParser job = new ExcelParser(new File("C:\\Users\\Дмитрий\\git\\JavaFXCRM\\JavaFXCRM\\src\\база демо 26-28.12.14 - Дима.xlsx"));
        	Person person=job.readNewBase(true).get(14);
        	System.out.println(person.getinitTel()+"   ||   "+person.getpromoCode());
        }



}

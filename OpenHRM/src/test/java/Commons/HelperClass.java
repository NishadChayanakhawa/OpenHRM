package Commons;
import org.openqa.selenium.WebDriver;
import java.util.List;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import java.util.Arrays;
public class HelperClass {
	final static String strDriverFullPath=
			"D:\\My_Projects\\selenium-java-3.141.59\\" +
					"ChromeDriver\\chromedriver.exe";
	public static String strTestDataFullPath="D:\\My_Projects\\TestData.xlsx";
	public static WebDriver getDriver() {
		System.setProperty("webdriver.chrome.driver", strDriverFullPath);
		return new ChromeDriver();
	}
	
	public static Object[][] getTestData(String strTestDataFullPath,String strSheetName,String[] IncludedGroups) {
		Object[][] objTestData=new Object[0][0];
		File objTestDataFile=null;
		FileInputStream objTestDataFileInputStream=null;
		Workbook objTestDataWorkbook=null;
		Sheet objTestDataWorksheet=null;
		try {
			objTestDataFile=new File(strTestDataFullPath);
			objTestDataFileInputStream=new FileInputStream(objTestDataFile);
			objTestDataWorkbook=new XSSFWorkbook(objTestDataFileInputStream);
			objTestDataWorkbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			objTestDataWorksheet=objTestDataWorkbook.getSheet(strSheetName);
			objTestData=getTestDataFromSheet(objTestDataWorksheet,IncludedGroups);
			objTestDataWorkbook.close();
			objTestDataFileInputStream.close();
		}
		catch(IOException e) {
			return null;
		}
		return objTestData;
	}
	
	private static Object[][] getTestDataFromSheet(Sheet objTestDataSheet,String[] IncludedGroups) {
		Object[][] objTestData=new Object[0][0];
		Object[] objTestDataRow=null;
		List<String> IncludedGroupsList=Arrays.asList(IncludedGroups);
		for (int iRow=1;iRow<=objTestDataSheet.getLastRowNum();iRow++) {
			Row objRow=objTestDataSheet.getRow(iRow);
			if (!isGroupIncluded(objRow,IncludedGroupsList)) {
				continue;
			}
			objTestDataRow=new Object[0];
			for (int iCell=1;iCell<objRow.getLastCellNum();iCell++) {
				Cell objCell=objRow.getCell(iCell);
				String strCellValue="";
				if (!(objCell==null || objCell.getCellType()==CellType.BLANK)) {
					strCellValue=objCell.getStringCellValue();
				}
				objTestDataRow=appendArray(objTestDataRow,strCellValue);
			}
			objTestData=appendArray(objTestData,objTestDataRow);
		}
		return objTestData;
	}
	
	private static Boolean isGroupIncluded(Row objRow,List<String> IncludedGroups) {
		Cell objGroupCell=objRow.getCell(0);
		if (IncludedGroups.contains(objGroupCell.getStringCellValue())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static Object[] appendArray(Object[] originalArray,Object itemToAppend) {
		Object[] appendedArray=Arrays.copyOf(originalArray, originalArray.length+1);
		appendedArray[appendedArray.length-1]=itemToAppend;
		return appendedArray;
	}
	
	private static Object[][] appendArray(Object[][] originalArray,Object[] itemToAppend) {
		Object[][] appendedArray=Arrays.copyOf(originalArray, originalArray.length+1);
		appendedArray[appendedArray.length-1]=itemToAppend;
		return appendedArray;
	}
}

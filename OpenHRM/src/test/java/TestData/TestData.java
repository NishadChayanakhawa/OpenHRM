package TestData;
import org.testng.annotations.DataProvider;
import java.lang.reflect.Method;
import org.testng.ITestContext;
import Commons.HelperClass;
public class TestData {
	final static String strLogPrefix="	";
	
	@DataProvider(name="TestDataRepository")
	public static Object[][] getTestData(ITestContext objTestContext,Method objMethod) {
		//System.out.println(strLogPrefix + "* Executing TestData.TestData.getTestData");
		//System.out.println(strLogPrefix + "  -Method Name    : ["+ objMethod.getName() + "]");
		//System.out.print(strLogPrefix + "  -Included Groups: ");
		//for (String strGroup: objTestContext.getIncludedGroups()) {
			//System.out.print("["+strGroup+"]");
		//}
		//System.out.println();
		//System.out.print(strLogPrefix + "  -Getting Test Data");
		Object objTestData[][]=HelperClass.getTestData
				(HelperClass.strTestDataFullPath,
						objMethod.getName(),objTestContext.getIncludedGroups());
		//System.out.println(".........Completed");
		//System.out.println(strLogPrefix + "  -Test Data: ");
		for (Object[] objTestDataRow:objTestData) {
			String strTestDataRow="";
			for (Object objTestDataCell:objTestDataRow) {
				strTestDataRow=strTestDataRow+"["+objTestDataCell+"]";
			}
			//System.out.println(strLogPrefix + "  " + strTestDataRow);
		}
		return objTestData;
	}
}

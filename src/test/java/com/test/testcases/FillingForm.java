package com.test.testcases;

import java.util.Iterator;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.base.PageBase;
import com.test.pages.LandingPage;
import com.test.pages.ProductsHome;
import com.test.util.TestUtil;

public class FillingForm extends PageBase {

	LandingPage landingPage;
	String parentWindow;
	ProductsHome productsHome;

	String sheetName1 = "FirstPage";
	String sheetName2 = "SecondPage";

	public FillingForm() {
		super();
	}

	@BeforeMethod
	public void setUp() {

		setupBrowser();
		landingPage = new LandingPage();
		productsHome = landingPage.navigateToForEnterprise();
	}

	@DataProvider
	public Object[][] getFirstPageData() {

		Object data[][] = TestUtil.getTestData(sheetName1);
		return data;
	}

	@Test(priority = 1, dataProvider = "getFirstPageData", groups = "Regression")
	public void fillingFormUsingValidDetails(String Fname, String Lname, String JFunc, String JTitle, String Email,
			String Instiname, String Institype, String disp, String country, String state) {

		parentWindow = driver.getWindowHandle();
		productsHome.clickonForCampus();
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> itr = handles.iterator();
		while (itr.hasNext()) {

			String childWindow1 = itr.next();
			if (!childWindow1.contentEquals(parentWindow)) {
				driver.switchTo().window(childWindow1);
				productsHome.clickGetStarted();
				try {
					
					Thread.sleep(1000);
					productsHome.execelAllValidDetails(Fname, Lname, JFunc, JTitle, Email, Instiname, Institype, disp,
							country, state);

				} catch (Exception e) {
					e.printStackTrace();
				}
				driver.close();
			}
			driver.switchTo().window(parentWindow);
		}
	}

	@DataProvider
	public Object[][] getSixthPageData() {

		Object data[][] = TestUtil.getTestData(sheetName2);
		return data;
	}

	@Test(priority = 2, dataProvider = "getSixthPageData", groups = "Regression")
	public void fillingWithoutEmail(String Fname, String Lname, String JFunc, String JTitle, String Instiname,
			String Institype, String disp, String country, String state) {

		parentWindow = driver.getWindowHandle();
		productsHome.clickonForCampus();
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> itr = handles.iterator();
		while (itr.hasNext()) {

			String childWindow1 = itr.next();
			if (!childWindow1.contentEquals(parentWindow)) {
				driver.switchTo().window(childWindow1);
				productsHome.clickGetStarted();
				try {
					
					Thread.sleep(1000);
					productsHome.execelInvalidEmail(Fname, Lname, JFunc, JTitle, Instiname, Institype, disp, country,
							state);

					Thread.sleep(2000);
					boolean flag = productsHome.emailErrorDisplayed();
					Assert.assertTrue(flag);
				} catch (Exception e) {
					e.printStackTrace();
				}
				driver.close();
			}
			driver.switchTo().window(parentWindow);
		}
	}

	@AfterMethod
	public void tearDown() {

		driver.quit();
	}
}

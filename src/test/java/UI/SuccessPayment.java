package UI;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SuccessPayment {
	
	public static String browser = "chrome";
	public static WebDriver driver;
	public static void main (String[] args) throws InterruptedException, IOException {
		
		if(browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} 
		//Navigate to URL
		driver.get("https://demo.midtrans.com/");
		
		//Manage window to maximize
		driver.manage().window().maximize();
		
		//Find Element to execute button buy now
		driver.findElement(By.xpath("//*[@id=\"container\"]/div/div/div[1]/div[2]/div/div/a")).click();
		
		//Wait load Element to execute button checkout
		WebDriverWait wait = new WebDriverWait(driver,2);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart-checkout"))).click();
		
		//Switch to frame because the elements after this are in the mid-trans frame
		driver.switchTo().frame("snap-midtrans");
		
		//Wait load element to execute element continue in order details
		WebDriverWait wait2 = new WebDriverWait(driver,2);
		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"application\"]/div[1]/a"))).click();
		
		//Wait load element to execute element credit card
		WebDriverWait wait3 = new WebDriverWait(driver,2);
		wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"payment-list\"]/div[1]/a"))).click();
		
		//Input card number
		driver.findElement(By.name("cardnumber")).sendKeys("4811 1111 1111 1114");
		
		//Input expired date
		driver.findElement(By.xpath("//*[@id=\"application\"]/div[3]/div/div/div/form/div[2]/div[2]/input")).sendKeys("03/24");
		
		//input cvv
		driver.findElement(By.xpath("//*[@id=\"application\"]/div[3]/div/div/div/form/div[2]/div[3]/input")).sendKeys("123");
		
		//Execute button paynow
		driver.findElement(By.className("button-main-content")).click();
		
		//Switch to frame because the elements after this are in the payment OTP frame
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"application\"]/div[3]/div/div/div/iframe")));
		
		//Wait load element to input OTP
		WebDriverWait wait4 = new WebDriverWait(driver,2);
		wait4.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#PaRes"))).sendKeys("112233");
		
		//Execute button OK
		driver.findElement(By.name("ok")).click();
		
		Thread.sleep(5000);
		//Take Screenshot for report the result test
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFile, new File(".//screenshot/SuccessTesting.png"));
		
		//Close test
		driver.close();
	}

}

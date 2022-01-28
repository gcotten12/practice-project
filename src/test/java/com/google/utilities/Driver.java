package com.google.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Driver {

    private Driver() {}
    /*
    Making oue driver instance private so this is not reachable outside the class
    Also we make it static because we are going to be using it in a static method, and
    we want it run before everything else
     */
    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if(driverPool.get() == null) {

            synchronized (Driver.class){
                String browserType = ConfigurationReader.getProperty("browser");

                switch(browserType) {
                    case "remote-chrome":
                        try {
                            URL url = new URL("http://52.201.222.18:4444/wd/hub");
                            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                            desiredCapabilities.setBrowserName("chrome");
                            driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case "chrome":
                        WebDriverManager.chromedriver().setup();
                        driverPool.set(new ChromeDriver());
                        driverPool.get().manage().window().maximize();
                        driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                        break;

                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        driverPool.set(new FirefoxDriver());
                        driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                        break;

                    case "ie":
                        WebDriverManager.iedriver().setup(); // internet explorer
                        driverPool.set(new InternetExplorerDriver());
                        driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                        break;

                }
            }

        }

        return driverPool.get();

    }

    public static void closeDriver() {
        if(driverPool.get()!=null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }

}

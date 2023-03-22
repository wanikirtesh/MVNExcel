package com.sbn.pages;

import com.sbn.entity.MyStep;
import com.sbn.util.MyReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StepDefs {
    public static boolean launchapplication(MyStep step, WebDriver driver){
        driver.get(step.getInputs()[0]);
        return true;
    }

    public static boolean login(MyStep step, WebDriver driver){
        if(step.getInputs()[0].equalsIgnoreCase("admin2")){
            driver.findElement(By.cssSelector("#user1")).sendKeys(step.getInputs()[0]);
        }
        driver.findElement(By.cssSelector("#user")).sendKeys(step.getInputs()[0]);
        driver.findElement(By.cssSelector("#password")).sendKeys(step.getInputs()[1]);
        MyReporter.info("putting some information");
        driver.findElement(By.cssSelector("#login")).click();
        return true;
    }
}

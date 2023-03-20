package com.sbn;

import com.sbn.entity.MyStep;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StepDefs {
    public static boolean launchApplication(MyStep step, WebDriver driver){
        driver.get(step.getInputs()[0]);
        return true;
    }

    public static boolean login(MyStep step, WebDriver driver){
        driver.findElement(By.cssSelector("#user")).sendKeys(step.getInputs()[0]);
        driver.findElement(By.cssSelector("#password")).sendKeys(step.getInputs()[1]);
        driver.findElement(By.cssSelector("#login")).click();
        return true;
    }
}

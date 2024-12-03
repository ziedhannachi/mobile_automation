package com.e2eTests.binitns.base;

import com.binitns.mobile.utils.DriversManager;
import com.binitns.mobile.utils.GlobalParams;
import com.binitns.mobile.utils.TestsUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BaseUtils {

    private static AppiumDriver driver;

    TestsUtils utils = new TestsUtils();
    private int maxScrolls = 30;

    // Initialisation du driver une fois en Factory
    public BaseUtils(){
        this.driver = new DriversManager().getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public void waitForVisibility(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestsUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void waitForVisibility(WebElement e, long time) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void waitForVisibility(By e) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestsUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(e));
    }

    public void wait(int milliseconds) {
        utils.Sleep(milliseconds);
    }

    public void clear(WebElement e) {
        waitForVisibility(e);
        e.clear();
    }

    public void click(WebElement e) {
        waitForVisibility(e);
        e.click();
    }

    public void click(WebElement e, String msg) {
        waitForVisibility(e);
        /*utils.log().info("-> " + msg );
        e.click();*/
        // Get element center
        int centerX = e.getLocation().getX() + (e.getSize().getWidth() / 2);
        int centerY = e.getLocation().getY() + (e.getSize().getHeight() / 2);
        // Perform precise click
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence click = new Sequence(finger, 1);
        click.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY - 20));
        click.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        click.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(click));
    }

    public void click(By e, String msg) {
        waitForVisibility(e);
        utils.log().info("-> " + msg);
        driver.findElement(e).click();
    }

    public void click(String resourceId, int nbrView, int index, String msg) {
        WebElement accessibility;
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                accessibility = andGetWebElementByParentResourceId(resourceId, nbrView, "android.widget.Button", index);
                break;
            case "iOS":
                accessibility = driver.findElement(AppiumBy.id(resourceId));
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
        waitForVisibility(accessibility);
        utils.log().info("-> " + msg);
        accessibility.click();
    }

    public void click(WebElement e, int nbrView, int index, String msg) {
        WebElement accessibility;
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                accessibility = andGetWebElementByParent(e, nbrView, "android.widget.Button", index);
                break;
            case "iOS":
                accessibility = iOSGetWebElementByParent(e, index, ".//XCUIElementTypeButton");
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
        waitForVisibility(accessibility);
        utils.log().info("-> " + msg);
        accessibility.click();
    }

    public void clickPassword(String password, String msg) {
        utils.log().info("-> " + msg);
        for (char digit : password.toCharArray()) {
            clickNumericButton(digit);
        }
    }

    private void clickNumericButton(char digit) {
        String accessibilityId = String.valueOf(digit);
        clickDynamicButton(accessibilityId);
    }

    public void clickDynamicButton(String accessibilityId) {
        utils.log().info("-> accessibility Id " + accessibilityId);
        WebElement button;

        switch(new GlobalParams().getPlatformName()){
            case "Android":
                button = driver.findElement(AppiumBy.xpath("//*[@resource-id=\""+accessibilityId+"\"]"));
                break;
            case "iOS":
                button = driver.findElement(AppiumBy.accessibilityId(accessibilityId));
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
        waitForVisibility(button);
        click(button, "press or click on " + accessibilityId);
    }


    public void scrollAndClick(String accessibilityId) {
        utils.log().info("-> accessibility Id " + accessibilityId);
        WebElement button = scrollToElement(accessibilityId, true);
        waitForVisibility(button);
        click(button, "press or click on " + accessibilityId);
    }

    public void scrollAndClick(String accessibilityId, String msg) {
        utils.log().info(msg);
        WebElement button = scrollToElement(accessibilityId, false);
        waitForVisibility(button);
        click(button, "press or click on " + accessibilityId);
    }

    public WebElement scrollToElement(String accessibilityId, Boolean centerElement) {
        utils.log().info("-> accessibility Id " + accessibilityId);
        WebElement button;

        switch(new GlobalParams().getPlatformName()){
            case "Android":
                button = andScrollToElementUsingUiScrollable(accessibilityId, centerElement);
                break;
            case "iOS":
                button = iOSScrollToElementUsingMobileScroll(accessibilityId, centerElement);
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
        waitForVisibility(button);
        return button;
    }

    public WebElement scrollToElement(WebElement e) {
        WebElement button;

        switch(new GlobalParams().getPlatformName()){
            case "Android":
                button = andScrollToElementUsingUiScrollable(e, true);
                break;
            case "iOS":
                button = iOSScrollToElementUsingMobileScroll(e);
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
        waitForVisibility(button);
        return button;
    }

    public void sendKeys(WebElement e, String txt) {
        waitForVisibility(e);
        e.sendKeys(txt);
    }

    public void sendKeys(String resourceId, String txt, String msg) {
        WebElement accessibility = driver.findElement(AppiumBy.id(resourceId));
        waitForVisibility(accessibility);
        clear(accessibility);
        utils.log().info("-> " + msg);
        accessibility.sendKeys(txt);
    }

    public void sendKeys(String resourceId, int nbrView,  String txt, String msg) {
        WebElement accessibility;
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                accessibility = andGetWebElementByParentResourceId(resourceId, nbrView, "android.widget.EditText", 0);
                break;
            case "iOS":
                accessibility = driver.findElement(AppiumBy.id(resourceId));
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
        waitForVisibility(accessibility);
        clear(accessibility);
        utils.log().info("-> " + msg);
        accessibility.sendKeys(txt);
    }

    public void sendKeys(WebElement e, String txt, String msg) {
        waitForVisibility(e);
        utils.log().info("-> " + msg);
        e.sendKeys(txt);
    }


    public void sendKeys(WebElement e, String parentResourceId, String extResourceId, int nbrView, String txt, String msg) {
        waitForVisibility(e);
        utils.log().info("-> " + msg);
        WebElement accessibility;
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                WebElement inputElement = andGetWebElementByParentResourceId(parentResourceId, nbrView, "android.widget.EditText", 0);
                waitForVisibility(inputElement);
                clear(inputElement);
                utils.log().info("-> " + msg);
                inputElement.sendKeys(txt);
                accessibility = andFindElementWithWait(extResourceId, false);
                break;
            case "iOS":
                accessibility = iosFindElementWithWait(extResourceId, false);
                e.sendKeys(txt);
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
        waitForVisibility(accessibility);
        if (accessibility.isDisplayed()) {
            click(accessibility, "press or click on " + accessibility);
        }
    }

    /**
     * Saisir avec option de perte de focus en cliquant ailleur sur un element après la saisie
     * */
    public void sendKeys(WebElement e, String resourceId, String txt, String msg) {
        waitForVisibility(e);
        utils.log().info("-> " + msg);
        e.sendKeys(txt);
        WebElement accessibility;
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                accessibility = andFindElementWithWait(resourceId, false);
                break;
            case "iOS":
                accessibility = iosFindElementWithWait(resourceId, false);
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
        waitForVisibility(accessibility);
        if (accessibility.isDisplayed()) {
            click(accessibility, "press or click on " + accessibility);
        }
    }

    public void sendSearchKeys(WebElement e, String txt, String msg) {
        WebElement accessibility;
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                sendKeys("ion-searchbar", 2, txt, "search an elected with - " + txt);
                break;
            case "iOS":
                sendKeys( e, txt, msg);
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
    }

    public String getAttribute(WebElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    public String getAttribute(By e, String attribute) {
        waitForVisibility(e);
        return driver.findElement(e).getAttribute(attribute);
    }

    public String getText(WebElement e, String msg) {
        String txt;
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                txt = getAttribute(e, "text");
                break;
            case "iOS":
                txt = getAttribute(e, "label");
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
        utils.log().info("-> " + msg + " " + txt);
        return txt;
    }

    public String getText(WebElement e, int nbrStaticTxt, String msg) {
        String txt;
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                txt = getAttribute(e, "text");
                break;
            case "iOS":
                WebElement element = iOSGetWebElementByParent(e, nbrStaticTxt, ".//XCUIElementTypeStaticText");
                txt = getAttribute(element, "label");
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
        utils.log().info("-> " + msg + txt);
        return txt;
    }

    public String getText(By e, String msg) {
        String txt;
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                txt = getAttribute(e, "text");
                break;
            case "iOS":
                txt = getAttribute(e, "label");
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
        utils.log().info("-> " + msg + txt);
        return txt;
    }

    public String getText(String resourceId, int nbrStaticTxt, String msg) {
        String txt;
        WebElement accessibilityElement = scrollToElement(resourceId, true);
        waitForVisibility(accessibilityElement);
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                txt = getAttribute(accessibilityElement, "text");
                break;
            case "iOS":
                WebElement element = iOSGetWebElementByParent(accessibilityElement, nbrStaticTxt, ".//XCUIElementTypeStaticText");
                txt = getAttribute(element, "label");
                break;
            default:
                throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
        }
        utils.log().info("-> " + msg + txt);
        return txt;
    }

    public void closeApp() {
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                ((InteractsWithApps) driver).terminateApp(driver.getCapabilities().
                        getCapability("appPackage").toString());
                break;
            case "iOS":
                ((InteractsWithApps) driver).terminateApp(driver.getCapabilities().
                        getCapability("bundleId").toString());
        }
    }

    public void launchApp() {
        switch(new GlobalParams().getPlatformName()){
            case "Android":
                ((InteractsWithApps) driver).activateApp(driver.getCapabilities().
                        getCapability("appPackage").toString());
                break;
            case "iOS":
                ((InteractsWithApps) driver).activateApp(driver.getCapabilities().
                        getCapability("bundleId").toString());
        }
    }

    public WebElement andScrollToElementUsingUiScrollable(String childLocAttr, String childLocValue) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                        + "new UiSelector()."+ childLocAttr +"(\"" + childLocValue + "\"));"));
    }

    public WebElement andScrollToElementUsingUiScrollable(String resourceId, Boolean centerElement) {
        WebElement element = andFindElementWithWait(resourceId, centerElement);
        if (element != null) {
            System.out.println(">>>> Element with resourceId '" + resourceId + "' found");
            return element;
        }

        System.out.println(">>>> Element with resourceId '" + resourceId + "' not found, attempting to scroll.");
        for (int i = 0; i < maxScrolls; i++) {
            element = andFindElementWithWait(resourceId, centerElement);
            if (element != null) {
                return element;
            } else {
                performScrollGesture();
            }
        }
        return null;
    }

    private WebElement andFindElementWithWait(String resourceId, Boolean centerElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            WebElement e =  wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath("//*[@resource-id=\"" + resourceId + "\"]")
            ));
            if (e.isDisplayed()) {
                if (centerElement) {
                    scrollToElementAndCenter(e);
                }
                return e;
            }
        } catch (Exception e) {
            System.out.println("---> Element with resourceId '" + resourceId + "' not found.");
        }
        return null;
    }

    private void performScrollGesture() {
        HashMap<String, Object> scrollObject = new HashMap<>();
        scrollObject.put("direction", "down");
        scrollObject.put("left", 100);  // Coordonnée X de début
        scrollObject.put("top", 200);   // Coordonnée Y de début
        scrollObject.put("width", 800); // Largeur de la zone de défilement
        scrollObject.put("height", 800); // Hauteur de la zone de défilement
        scrollObject.put("percent", 0.9); // Pourcentage de défilement
        driver.executeScript("mobile: scrollGesture", scrollObject);
    }

    public WebElement andScrollToElementUsingUiScrollable(WebElement element, Boolean centerElement) {
        if (element.isDisplayed()) {
            if (centerElement) {
                scrollToElementAndCenter(element);
            }
            return element;
        }
        System.out.println(">>>> Element not found, attempting to scroll.");
        for (int i = 0; i < maxScrolls; i++) {
            if (element.isDisplayed()) {
                if (centerElement) {
                    scrollToElementAndCenter(element);
                }
                return element;
            } else {
                performScrollGesture();
            }
        }
        return null;
    }

    public WebElement iOSScrollToElementUsingMobileScroll(String accessibilityId, Boolean centerElement) {
        WebElement element = iosFindElementWithWait(accessibilityId, centerElement);
        if (element != null) {
            return element;
        }

        // Si l'élément n'est pas trouvé, tenter de faire défiler pour le rendre visible
        System.out.println(">>>> Element with accessibilityId '" + accessibilityId + "' not found, attempting to scroll.");
        HashMap<String, Object> scrollObject = new HashMap<>();
        scrollObject.put("name", accessibilityId);
        scrollObject.put("direction", "down");
        scrollObject.put("percent", 0.5); // Pourcentage de défilement
        driver.executeScript("mobile:scroll", scrollObject);

        try {
            // Réessayer de trouver l'élément après le défilement
            WebElement e = iosFindElementWithWait(accessibilityId, centerElement);
            if (e != null) {
                return e;
            }
        } catch (Exception e) {
            System.out.println("****> Element with accessibilityId '" + accessibilityId + "' still not found after scrolling.");
        }
        return null;
    }

    private WebElement iosFindElementWithWait(String accessibilityId, Boolean centerElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            WebElement e = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId(accessibilityId)
            ));
            if (e.isDisplayed()) {
                if(centerElement) {
                    scrollToElementAndCenter(e);
                }
                return e;
            }
        } catch (Exception e) {
            System.out.println("---> Element with accessibilityId '" + accessibilityId + "' not found.");
        }
        return null;
    }


    public WebElement iOSScrollToElementUsingMobileScroll(WebElement e) {
        waitForVisibility(e);
        if (e.isDisplayed()) {
            return e;
        }

        String accessibilityName = e.getTagName();
        HashMap<String, Object> scrollObject = new HashMap<>();
        scrollObject.put("name", accessibilityName);
        scrollObject.put("direction", "down");
        scrollObject.put("percent", 0.5); // Pourcentage de défilement
        driver.executeScript("mobile:scroll", scrollObject);

        try {
            if (e.isDisplayed()) {
                return e;
            }
        } catch (Exception ex) {
            System.out.println("****> Element with accessibilityId '" + accessibilityName + "' still not found after scrolling.");
        }
        return null;
    }

    public WebElement andGetWebElementByParentResourceId(String resourceId, int nbrView, String className, int index) {
        WebElement parentView = driver.findElement(AppiumBy.xpath("//*[@resource-id=\""+resourceId+"\"]"));
        WebElement editText = parentView;

        for (int i = 0; i < nbrView; i++) {
            editText = editText.findElement(By.className("android.view.View"));
        }

        List<WebElement> editTexts = editText.findElements(By.className(className));
        if ( editTexts.size() > index ) {
            editText = editTexts.get(index);
            return editText;
        } else {
            throw new IllegalStateException("-> Index inconnu : " + index);
        }
    }

    public WebElement andGetWebElementByParent(WebElement parentView, int nbrView, String className, int index) {
        WebElement editText = parentView;

        for (int i = 0; i < nbrView; i++) {
            editText = editText.findElement(By.className("android.view.View"));
        }

        List<WebElement> editTexts = editText.findElements(By.className(className));
        if ( editTexts.size() > index ) {
            editText = editTexts.get(index);
            return editText;
        } else {
            throw new IllegalStateException("-> Index inconnu : " + index);
        }
    }

    public WebElement iOSGetWebElementByParent(WebElement parentView,  int index, String path) {
        WebElement editElement = parentView;
        List<WebElement> editElements = editElement.findElements(By.xpath(path));
        System.out.println( editElements );
        if ( editElements.size() > index ) {
            editElement = editElements.get(index);
            return editElement;
        } else {
            throw new IllegalStateException("-> Index inconnu : " + index);
        }
    }

    public By iOSScrollToElementUsingMobileScrollParent(WebElement parentE, String predicateString) {
        RemoteWebElement parent = (RemoteWebElement)parentE;
        String parentID = parent.getId();
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("element", parentID);
        scrollObject.put("predicateString", predicateString);
        driver.executeScript("mobile:scroll", scrollObject);
        By m = AppiumBy.iOSNsPredicateString(predicateString);
        System.out.println("-> Mobile Element is " + m);
        return m;
    }

    public void scrollToElementAndCenter(WebElement element) {
        int elementDiv = 3;
        int screenHeight = driver.manage().window().getSize().getHeight();
        int elementY = element.getLocation().getY() - 20;
        int elementHeight = element.getSize().getHeight();
        int centerY = screenHeight / elementDiv;
        int screenWidth = driver.manage().window().getSize().getWidth();
        int startY = elementY + (elementHeight / elementDiv);
        int endY = centerY;
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scroll = new Sequence(finger, 1);

        if (startY > endY) {
            scroll.addAction(finger.createPointerMove(Duration.ZERO,
                    PointerInput.Origin.viewport(), screenWidth / elementDiv, startY));
            scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            scroll.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                    PointerInput.Origin.viewport(), screenWidth / elementDiv, endY));
            scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        } else {
            scroll.addAction(finger.createPointerMove(Duration.ZERO,
                    PointerInput.Origin.viewport(), screenWidth / elementDiv, endY));
            scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            scroll.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                    PointerInput.Origin.viewport(), screenWidth / elementDiv, startY));
            scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        }
        driver.perform(Arrays.asList(scroll));
        wait(2000);
    }

    public boolean find(final WebElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            return wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (element.isDisplayed()) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    public boolean find(final String accessibilityId, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            return wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    WebElement element;
                    switch(new GlobalParams().getPlatformName()){
                        case "Android":
                            element = andScrollToElementUsingUiScrollable(accessibilityId, true);
                            break;
                        case "iOS":
                            element = iOSScrollToElementUsingMobileScroll(accessibilityId, true);
                            break;
                        default:
                            throw new IllegalStateException("-> Platform inconnu : " + new GlobalParams().getPlatformName());
                    }
                    if (element.isDisplayed()) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    public boolean find(final WebElement element) {
        try {
            waitForVisibility(element, 5);
            return  true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean find(final By element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            return wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (driver.findElement(element).isDisplayed()) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }

    }

    public void tapByCoordinates(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tapSequence = new Sequence(finger, 1);

        tapSequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tapSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tapSequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tapSequence));;
    }

    /*
    public WebElement scrollToElementAndroid(WebElement e) {
        WebElement button;
        button = andScrollToElementUsingUiScrollable(e, true);
        waitForVisibility(button);
        return button;
    }*/

    public void scrollToElementAndroid(String visibleText) {

        try {
            driver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + visibleText + "\").instance(0))"));
        } catch ( Exception e){}

    }

    public static AppiumDriver getDriver() {
        return driver;
    }
}




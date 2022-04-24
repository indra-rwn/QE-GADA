package pages.base;

import org.openqa.selenium.By;

public abstract class BaseLocator {

    public By getElementById(String id) {
        return By.id(id);
    }
    public By getElementByXpath(String xpath) {
        return By.xpath(xpath);
    }
    public By getElementByCssSelector(String css) {
        return By.cssSelector(css);
    }
    public By getElementByClassName(String className) {
        return By.className(className);
    }
    public By getElementByName(String name) {
        return By.name(name);
    }
    public By getElementByLinkText(String linkText) {
        return By.linkText(linkText);
    }
}
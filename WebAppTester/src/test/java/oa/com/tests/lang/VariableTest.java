package oa.com.tests.lang;

import oa.com.tests.actionrunners.interfaces.PathKeeper;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import java.util.LinkedList;
import java.util.List;

public class VariableTest {
    /**
     * Hace una prueba del operador equals, en subclases
     * de {@link Variable}
     */
    @Test
    public void testEquals(){
        String varName = "codigo";
        WebElement webElement = new WebElement() {
            @Override
            public void click() {

            }

            @Override
            public void submit() {

            }

            @Override
            public void sendKeys(CharSequence... keysToSend) {

            }

            @Override
            public void clear() {

            }

            @Override
            public String getTagName() {
                return "";
            }

            @Override
            public String getAttribute(String name) {
                return "";
            }

            @Override
            public boolean isSelected() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public String getText() {
                return "";
            }

            @Override
            public List<WebElement> findElements(By by) {
                return List.of();
            }

            @Override
            public WebElement findElement(By by) {
                return null;
            }

            @Override
            public boolean isDisplayed() {
                return false;
            }

            @Override
            public Point getLocation() {
                return null;
            }

            @Override
            public Dimension getSize() {
                return null;
            }

            @Override
            public Rectangle getRect() {
                return null;
            }

            @Override
            public String getCssValue(String propertyName) {
                return "";
            }

            @Override
            public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
                return null;
            }
        };
        Variable var1 = new StringVariable(varName,"NULL")
                ,var2 = new StringVariable(varName,"ALGO")
                ,var3 = new StringVariable(varName+"2","NADA")
                ,var4 = new SelectorVariable(varName,new PathKeeper("#table", PathKeeper.SearchTypes.CSS),webElement)
                ,var5 = new SelectorVariable(varName,new PathKeeper("#table2", PathKeeper.SearchTypes.CSS),webElement)
                ,var6 = new SelectorVariable(varName+"2",new PathKeeper("#table3", PathKeeper.SearchTypes.CSS),webElement)
                ,var7 = new WebElementVariable(varName, new PathKeeper("#table4", PathKeeper.SearchTypes.CSS), "nada dentro", webElement)
                ,var8 = new WebElementVariable(varName, new PathKeeper("#table5", PathKeeper.SearchTypes.CSS), "nada dentro", webElement)
                ,var9 = new WebElementVariable(varName+"2", new PathKeeper("#table6", PathKeeper.SearchTypes.CSS), "nada dentro", webElement)
                ;
        assert(var1.equals(var2));
        assert(!var1.equals(var3));
        assert(var1.equals(var4));
        assert(var1.equals(var5));
        assert(var4.equals(var5));
        assert(!var4.equals(var6));
        assert(var4.equals(var7));
        assert(var7.equals(var8));
        assert(!var7.equals(var9));
        List<Variable> variables = new LinkedList<>(List.of(var1, var2, var3, var4, var5, var6, var7, var8, var9));
        assert(variables.contains(var1));
        assert(variables.contains(var4));
        assert(variables.contains(var7));
        variables.remove(var1);
        assert(variables.contains(var7));
    }
}

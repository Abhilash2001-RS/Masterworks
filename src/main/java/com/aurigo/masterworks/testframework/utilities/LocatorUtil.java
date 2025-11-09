package com.aurigo.masterworks.testframework.utilities;

import com.aurigo.masterworks.testframework.BaseFramework;
import com.aurigo.masterworks.testframework.utilities.helper.EnvironmentHelper;
import com.aurigo.masterworks.testframework.utilities.models.Locator;
import com.aurigo.masterworks.testframework.utilities.models.LocatorJsonObj;
import com.aurigo.masterworks.testframework.webUI.constants.Constants;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.util.Strings;
import org.openqa.selenium.By;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class LocatorUtil extends BaseFramework {

    public static HashMap<String, By> getLocators(String jsonFileName) {
        String filePath = getFilePath(jsonFileName);
        return getLocatorsFromPath(filePath);
    }


    private static HashMap<String, By> getLocatorsFromPath(String filePath) {
        ArrayList<LocatorJsonObj> locatorJsonObjList = JsonUtil.deSerializeFromFile(new TypeToken<ArrayList<LocatorJsonObj>>() {
        }.getType(), filePath);
        return createLocatorMap(locatorJsonObjList);
    }


    public static  HashMap<String, Locator> getRibbonLocators() {
        String jsonFileName="RibbonMenu.json";
        String filePath = getFilePath(jsonFileName);
        return getLocatorMapFromPath(filePath);
    }

    private static HashMap<String, Locator> getLocatorMapFromPath(String filePath){
        ArrayList<LocatorJsonObj> locatorJsonObjList = JsonUtil.deSerializeFromFile(new TypeToken<ArrayList<LocatorJsonObj>>(){}
                .getType(), filePath);
        return createLocatorMapNew(locatorJsonObjList);
    }

    private static HashMap<String, Locator> createLocatorMapNew(ArrayList<LocatorJsonObj> locatorJsonObjList){
        HashMap<String, Locator> locators = new HashMap<>();

        for (var locator :  locatorJsonObjList){
            locators.put(locator.name,extractLocator(locator,locatorJsonObjList));
        }
        return locators;
    }

    private static Locator extractLocator(LocatorJsonObj locator, ArrayList<LocatorJsonObj> locatorList) {
        Locator extractedLocator = new Locator();
        extractedLocator.name = locator.name;

        if (Strings.isNotBlank(locator.parent)) {
            var parentLocator = locatorList.stream().filter(x -> x.equals(locator.parent)).findFirst().orElse(null);
            if (parentLocator != null) {
                extractedLocator.parent = extractLocator(parentLocator, locatorList);
            }
        }
        switch (locator.type) {
            case "id":
                extractedLocator.identifier = By.id(locator.value);
                break;
            case "xpath":
                extractedLocator.identifier = By.xpath(locator.value);
                break;
            case "cssSelector":
                extractedLocator.identifier = By.cssSelector(locator.value);
                break;
            case "name":
                extractedLocator.identifier = By.name(locator.value);
                break;
            case "className":
                extractedLocator.identifier = By.className(locator.value);
                break;
            case "tagName":
                extractedLocator.identifier = By.tagName(locator.value);
                break;
            case "linkText":
                extractedLocator.identifier = By.linkText(locator.value);
                break;
            case "partialLinkText":
                extractedLocator.identifier = By.partialLinkText(locator.value);
                break;
        }
        return extractedLocator;
    }

    private static String getFilePath(String jsonFileName) {
        String filePath = Paths.get(Constants.TEST_CLASSES_FOLDER_PATH, Constants.DEFAULT_LOCATORS_DIRECTORY, jsonFileName).toString();
        var locatorsDirectory = System.getProperty("locatorsDirectory");
        if (locatorsDirectory == null) {
            locatorsDirectory = EnvironmentHelper.getPropertyValue("locatorsDirectory");
        }
        var filePathToDefinedDirectory = Paths.get(Constants.TEST_CLASSES_FOLDER_PATH, locatorsDirectory, jsonFileName).toString();
        var locatorsFile = new File(filePathToDefinedDirectory);
        if (locatorsFile.exists()) {
            filePath = filePathToDefinedDirectory;
        }
        return filePath;
    }


    private static HashMap<String, By> createLocatorMap(ArrayList<LocatorJsonObj> locatorJsonObjList){
        HashMap<String, By> locators = new HashMap<>();

        for (var locator :  locatorJsonObjList){
            switch (locator.type){
                case "id":
                    locators.put(locator.name, By.id(locator.value));
                    break;
                case "xpath":
                    locators.put(locator.name, By.xpath(locator.value));
                    break;
                case "cssSelector":
                    locators.put(locator.name, By.cssSelector(locator.value));
                    break;
                case "name":
                    locators.put(locator.name, By.name(locator.value));
                    break;
                case "className":
                    locators.put(locator.name, By.className(locator.value));
                    break;
                case "tagName":
                    locators.put(locator.name, By.tagName(locator.value));
                    break;
                case "linkText":
                    locators.put(locator.name, By.linkText(locator.value));
                    break;
                case "partialLinkText":
                    locators.put(locator.name, By.partialLinkText(locator.value));
                    break;
            }
        }
        return locators;
    }





}


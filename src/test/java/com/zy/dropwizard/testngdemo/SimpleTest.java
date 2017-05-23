package com.zy.dropwizard.testngdemo;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 17/5/23
 *
 * @author yue.zhang
 */
@Slf4j
public class SimpleTest {

    @BeforeClass
    public void setUp(){
        System.out.println("setup");
    }

    @Test(groups = {"fast"})
    public void fastTest1(){
        System.out.println("fast test1");
    }

    @Test(groups = {"fast"})
    public void fastTest2(){
        System.out.println("fast test2");
    }

    @Test(groups = {"slow"})
    public void slowTest1(){
        System.out.println("slow test1");
    }

    @Test(groups = {"slow"})
    public void slowTest2(){
        System.out.println("slow test2");
    }

    @Test(dependsOnGroups = {"fast","slow"})
    public void runFinal(){
        System.out.println("run final");
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void divisionWithException(){
        int i = 1 / 0 ;
        System.out.println("After division the value of i is :"+ i);
    }

    @Test(timeOut = 100)
    public void timeoutTest(){
        try {
            Thread.sleep(80);
            System.out.println("timeoutTest success");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = false)
    @Parameters({"pageNum","pageSize"})
    public void parameterTest(int pageNum , int pageSize){
        System.out.println("pageNum: " + pageNum + "  ,pageSize: " + pageSize);
    }

    @Test(dataProvider = "provideNumbers")
    public void dataProviderTest(int number , int expected){
        Assert.assertEquals(number + 10 , expected);
        System.out.println("dataProviderTest success, number: " + number + " , expected: " + expected);
    }

    @DataProvider(name = "provideNumbers")
    public Object [][] provideData(){
        return new Object[][]{{10,20},{100,110},{200,210}};
    }

    @Test(dataProvider = "provideMap")
    public void dataProviderTest2(Map<String, String> map){
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("[Key] : " + entry.getKey() + " [Value] : " + entry.getValue());
        }
    }

    @DataProvider(name = "provideMap")
    public Object [][] provideMapData(){
        Map<String,String> map = getMapData();
        return new Object[][]{{map}};
    }

    public Map<String,String> getMapData(){
        Map<String,String> map = new HashMap<>();
        map.put("a1","110");
        map.put("a2","120");
        map.put("a3","130");
        map.put("a4","140");
        return map;
    }

}

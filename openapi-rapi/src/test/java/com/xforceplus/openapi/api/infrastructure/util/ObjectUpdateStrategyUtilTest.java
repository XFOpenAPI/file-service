//package com.xforceplus.openapi.api.infrastructure.util;
//
//import cn.hutool.core.date.DateTime;
//import com.google.common.collect.Maps;
//import com.xforceplus.ultraman.app.openapirapi.metadata.dict.InvoiceFrom;
//import io.vavr.Tuple;
//import io.vavr.Tuple2;
//import io.vavr.Tuple3;
//import org.junit.jupiter.api.Test;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ObjectUpdateStrategyUtilTest {
//
//    @Test
//    void oldRecordPriorityLowerThanNewRecord() {
//        String objectName = "purchaseInvoice";
//
//        Tuple2<Map<String, Object>, Map<String, Object>> data = mockDataCase1();
//
//        ObjectUpdateStrategyUtil objectUpdateStrategyUtil = new ObjectUpdateStrategyUtil();
//        Map<String, Tuple3<String, Object, Object>> result = objectUpdateStrategyUtil.computeObjectFieldUpdateResult(objectName, InvoiceFrom.TAXWARE_LF.code(), data._1(), data._2());
//        // 应该生成 status/changeTime/changeUser/amountWithoutTax/sellerTaxNo/sellerName 6个字段
//        assertEquals(5, result.size());
//        assertTrue(result.containsKey("status"));
//        assertTrue(result.containsKey("changeTime"));
//        assertTrue(result.containsKey("changeUser"));
//        assertTrue(result.containsKey("sellerTaxNo"));
//        assertTrue(result.containsKey("sellerName"));
//
//        assertEquals("0", result.get("status")._3());
//        assertEquals("zhangsan_new", result.get("changeUser")._3());
//        assertEquals("2022-11-14-new", result.get("changeTime")._3());
//        assertEquals("999", result.get("sellerTaxNo")._3());
//        assertEquals("云砺", result.get("sellerName")._3());
//    }
//
//    @Test
//    void oldRecordPriorityHigherThanNewRecordCase1() {
//
//        String objectName = "purchaseInvoice";
//        Tuple2<Map<String, Object>, Map<String, Object>> data = mockDataCase1();
//
//        ObjectUpdateStrategyUtil objectUpdateStrategyUtil = new ObjectUpdateStrategyUtil();
//        Map<String, Tuple3<String, Object, Object>> result = objectUpdateStrategyUtil.computeObjectFieldUpdateResult(objectName, "PURCHASE_V4", data._2(), data._1());
//        assertEquals(1, result.size());
//
//        assertTrue(result.containsKey("amountWithoutTax"));
//        assertEquals("10.00", result.get("amountWithoutTax")._3());
//
//    }
//
//    @Test
//    void oldRecordPriorityHigherThanNewRecordCase2() {
//
//        String objectName = "purchaseInvoice";
//        Tuple2<Map<String, Object>, Map<String, Object>> data = mockDataCase2();
//
//        ObjectUpdateStrategyUtil objectUpdateStrategyUtil = new ObjectUpdateStrategyUtil();
//        Map<String, Tuple3<String, Object, Object>> result = objectUpdateStrategyUtil.computeObjectFieldUpdateResult(objectName, "PURCHASE_V4", data._1(), data._2());
//
//        // 应该生成 status/changeTime/changeUser/sellerTaxNo/sellerName 5个字段
//        assertEquals(6, result.size());
//        assertTrue(result.containsKey("status"));
//        assertTrue(result.containsKey("changeTime"));
//        assertTrue(result.containsKey("changeUser"));
//        assertTrue(result.containsKey("sellerTaxNo"));
//        assertTrue(result.containsKey("sellerName"));
//        assertTrue(result.containsKey("amountWithoutTax"));
//
//        assertEquals("10.00_new", result.get("amountWithoutTax")._3());
//        assertEquals("0", result.get("status")._3());
//        assertEquals("zhangsan_new", result.get("changeUser")._3());
//        assertEquals("2022-11-14-new", result.get("changeTime")._3());
//        assertEquals("999", result.get("sellerTaxNo")._3());
//        assertEquals("云砺", result.get("sellerName")._3());
//
//    }
//
//    @Test
//    void oldRecordPriorityHigherThanNewRecordCase3() {
//
//        String objectName = "purchaseInvoice";
//        Tuple2<Map<String, Object>, Map<String, Object>> data = mockDataCase3();
//
//        ObjectUpdateStrategyUtil objectUpdateStrategyUtil = new ObjectUpdateStrategyUtil();
//        Map<String, Tuple3<String, Object, Object>> result = objectUpdateStrategyUtil.computeObjectFieldUpdateResult(objectName, "PURCHASE_V4", data._1(), data._2());
//
//        // 应该生成 sellerTaxNo/sellerName 2个字段
//        assertEquals(3, result.size());
//
//        assertTrue(result.containsKey("sellerTaxNo"));
//        assertTrue(result.containsKey("sellerName"));
//        assertTrue(result.containsKey("amountWithoutTax"));
//
//        assertEquals("999", result.get("sellerTaxNo")._3());
//        assertEquals("云砺", result.get("sellerName")._3());
//        assertEquals("10.00_new", result.get("amountWithoutTax")._3());
//
//    }
//
//
//    @Test
//    void oldRecordPriorityHigherThanNewRecordCase4() {
//
//        String objectName = "purchaseInvoice";
//        Tuple2<Map<String, Object>, Map<String, Object>> data = mockDataCase3();
//
//        ObjectUpdateStrategyUtil objectUpdateStrategyUtil = new ObjectUpdateStrategyUtil();
//        Map<String, Tuple3<String, Object, Object>> result = objectUpdateStrategyUtil.computeObjectFieldUpdateResult(objectName, "PURCHASE_V4", data._1(), data._2());
//
//        // 应该生成 sellerTaxNo/sellerName 2个字段
//        assertEquals(1, result.size());
//
//        assertTrue(result.containsKey("amountWithoutTax"));
//
//        assertEquals("10.00_new", result.get("amountWithoutTax")._3());
//
//    }
//
//    private Tuple2<Map<String, Object>, Map<String, Object>> mockDataCase1(){
//
//        Map<String, Object> dataOne = Maps.newHashMap();
//        dataOne.put("status", "1");
//        dataOne.put("reverseFlag", true);
//        dataOne.put("authStatus", "3");
//        dataOne.put("changeTime", DateTime.now());
//        dataOne.put("changeUser", "zhangsan");
//        dataOne.put("invoiceNo", "1");
//        dataOne.put("invoiceCode", "1");
//        dataOne.put("amountWithoutTax", "10.00");
//        dataOne.put("dataSource", InvoiceFrom.PURCHASE_V4.code());
//
//        Map<String, Object> dataTwo = Maps.newHashMap();
//        dataTwo.put("status", "0");
//        dataTwo.put("reverseFlag", true);
//        dataTwo.put("authStatus", "3");
//        dataTwo.put("changeTime", "2022-11-14-new");
//        dataTwo.put("changeUser", "zhangsan_new");
//        dataTwo.put("invoiceNo", "1");
//        dataTwo.put("invoiceCode", "1");
//        dataTwo.put("amountWithoutTax", "10.00_new");
//        dataTwo.put("dataSource", InvoiceFrom.TAXWARE_LF.code());
//        dataTwo.put("sellerTaxNo", "999");
//        dataTwo.put("sellerName", "云砺");
//
//        return Tuple.of(dataOne, dataTwo);
//    }
//
//
//    private Tuple2<Map<String, Object>, Map<String, Object>> mockDataCase2(){
//
//        Map<String, Object> dataOne = Maps.newHashMap();
//        dataOne.put("status", "1");
//        dataOne.put("reverseFlag", true);
//        dataOne.put("authStatus", "3");
//        dataOne.put("changeTime", DateTime.now());
//        dataOne.put("changeUser", "zhangsan");
//        dataOne.put("invoiceNo", "1");
//        dataOne.put("invoiceCode", "1");
//        dataOne.put("amountWithoutTax", "10.00");
//        dataOne.put("dataSource", InvoiceFrom.TAXWARE_LF.code());
//
//        Map<String, Object> dataTwo = Maps.newHashMap();
//        dataTwo.put("status", "0");
//        dataTwo.put("reverseFlag", true);
//        dataTwo.put("authStatus", "3");
//        dataTwo.put("changeTime", "2022-11-14-new");
//        dataTwo.put("changeUser", "zhangsan_new");
//        dataTwo.put("invoiceNo", "1");
//        dataTwo.put("invoiceCode", "1");
//        dataTwo.put("amountWithoutTax", "10.00_new");
//        dataTwo.put("dataSource", InvoiceFrom.PURCHASE_V4.code());
//        dataTwo.put("sellerTaxNo", "999");
//        dataTwo.put("sellerName", "云砺");
//
//        return Tuple.of(dataOne, dataTwo);
//    }
//
//    private Tuple2<Map<String, Object>, Map<String, Object>> mockDataCase3(){
//
//        Map<String, Object> dataOne = Maps.newHashMap();
//        dataOne.put("status", "0");
//        dataOne.put("reverseFlag", true);
//        dataOne.put("authStatus", "3");
//        dataOne.put("changeTime", DateTime.now());
//        dataOne.put("changeUser", "zhangsan");
//        dataOne.put("invoiceNo", "1");
//        dataOne.put("invoiceCode", "1");
//        dataOne.put("amountWithoutTax", "10.00");
//        dataOne.put("dataSource", InvoiceFrom.TAXWARE_LF.code());
//
//        Map<String, Object> dataTwo = Maps.newHashMap();
//        dataTwo.put("status", "1");
//        dataTwo.put("reverseFlag", true);
//        dataTwo.put("authStatus", "3");
//        dataTwo.put("changeTime", "2022-11-14-new");
//        dataTwo.put("changeUser", "zhangsan_new");
//        dataTwo.put("invoiceNo", "1");
//        dataTwo.put("invoiceCode", "1");
//        dataTwo.put("amountWithoutTax", "10.00_new");
//        dataTwo.put("dataSource", InvoiceFrom.PURCHASE_V4.code());
//        dataTwo.put("sellerTaxNo", "999");
//        dataTwo.put("sellerName", "云砺");
//
//        return Tuple.of(dataOne, dataTwo);
//    }
//
//
//    private Tuple2<Map<String, Object>, Map<String, Object>> mockDataCase4(){
//
//        Map<String, Object> dataOne = Maps.newHashMap();
//        dataOne.put("status", "0");
//        dataOne.put("reverseFlag", true);
//        dataOne.put("authStatus", "3");
//        dataOne.put("changeTime", DateTime.now());
//        dataOne.put("changeUser", "zhangsan");
//        dataOne.put("invoiceNo", "1");
//        dataOne.put("invoiceCode", "1");
//        dataOne.put("amountWithoutTax", "10.00");
//        dataOne.put("dataSource", InvoiceFrom.TAXWARE_LF.code());
//        dataOne.put("sellerTaxNo", "999");
//        dataOne.put("sellerName", "云砺");
//
//        Map<String, Object> dataTwo = Maps.newHashMap();
//        dataTwo.put("status", "1");
//        dataTwo.put("reverseFlag", true);
//        dataTwo.put("authStatus", "3");
//        dataTwo.put("changeTime", "2022-11-14-new");
//        dataTwo.put("changeUser", "zhangsan_new");
//        dataTwo.put("invoiceNo", "1");
//        dataTwo.put("invoiceCode", "1");
//        dataTwo.put("amountWithoutTax", "10.00_new");
//        dataTwo.put("dataSource", InvoiceFrom.PURCHASE_V4.code());
//        dataTwo.put("sellerTaxNo", "");
//        dataTwo.put("sellerName", "");
//
//        return Tuple.of(dataOne, dataTwo);
//    }
//}
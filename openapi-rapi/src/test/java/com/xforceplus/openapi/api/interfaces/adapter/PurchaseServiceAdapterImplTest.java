package com.xforceplus.openapi.api.interfaces.adapter;

import io.vavr.Tuple;
import io.vavr.Tuple3;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PurchaseServiceAdapterImplTest {

    @Test
    public void testCallVerifyResult() {

        Tuple3<String, Object, Object> demo = Tuple.of("a", "b", null);

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("a", demo._3);
        assertTrue(true);

    }


}
package com.directfn.exchange_simulator.common_util.utils;

import java.util.HashMap;
import java.util.Map;


public final class GlobalStatus {
    private static Map<String, String> clOrdIdOverIsValidToProcess;
    private static Map<String, Object> openOrderMap =new HashMap<>();

    private GlobalStatus() {

    }

    public static Map<String, String> getClOrdIdOverIsValidToProcess() {
        return clOrdIdOverIsValidToProcess;
    }

    public static void setClOrdIdOverIsValidToProcess(Map<String, String> clOrdIdOverIsValidToProcess) {
        GlobalStatus.clOrdIdOverIsValidToProcess = clOrdIdOverIsValidToProcess;
    }

    public static Object getOrderBean(String clOrderId){
        return openOrderMap.get(clOrderId);
    }

    public static void addOpenOrder(String clOrderId, Object order){
        openOrderMap.put(clOrderId,order);
    }

    public static void removeOpenOrder(String clOrderId){
        openOrderMap.remove(clOrderId);
    }
}

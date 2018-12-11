/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.util;

import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author CPizarro
 */
public class MapsUtil {

    public static <K, V> Map.Entry<K, V> getFirst(Map<K, V> map) {
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    public static <K, V> K getFirstKey(Map<K, V> map) {
        return getFirst(map).getKey();
    }

    public static <K, V> V getFirstValue(Map<K, V> map) {
        return getFirst(map).getValue();
    }

    public static <K, V> Map.Entry<K, V> getLast(Map<K, V> map) {
        Map.Entry<K, V> last = null;
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            last = it.next();
        }
        return last;
    }

    public static <K, V> K getLastKey(Map<K, V> map) {
        return getLast(map).getKey();
    }

    public static <K, V> V getLastValue(Map<K, V> map) {
        return getLast(map).getValue();
    }

    public static <K, V> Map.Entry<K, V> getNext(Map<K, V> map, Map.Entry<K, V> entry) {
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().equals(entry)) {
                return it.next();
            }
        }
        return null;
    }

    public static <K, V> K getNextKey(Map<K, V> map, K key) {
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> entry = it.next();
            if (entry.getKey().equals(key)) {
                return it.next().getKey();
            }
        }
        return null;
    }

    public static <K, V> V getNextValue(Map<K, V> map, V value) {
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> entry = it.next();
            if (entry.getValue().equals(value)) {
                if (it.hasNext()) {
                    return it.next().getValue();
                }
            }
        }
        return null;
    }

}

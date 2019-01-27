package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ArrayUtils {
    static JSONArray nullToEmptyJSON(JSONArray array) {
        return array != null ? array : new JSONArray();
    }
    public static List<String> nullToEmptyStrings(List<String> array) {
        return array != null ? array : new ArrayList<String>();
    }
}

package be.akimts.test.trad.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgsUtils {
    public static  Map<String, String> argsToMap(String[] args){
        Map<String,String> argMap = new HashMap<>();
        for (String arg : args) {
            String[] parts = arg.split("=");
            String key = parts[0].substring(2);
            String value = parts[1];
            argMap.put(key,value);
        }
        return argMap;
    }
}

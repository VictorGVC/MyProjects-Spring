package com.victorgvc.multilanguagespring.utils;

public class Validations {
    
    public static void notExists(Object obj, String msg) throws RuntimeException{

        if(obj == null)
            throw new RuntimeException(msg);
        if(obj instanceof String && ((String)obj).isEmpty())
            throw new RuntimeException(msg);
    }

    public static void notEquals(Object obj1, Object obj2, String msg) throws RuntimeException{

        if(obj1 instanceof String && !((String)obj1).equals(obj2))
            throw new RuntimeException(msg);
        if(!obj1.equals(obj2))
            throw new RuntimeException(msg);
    }

    public static void exists(Object obj, String msg) throws Exception{

        if(obj != null)
            throw new Exception(msg);
    }

    
}

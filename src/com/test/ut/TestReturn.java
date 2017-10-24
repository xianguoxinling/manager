package com.test.ut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class TestReturn
{

    public static void main(String[] args)
    {
        JSONObject json = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errno", "0");
        List<String> data = new ArrayList<String>();
        data.add("122421");
        data.add("33333");
        map.put("data", data);
        json = JSONObject.fromObject(map);
        
        System.out.println(json);
    }

}

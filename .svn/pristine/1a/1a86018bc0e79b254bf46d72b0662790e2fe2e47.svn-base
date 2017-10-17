package com.sinosoft.test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test1 {
	public static void main(String[] args) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("typeName", "aa");
	    map.put("count", "12");
	    Map<String, Object> map1 = new HashMap<String, Object>();  
	    map1.put("typeName", "bb");
	    map1.put("count", "23");
	    Map<String, Object> map2 = new HashMap<String, Object>();  
	    map2.put("typeName", "cc"); 
	    map2.put("count", "33");
	    
	    Map<String, Object> map3 = new HashMap<String, Object>();  
	    map3.put("typeName", "aa");  
	    map3.put("count", "23");
	    Map<String, Object> map4 = new HashMap<String, Object>();  
	    map4.put("typeName", "bb");  
	    map4.put("count", "11");
	    Map<String, Object> map5 = new HashMap<String, Object>();  
	    map5.put("typeName", "dd");
	    map5.put("count", "5");
	    
	    
	    List<Map<String, Object>> big = new ArrayList<Map<String, Object>>();  
	    big.add(map); 
	    big.add(map1);
	    big.add(map2);
	    List<Map<String, Object>> small = new ArrayList<Map<String, Object>>();  
	    small.add(map3); 
	    small.add(map4);
	    small.add(map5);
	    
	    Map<String,Integer> bMap = new HashMap<String, Integer>();
	    for(int i=0;i<big.size();i++){
	    	bMap.put(big.get(i).get("typeName").toString(), Integer.parseInt(big.get(i).get("count").toString()));
	    }
	    Map<String,Integer> sMap = new HashMap<String, Integer>();
	    for(int i=0;i<small.size();i++){
	    	sMap.put(small.get(i).get("typeName").toString(), Integer.parseInt(small.get(i).get("count").toString()));
	    }
	    
	    Map<String, Integer> zl = new HashMap<String, Integer>();
	    Map<String, Integer> jl = new HashMap<String, Integer>();
	    for (String b : bMap.keySet()) {
    		if(sMap.containsKey(b)){
    			int bNum = bMap.get(b);
    			int sNum = sMap.get(b);
    			if(bNum>sNum){
    				jl.put(b, bNum-sNum);
    			}
    			if(bNum<sNum){
    				zl.put(b, sNum-bNum);
    			}
    		}
    		if(!sMap.containsKey(b)){
    			jl.put(b, bMap.get(b));
    		}
	    }
	    for (String s : sMap.keySet()) {
    		if(!bMap.containsKey(s)){
    			zl.put(s, sMap.get(s));
    		}
	    }
	    StringBuffer sb = new StringBuffer();
	    for(String k : jl.keySet()){
	    	sb.append(k).append(":").append(jl.get(k)).append(",");
	    }
	    String n = sb.toString();
	    System.out.println(n.substring(0, n.length()-1));
	    /*System.out.println("1111111");
	    for(String k : zl.keySet()){
	    	System.out.println(k+"=="+zl.get(k));
	    }*/
	}
}

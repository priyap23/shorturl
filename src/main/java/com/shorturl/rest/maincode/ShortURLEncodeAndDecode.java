package com.shorturl.rest.maincode;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ShortURLEncodeAndDecode {

	private static ShortURLEncodeAndDecode objEncodeDecode = null;
	private static Map<String, String> hmKeyword = new HashMap();
	private static Map<String, String> hmReverseKeyword = new HashMap();
	
	public static ShortURLEncodeAndDecode getInstance() {
		
		if(null == objEncodeDecode) {
			objEncodeDecode = new ShortURLEncodeAndDecode();
			getKeywordMap();
			getReverseKeywordMap();
		} 
		 
		return objEncodeDecode;
	}
	
	
	public String encodeAndDecodeURL(String strURL) throws Exception{
		
		String strConvertedURL = "";
		
		if(strURL.startsWith("http") || strURL.startsWith("https")) {
			strConvertedURL = convertLongToShort(strURL);
		}else {
			//decode URL
			strConvertedURL = convertShortToLong(strURL);
		}
		return strConvertedURL;
	}

	
	private String convertShortToLong(String strURL) throws Exception{
		String strLongURL = strURL;
		boolean isPathAvailable = false;
		//Spilt on 0
		String[] arrURL1 =  strURL.split("0");
		String strProtocol = arrURL1[0];
		strProtocol = strProtocol.replace(strProtocol, hmReverseKeyword.get(strProtocol)+"://");
		
		//Split on 1
		String strDomain =  arrURL1[1];
		String[] arrPath = null;
		if(strDomain.contains("_")) {
			arrPath = strDomain.split("_");	//Split on # to get path
			strDomain = arrPath[0];
			isPathAvailable = true;
		}
		
		StringBuilder sbDomain = new StringBuilder();
		String[] arrDomain = strDomain.split("1");
		for(int i = 0; i < arrDomain.length; i++) {
			sbDomain.append(hmReverseKeyword.get(arrDomain[i]));
			if((i + 1) != arrDomain.length) {
				sbDomain.append(".");
			}
			
		}
		
		if(isPathAvailable) {
			sbDomain.append("/");
			for(int i = 1; i < arrPath.length; i++) {
				sbDomain.append(hmReverseKeyword.get(arrPath[i]));
				if((i + 1) != arrPath.length) {
					sbDomain.append("/");
				}
			}
		}
				
		strLongURL = strProtocol + sbDomain.toString();
		return strLongURL;
	}


	private String convertLongToShort(String strLongURL) throws Exception{
		
		String strConvertedURL = "";
		
		StringBuilder sbURL = new StringBuilder("http://localhost:8080/url?url=");
		URL url;
		url = new URL(strLongURL);
		
		// protocol
		String strProtocol = url.getProtocol();
		//shorten protocol
		sbURL.append(hmKeyword.get(strProtocol)); 
		sbURL.append("0");
		
		
		//domain
		String strDomain = url.getHost();
		String[] arrDomain = strDomain.split("[\\.]");
					
		for(int i = 0; i < arrDomain.length; i++) {
			sbURL.append(hmKeyword.get(arrDomain[i]));
			if((i + 1) != arrDomain.length) {
				sbURL.append(hmKeyword.get("."));
			}
		}
		
		//Path
		String strPath = url.getPath();
		if(null != strPath && !strPath.isEmpty()) {
			sbURL.append("_");
		}
		String[] arrpath = strPath.split("/");
		for(int i = 0; i < arrpath.length; i++) {
			if(hmKeyword.containsKey(arrpath[i])) {
				sbURL.append(hmKeyword.get(arrpath[i]));
				if((i + 1) != arrpath.length) {
					sbURL.append("_");
				}
			}
			
		}
		
		strConvertedURL = sbURL.toString();
		
		
		return strConvertedURL;
	}
	
	
	private static void getKeywordMap() {
		hmKeyword.put("www","W");
		hmKeyword.put("http","h");
		hmKeyword.put("https","hs");
		hmKeyword.put("com","c");
		hmKeyword.put("facebook","fb");
		hmKeyword.put("linkedin","L");
		hmKeyword.put("twitter","tw");
		hmKeyword.put("timesofindia","toi");
		hmKeyword.put("monster","m");
		hmKeyword.put("in","I");
		hmKeyword.put("se","S");
		hmKeyword.put("://","0");
		hmKeyword.put(".","1");
		hmKeyword.put("google","G");
		hmKeyword.put("thenewbieguide", "tbg");
		hmKeyword.put("folkuniversitetet", "fu");
		hmKeyword.put("school", "Sc");
		hmKeyword.put("stockholm-university", "su");
		hmKeyword.put("knowledge", "K");
		hmKeyword.put("top-10-companies-in-sweden", "ten");
		hmKeyword.put("swedish", "sw");
		hmKeyword.put("where-to-learn-swedish", "lS");
		hmKeyword.put("In-English", "en");
		hmKeyword.put("Swedish-courses", "sC");
		hmKeyword.put("SFI---swedish-for-immigrants", "sfi");
		hmKeyword.put("datantify", "d");
	}
	
	private static void getReverseKeywordMap() {
		for (Map.Entry<String, String> entry : hmKeyword.entrySet()) {
			String strValue = entry.getValue();
			String strKey = entry.getKey();
			hmReverseKeyword.put(strValue, strKey);
		}
	}
	
}

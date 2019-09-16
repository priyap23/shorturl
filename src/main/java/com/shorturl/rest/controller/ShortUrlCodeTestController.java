package com.shorturl.rest.controller;

import java.util.HashMap;
import java.util.Random;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shorturl.rest.maincode.ShortURLEncodeAndDecode;
import com.shorturl.rest.maincode.URLException;
import com.shorturl.rest.maincode.URLHolder;

@RestController
public class ShortUrlCodeTestController {

	@RequestMapping(method = RequestMethod.GET, value="/url")
	
	public ResponseEntity<Object> shortnedURL(@RequestParam(name = "url") String strLongURL) {
		
		URLHolder objURlHolder = new URLHolder();
		String strInputLongUrl = strLongURL;
		HttpStatus httpStatusCode =  HttpStatus.OK;
		String strReturnValue = "";
		HttpHeaders headers = new HttpHeaders();
		URLException objExp = new URLException();
		try {
		if(null != strInputLongUrl && !strInputLongUrl.isEmpty() && strInputLongUrl.startsWith("http")) {
			System.out.println("Long URL***********"+strInputLongUrl);
			strReturnValue =  ShortURLEncodeAndDecode.getInstance().encodeAndDecodeURL(strInputLongUrl);
			objURlHolder.setUrl(strReturnValue);
			//throw new Exception("Errror ......");
		} else {
			strReturnValue =  ShortURLEncodeAndDecode.getInstance().encodeAndDecodeURL(strInputLongUrl);
			objURlHolder.setUrl(strReturnValue);
			httpStatusCode = HttpStatus.FOUND;
			headers.add("Location", strReturnValue);
		}
		}catch(Exception e) {
			httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			objExp.setErrorMsg(e.getMessage());
			return new ResponseEntity<Object>(objExp, httpStatusCode);
		}
		return new ResponseEntity<Object>(objURlHolder, headers,httpStatusCode);
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/sl") 
	public ResponseEntity<Object> getShortURL(@RequestParam(name = "url") String strLongURL){
		
		URLHolder objURlHolder = new URLHolder();
		String strInputLongUrl = strLongURL;
		HttpStatus httpStatusCode =  HttpStatus.OK;
		String strReturnValue = "";
		HttpHeaders headers = new HttpHeaders();
		URLException objExp = new URLException();
		try {
		if(null != strInputLongUrl && !strInputLongUrl.isEmpty() && strInputLongUrl.startsWith("http")) {
			System.out.println("Long URL***********"+strInputLongUrl);
			strReturnValue =  encode(strInputLongUrl);
			objURlHolder.setUrl(strReturnValue);
			//throw new Exception("Errror ......");
		} else {
			strReturnValue =  decode(strInputLongUrl);
			objURlHolder.setUrl(strReturnValue);
			httpStatusCode = HttpStatus.FOUND;
			headers.add("Location", strReturnValue);
		}
		}catch(Exception e) {
			httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			objExp.setErrorMsg(e.getMessage());
			return new ResponseEntity<Object>(objExp, httpStatusCode);
		}
		return new ResponseEntity<Object>(objURlHolder, headers,httpStatusCode);
		
	}

	
	//HashMap to store the longUrl and the randomly generated string
    HashMap<String,String> urlMap = new HashMap<>();  

   // Encodes a URL to a shortened URL.
   public String encode(String longUrl) {
       Random rand = new Random();
       int urlLen = 6;
       char [] shortURL = new char[urlLen];
       String randChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

       for(int i = 0; i < urlLen; i++ )
           shortURL[i] = randChars.charAt(rand.nextInt(randChars.length()));

       StringBuilder sb = new StringBuilder("http://localhost:8080/sl?url=");
       sb.append(new String(shortURL));
       System.out.println(sb);

       urlMap.put(sb.toString(),longUrl);

       return sb.toString();

   }

   // Decodes a shortened URL to its original URL.
   public String decode(String shortUrl) {
	   
	   	String strURL = "http://localhost:8080/sl?url=";
	   	System.out.println("shortUrl ::::::: "+urlMap);
	   	return  urlMap.get(strURL +shortUrl);

   }

	
}

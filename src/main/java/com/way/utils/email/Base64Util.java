package com.way.utils.email;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {
     public static String base64EncodeStr(byte[] str) {
           if(str == null) {return "" ;}
           BASE64Encoder base64Encoder = new BASE64Encoder();   
           String content = base64Encoder.encodeBuffer(str);
           content = content.replaceAll("\r", "").replaceAll("\n", "");
           return content;
     }
     
     public static String base64Decoder(String str){
           if(StringUtils.isBlank(str)) {return "";}
           
           str = str.replace(" ", "+");
           
           BASE64Decoder base64Decoder = new BASE64Decoder();
           try {
                byte[] b = base64Decoder.decodeBuffer(str);
                return new String(b,"UTF-8");
           } catch(Exception e) {
                e.printStackTrace();
                return "";
           }
     }

}


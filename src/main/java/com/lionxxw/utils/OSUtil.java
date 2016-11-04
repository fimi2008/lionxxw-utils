package com.lionxxw.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;


public class OSUtil {
	
	final static Logger logger = LoggerFactory.getLogger(OSUtil.class);

	public static final String RESOURCE_PATH;  
	  
    public static final boolean isLinux;  
    
    static {  

    	URL resource = OSUtil.class.getClassLoader().getResource("");
        String resourcePath = resource.getPath() + "resources/img/";
        if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1  
                && resourcePath.startsWith("/")) {  
        	resourcePath = resourcePath.substring(1);  
            isLinux = false;  
        } else {  
            isLinux = true;  
        }  
        RESOURCE_PATH = resourcePath;  
        logger.info("系统环境:{} 资源路径：{}",isLinux ? "Linux":"WINDOWS",RESOURCE_PATH);
    }  
}

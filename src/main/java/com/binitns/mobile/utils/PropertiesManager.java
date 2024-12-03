package com.binitns.mobile.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {

    private static Properties props = new Properties();
    TestsUtils utils = new TestsUtils();

    public Properties getProps() throws IOException {
        InputStream inputStream = null;
        String propsFileName = "config.properties";

        if(props.isEmpty()){
            try{
                utils.log().info("-> Chargement du config properties 🙂");
                inputStream = getClass().getClassLoader().getResourceAsStream(propsFileName);
                props.load(inputStream);
                utils.log().info("-> La config properties est bien chargée 🤩");
            } catch (IOException e) {
                e.printStackTrace();
                utils.log().fatal("-> Erreur de chargement du config properties 🥹: " + e.toString());
                throw e;
            } finally {
                if(inputStream != null){
                    inputStream.close();
                }
            }
        }
        return props;
    }
}


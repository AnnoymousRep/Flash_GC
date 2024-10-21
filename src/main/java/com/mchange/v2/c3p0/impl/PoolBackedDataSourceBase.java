//package com.mchange.v2.c3p0.impl;
//
//import com.mchange.v2.naming.ReferenceIndirector;
//import com.mchange.v2.c3p0.C3P0Registry;
//import com.mchange.v2.c3p0.cfg.C3P0Config;
//import com.mchange.v2.log.MLevel;
//import com.mchange.v2.log.MLog;
//import com.mchange.v2.naming.JavaBeanReferenceMaker;
//import com.mchange.v2.ser.IndirectlySerialized;
//import com.mchange.v2.ser.SerializableUtils;
//
//import javax.naming.NamingException;
//import javax.naming.Reference;
//import javax.naming.Referenceable;
//import javax.sql.ConnectionPoolDataSource;
//import java.beans.*;
//import java.io.*;
//import java.sql.SQLFeatureNotSupportedException;
//import java.util.Collections;
//import java.util.Map;
//import java.util.logging.Logger;
//
//public class PoolBackedDataSourceBase extends IdentityTokenResolvable implements Referenceable, Serializable {
//    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
//    protected VetoableChangeSupport vcs = new VetoableChangeSupport(this);
//    private ConnectionPoolDataSource connectionPoolDataSource;
//    private String dataSourceName = C3P0Config.initializeStringPropertyVar("dataSourceName", C3P0Defaults.dataSourceName());
//    private Map extensions = C3P0Config.initializeExtensions();
//    private String factoryClassLocation = C3P0Config.initializeStringPropertyVar("factoryClassLocation", C3P0Defaults.factoryClassLocation());
//    private volatile String identityToken;
//    private int numHelperThreads = C3P0Config.initializeIntPropertyVar("numHelperThreads", C3P0Defaults.numHelperThreads());
//    private static final long serialVersionUID = 1L;
//    private static final short VERSION = 1;
//    static final JavaBeanReferenceMaker referenceMaker = new JavaBeanReferenceMaker();
//
//    protected PropertyChangeSupport getPropertyChangeSupport() {
//        return this.pcs;
//    }
//
//    protected VetoableChangeSupport getVetoableChangeSupport() {
//        return this.vcs;
//    }
//
//    public synchronized ConnectionPoolDataSource getConnectionPoolDataSource() {
//        return this.connectionPoolDataSource;
//    }
//
//    public synchronized void setConnectionPoolDataSource(ConnectionPoolDataSource connectionPoolDataSource) throws PropertyVetoException {
//        ConnectionPoolDataSource oldVal = this.connectionPoolDataSource;
//        if (!this.eqOrBothNull(oldVal, connectionPoolDataSource)) {
//            this.vcs.fireVetoableChange("connectionPoolDataSource", oldVal, connectionPoolDataSource);
//        }
//
//        this.connectionPoolDataSource = connectionPoolDataSource;
//        if (!this.eqOrBothNull(oldVal, connectionPoolDataSource)) {
//            this.pcs.firePropertyChange("connectionPoolDataSource", oldVal, connectionPoolDataSource);
//        }
//
//    }
//
//    public synchronized String getDataSourceName() {
//        return this.dataSourceName;
//    }
//
//    public synchronized void setDataSourceName(String dataSourceName) {
//        String oldVal = this.dataSourceName;
//        this.dataSourceName = dataSourceName;
//        if (!this.eqOrBothNull(oldVal, dataSourceName)) {
//            this.pcs.firePropertyChange("dataSourceName", oldVal, dataSourceName);
//        }
//
//    }
//
//    public synchronized Map getExtensions() {
//        return Collections.unmodifiableMap(this.extensions);
//    }
//
//    public synchronized void setExtensions(Map extensions) {
//        this.extensions = Collections.unmodifiableMap(extensions);
//    }
//
//    public synchronized String getFactoryClassLocation() {
//        return this.factoryClassLocation;
//    }
//
//    public synchronized void setFactoryClassLocation(String factoryClassLocation) {
//        this.factoryClassLocation = factoryClassLocation;
//    }
//
//    public String getIdentityToken() {
//        return this.identityToken;
//    }
//
//    public void setIdentityToken(String identityToken) {
//        String oldVal = this.identityToken;
//        this.identityToken = identityToken;
//        if (!this.eqOrBothNull(oldVal, identityToken)) {
//            this.pcs.firePropertyChange("identityToken", oldVal, identityToken);
//        }
//
//    }
//
//    public synchronized int getNumHelperThreads() {
//        return this.numHelperThreads;
//    }
//
//    public synchronized void setNumHelperThreads(int numHelperThreads) {
//        int oldVal = this.numHelperThreads;
//        this.numHelperThreads = numHelperThreads;
//        if (oldVal != numHelperThreads) {
//            this.pcs.firePropertyChange("numHelperThreads", oldVal, numHelperThreads);
//        }
//
//    }
//
//    public void addPropertyChangeListener(PropertyChangeListener pcl) {
//        this.pcs.addPropertyChangeListener(pcl);
//    }
//
//    public void addPropertyChangeListener(String propName, PropertyChangeListener pcl) {
//        this.pcs.addPropertyChangeListener(propName, pcl);
//    }
//
//    public void removePropertyChangeListener(PropertyChangeListener pcl) {
//        this.pcs.removePropertyChangeListener(pcl);
//    }
//
//    public void removePropertyChangeListener(String propName, PropertyChangeListener pcl) {
//        this.pcs.removePropertyChangeListener(propName, pcl);
//    }
//
//    public PropertyChangeListener[] getPropertyChangeListeners() {
//        return this.pcs.getPropertyChangeListeners();
//    }
//
//    public void addVetoableChangeListener(VetoableChangeListener vcl) {
//        this.vcs.addVetoableChangeListener(vcl);
//    }
//
//    public void removeVetoableChangeListener(VetoableChangeListener vcl) {
//        this.vcs.removeVetoableChangeListener(vcl);
//    }
//
//    public VetoableChangeListener[] getVetoableChangeListeners() {
//        return this.vcs.getVetoableChangeListeners();
//    }
//
//    private boolean eqOrBothNull(Object a, Object b) {
//        return a == b || a != null && a.equals(b);
//    }
//
//    private void writeObject(ObjectOutputStream oos) throws IOException {
//        oos.writeShort(1);
//
//        ReferenceIndirector indirector;
//        try {
//            SerializableUtils.toByteArray(this.connectionPoolDataSource);
//            oos.writeObject(this.connectionPoolDataSource);
//        } catch (NotSerializableException var9) {
//            MLog.getLogger(this.getClass()).log(MLevel.FINE, "Direct serialization provoked a NotSerializableException! Trying indirect.", var9);
//
//            try {
//                indirector = new ReferenceIndirector();
//                oos.writeObject(indirector.indirectForm(this.connectionPoolDataSource));
//            } catch (IOException var7) {
//                throw var7;
//            } catch (Exception var8) {
//                throw new IOException("Problem indirectly serializing connectionPoolDataSource: " + var8.toString());
//            }
//        }
//
//        oos.writeObject(this.dataSourceName);
//
//        try {
//            SerializableUtils.toByteArray(this.extensions);
//            oos.writeObject(this.extensions);
//        } catch (NotSerializableException var6) {
//            MLog.getLogger(this.getClass()).log(MLevel.FINE, "Direct serialization provoked a NotSerializableException! Trying indirect.", var6);
//
//            try {
//                indirector = new ReferenceIndirector();
//                oos.writeObject(indirector.indirectForm(this.extensions));
//            } catch (IOException var4) {
//                throw var4;
//            } catch (Exception var5) {
//                throw new IOException("Problem indirectly serializing extensions: " + var5.toString());
//            }
//        }
//
//        oos.writeObject(this.factoryClassLocation);
//        oos.writeObject(this.identityToken);
//        oos.writeInt(this.numHelperThreads);
//    }
//
//    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
//        short version = ois.readShort();
//        switch (version) {
//            case 1:
//                Object o = ois.readObject();
//                if (o instanceof IndirectlySerialized) {
//                    o = ((IndirectlySerialized)o).getObject();
//                }
//
//                this.connectionPoolDataSource = (ConnectionPoolDataSource)o;
//                this.dataSourceName = (String)ois.readObject();
//                o = ois.readObject();
//                if (o instanceof IndirectlySerialized) {
//                    o = ((IndirectlySerialized)o).getObject();
//                }
//
//                this.extensions = (Map)o;
//                this.factoryClassLocation = (String)ois.readObject();
//                this.identityToken = (String)ois.readObject();
//                this.numHelperThreads = ois.readInt();
//                this.pcs = new PropertyChangeSupport(this);
//                this.vcs = new VetoableChangeSupport(this);
//                return;
//            default:
//                throw new IOException("Unsupported Serialized Version: " + version);
//        }
//    }
//
//    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
//        return C3P0ImplUtils.PARENT_LOGGER;
//    }
//
//    public String toString() {
//        StringBuffer sb = new StringBuffer();
//        sb.append(super.toString());
//        sb.append(" [ ");
//        sb.append("connectionPoolDataSource -> " + this.connectionPoolDataSource);
//        sb.append(", ");
//        sb.append("dataSourceName -> " + this.dataSourceName);
//        sb.append(", ");
//        sb.append("extensions -> " + this.extensions);
//        sb.append(", ");
//        sb.append("factoryClassLocation -> " + this.factoryClassLocation);
//        sb.append(", ");
//        sb.append("identityToken -> " + this.identityToken);
//        sb.append(", ");
//        sb.append("numHelperThreads -> " + this.numHelperThreads);
//        String extraToStringInfo = this.extraToStringInfo();
//        if (extraToStringInfo != null) {
//            sb.append(extraToStringInfo);
//        }
//
//        sb.append(" ]");
//        return sb.toString();
//    }
//
//    protected String extraToStringInfo() {
//        return null;
//    }
//
//    public Reference getReference() throws NamingException {
//        return referenceMaker.createReference(this);
//    }
//
//    private PoolBackedDataSourceBase() {
//    }
//
//    public PoolBackedDataSourceBase(boolean autoregister) {
//        if (autoregister) {
//            this.identityToken = C3P0ImplUtils.allocateIdentityToken(this);
//            C3P0Registry.reregister(this);
//        }
//
//    }
//
//    static {
//        referenceMaker.setFactoryClassName("com.mchange.v2.c3p0.impl.C3P0JavaBeanObjectFactory");
//        referenceMaker.addReferenceProperty("connectionPoolDataSource");
//        referenceMaker.addReferenceProperty("dataSourceName");
//        referenceMaker.addReferenceProperty("extensions");
//        referenceMaker.addReferenceProperty("factoryClassLocation");
//        referenceMaker.addReferenceProperty("identityToken");
//        referenceMaker.addReferenceProperty("numHelperThreads");
//    }
//}
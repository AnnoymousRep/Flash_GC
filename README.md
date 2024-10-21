## New GCs Found by Flash

### Basic

- dependency : jdk8u20
- All applications for tested are in `applications.zip`
- New attack vectors are recorded in the file `attck_vectors.txt` 

### Modify writeObject process built-in JDK

We find more `writeObject`  method that may lead to exploitation unsuccessful by fuzzing and we use Dynamic Agent to modify the behavior. We also provide the jar file in resources, the usage is like `-javaagent:src/main/resources/agent/xxx.jar`. Here are their functionality ：

- certPath.jar: remove `writeReplace`
- namedNode.jar: directly write fields
- jComponent_equals.jar: triger `equals` easily
- action_equals.jar: triger `equals` easily
- actionMap_equals.jar: triger `equals` easily
- css.jar: trigger `hashCode` easily
- actionMap_hashCode.jar: trigger `hashCode` easily
- jComponent_hashCode.jar: trigger `hashCode` easily
- basicPerms.jar: trigger `interface call` easily

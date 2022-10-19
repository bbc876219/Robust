package robust.gradle.plugin

import com.android.SdkConstants
import com.android.build.api.transform.TransformInput
import javassist.ClassPool
import javassist.CtClass

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.regex.Matcher
/**
 * Created by mivanzhang on 16/11/3.
 */
class ConvertUtils {
    static List<CtClass> toCtClasses(Collection<TransformInput> inputs, ClassPool classPool) {
        List<String> classNames = new ArrayList<>(1024*20)
        List<CtClass> allClass = new ArrayList<>(1024*20);
        def startTime = System.currentTimeMillis()
        inputs.each {
            it.directoryInputs.each {
                def dirPath = it.file.absolutePath
                println "ConvertUtils.toCtClasses.directoryInputs.insertClassPath = ${it.file.absolutePath}"
                classPool.insertClassPath(it.file.absolutePath)
                println "classPool.insertClassPath: ${it.file.absolutePath} "
                org.apache.commons.io.FileUtils.listFiles(it.file, null, true).each {
                    if (it.absolutePath.endsWith(SdkConstants.DOT_CLASS)) {
                        def className = it.absolutePath.substring(dirPath.length() + 1, it.absolutePath.length() - SdkConstants.DOT_CLASS.length()).replaceAll(Matcher.quoteReplacement(File.separator), '.')
                        if (classNames.contains(className)) {
                            Syste.out.println("You have duplicate classes with the same name : " + className + " please remove duplicate classes")
                        } else {
                            println "ConvertUtils.directoryInputs.toCtClasses.insertClassNames = ${className}"
                            classNames.add(className)
                        }

                    }
                }
            }

            it.jarInputs.each {
                classPool.insertClassPath(it.file.absolutePath)
                println "classPool.insertClassPath: ${it.file.absolutePath} "
                println "ConvertUtils.toCtClasses.jarInputs.insertClassPath = ${it.file.absolutePath}"
                def jarFile = new JarFile(it.file)
                Enumeration<JarEntry> classes = jarFile.entries();
                while (classes.hasMoreElements()) {
                    JarEntry libClass = classes.nextElement();
                    String className = libClass.getName();
                    if (className.endsWith(SdkConstants.DOT_CLASS)) {
                        className = className.substring(0, className.length() - SdkConstants.DOT_CLASS.length()).replaceAll('/', '.')
                        if (classNames.contains(className)) {
                            Syste.out.println("You have duplicate classes with the same name : " + className + " please remove duplicate classes ")
                        } else {
                            println "ConvertUtils.jarInputs.toCtClasses.insertClassNames = ${className}"
                            classNames.add(className)
                        }

                    }
                }
            }
        }
        def cost = (System.currentTimeMillis() - startTime) / 1000
        println "read all class file cost $cost second classNames.size=${classNames.size()}"
        classNames.each {
            try {
                allClass.add(classPool.get(it));
            } catch (javassist.NotFoundException e) {
                println "class not found exception class name:  $it "

            }

        }

        Collections.sort(allClass, new Comparator<CtClass>() {
            @Override
            int compare(CtClass class1, CtClass class2) {
                return class1.getName() <=> class2.getName();
            }
        });
        return allClass;
    }


}
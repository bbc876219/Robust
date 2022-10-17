package robust.gradle.plugin.asm;

import com.google.common.base.Preconditions;

public class AsmUtils {
    private AsmUtils() {}

    public static final String CONSTRUCTOR = "<init>";
    public static final String CLASS_INITIALIZER = "<clinit>";

    /**
     * Converts a class name from the Java language naming convention (foo.bar.baz) to the JVM
     * internal naming convention (foo/bar/baz).
     */

    public static String toInternalName( String className) {
        return className.replace('.', '/');
    }

    /**
     * Gets the class name from a class member internal name, like {@code com/foo/Bar.baz:(I)V}.
     */

    public static String getClassName( String memberName) {
        Preconditions.checkArgument(memberName.contains("."), "Class name passed as argument.");
        return memberName.substring(0, memberName.indexOf('.'));
    }
}

package com.aurigo.masterworks.testframework.utilities.annotations;

public @interface TestInfo {

    String[] testIds() default "";

    String[] tags() default "";

    String  downloadPath() default  "";
}

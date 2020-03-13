package com.droider.anno;

import java.net.SocketOption;

@MyAnnoClass
public class MyAnno {
    @MyAnnoField(info = "Hello my friend")
    public String sayWhat;

    @MyAnnoMethod(name = "dorider", age = 26)
    public void outputInfo() {
        System.out.println("My AnnoMethod");
    }
}

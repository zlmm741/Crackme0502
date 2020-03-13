package com.droider.crackme0502;

import android.app.Application;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MyApp extends Application {
    @Override
    @SuppressWarnings("unchecked")
    public void onCreate() {
        try {
            Class<?> c = Class.forName("com.droider.crackme0502.MainActivity");
            // 初始化外部类
            Object o = c.newInstance();
            // 获取内部类
            Class[] cs = c.getClasses();
            Class[] args = new Class[2];
            // 第一个参数为父类引用
            args[0] = com.droider.crackme0502.MainActivity.class;
            // 第二个参数为 String 类型
            args[1] = String.class;
            // 获取构造函数
            Constructor<?> co = cs[0].getConstructor(args);
            // 初始化 SNChecker
            Object classSNChecker = co.newInstance(o, "11111");
            // 获取 isRegistered() 方法
            Method method = cs[0].getDeclaredMethod("isRegistered");
            // 调用 isRegistered() 方法
            boolean bMod = (boolean)method.invoke(classSNChecker);
            // 这个 SN 是错误的，若返回 true 说明程序被修改过
            if (bMod) {
                System.out.println("程序被修改！");
                // 结束程序
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            // 结束程序
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        super.onCreate();
    }
}

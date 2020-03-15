package com.droider.crackme0502;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.droider.anno.MyAnnoClass;
import com.droider.anno.MyAnnoField;
import com.droider.anno.MyAnnoMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private Button btnAnnotation;
    private Button btnCheckSN;
    private EditText edtSN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAnnotation = (Button)findViewById(R.id.btn_annotation);
        btnCheckSN = (Button)findViewById(R.id.btn_checksn);
        edtSN = (EditText)findViewById(R.id.edit_sn);

        btnAnnotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAnnotations();
            }
        });

        btnCheckSN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SNChecker checker = new SNChecker(edtSN.getText().toString());
                String str = checker.isRegistered() ? "注册码正确" : "注册码错误";
                Toast.makeText(MainActivity.this,
                        str,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAnnotations() {
        try {
            Class<?> anno = Class.forName("com.droider.anno.MyAnno");
            if (anno.isAnnotationPresent(MyAnnoClass.class)) {
                MyAnnoClass myAnno = anno.getAnnotation(MyAnnoClass.class);
                Toast.makeText(this,
                        myAnno.value(),
                        Toast.LENGTH_SHORT).show();
            }
            Method method = anno.getMethod("outputInfo", (Class[])null);
            if (method.isAnnotationPresent(MyAnnoMethod.class)) {
                MyAnnoMethod myMethod = method.getAnnotation(MyAnnoMethod.class);
                String str = myMethod.name() + " is " + myMethod.age() + " years old.";
                Toast.makeText(this,
                        str,
                        Toast.LENGTH_SHORT).show();
            }
            Field field = anno.getField("sayWhat");
            if (field.isAnnotationPresent(MyAnnoField.class)) {
                MyAnnoField myField = field.getAnnotation(MyAnnoField.class);
                Toast.makeText(this,
                        myField.info(),
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class SNChecker {
        private String sn;

        public SNChecker(String sn) {
            this.sn = sn;
        }

        public boolean isRegistered() {
            boolean result = false;
            char ch = '\0';
            int sum = 0;
            if (sn == null || sn.length() < 8) {
                return result;
            }
            int len = sn.length();
            if (len == 8) {
                ch = sn.charAt(0);
                switch (ch) {
                    case 'a':
                    case 'f':
                        result = true;
                        break;
                    default:
                        result = false;
                        break;
                }
                if (result) {
                    ch = sn.charAt(3);
                    switch (ch) {
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                            result = true;
                            break;
                        default:
                            result = false;
                            break;
                    }
                }
            }
            else if (len == 16) {
                for (int i = 0; i < len; ++i) {
                    char chPlus = sn.charAt(i);
                    sum += (int)chPlus;
                }
                result = ((sum % 6) == 0) ? true : false;
            }
            return result;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}

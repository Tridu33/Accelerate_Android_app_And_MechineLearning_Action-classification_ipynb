package com.example.tri_axial_accelerometers;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tx1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt1 = (Button) findViewById(R.id.button1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TriAxialSensorActivity.class);
                startActivity(intent);
            }
        });
        Button bt2 = (Button) findViewById(R.id.button2);
        tx1 = (TextView) findViewById(R.id.textView);//Text view添加滚动条设置
        //从系统获得传感器管理器
        final SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str;
                //从传感器管理器中获得全部的传感器列表
                List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);
                int i;
                //给ViewText添加滚动条
                tx1.setMovementMethod(ScrollingMovementMethod.getInstance());
                //显示有多少个传感器
                tx1.setText("经检测该手机有" + allSensors.size() + "个传感器，它们分别是:");
                Sensor s;
                //显示每个传感器的具体信息
                for (i = 0; i < allSensors.size(); i++) {
                    s = allSensors.get(i);
                    str = "设备名称：" + s.getName();
                    switch (s.getType()) {
                        //加速传感器     Sensor.TYPE_ACCELEROMETER
                        case Sensor.TYPE_ACCELEROMETER:
                            tx1.setText(tx1.getText() + "\n" + i + "加速传感器accelerometer:\n" + str);
                            break;
                        //陀螺仪传感器    Sensor.TYPE_GYROSCOPE
                        case Sensor.TYPE_GYROSCOPE:
                            tx1.setText(tx1.getText() + "\n" + i + "陀螺仪传感器gyroscope:\n" + str);
                            break;
                        //环境光仪传感器   Sensor.TYPE_LIGHT
                        case Sensor.TYPE_LIGHT:
                            tx1.setText(tx1.getText() + "\n" + i + "环境光仪传感器light:\n" + str);
                            break;
                        //电磁场传感器    Sensor.TYPE_MAGNETIC_FIELD
                        case Sensor.TYPE_MAGNETIC_FIELD:
                            tx1.setText(tx1.getText() + "\n" + i + "电磁场传感器magnetic:\n" + str);
                            break;
                        //方向传感器    Sensor.TYPE_ORIENTATION:
                        case Sensor.TYPE_ORIENTATION:
                            tx1.setText(tx1.getText() + "\n" + i + "方向传感器orientation:\n" + str);
                            break;
                        //压力传感器     Sensor.TYPE_PRESSURE:
                        case Sensor.TYPE_PRESSURE:
                            tx1.setText(tx1.getText() + "\n" + i + "压力传感器pressure:\n" + str);
                            break;
                        //距离传感器     Sensor.TYPE_PROXIMITY:
                        case Sensor.TYPE_PROXIMITY:
                            tx1.setText(tx1.getText() + "\n" + i + "距离传感器proximity:\n" + str);
                            break;
                        //温度传感器     Sensor.TYPE_TEMPERATURE:
                        case Sensor.TYPE_TEMPERATURE:
                            tx1.setText(tx1.getText() + "\n" + i + "温度传感器temperature:\n" + str);
                            break;

                        default:
                            tx1.setText(tx1.getText() + "\n" + i + "未知传感器:\n" + str);
                            break;
                    }
                }
            }
        });
    }



}
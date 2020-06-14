package com.example.tri_axial_accelerometers;


import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Service;
import android.content.Context;
import android.hardware.SensorEventListener;

import java.math.BigDecimal;


public class TriAxialSensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;//定义传感器管理器
    private Vibrator vibrator;//振动器
    private int mPhone=156;//Sony XZ1手机默认质量156克，如果客户输入重量则更新复制，否则用默认重量
    //private String number="156";
    private double maxValue=0;//初始化合加速度
    private TextView mTxtValue;
    private EditText etmPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tri_axial_sensor);
        mTxtValue = (TextView) findViewById(R.id.txt_value);//实时更新数据mTxtValue
        Button bt2 = (Button) findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);//获取传感器管理器
        vibrator= (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);//括号强制类型转换，获取振动器
    }


    @Override
    protected void onResume() {
        super.onResume();//重写onResume方法
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),sensorManager.SENSOR_DELAY_GAME);// 微传感器加速度注册监听器
        etmPhone = (EditText) findViewById(R.id.mPhoneText);//
        etmPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("mPhone_editText",s.toString());
                mPhone = Integer.parseInt(s.toString().trim());
                Toast.makeText(TriAxialSensorActivity.this, "更新手机重量"+mPhone+"g", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button bt1 = (Button) findViewById(R.id.button1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TriAxialSensorActivity.this, "手机"+mPhone+"g,受力(N)："+maxValue*mPhone, Toast.LENGTH_SHORT).show();
            }
        });
        Button bt3 = (Button) findViewById(R.id.button3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(500);//设置振动器的频率,要先Manifest.xml设置使用权限<uses-permission android:name="android.permission.VIBRATE"></uses-permission>，否则报错不能用
                sensorManager.unregisterListener(TriAxialSensorActivity.this);//取消注册的监听器
                Toast.makeText(TriAxialSensorActivity.this, "手机"+mPhone+"g,受力(N)："+maxValue*mPhone, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        // 取消监听,如果没有点击button3取消注册，则在停止Activity的时候注销
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {//传感器值改变时触发的方法
        int sensorType = event.sensor.getType();//获取传感器的类型保存到int
        if(sensorType== Sensor.TYPE_ACCELEROMETER){
            float[] values=event.values;//获取传感器的值
            StringBuilder sb = new StringBuilder();
            sb.append("X方向的加速度：");
            sb.append(values[0]);
            sb.append("\nY方向的加速度：");
            sb.append(values[1]);
            sb.append("\nZ方向的加速度：");
            sb.append(values[2]);
            mTxtValue.setText(sb.toString());//实时更新数据mTxtValue
            BigDecimal b0 = new BigDecimal(String.valueOf(values[0]));
            double d0 = b0.doubleValue();
            BigDecimal b1 = new BigDecimal(String.valueOf(values[0]));
            double d1 = b1.doubleValue();
            BigDecimal b2 = new BigDecimal(String.valueOf(values[0]));
            double d2 = b2.doubleValue();//float精确转型doble需要先转为BigDecimal
            double newMaxValue = 0.001*Math.sqrt(Math.pow(d0,2)+Math.pow(d1,2)+Math.pow(d2,2));//合加速度
            if(newMaxValue>=maxValue){maxValue=newMaxValue;}//更新maxValues
        }//判断是否是加速度传感器的类型是否对应加速度传感器的常量,更新合加速度maxValues
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {//传感器精度改变时触发的方法

    }
}

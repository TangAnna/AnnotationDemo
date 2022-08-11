package com.tang.demo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tang.annotation.MyClass;
import com.tang.annotation.view.BindView;



@MyClass("Anna")
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_textView)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTextView.setText("MainActivity");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, com.tang.demo.MainActivity2.class));
            }
        });
    }
}
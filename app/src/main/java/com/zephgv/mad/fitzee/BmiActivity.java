package com.zephgv.mad.fitzee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BmiActivity extends AppCompatActivity {

    TextView mbmidisplay,magedisplay,mweightdisplay,mheightdisplay,mbmicategory,mgender, mrangedisplay;
    Button mgotomain;
    Intent intent;
    ImageView mimageview;
    String mbmi;
    String cateogory;
    float intbmi;
    String height;
    String weight;
    float intheight,intweight;
    RelativeLayout mbackground;
    private long backPressed;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);


        intent=getIntent();
        mbmidisplay=findViewById(R.id.bmidisplay);
        mbmicategory = findViewById(R.id.bmicategorydispaly);
        mgotomain=findViewById(R.id.gotomain);
        mrangedisplay=findViewById(R.id.rangedisplay);

        mimageview=findViewById(R.id.imageview);
        mgender=findViewById(R.id.genderdisplay);
        mbackground=findViewById(R.id.contentlayout);


        height=intent.getStringExtra("height");
        weight=intent.getStringExtra("weight");


        intheight=Float.parseFloat(height);
        intweight=Float.parseFloat(weight);

        intheight=intheight/100;
        intbmi=intweight/(intheight*intheight);


        mbmi=Float.toString(intbmi);
        System.out.println(mbmi);

        if(intbmi<16)
        {
            mbmicategory.setText("Severe Thinness");
            mrangedisplay.setText("BMI<16");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.crosss);

        }
        else if(intbmi<16.9 && intbmi>16)
        {
            mbmicategory.setText("Moderate Thinness");
            mrangedisplay.setText("16<BMI<16.9");
            mbackground.setBackgroundColor(R.color.halfwarn);
            mimageview.setImageResource(R.drawable.warning);

        }
        else if(intbmi<18.4 && intbmi>17)
        {
            mbmicategory.setText("Mild Thinness");
            mrangedisplay.setText("17<BMI<18.4");
            mbackground.setBackgroundColor(R.color.halfwarn);
            mimageview.setImageResource(R.drawable.warning);
        }
        else if(intbmi<24.9 && intbmi>18.5 )
        {
            mbmicategory.setText("Normal");
            mrangedisplay.setText("18.5<BMI<24.9");
            mimageview.setImageResource(R.drawable.ok);
        }
        else if(intbmi <29.9 && intbmi>25)
        {
            mbmicategory.setText("Overweight");
            mrangedisplay.setText("25<BMI<29.9");
            mbackground.setBackgroundColor(R.color.halfwarn);
            mimageview.setImageResource(R.drawable.warning);
        }
        else if(intbmi<34.9 && intbmi>30)
        {
            mbmicategory.setText("Obese Class I");
            mrangedisplay.setText("30<BMI<34.9");
            mbackground.setBackgroundColor(R.color.halfwarn);
            mimageview.setImageResource(R.drawable.warning);
        }
        else
        {
            mbmicategory.setText("Obese Class II");
            mrangedisplay.setText("35<BMI");
            mbackground.setBackgroundColor(R.color.warn);
            mimageview.setImageResource(R.drawable.crosss);
        }

        mgender.setText(intent.getStringExtra("gender"));
        mbmidisplay.setText(mbmi);


        mgotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),BmicalcActivity.class);
                startActivity(intent1);
            }
        });

    }
}
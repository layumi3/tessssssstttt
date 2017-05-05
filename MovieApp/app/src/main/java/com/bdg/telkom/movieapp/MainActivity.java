package com.bdg.telkom.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.inputsatu)
    EditText inputsatu;
    @BindView(R.id.inputdua)
    EditText inputdua;
    @BindView(R.id.btn_total)
    Button btnTotal;
    @BindView(R.id.result)
    TextView result;

    private int valueInput1, valueInput2, valueResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_total)
    protected void hitung(){
        //convert text input (String) menjadi int
        valueInput1 = Integer.parseInt(inputsatu.getText().toString());
        valueInput2 = Integer.parseInt(inputdua.getText().toString());

        //menjumlahkan input 1 dan input 2
        valueResult = valueInput1 + valueInput2;

        //tampilkan hasil perhitungan
        result.setText(inputsatu.getText().toString() + " + " + inputdua.getText().toString() + " = " + valueResult);

        //reset input 1 dan input 2
        inputsatu.setText("");
        inputdua.setText("");
    }
}

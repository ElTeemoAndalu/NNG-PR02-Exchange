package es.iessaldillo.nicol.exchange;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private EditText txtAmount;
    private RadioGroup rgFromCurrency,rgToCurrency;
    private Button btnExchange;
    private RadioButton rbFromEuro,rbToEuro,rbToDollar,rbFromDollar,rbFromPound,rbToPound;
    private double changeDollar,changeEuro,changePound,result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        txtAmount = ActivityCompat.requireViewById(this,R.id.txtAmount);
        rgFromCurrency = ActivityCompat.requireViewById(this,R.id.rgFromCurrency);
        rgToCurrency = ActivityCompat.requireViewById(this,R.id.rgToCurrency);
        btnExchange = ActivityCompat.requireViewById(this,R.id.btnExchange);
        rbFromEuro = ActivityCompat.requireViewById(this,R.id.rbFromEuro);
        rbToEuro = ActivityCompat.requireViewById(this,R.id.rbToEuro);
        rbFromDollar = ActivityCompat.requireViewById(this,R.id.rbFromDollar);
        rbToDollar = ActivityCompat.requireViewById(this,R.id.rbToDollar);
        rbFromPound = ActivityCompat.requireViewById(this,R.id.rbFromPound);
        rbToPound = ActivityCompat.requireViewById(this,R.id.rbToPound);

        rgFromCurrency.setOnCheckedChangeListener(this);
        rgToCurrency.setOnCheckedChangeListener(this);
        btnExchange.setOnClickListener(v -> exchange());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if(group.getId() == rgFromCurrency.getId()){
            if (checkedId == rbFromEuro.getId()) {
                updateRadioButtons(rbToEuro,rgToCurrency);
            } else if(checkedId == rbFromDollar.getId()) {
                updateRadioButtons(rbToDollar,rgToCurrency);
            } else {
                updateRadioButtons(rbToPound,rgToCurrency);
            }

        }else{
            if (checkedId == rbToEuro.getId()) {
                updateRadioButtons(rbFromEuro,rgFromCurrency);
            } else if(checkedId == rbToDollar.getId()) {
                updateRadioButtons(rbFromDollar,rgFromCurrency);
            } else {
                updateRadioButtons(rbFromPound,rgFromCurrency);
            }
        }



    }


    private void updateRadioButtons(RadioButton radiobutton, RadioGroup group) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if(!group.getChildAt(i).isEnabled()){
                    group.getChildAt(i).setEnabled(true);
                    break;
                }
            }
        radiobutton.setEnabled(false);

    }



    private void exchange() {

    }
}

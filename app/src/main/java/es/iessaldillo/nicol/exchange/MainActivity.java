package es.iessaldillo.nicol.exchange;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private EditText txtAmount;
    private RadioGroup rgFromCurrency,rgToCurrency;
    private Button btnExchange;
    private RadioButton rbFromEuro,rbToEuro,rbToDollar,rbFromDollar,rbFromPound,rbToPound;
    private double changeEuroDollar,changeEuroPound, changeDollarPound;
    private ImageView imgFrom,imgTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        establishCurrencyRates();
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
        imgFrom = ActivityCompat.requireViewById(this,R.id.imgFrom);
        imgTo = ActivityCompat.requireViewById(this,R.id.imgTo);

        imgFrom.setImageResource(R.drawable.ic_euro);
        imgTo.setImageResource(R.drawable.ic_dollar);

        rgFromCurrency.setOnCheckedChangeListener(this);
        rgToCurrency.setOnCheckedChangeListener(this);
        btnExchange.setOnClickListener(v -> exchange());
    }

    private void establishCurrencyRates() {
        changeEuroDollar = 1.16;
        changeEuroPound = 0.89;
        changeDollarPound = 0.77;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if(group.getId() == rgFromCurrency.getId()){

            if (checkedId == rbFromEuro.getId()) {
                updateRadioButtons(rbToEuro,rgToCurrency);
                imgFrom.setImageResource(R.drawable.ic_euro);

            } else if(checkedId == rbFromDollar.getId()) {
                updateRadioButtons(rbToDollar,rgToCurrency);
                imgFrom.setImageResource(R.drawable.ic_dollar);

            } else {
                updateRadioButtons(rbToPound,rgToCurrency);
                imgFrom.setImageResource(R.drawable.ic_pound);
            }

        }else{
            if (checkedId == rbToEuro.getId()) {
                updateRadioButtons(rbFromEuro,rgFromCurrency);
                imgTo.setImageResource(R.drawable.ic_euro);

            } else if(checkedId == rbToDollar.getId()) {
                updateRadioButtons(rbFromDollar,rgFromCurrency);
                imgTo.setImageResource(R.drawable.ic_dollar);

            } else {
                updateRadioButtons(rbFromPound,rgFromCurrency);
                imgTo.setImageResource(R.drawable.ic_pound);

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

        int fromRbChecked,rbToChecked;
        double result;
        String toastText;

        result = Double.parseDouble(txtAmount.getText().toString());
        toastText = "" + result;

        fromRbChecked = rgFromCurrency.getCheckedRadioButtonId();
        rbToChecked = rgToCurrency.getCheckedRadioButtonId();

        if(fromRbChecked == rbFromEuro.getId() && rbToChecked == rbToDollar.getId()){
            result *= changeEuroDollar;
        }else if(fromRbChecked == rbFromEuro.getId() && rbToChecked == rbToPound.getId()){
            result *= changeEuroPound;
        }else if(fromRbChecked == rbFromDollar.getId() && rbToChecked == rbToEuro.getId()){
            result /= changeEuroDollar;
        }else if(fromRbChecked == rbFromDollar.getId() && rbToChecked == rbToPound.getId()){
            result *= changeDollarPound;
        }else if(fromRbChecked == rbFromPound.getId() && rbToChecked == rbToEuro.getId()){
            result /= changeEuroPound;
        }else{
            result /= changeDollarPound;
        }

        result = Math.round(result * 100.0) / 100.0;

        toastText += " -> " + result;

        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();

    }
}

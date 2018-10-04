package es.iessaldillo.nicol.exchange;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private EditText txtAmount;
    private RadioGroup rgFromCurrency,rgToCurrency;
    // SI SOLO LO USAS EN UN ÚNICO MÉTODO DEFÍNELO COMO LOCAL.
    private Button btnExchange;
    private RadioButton rbFromEuro,rbToEuro,rbToDollar,rbFromDollar,rbFromPound,rbToPound;
    private double changeEuroDollar,changeEuroPound, changeDollarPound;
    private ImageView imgFrom,imgTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se inicializan las vistas y los valores de cambio de moneda
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

        rgFromCurrency.setOnCheckedChangeListener(this);
        rgToCurrency.setOnCheckedChangeListener(this);
        btnExchange.setOnClickListener(v -> exchange());
        txtAmount.setOnClickListener(v -> txtAmount.selectAll());
        // AGREGO ESTAS LÍNEAS PARA QUE PASE TESTS.
        imgFrom.setTag(R.drawable.ic_euro);
        imgTo.setTag(R.drawable.ic_dollar);
    }

    //Establece los valores que se usarán para el calculo de cambio de moneda
    //En el futuro esta parte se cambiará por valores extraídos de internet
    // y se actualizarán con el inicio de la aplicación.
    private void establishCurrencyRates() {
        changeEuroDollar = 1.16;
        changeEuroPound = 0.89;
        changeDollarPound = 0.77;
    }

    // IN ENGLISH, PLEASE
    //Simplemente activa los radio botones previamente desactivados
    // y desactiva los de las monedas seleccionadas con cada cambio (a través de otra función).
    //Y también va alternando entre los distintos símbolos de las monedas seleccionadas
    // en cada grupo cuando se hacen cambios.
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        // USA Code -> Reformat Code PARA QUE EL CÓDIGO QUEDE BONITO.
        if(group.getId() == rgFromCurrency.getId()){

            if (checkedId == rbFromEuro.getId()) {
                updateRadioButtons(rbToEuro,rgToCurrency);
                imgFrom.setImageResource(R.drawable.ic_euro);
                // AGREGO ESTA LÍNEA PARA PASE TESTS
                imgFrom.setTag(R.drawable.ic_euro);

            } else if(checkedId == rbFromDollar.getId()) {
                updateRadioButtons(rbToDollar,rgToCurrency);
                imgFrom.setImageResource(R.drawable.ic_dollar);
                // AGREGO ESTA LÍNEA PARA PASE TESTS
                imgFrom.setTag(R.drawable.ic_dollar);

            } else {
                updateRadioButtons(rbToPound,rgToCurrency);
                imgFrom.setImageResource(R.drawable.ic_pound);
                // AGREGO ESTA LÍNEA PARA PASE TESTS
                imgFrom.setTag(R.drawable.ic_pound);
            }

        }else{
            if (checkedId == rbToEuro.getId()) {
                updateRadioButtons(rbFromEuro,rgFromCurrency);
                imgTo.setImageResource(R.drawable.ic_euro);
                // AGREGO ESTA LÍNEA PARA PASE TESTS
                imgTo.setTag(R.drawable.ic_euro);

            } else if(checkedId == rbToDollar.getId()) {
                updateRadioButtons(rbFromDollar,rgFromCurrency);
                imgTo.setImageResource(R.drawable.ic_dollar);
                // AGREGO ESTA LÍNEA PARA PASE TESTS
                imgTo.setTag(R.drawable.ic_dollar);

            } else {
                updateRadioButtons(rbFromPound,rgFromCurrency);
                imgTo.setImageResource(R.drawable.ic_pound);
                // AGREGO ESTA LÍNEA PARA PASE TESTS
                imgTo.setTag(R.drawable.ic_pound);
            }
        }

    }

    // ME GUSTA MUCHO ESTA IDEA.
    //Subfunción de la anterior. Detecta el botón desactivado del grupo que se le pase, lo activa y finalmente
    //desactiva el botón que también se le pasa, que será el que se ha seleccionado en el grupo opuesto
    private void updateRadioButtons(RadioButton radiobutton, RadioGroup group) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if(!group.getChildAt(i).isEnabled()){
                    group.getChildAt(i).setEnabled(true);
                    break;
                }
            }

        radiobutton.setEnabled(false);

    }

    //Detecta que monedas han sido seleccionadas en los dos grupos disponibles, realiza el cálculo en función
    //de ello y finalmente muestra un mensaje con el resultado.
    private void exchange() {

        int fromRbChecked,rbToChecked;
        double result;
        String toastText,stringOftxtAmount,defaultAmount;

        stringOftxtAmount = txtAmount.getText().toString();
        defaultAmount = "0.00";

        // USA MEJOR TextUtils.equals EN VEZ DE equals(). SE CONTROLA AUTOMÁTICAMENTE EL NULL.
        // LA COMPROBACIÓN PUEDES HACER SIMPLEMENTE CON UN try catch sobre el método Double
        // .parseDouble()
        if (!stringOftxtAmount.equals(".") && !stringOftxtAmount.equals("")) {
            result = convertStrToDbl(txtAmount.getText().toString());
            toastText = "" + result;

            fromRbChecked = rgFromCurrency.getCheckedRadioButtonId();
            rbToChecked = rgToCurrency.getCheckedRadioButtonId();

            if(fromRbChecked == rbFromEuro.getId()){
                // NO USES NÚMERO MÁGICOS. DEFINE CONSTANTES.
                toastText += "\u20ac";
                if(rbToChecked == rbToDollar.getId()){
                    result *= changeEuroDollar;
                }else{
                    result *= changeEuroPound;
                }
            }else if(fromRbChecked == rbFromDollar.getId()){
                toastText += "\u0024";
                if(rbToChecked == rbToEuro.getId()){
                    result /= changeEuroDollar;
                }else{
                    result *= changeDollarPound;
                }
            }else{
                toastText += "\u00a3";
                if(rbToChecked == rbToEuro.getId()){
                    result /= changeEuroPound;
                }else{
                    result /= changeDollarPound;
                }
            }

            result = Math.round(result * 100.0) / 100.0;
            ;
            toastText += " -> " + result;

            if (rbToChecked == rbToEuro.getId())
                toastText += "\u20ac";
            else if (rbToChecked == rbToDollar.getId())
                toastText += "\u0024";
            else
                toastText += "\u00a3";
        }else{
            toastText = "It is not a valid number. Resetting value.";
            txtAmount.setText(defaultAmount);
        }


        if (!stringOftxtAmount.equals("0.00")) {
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
        }

    }

    //Función para acortar el cambio de cadena a double
    public double convertStrToDbl(String string){
        return Double.parseDouble(string);
    }
}

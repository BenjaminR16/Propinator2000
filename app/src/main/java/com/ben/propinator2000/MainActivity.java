package com.ben.propinator2000;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView labelNumber;
    private RadioGroup radioGroup;
    private RadioButton radioButtonMalo, radioButtonBueno, radioButtonExcelente;
    private TextView labelPropina;

    private String numberString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        labelNumber = findViewById(R.id.labelNumber);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonMalo = findViewById(R.id.radioButtonMalo);
        radioButtonBueno = findViewById(R.id.radioButtonBueno);
        radioButtonExcelente = findViewById(R.id.radioButtonExcelente);
        labelPropina = findViewById(R.id.labelPropina);

        //configurancion de los botones en un metodo y un array
        setUpPadButtons();


        Button buttonCalcular = findViewById(R.id.buttonCalcular);
        buttonCalcular.setOnClickListener(v -> calculateTip());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //metodo con array de botones
    private void setUpPadButtons() {
        Button[] numberButtons = new Button[]{
                findViewById(R.id.button0), findViewById(R.id.button1),
                findViewById(R.id.button2), findViewById(R.id.button3),
                findViewById(R.id.button4), findViewById(R.id.button5),
                findViewById(R.id.button6), findViewById(R.id.button7),
                findViewById(R.id.button8), findViewById(R.id.button9),
                findViewById(R.id.buttonClear)
        };

        for (Button button : numberButtons) {
            button.setOnClickListener(this::onPadButtonClick);
        }
    }

    //click bottones y anadir numero
    private void onPadButtonClick(View view) {
        Button button = (Button) view;
        String text = button.getText().toString();

        if (text.equals("Borrar")) {
            if (numberString.length() > 0) {
                numberString = numberString.substring(0, numberString.length() - 1);
                labelNumber.setText(numberString.isEmpty() ? "0" : numberString);
            }
        } else {
            numberString += text;
            labelNumber.setText(numberString);
        }
    }


    private void calculateTip() {
        if (numberString.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa un precio.", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(numberString);
        double tipPercentage = 0;

        //porcentaje del trato para la protina
        if (radioButtonMalo.isChecked()) {
            tipPercentage = 0.1;
        } else if (radioButtonBueno.isChecked()) {
            tipPercentage = 0.15;
        } else if (radioButtonExcelente.isChecked()) {
            tipPercentage = 0.2;
        } else {
            Toast.makeText(this, "Por favor selecciona un trato.", Toast.LENGTH_SHORT).show();
            return;
        }

        //propina
        double tip = price * tipPercentage;
        labelPropina.setText("Propina: $" + String.format("%.2f", tip));
    }


}






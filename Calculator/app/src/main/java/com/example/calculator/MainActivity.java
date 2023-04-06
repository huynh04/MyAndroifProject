package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.mozilla.javascript.*;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView result, problem;
    MaterialButton button_c, button_open_braket, button_close_braket,button_ac;
    MaterialButton button_divile, button_multiply, button_sub,button_add,button_equals, button_dot;
    MaterialButton button_0,button_1,button_2,button_3,button_4,button_5,button_6,button_7,button_8,
            button_9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        problem = findViewById(R.id.problem);
        assign(button_c,R.id.button_c);
        assign(button_open_braket,R.id.button_open_braket);
        assign(button_close_braket,R.id.button_close_bracket);
        assign(button_ac,R.id.button_ac);
        assign(button_divile,R.id.button_divile);
        assign(button_multiply,R.id.button_multiply);
        assign(button_sub,R.id.button_sub);
        assign(button_add,R.id.button_add);
        assign(button_dot,R.id.button_dot);
        assign(button_equals,R.id.button_equals);
        assign(button_0,R.id.button_0);
        assign(button_1,R.id.button_1);
        assign(button_2,R.id.button_2);
        assign(button_3,R.id.button_3);
        assign(button_4,R.id.button_4);
        assign(button_5,R.id.button_5);
        assign(button_6,R.id.button_6);
        assign(button_7,R.id.button_7);
        assign(button_8,R.id.button_8);
        assign(button_9,R.id.button_9);
    }

    void assign(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String datatoCalculator = problem.getText().toString().concat(String.valueOf(buttonText));
        if (buttonText.equals("AC")){
            problem.setText("");
            result.setText("0");
            return;
        }

        if (buttonText.equals("=")){
            problem.setText(result.getText());
            return;
        }

        if (buttonText.equals("C")) {
            datatoCalculator = datatoCalculator.substring(0, problem.getText().length() - 1);
        }
        problem.setText(datatoCalculator);

        String finalResult = getResult(datatoCalculator);
        if (!finalResult.equals("Error")){
            result.setText(finalResult);
        }
    }

    String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;

        }catch (Exception e){
            return  "Error";
        }

    }
}

package com.example.columnpicker;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.columnpicker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getPickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(binding.setMax.getText().toString().isEmpty()) && !(binding.setMin.getText().toString().isEmpty())) {
                    intializePicker();
                    binding.numberPicker2.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(MainActivity.this, "Set both values to see picker", Toast.LENGTH_SHORT).show();
                }
            }
        });



        binding.normalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.reverseBtn.setChecked(false);
                binding.numberPicker2.setVisibility(View.GONE);
                binding.numberPicker.setVisibility(View.GONE);
            }
        });

        binding.reverseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.normalBtn.setChecked(false);
                binding.numberPicker.setVisibility(View.GONE);
                binding.numberPicker2.setVisibility(View.GONE);
            }
        });

        binding.showVal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    binding.numberPicker.setVisibility(View.VISIBLE);
                }
                else {
                    binding.numberPicker.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    //To intialize the picker
    private void intializePicker() {
        binding.numberPicker2.setMinValue(Integer.valueOf(binding.setMin.getText().toString()));
        binding.numberPicker2.setMaxValue((Integer.valueOf(binding.setMax.getText().toString())));
        binding.numberPicker.setMinValue(Integer.valueOf(binding.setMin.getText().toString()));
        binding.numberPicker.setMaxValue((Integer.valueOf(binding.setMax.getText().toString())));


        binding.numberPicker2.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                String ans;
                if (binding.reverseBtn.isChecked()) {
                    /*binding.normalBtn.setChecked(false)*/;
                    ans = ReverseColName(value);
                    return ans;
                } else {
                    binding.reverseBtn.setChecked(false);
                    ans = ColName(value);
                    return  ans;
                }
            }
        });
        View firstItem = binding.numberPicker2.getChildAt(0);
        if (firstItem != null) {
            firstItem.setVisibility(View.INVISIBLE);
        }
        binding.numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                binding.numberPicker2.getValue();
                binding.numberPicker.setValue(newVal);
            }
        });
        binding.numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                binding.numberPicker.getValue();
                binding.numberPicker2.setValue(newVal);
            }
        });


    }
    //To get coloum name in order
    private String ColName(int col) {
        StringBuilder CV = new StringBuilder();
        while (col > 0) {
            int rem = col % 26;
            if (rem == 0) {
                CV.append('Z');
                col = (col / 26) - 1;
            } else {
                CV.append((char) (64 + rem));
                col /= 26;
            }
        }
        return CV.reverse().toString();
    }
    //To get coloum name in reverse order
    private String ReverseColName(int col) {
        StringBuilder CV = new StringBuilder();
        while (col > 0) {
            int rem = col % 26;
            if (rem == 0) {
                CV.append('A');
                col = (col / 26) - 1;
            } else {
                CV.append((char) (65 + 26 - rem));
                col /= 26;
            }
        }
        return CV.reverse().toString();
    }
}
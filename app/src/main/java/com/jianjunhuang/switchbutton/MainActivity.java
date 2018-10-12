package com.jianjunhuang.switchbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jianjunhuang.lib.switchbutton.Status;
import com.jianjunhuang.lib.switchbutton.SwitchButton;

public class MainActivity extends AppCompatActivity {
  int index = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final SwitchButton switchButton = findViewById(R.id.test_btn);
    switchButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(MainActivity.this,""+index,Toast.LENGTH_SHORT).show();
        switch (index) {
          case 0:
            switchButton.switchStatus(Status.INVALID,true);
            index = 1;
            break;
          case 1:
            switchButton.switchStatus(Status.DEFAULT,true);
            index = 3;
            break;
          case 3:
            switchButton.switchStatus(Status.CHECKED,true);
            index = 0;
            break;
        }
      }
    });
  }
}

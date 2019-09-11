package pilnik.belstu.lab2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int selectWay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
    public void Pisos(View v){
        GetResult();
    }
    public int GetSex(){
        RadioGroup sex = (RadioGroup)findViewById(R.id.group_sex);
        switch (sex.getCheckedRadioButtonId()){
            case R.id.radioButton:
                selectWay = 1;
                break;
            case R.id.radioButton2:
                selectWay = 2;
                break;
        }
        return selectWay;
    }

    public double AMR(){
        Spinner lifestyle = (Spinner)findViewById(R.id.spinner);
        double k=0;
        int text = (int)lifestyle.getSelectedItemId();

        switch (text){
            case 1:
              k =  1.2;
              break;
            case 2:
                k = 1.375;
                break;
            case 3:
                k = 1.55;
                break;
            case 4:
                k = 1.725;
                break;
            case 5:
                k= 1.9;
                break;
        }
        return k;
    }

    public double BMR(){
        double BMR=0;
        int sex = GetSex();
        EditText weight = (EditText)findViewById(R.id.weight);
        EditText height = (EditText)findViewById(R.id.height);
        EditText age = (EditText)findViewById(R.id.age);
        if(sex == 1){
            BMR = 655.0955 + 9.5634*Integer.parseInt(weight.getText().toString())+1.8496*Integer.parseInt(height.getText().toString())-4.6756*Integer.parseInt(age.getText().toString());
        }
        else{
            BMR = 64.4730 + 13.7516*Integer.parseInt(weight.getText().toString())+5.0033*Integer.parseInt(height.getText().toString())-6.7550*Integer.parseInt(age.getText().toString());
        }
        return BMR;
    }
    public void GetResult(){
        TextView result = (TextView)findViewById(R.id.result);
        double resultt = AMR()*BMR();
        result.setText(String.valueOf(Math.round(resultt)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

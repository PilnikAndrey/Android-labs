package pilnik.belstu.lab3;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "reminderss.json";
    private EditText textEdit;
    private List<EventModel> notes;
    private CalendarView calendar;
    private String date;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notes = ReadEventList();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CalendarView calendarView = findViewById(R.id.calendarView);
        textView = findViewById(R.id.textView);
        textEdit = findViewById(R.id.editText);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                String text = "";
                String dateValue = String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year);
                textView.setText(dateValue);
                EventModel model;
                Boolean textexist = false;
                for (int i = 0; i < notes.size(); i++) {
                    model = notes.get(i);

                    if (model.dateEvent.equals(dateValue)) {
                        textEdit.setText(model.event);
                        textView.setText(model.dateEvent);
                        textexist = true;
                    }

                }
                if(textexist == false){
                    textEdit.setText("");
                }
            }
        });
    }

    public void delete(View v){
        for (int i = 0; i < notes.size(); i++) {
            EventModel model = notes.get(i);

            if (model.dateEvent.equals(textView.getText().toString())) {
                notes.remove(i);
            }
            else
            {
                textEdit.getText().clear();
            }
        }

        SaveEventList(notes);
    }

    public void save(View v) {
        EventModel newEvent = new EventModel(
                textView.getText().toString(),
                textEdit.getText().toString()
        );

        notes.add(newEvent);

        SaveEventList(notes);
        textEdit.getText().clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<EventModel> ReadEventList() {
        Gson gson = new Gson();
        String filePath = GetJsonFilePath();
        String json = ReadFile(filePath);

        if (json == null) {
            return new ArrayList<EventModel>();
        }

        Type collectionType = new TypeToken<List<EventModel>>(){}.getType();
        return gson.fromJson(json, collectionType);
    }

    private void SaveEventList(List<EventModel> eventList) {
        Gson gson = new Gson();
        String filePath = GetJsonFilePath();
        String json = gson.toJson(eventList);

        SaveFile(filePath, json);
    }

    private void SaveFile(String path, String text) {
        try {
            File myFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(myFile, false);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String ReadFile(String filePath) {
        String text = null;

        try {
            File file = new File(filePath);
            if (file.exists()) {
                StringBuilder stringBuilder = new StringBuilder();
                InputStream inputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                while ((text = bufferedReader.readLine()) != null) {
                    stringBuilder.append(text);
                }
                return stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return text;
    }

    private String GetJsonFilePath() {
        return MessageFormat.format("{0}/{1}", getApplicationContext().getFilesDir(), FILE_NAME);
    }
}

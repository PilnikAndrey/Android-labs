package pilnik.belstu.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Add_info extends AppCompatActivity {


    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    EditText email;
    EditText location;
    EditText number;
    EditText link;
    Button delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        email = findViewById(R.id.editText);
        location = findViewById(R.id.editText2);
        number = findViewById(R.id.editText3);
        link = findViewById(R.id.editText4);
        delButton = findViewById(R.id.delete);
        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            email.setText(userCursor.getString(1));
            location.setText(userCursor.getString(2));
            number.setText(String.valueOf(userCursor.getInt(3)));
            link.setText(userCursor.getString(4));


            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }
    public void Back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        // Закрываем подключение и курсор
        db.close();

    }

    public void Phone(View view){
        EditText number= findViewById(R.id.editText);
        String toDial="tel:"+number.getText().toString();
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(toDial)));
    }

    public void Email(View view){
        EditText number= findViewById(R.id.editText3);
        String email=number.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.putExtra(Intent.EXTRA_TEXT, "mail body");
        startActivity(Intent.createChooser(intent, ""));
    }

    public void Maps(View view){
        EditText location = findViewById(R.id.editText2);
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=1600 "+location.getText().toString());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void Link(View view){
        EditText number= findViewById(R.id.editText4);
        String link=number.getText().toString();
        Intent browserIntent = new  Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }
    public void Add(View view){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_EMAIL, email.getText().toString());
        cv.put(DatabaseHelper.COLUMN_LOCATION, location.getText().toString());
        cv.put(DatabaseHelper.COLUMN_PHONE_NUMBER, Integer.parseInt(number.getText().toString()));
        cv.put(DatabaseHelper.COLUMN_LINK, link.getText().toString());


        if (userId > 0) {
            db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + String.valueOf(userId), null);
        } else {
            db.insert(DatabaseHelper.TABLE, null, cv);
        }
        Back(view);
    }
    public void delete(View view){
        db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
        Back(view);
    }
    @Override
    public void onPause() {

        super.onPause();
    }
}

package net.ivanvega.contentproviderscontactscurso;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity {
    Cursor c;
    ListView lsv;
    EditText txt;

    String[] columnas = new String[]{UserDictionary.Words.APP_ID,
            UserDictionary .Words._ID,
            UserDictionary.Words.WORD,
            UserDictionary.Words.FREQUENCY ,
            UserDictionary.Words.LOCALE
    };


    



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lsv = (ListView)findViewById(R.id.lsv);
        txt = (EditText)findViewById(R.id.txt);

        c = getContentResolver().query
                (ContactsContract.Contacts.CONTENT_URI, new String[]{
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts._ID
                }, null, null, null);

        SimpleCursorAdapter adp = new SimpleCursorAdapter(this,
                android.R.layout .simple_list_item_2,
                c,new String[]{
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts._ID
        },
        new int[]{android.R.id.text1, android.R.id.text2},
                Adapter.IGNORE_ITEM_VIEW_TYPE
        );

        lsv.setAdapter(adp);


    }

    public void btnConsultar_click(View v){
        c = getContentResolver().query
                (UserDictionary.Words.CONTENT_URI,columnas,null,null,null);

        SimpleCursorAdapter adp = new SimpleCursorAdapter(
                this,
                android.R.layout .simple_list_item_2,
                c, new String[]{
                    UserDictionary .Words.WORD,
                UserDictionary .Words.LOCALE},
                new int[]
                        {android.R.id.text1,
                                android.R.id.text2},
                Adapter.IGNORE_ITEM_VIEW_TYPE

                );

        lsv.setAdapter(adp);


    }

    public void btnInsert_click(View v){
        ContentValues cv = new ContentValues();
        cv.put(UserDictionary.Words.WORD,
                txt.getText().toString());
        cv.put(UserDictionary.Words.LOCALE,
                "es_US");
            cv.put(UserDictionary.Words.FREQUENCY,
                    100);

        Uri uri = getContentResolver().
                insert(
                        UserDictionary.Words.CONTENT_URI,
                        cv);
        Log.i("MIURICONTENT", uri.toString());
    }

    public void btnUpdate_click(View v){
        ContentValues cv = new ContentValues();
        cv.put(UserDictionary.Words.WORD,
                txt.getText().toString());

        getContentResolver().update(UserDictionary.Words.CONTENT_URI,
                cv,
                UserDictionary.Words._ID + "=?", new String[]{"16"});


    }

    public void btnDelete_click(View v){
        getContentResolver() .delete(UserDictionary.Words.CONTENT_URI,
                UserDictionary.Words._ID + "=3", null);

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

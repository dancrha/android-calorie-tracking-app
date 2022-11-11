package com.example.androidapplications;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends AppCompatActivity {
    final String logTAG = "ChatWindow";
    ArrayList<String> sentArray = new ArrayList<>();
    // Database fields
    private SQLiteDatabase database;
    private ChatDatabaseHelper dbOpenHelper = new ChatDatabaseHelper(this);
    private String[] columns = {ChatDatabaseHelper.KEY_ID,
            ChatDatabaseHelper.KEY_MESSAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);


        ListView listview = (ListView) findViewById(R.id.listviewid);
        EditText edittext = (EditText) findViewById(R.id.sendedittextid);
        Button sendbtn = (Button) findViewById(R.id.sendbuttonid);
        ChatAdapter messageAdapter = new ChatAdapter(this);
        listview.setAdapter(messageAdapter);

        sendbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = edittext.getText().toString();
                        sentArray.add(text);
                        messageAdapter.notifyDataSetChanged(); //restarts process of getCount()/GetView()
                        edittext.setText("");

                        database = dbOpenHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(ChatDatabaseHelper.KEY_MESSAGE, text);
                        long insert_message = database.insert(ChatDatabaseHelper.TABLE_Of_My_ITEMS, null, values);
                        Cursor c = database.query(ChatDatabaseHelper.TABLE_Of_My_ITEMS, columns,
                                ChatDatabaseHelper.KEY_MESSAGE +
                                "=" + insert_message, null, null, null,null );
                        c.moveToFirst();
                        c.close();
                    }

                }
        );
        database = dbOpenHelper.getReadableDatabase();

        Cursor cursor = database.query(ChatDatabaseHelper.TABLE_Of_My_ITEMS,
                columns, null, null,
                null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            @SuppressLint("Range")
            String mess = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
            Log.i(logTAG, "Cursor's column count = " + cursor.getColumnCount());
            sentArray.add(mess);
            cursor.moveToNext();

            for(int i = 0; i < cursor.getColumnCount(); i++){
                System.out.println(cursor.getColumnName(i));
            }
        }
        cursor.close();
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            int count = sentArray.size();
            return count;
        }

        public String getItem(int position) {
            return sentArray.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;

            if (position%2 == 0) result = inflater.inflate(R.layout.chat_row_outgoing, null);
            //Changed the order of chat_row_incoming and outgoing to make the first message appear
                // as outgoing, as shown in the screenshot in the assignment
            else result = inflater.inflate(R.layout.chat_row_incoming, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;

            }
        }








    protected void onDestroy(){
        super.onDestroy();
        Log.i("ChatDatabaseHelper", "In onDestroy()");
        database.close();
    }
    }

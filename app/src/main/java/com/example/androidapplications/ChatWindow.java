package com.example.androidapplications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    final String logTAG = "ChatWindow";
    ArrayList<String> sentArray = new ArrayList<>();

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
                        sentArray.add(edittext.getText().toString());
                        messageAdapter.notifyDataSetChanged(); //restarts process of getCount()/GetView()
                        edittext.setText("");


                    }
                }
        );


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

    }

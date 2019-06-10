package com.infinacle.chat.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.infinihelpdesk.utils.InfiniHelpDesk;
import com.infinihelpdesk.utils.InfiniHelpDeskIntent;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton sendFab;
    Activity activity;
    Intent intent;
    final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        sendFab = findViewById(R.id.sendFab);
        sendFab.setOnClickListener(view -> {
            String idReference = createIdReference(activity);

            InfiniHelpDeskIntent infiniDesk = new InfiniHelpDeskIntent();
            infiniDesk.setIdRef(idReference);
            infiniDesk.setIdRefType(Constant.ID_REFERENCE_TYPE);
            infiniDesk.setApiKey(Constant.API_KEY);
            infiniDesk.setName(Constant.VISITOR_NAME);
            infiniDesk.setEmail(Constant.VISITOR_EMAIL);
            infiniDesk.setLang(Constant.LANG);

            intent = InfiniHelpDesk.getInstance().startChat(infiniDesk, activity);
            startActivityForResult(intent, 1);
        });
    }

    public synchronized String createIdReference(Context context) {
        String idReference = "";

        SharedPreferences sharedPrefs = context.getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE);
        idReference = sharedPrefs.getString(PREF_UNIQUE_ID, null);
        if (idReference == null) {
            idReference = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(PREF_UNIQUE_ID, idReference);
            editor.commit();
        }

        return idReference;
    }
}

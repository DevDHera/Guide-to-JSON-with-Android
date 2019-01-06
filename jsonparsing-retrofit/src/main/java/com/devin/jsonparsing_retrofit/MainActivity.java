package com.devin.jsonparsing_retrofit;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.devin.jsonparsing_retrofit.model.ContactsItem;
import com.devin.jsonparsing_retrofit.rest.ApiClient;
import com.devin.jsonparsing_retrofit.rest.ApiInterface;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pro;
    private ListView lv;
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.list);
        contactList = new ArrayList<>();

        getContacts();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayFullContactDetails(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_fetch) {
            getContacts();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getContacts() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<com.devin.jsonparsing_retrofit.model.Response> call = apiService.getAllContacts();
        call.enqueue(new Callback<com.devin.jsonparsing_retrofit.model.Response>() {
            @Override
            public void onResponse(Call<com.devin.jsonparsing_retrofit.model.Response> call, Response<com.devin.jsonparsing_retrofit.model.Response> response) {
                List<ContactsItem> contacts = response.body().getContacts();
                Log.d(TAG, "Number of movies received: " + contacts.size());

                generateContactListView(contacts);
            }

            @Override
            public void onFailure(Call<com.devin.jsonparsing_retrofit.model.Response> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void generateContactListView(List<ContactsItem> contacts) {
        for (int i = 0; i < contacts.size(); i++) {
            HashMap<String, String> contact = new HashMap<>();

            contact.put("id", contacts.get(i).getId());
            contact.put("name", contacts.get(i).getName());
            contact.put("email", contacts.get(i).getEmail());
            contact.put("address", contacts.get(i).getAddress());
            contact.put("gender", contacts.get(i).getGender());
            contact.put("mobile", contacts.get(i).getPhone().getMobile());
            contact.put("home", contacts.get(i).getPhone().getHome());
            contact.put("office", contacts.get(i).getPhone().getOffice());

            contactList.add(contact);
        }

        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this, contactList,
                R.layout.list_item, new String[]{"name", "email",
                "mobile"}, new int[]{R.id.name,
                R.id.email, R.id.mobile});

        lv.setAdapter(adapter);
    }

    public void displayFullContactDetails(int position) {
        HashMap<String, String> hashMap = (HashMap<String, String>) lv.getAdapter().getItem(position);

        new LovelyStandardDialog(MainActivity.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.darkDeepOrange)
                .setIcon(R.drawable.ic_info_outline_white_36dp)
                .setTitle("Full Details")
                .setMessage(
                        "ID: \t\t\t\t\t\t\t\t" + hashMap.get("id") + "\n"
                                + "Name: \t\t\t\t" + hashMap.get("name") + "\n"
                                + "Email: \t\t\t\t" + hashMap.get("email") + "\n"
                                + "Address: \t\t" + hashMap.get("address") + "\n"
                                + "Gender: \t\t\t" + hashMap.get("gender") + "\n"
                                + "Mobile: \t\t\t" + hashMap.get("mobile") + "\n"
                                + "Home: \t\t\t\t" + hashMap.get("home") + "\n"
                                + "Office: \t\t\t\t" + hashMap.get("office") + "\n")

                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}

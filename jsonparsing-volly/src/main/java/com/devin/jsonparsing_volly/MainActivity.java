package com.devin.jsonparsing_volly;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static String url = "https://api.androidhive.info/contacts/";

    private static String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pro;
    private ListView lv;

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        lv = findViewById(R.id.list);

        pro = new ProgressDialog(this);
        pro.setMessage("Please wait...");
        pro.setCancelable(false);

        getContacts();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayFullContactDetails(i);
            }
        });
    }

    private void showPro() {
        if (!pro.isShowing())
            pro.show();
    }

    private void hidePro() {
        if (pro.isShowing())
            pro.dismiss();
    }

    private void getContacts() {
        showPro();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    JSONArray contacts = response.getJSONArray("contacts");

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        HashMap<String, String> contact = new HashMap<>();

                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("address", address);
                        contact.put("gender", gender);
                        contact.put("mobile", mobile);
                        contact.put("home", home);
                        contact.put("office", office);

                        contactList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidePro();

                ListAdapter adapter = new SimpleAdapter(
                        MainActivity.this, contactList,
                        R.layout.list_item, new String[]{"name", "email",
                        "mobile"}, new int[]{R.id.name,
                        R.id.email, R.id.mobile});

                lv.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

                hidePro();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_fetch){
            getContacts();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayFullContactDetails(int position) {
        HashMap<String, String> hashMap = (HashMap<String, String>) lv.getAdapter().getItem(position);

        new LovelyStandardDialog(MainActivity.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.darkDeepOrange)
                .setIcon(R.drawable.ic_info_outline_white_36dp)
                .setTitle("Full Details")
                .setMessage(
                        "ID: \t\t\t\t\t\t\t\t"+hashMap.get("id")+"\n"
                                +"Name: \t\t\t\t"+hashMap.get("name")+"\n"
                                +"Email: \t\t\t\t"+hashMap.get("email")+"\n"
                                +"Address: \t\t"+hashMap.get("address")+"\n"
                                +"Gender: \t\t\t"+hashMap.get("gender")+"\n"
                                +"Mobile: \t\t\t"+hashMap.get("mobile")+"\n"
                                +"Home: \t\t\t\t"+hashMap.get("home")+"\n"
                                +"Office: \t\t\t\t"+hashMap.get("office")+"\n")

                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}

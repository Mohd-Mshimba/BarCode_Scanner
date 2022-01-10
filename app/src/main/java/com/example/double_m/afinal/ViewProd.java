package com.example.double_m.afinal;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.validation.Validator;

public class ViewProd extends AppCompatActivity {
    private String product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prod);
        Intent intent =getIntent();
        product=intent.getStringExtra("product");
        loadData(product);
       // Toast.makeText(getApplicationContext(),product,Toast.LENGTH_LONG).show();
    }

    public void loadData(String product){
        final TextView SCode =(TextView) findViewById(R.id.SCode);
        final TextView SName =(TextView) findViewById(R.id.SName);
        final TextView Batch =(TextView) findViewById(R.id.Batch);
        final TextView ProdDate =(TextView) findViewById(R.id.ProdDate);
        final TextView ExpDate =(TextView) findViewById(R.id.ExpDate);
        final TextView Manufacture =(TextView) findViewById(R.id.Manufacture);

        String  url = "http://192.168.188.163:8080/FINAL/api.jsp?SCode="+product;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            HashMap<String, Object> hashMap = new Gson().fromJson(jsonObject.toString(), HashMap.class);
                            SCode.setText(hashMap.get("SCode").toString());
                            SName.setText(hashMap.get("SName").toString());
                            Batch.setText(hashMap.get("Batch").toString());
                            ProdDate.setText(hashMap.get("ProdDate").toString());
                            ExpDate.setText(hashMap.get("ExpDate").toString());
                            Manufacture.setText(hashMap.get("Manufacture").toString());
                           // Toast.makeText(getApplicationContext(),"hello "+hashMap.get("SCode").toString(),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameter = new HashMap<>();
                return parameter;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}

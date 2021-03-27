package codinginflow.shoxrux.weatherappwithvolley;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView date,city,temp,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = findViewById(R.id.date);
        city = findViewById(R.id.city);
        temp = findViewById(R.id.temp);
        type = findViewById(R.id.type);

        find_weather();
    }

    public void find_weather(){
        String url = "http://api.openweathermap.org/data/2.5/weather?q=London&appid=99ae26bf79a00898b0d75f39eeeb1651";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String j_temp = String.valueOf(main_object.getDouble("temp"));
                    String j_type = object.getString("description");
                    String j_city = response.getString("name");

                    city.setText(j_city);
                    type.setText(j_type);

                    double temp_int = Double.parseDouble(j_temp);
                    double celsie = (temp_int - 32) / 1.800;
                    celsie = Math.round(celsie);
                    int i = (int) celsie;
                    temp.setText(String.valueOf(i));

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
                    String date_format = simpleDateFormat.format(calendar.getTime());

                    date.setText(date_format);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}
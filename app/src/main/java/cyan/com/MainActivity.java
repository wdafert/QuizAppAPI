package cyan.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.buttonNextQuiz);

        requestQueue = Volley.newRequestQueue(this);
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });

    }

    private void jsonParse() {
        int randomNumber = (int) Math.floor(Math.random()*8)+2;
        String randomNumberString = Integer.toString(randomNumber);
        Log.e("Rest Response", randomNumberString);
        String url = "http://192.168.0.213:8889/api/quizzes/"+ randomNumberString;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("Rest Response","inside for loop1");
                            JSONArray jsonArray = response.getJSONArray("content");
                            Log.e("Rest Response","inside for loop2");
//                            JSONObject quiz = response.getJSONObject(0);
//                                String title = quiz.getString("title");
//                                String text = quiz.getString("text");
//                                Log.e("Rest Response",text);
//                                Log.e("Rest Response","inside for loop3");
//                                mTextViewResult.append(title+ " "+ text);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Rest Response", response.toString());


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response", error.toString());
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Basic dGVzdEBnbWFpbC5jb206c2VjcmV0");
                return params;
            }

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("User", UserName);
//                params.put("Pass", PassWord);
//                return params;
//            }
        }


                ;

        requestQueue.add(objectRequest);
    }
}
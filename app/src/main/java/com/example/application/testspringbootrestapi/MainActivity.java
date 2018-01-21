package com.example.application.testspringbootrestapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.application.testspringbootrestapi.models.ApiGithub;
import com.example.application.testspringbootrestapi.models.Greeting;
import com.example.application.testspringbootrestapi.models.Post;
import com.example.application.testspringbootrestapi.models.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpRequestTask().execute();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
    protected void apiUserStringResp() throws Exception {
        final String url = "https://jsonplaceholder.typicode.com/users";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String resp = restTemplate.getForObject(url, String.class);
        JSONArray jsonArray = new JSONArray(resp.toString());

        Log.i("apiUserStringResp  =" , String.valueOf(jsonArray.length()) );
        Log.i("apiUserStringResp  =" , jsonArray.getJSONObject(0).get("name").toString());
    }
    protected void apiUserObjectResp() throws Exception {
        final String url = "https://jsonplaceholder.typicode.com/users";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        User[] resp = restTemplate.getForObject(url, User[].class);

        for(User user : resp) {
            Log.i("apiUserObjectResp  =" , user.toString());
        }
    }

    protected void apiUserPostObjectResp() throws Exception {
        final String url = "https://jsonplaceholder.typicode.com/posts?userId=1";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Post[] resp = restTemplate.getForObject(url, Post[].class);

        for(Post post : resp) {
            Log.i("apijsonplaceholder  =" , post.getBody().toString());
        }
    }

    protected void apiUserPostStringResp() throws Exception {
        final String url = "https://jsonplaceholder.typicode.com/posts?userId=1";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String resp = restTemplate.getForObject(url, String.class);
        JSONArray jsonArray = new JSONArray(resp.toString());

        Log.i("apijsonplaceholder  =" , String.valueOf(jsonArray.length()) );
        Log.i("apijsonplaceholder  =" , jsonArray.getJSONObject(1).get("id").toString());
    }

    protected void apiGithubObjectResp() throws Exception {
        final String url = "https://api.github.com/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ApiGithub apiGithub = restTemplate.getForObject(url, ApiGithub.class);

        Log.i("apiGithubObjectResp =" , apiGithub.toString());
    }

    protected void apiGithubStringResp() throws Exception {
        final String url = "https://api.github.com/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String apiGithub = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = new JSONObject(apiGithub.toString());
        Log.i("apiGithubStringResp =" , jsonObject.toString());
        Log.i("apiGithubStringResp =" , jsonObject.get("keys_url").toString());
    }

    public void restStringResp() {
        final String url = "http://rest-service.guides.spring.io/greeting";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("test", "test'");

//                HttpEntity<?> request = new HttpEntity<>(requestBody, headers);
        HttpEntity<?> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        if(resp.getBody() != null) {
            try {
                JSONObject jsonObject = new JSONObject(resp.getBody().toString());
                Log.i("instanceof = ", jsonObject.toString());
            } catch(Exception e) {
                Log.i("e = ", e.getMessage());
            }
        }
        Log.i("restStringResp = ", resp.getStatusCode().toString());
        Log.i("restStringResp = ", resp.getBody().toString());
        Log.i("restStringResp = ", resp.getClass().toString());
        Log.i("restStringResp = ", resp.getHeaders().toString());
        HttpHeaders respHeaders = resp.getHeaders();

        Log.i("restStringResp = ", respHeaders.getContentType().toString());
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {

        protected void restWithHeaderAndBody() {
            final String url = "http://rest-service.guides.spring.io/greeting";

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<String, String>();
            requestBody.put("test", "test'");

//                HttpEntity<?> request = new HttpEntity<>(requestBody, headers);
            HttpEntity<?> request = new HttpEntity<>(null, headers);
            ResponseEntity<Greeting> resp = restTemplate.exchange(url, HttpMethod.GET, request,Greeting.class);

            Log.i("resp = ", resp.getStatusCode().toString());
            Log.i("resp = ", resp.toString());
            Log.i("resp = ", resp.getHeaders().toString());
            HttpHeaders respHeaders = resp.getHeaders();

            Log.i("resp = ", respHeaders.getContentType().toString());
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            Log.i("onPostExecute=", greeting.getContent());
            Log.i("onPostExecute=", greeting.toString());
            super.onPostExecute(greeting);
        }

        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);

                apiUserStringResp();
                apiUserObjectResp();
//                apiUserPostObjectResp();
//                apiUserPostStringResp();

//                apiGithubObjectResp();
//                apiGithubStringResp();
//                restWithHeaderAndBody();
//                restStringResp();

                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }
    }
}

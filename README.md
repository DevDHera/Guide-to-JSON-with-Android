<p align="center">
<img src="https://github.com/DevDHera/Guide-to-JSON-with-Android/blob/master/public/json-img.png" alt="json_parsing">
</p>
<h2 align="center">JSON Parsing - Android Guide</h2>

<p align="center">
<a href="https://github.com/DevDHera/Guide-to-JSON-with-Android/blob/master/LICENSE"><img src="https://img.shields.io/github/license/DevDHera/Guide-to-JSON-with-Android.svg" alt="License"></a>
<a href="https://github.com/DevDHera/Guide-to-JSON-with-Android/issues"><img src="https://img.shields.io/github/issues/DevDHera/Guide-to-JSON-with-Android.svg" alt="issues"></a>
</p>


## Overview

This projects focus on providing a basis for dealing with `JSON` responses that get when using a `REST API`.

Project showcase the JSON parsing using a simple list view based Android Application.


## Content

Project divided into three sections like below.

* [JSON Parsing - Standard](#json-parsing---standard)
* [JSON Parsing - Using Volly](#json-parsing---using-volly)
* [JSON Parsing - Using Retrofit](#json-parsing---using-retrofit)

#### JSON Parsing - Standard 

Here we use a separate `HttpHandler` class to modularize and organize our HttpUrlConnections.

```java
public String makeServiceCall(String reqUrl) {
    String response = null;

    try {
        URL url = new URL(reqUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        InputStream in = new BufferedInputStream(conn.getInputStream());
        response = convertStreamToString(in);

    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return response;
}
```

**[Source](https://github.com/DevDHera/Guide-to-JSON-with-Android/tree/master/app)**

#### JSON Parsing - Using Volly

Here we use a separate `AppController` singleton class to initialize volly objects.

```java
public static synchronized AppController getInstance() {
        return mInstance;
}

public RequestQueue getRequestQueue() {
    if (mRequestQueue == null) {
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    return mRequestQueue;
}
```  

**[Source](https://github.com/DevDHera/Guide-to-JSON-with-Android/tree/master/jsonparsing-volly)**


#### JSON Parsing - Using Retrofit

One of the favourite ways to deal with the Networks is to use the `Retrofit Library`.

We love :heart: Retrofit because its type-safe nature.

Following is how we create a Retrofit instance.

```java
public class ApiClient {
    public static final String BASE_URL = "https://api.androidhive.info/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
```

**[Source](https://github.com/DevDHera/Guide-to-JSON-with-Android/tree/master/jsonparsing-retrofit)**


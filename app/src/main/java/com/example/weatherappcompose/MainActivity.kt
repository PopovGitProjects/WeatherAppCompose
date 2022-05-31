package com.example.weatherappcompose

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherappcompose.constants.Constants
import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {

    val state = remember {
        mutableStateOf("Unknown")
    }

    Column(
            modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                modifier = Modifier
                .padding(top = 5.dp),
                onClick = { state.value = "Moscow" }
            ) {
                Text(text = "Moscow")
            }
            Button(
                modifier = Modifier
                    .padding(top = 5.dp),
                onClick = { state.value = "Perm'" }
            ) {
                Text(text = "Perm'")
            }
        }
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Temperature: ${state.value}!",
                fontSize = 32.sp
            )
            Text(
                text = "Pressure: ${state.value}!",
                fontSize = 32.sp
            )
        }
        Box(
            modifier = Modifier
                .weight(2f),
            contentAlignment = Alignment.BottomEnd
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Refresh")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppComposeTheme {
        Greeting()
    }
}
private fun getResult(city: String, state: MutableState<String>, context: Context){
    val url = "https://api.weatherapi.com/v1/current.json" +
            "?key=${Constants.API_KEY}" +
            "&q=$city&aqi=no"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(Request.Method.GET,
        url,
        {
                response ->
            val obj = JSONObject(response)
            val result = obj.getJSONObject("current").getString("temp_c")
        },
        {
                error -> Log.d("MyLog", "Valle error $error")
        }
    )
    queue.add(stringRequest)
}
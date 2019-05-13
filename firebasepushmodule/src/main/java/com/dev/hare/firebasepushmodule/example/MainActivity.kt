package com.dev.hare.firebasepushmodule.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.hare.firebasepushmodule.R
import com.dev.hare.firebasepushmodule.util.FirebaseUtil


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseUtil.getToken(object : FirebaseUtil.OnGetTokenSuccessListener {
            override fun onSuccess(token: String) {
                ExampleHttpService.insertToken(token, "")
            }
        })
    }

}

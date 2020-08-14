package com.gabrielfv.biller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gabrielfv.biller.nav.BillerRoutes
import com.gabrielfv.core.nav.NavManager

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavManager.init(BillerRoutes())
    }
}
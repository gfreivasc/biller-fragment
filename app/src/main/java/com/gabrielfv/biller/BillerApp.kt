package com.gabrielfv.biller

import android.app.Application
import com.gabrielfv.biller.database.DatabaseModule

class BillerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseModule.initDatabase(this)
    }
}
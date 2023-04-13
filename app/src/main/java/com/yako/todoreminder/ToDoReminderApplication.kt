package com.yako.todoreminder

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration

class ToDoReminderApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        //uiスレッドでの書き込み許可
        var config= RealmConfiguration.Builder()
            //.deleteRealmIfMigrationNeeded()
            .allowWritesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)
        //todo Realmマイグレーション
    }
}
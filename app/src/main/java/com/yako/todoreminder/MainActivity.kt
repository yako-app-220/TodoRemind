package com.yako.todoreminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.os.Vibrator
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.yako.todoreminder.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var id = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        setupWithNavController(binding.bottomNavigation, navController)

        Log.i("ActivityMain",id.toString())
        id =intent?.getLongExtra("id", -1L) ?: -1L
        if(id!=-1L) {
            // デバイスのスリープを解除するためのウェイクロックを取得する
            val powerManager = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
            val wakeLock = powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "AlarmReceiver:WakeLock"
            )
            // ウェイクロックを取得する（10秒間）
            wakeLock.acquire(10 * 1000L)
            // デバイスを振動させるためのバイブレーターを取得する
            val vibrator = application.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            // バイブレーションパターンを作成する（ミリ秒単位でオンとオフの時間を交互に指定する）
            val vibrationPattern =
                longArrayOf(0, 500, 500, 500, 500) // 最初の0は待機時間、次の500はオン時間、次の500はオフ時間、というように繰り返す
            // バイブレーターにバイブレーションパターンを設定する（-1は繰り返ししないことを意味する）
            vibrator.vibrate(vibrationPattern, -1)

        }
    }

    fun getToDoId():Long{
        return id
    }

    fun startToDoActivity(id: Long) {
        val intent = Intent(this, ToDoEditActivity::class.java)
        intent.putExtra("todoId", id)
        this.startActivity(intent)
    }
}

// 指定時間に呼び出されるクラス
// マニフェストにタグを追記するのを忘れずに
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val id= intent?.getLongExtra("id", -1) ?: -1
        Log.i("received",id.toString())
        val mainActivityIntent = Intent(context, MainActivity::class.java)
            .putExtra("id",id)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //mainActivityIntent.putExtra("Trigger", "Receiver")
        context!!.startActivity(mainActivityIntent)
    }
}
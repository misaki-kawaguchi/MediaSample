package com.misakikawaguchi.mediasample

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    // メディアプレーヤーフィールド
    private var _player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // フィールドのメディアプレーヤーオブジェクトを生成
        _player = MediaPlayer()
    }
}
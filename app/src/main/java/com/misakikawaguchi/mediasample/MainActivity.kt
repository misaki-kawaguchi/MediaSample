package com.misakikawaguchi.mediasample

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    // メディアプレーヤーフィールド
    private var _player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ①-1 フィールドのメディアプレーヤーオブジェクトを生成
        _player = MediaPlayer()

        // 音声ファイルのURI文字列を生成
        val mediaFileUriString = "android.resource://${packageName}/${R.raw.mountain_stream}"
        // 音声ファイルのURI文字列を元にURIオブジェクトを作成
        val mediaFileUri = Uri.parse(mediaFileUriString)

        try {
            // ①-2 メディアプレーヤーに音声ファイルを指定
            _player?.setDataSource(applicationContext, mediaFileUri)
        }
    }
}
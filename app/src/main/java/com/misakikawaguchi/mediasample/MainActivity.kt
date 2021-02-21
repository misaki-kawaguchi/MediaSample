package com.misakikawaguchi.mediasample

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import java.io.IOException

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

            // ①-3 非同期でのメディア再生準備が完了した際のリスナを設定
            _player?.setOnPreparedListener(PlayerPreparedListener())
            // ①-3 メディア再生が終了した際のリスナを設定
            _player?.setOnCompletionListener(PlayerCompletionListener())

            // ①-4 非同期でメディア再生を準備
            _player?.prepareAsync()
        }

        // setDataSource()ではIllegalArgumentExceptionとIOExceptionの2種の例外が発生する
        catch (ex: IllegalAccessException) {
            Log.e("MediaSample", "メディアプレーヤー準備時の例外発生", ex)
        }

        catch (ex: IOException) {
            Log.e("MediaSample", "メディアプレーヤー準備時の例外発生", ex)
        }
    }

    // プレーヤー再生準備が整った時のリスナクラス
    private inner class PlayerPreparedListener : MediaPlayer.OnPreparedListener {
        override fun onPrepared(mp: MediaPlayer) {
            // 各ボタンをタップ可能に設定
            val btPlay = findViewById<Button>(R.id.btPlay)
            btPlay.isEnabled = true
            val btBack = findViewById<Button>(R.id.btBack)
            btBack.isEnabled = true
            val btForward = findViewById<Button>(R.id.btForward)
            btForward.isEnabled = true
        }
    }
}
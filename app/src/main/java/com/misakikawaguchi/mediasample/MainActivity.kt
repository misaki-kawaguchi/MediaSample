package com.misakikawaguchi.mediasample

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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

    // 再生が終了した時のリスナクラス
    private  inner class PlayerCompletionListener : MediaPlayer.OnCompletionListener {
        override fun onCompletion(mp: MediaPlayer) {
            // 再生ボタンのラベルを「再生」に設定
            val btPlay = findViewById<Button>(R.id.btPlay)
            btPlay.setText(R.string.bt_play_play)
        }
    }

    // 再生ボタンタップ時の処理
    fun onPlayButtonClick(view: View) {
        // フィールドのプレーヤーがnullじゃなかったら
        _player?.let {
            // 再生ボタンを取得
            val btPlay = findViewById<Button>(R.id.btPlay)
            // プレーヤーが再生中だったら
            if(it.isPlaying) {
                // プレーヤーを一時停止
                it.pause()
                // 再生ボタンのラベルを「再生」に設定
                btPlay.setText(R.string.bt_play_play)
            }
            // プレーヤーが再生中じゃなかったら
            else {
                // プレーヤーを再生
                it.start()
                // 再生ボタンのラベルを「一時停止」に設定
                btPlay.setText(R.string.bt_play_pause)
            }
        }
    }

    // アクティビティ終了時の処理
    override fun onDestroy() {
        //親クラスのメソッド呼び出し
        super.onDestroy()

        // フィールドのプレーヤーがnullじゃなかったら
        _player?.let {
            // プレーヤーが再生中なら
            if(it.isPlaying) {
                // プレーヤーを停止
                it.stop()
            }
            // プレーヤーを解放
            it.release()
            // プレーヤーフィールドをnullに
            _player = null
        }
    }

    // 戻る処理
    fun onBackButtonClick(view: View) {
        // 再生位置を先頭に変更
        _player?.seekTo(0)
    }

}
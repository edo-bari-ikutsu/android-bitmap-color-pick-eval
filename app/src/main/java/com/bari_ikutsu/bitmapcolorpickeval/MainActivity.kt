package com.bari_ikutsu.bitmapcolorpickeval

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    lateinit var imageView : ImageView
    lateinit var colorDisplay : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // UI要素の取得
        imageView = findViewById(R.id.imageView) as ImageView
        colorDisplay = findViewById(R.id.colorDisplay) as LinearLayout

        // タッチイベントの処理
        imageView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE ->
                {
                    // タッチ座標
                    val x = event.x
                    val y = event.y

                    // Bitmap取得
                    val bitmap = (imageView.drawable as BitmapDrawable).bitmap

                    // タッチ座標をBitmapの座標に変換
                    val bitmapWidth = bitmap.width
                    val layoutWidth = imageView.measuredWidth
                    val scaledX = x * bitmapWidth / layoutWidth
                    val scaledY = y * bitmapWidth / layoutWidth

                    // 色を取得してcolorDisplay領域に設定
                    val color = getBitmapColor(bitmap, Point(scaledX.toInt(), scaledY.toInt()))
                    if (color != null) {
                        colorDisplay.setBackgroundColor(color)
                    }

                    // イベント消化を表すtrueを返却
                    true
                }
                else -> {
                    // イベント未消化を表すfalseを返却
                    false
                }
            }
        }
    }

    /**
     * Bitmapから色を抽出する
     */
    fun getBitmapColor(bitmap: Bitmap, point: Point) : Int? {
        if (point.x < 0 || point.y < 0 || point.x > bitmap.width || point.y > bitmap.height) {
            return null
        }
        // pixelの色情報を取得
        return bitmap.getPixel(point.x, point.y)
    }
}

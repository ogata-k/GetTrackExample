package com.example.owner.gettrackexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val trackList = this.findViewById<ListView>(R.id.track_list)  // ListViewを取得
        val tracks = Track.getItems(this)  // 配置するデータを取得する
        val adapter = ListTrackAdapter(this, tracks)  // adapterを用意する
        trackList.adapter = adapter  // adapterを配置する
        trackList.setOnItemClickListener { parent, view, position, id -> // 各Track選択時に行われるイベント
            val item = parent.getItemAtPosition(position) as Track  // itemを取得
            val str = "「${item.title}」 by ${item.artist}"
            Toast.makeText(this, str, Toast.LENGTH_LONG).show() // トースト表示（長め）
        }
    }
}

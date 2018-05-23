package com.example.owner.gettrackexample

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log

class Track(val title: String, val album: String, val artist: String, val duration: Long){
    //  Track検索時に取得できるcursorを用いて取得するためのコンストラクタ
    constructor(cursor: Cursor) : this(
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
    )
    companion object {
        @JvmField  // 検索するデータの列名
        val PROJECTION = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST
        )
        @JvmField  // 検索するデータ名一覧
        val SEARCH_URIS = arrayOf(
                MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                )
        fun getItems(activity: Context): List<Track>{
            val tracks = mutableListOf<Track>()
            val resolver = activity.contentResolver
            SEARCH_URIS.map{  // 各uriに対して
                // データを検索して
                val cursor = resolver.query(it, Track.PROJECTION,null, null, null)
                while(cursor.moveToNext()){  // データの次がある限り(最初のindexは-1)
                    tracks.add(Track(cursor))  // データを加える
                }
                cursor.close()  // 後処理
            }
            // 結果をソートしておく
            tracks.sortBy {
                it.title
            }
            return tracks
        }
    }
}

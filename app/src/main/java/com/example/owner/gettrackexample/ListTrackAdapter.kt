package com.example.owner.gettrackexample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

//  リスト表示用の基本アダプターArrayAdapterを継承
class ListTrackAdapter(context: Context, data: List<Track>): ArrayAdapter<Track>(context, 0, data) {
    var mInflater: LayoutInflater  // レイアウト生成用のビルダー

    init {  // 初期化
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = getItem(position)
        // convertViewはListViewの表示用の部品一つ一つ
        var resultView = convertView

        //  以下の方法で実はしなくてもいいが処理コスト削減のために行う
        class ViewHolder(  // それぞれの表示用アイテムを保持したクラス
            val trackTextView: TextView,
            val durationTextView: TextView,
            val albumTextView: TextView,
            val artistTextView: TextView
        )
        var holder: ViewHolder
        if(resultView == null){ // 各パーツにおいて初めて表示するとき
            resultView = mInflater.inflate(R.layout.adapter_layout, null)  //  adapter用のレイアウトを作成
            holder = ViewHolder(  // レイアウト上のViewを取得する
                    resultView.findViewById(R.id.title),
                    resultView.findViewById(R.id.duration),
                    resultView.findViewById(R.id.album),
                    resultView.findViewById(R.id.artist)
                    )
                    resultView.tag = holder  // viewとholderを結び付ける
        }else{  // すでに一度は表示に使われていた時
            holder = resultView.tag as ViewHolder
        }

        val dm = item.duration/60000  // Trackの時間(ミリ秒)から分を取得
        val ds = (item.duration-(dm*60000))/1000  // Trackの時間(ミリ秒)から秒を取得

        // 各Viewの表示文字列を変更
        holder.trackTextView.text = item.title
        holder.durationTextView.text = String.format("(%d:%02d)",dm,ds)
        holder.albumTextView.text = item.album
        holder.artistTextView.text = item.artist

        return resultView!!
    }
}

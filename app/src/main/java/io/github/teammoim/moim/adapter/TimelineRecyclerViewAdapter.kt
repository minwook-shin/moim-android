package io.github.teammoim.moim.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.teammoim.moim.R
import io.github.teammoim.moim.TimelineModel
import kotlinx.android.synthetic.main.recycler_view_timeline_item.view.*

class TimelineRecyclerViewAdapter(val context: Context, val dataSet: ArrayList<TimelineModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mDataSet = ArrayList<TimelineModel>()
    private var mInflater: LayoutInflater? = null
    private var mContext: Context? = null

    init {
        mContext = context
        mDataSet = dataSet
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_timeline_item, p0, false))
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val holder: ViewHolder = p0 as ViewHolder
        if (0 <= p1 && p1 < mDataSet.size) {
            val data = mDataSet[p1]
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: TimelineModel) {
            itemView.bodyText.text = data.text
            itemView.nameText.text = data.name
        }
    }
}

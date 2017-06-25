package com.blogspot.shudiptotrafder.movienewsv3.adapter

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.blogspot.shudiptotrafder.movienewsv3.R

/**
 * Created by Shudipto on 6/11/2017.
 */

class MyAdapter constructor(context:Context,clickListener:ClickListener) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var mCursor:Cursor? = null
    var clickListener:ClickListener? = null

    var mContext:Context? = null


    init {
        mContext = context
        this.clickListener = clickListener
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder?, p1: Int) {
        viewHolder!!.imageView
    }

    override fun getItemCount(): Int {

        if (mCursor == null) return 0

        return mCursor!!.count
    }

    override fun onCreateViewHolder(parent: ViewGroup?, ViewType: Int):
            MyViewHolder {

        val view:View = LayoutInflater.from(parent?.context).inflate(R.layout.main_list,parent,false)

        return MyViewHolder(view)
    }

    fun swapCusror(cursor:Cursor):Unit{

        mCursor = cursor

        if (mCursor != null){
            notifyDataSetChanged()
        }
    }

    interface ClickListener{
        fun onItemClick(position:Int):Unit
    }

    inner class MyViewHolder internal constructor(itemView: View) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val cardView: CardView = itemView.findViewById(R.id.mainListCard) as CardView
        val imageView: ImageView = itemView.findViewById(R.id.mainListImageView) as ImageView

        init {
            cardView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            //val position:Int = adapterPosition
            clickListener!!.onItemClick(adapterPosition)
        }
    }
}

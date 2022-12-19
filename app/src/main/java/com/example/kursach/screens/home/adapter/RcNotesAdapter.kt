package com.example.kursach.screens.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.R
import com.example.kursach.database.notes.entities.NoteEntity

class RcNotesAdapter: ListAdapter<NoteEntity, RcNotesAdapter.NoteHolder>(NoteItemDiffCallback()){

    var onItemLongClickListener: ((NoteEntity) -> Unit)? = null
    var onItemClickListener: ((NoteEntity) -> Unit)? = null

    class NoteHolder(view: View): RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.tv_title)
        val time = view.findViewById<TextView>(R.id.tv_time)
    }

    class NoteItemDiffCallback: DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        Log.e("create","createViewHolder")
        val layout = when(viewType){
            VIEW_TYPE_ENABLED -> R.layout.item_note_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_note_disabled
            else -> throw RuntimeException("unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return NoteHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if(item.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val item = getItem(position)
        holder.title.text = item.title
        holder.time.text = item.time
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
        holder.itemView.setOnLongClickListener{
            onItemLongClickListener?.invoke(item)
            true
        }
    }

    companion object{
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0

        const val MAX_POOL_SIZE = 15
    }
}
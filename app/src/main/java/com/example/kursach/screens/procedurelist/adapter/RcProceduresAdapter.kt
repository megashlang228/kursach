package com.example.kursach.screens.procedurelist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.R
import com.example.kursach.database.procedure.entities.ProcedureEntity

class RcProceduresAdapter : ListAdapter<ProcedureEntity, RcProceduresAdapter.ProcedureHolder>(ProcedureItemDiffCallback()){

    var onItemClickListener: ((ProcedureEntity) -> Unit)? = null

    class ProcedureHolder(view: View): RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.tv_title)
    }

    class ProcedureItemDiffCallback: DiffUtil.ItemCallback<ProcedureEntity>() {
        override fun areItemsTheSame(oldItem: ProcedureEntity, newItem: ProcedureEntity): Boolean {
            return oldItem.Id == newItem.Id
        }
        override fun areContentsTheSame(oldItem: ProcedureEntity, newItem: ProcedureEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureHolder {
        Log.e("create","createViewHolder")
        val layout = R.layout.item_procedure
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return ProcedureHolder(view)
    }

    override fun onBindViewHolder(holder: ProcedureHolder, position: Int) {
        val item = getItem(position)
        holder.title.text = item.name
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
    }
}
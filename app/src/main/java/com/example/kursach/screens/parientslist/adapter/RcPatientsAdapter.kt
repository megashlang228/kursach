package com.example.kursach.screens.parientslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.R
import com.example.kursach.database.notes.entities.NoteEntity
import com.example.kursach.database.patients.entities.PatientEntity

class RcPatientsAdapter: ListAdapter<PatientEntity, RcPatientsAdapter.PatientHolder>(PatientItemDiffCallback()) {


    var onItemClick: ((PatientEntity) -> Unit)? = null

    class PatientHolder(view: View): RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.tv_patient_name)
        val ward = view.findViewById<TextView>(R.id.tv_patient_ward)
    }

    class PatientItemDiffCallback: DiffUtil.ItemCallback<PatientEntity>() {
        override fun areItemsTheSame(oldItem: PatientEntity, newItem: PatientEntity): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: PatientEntity, newItem: PatientEntity): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false)
        return PatientHolder(view)
    }

    override fun onBindViewHolder(holder: PatientHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.FIO
        holder.ward.text = item.ward
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }
}



package com.example.kursach.screens.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursach.R
import com.example.kursach.databinding.ActivityMainBinding
import com.example.kursach.databinding.FragmentHomeBinding
import com.example.kursach.screens.home.adapter.RcNotesAdapter
import com.example.kursach.screens.notedetail.NoteDetailFragment
import com.example.kursach.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    private lateinit var adapter: RcNotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        val recyclerNotes = binding.recyclerNotes
        recyclerNotes.layoutManager = LinearLayoutManager(requireContext())
        adapter = RcNotesAdapter()
        recyclerNotes.adapter = adapter
        recyclerNotes.recycledViewPool.setMaxRecycledViews(
            RcNotesAdapter.VIEW_TYPE_DISABLED,
            RcNotesAdapter.MAX_POOL_SIZE
        )
        recyclerNotes.recycledViewPool.setMaxRecycledViews(
            RcNotesAdapter.VIEW_TYPE_ENABLED,
            RcNotesAdapter.MAX_POOL_SIZE
        )

        adapter.onItemLongClickListener = {
            viewModel.updateEnabledNote(it)
        }
        adapter.onItemClickListener = {
            val args = Bundle()
            args.putString(SCREEN_MODE, MODE_EDIT)
            args.putInt(NOTE_ID, it.Id)
            findNavController().navigate(R.id.action_home_fragment_to_noteDetailFragment, args)
        }
        setupSwipeListener(recyclerNotes)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.initDatabase()
        viewModel.notes.observe(viewLifecycleOwner) { listNotes ->
            listNotes.reversed()
            adapter.submitList(listNotes)
            for (l in listNotes) {
                Log.e("list", l.toString())
            }
        }
        binding.btnAddNoteHome.setOnClickListener{
            findNavController().navigate(R.id.action_home_fragment_to_noteDetailFragment,
                bundleOf(SCREEN_MODE to MODE_ADD))
        }
        binding.all.setOnClickListener{
            onRadioButtonClicked(it)
        }
        binding.now.setOnClickListener{
            onRadioButtonClicked(it)
        }
        binding.custom.setOnClickListener{
            onRadioButtonClicked(it)
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            when (view.getId()) {
                R.id.all ->
                    if (checked) {
                        viewModel.initDatabase()
                    }
                R.id.now ->
                    if (checked) {
                        val myCalendar = Calendar.getInstance()
                        val myFormat = "dd-MM-yyyy"
                        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
                        viewModel.getNotesByDate(sdf.format(myCalendar.time))
                    }
                R.id.custom ->
                    if (checked) {
                        val myCalendar = Calendar.getInstance()

                        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                            myCalendar.set(Calendar.YEAR, year)
                            myCalendar.set(Calendar.MONTH, month)
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            updateTvDate(myCalendar)
                        }
                            DatePickerDialog(requireContext(), datePicker, myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
                    }
            }
        }
    }

    private fun updateTvDate(calendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        viewModel.getNotesByDate(sdf.format(calendar.time))
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteNote(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }
}
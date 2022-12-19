package com.example.kursach.screens.procedurelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kursach.*
import com.example.kursach.databinding.FragmentHomeBinding
import com.example.kursach.databinding.FragmentProcedureListBinding
import com.example.kursach.screens.home.HomeViewModel
import com.example.kursach.screens.home.adapter.RcNotesAdapter
import com.example.kursach.screens.procedurelist.adapter.RcProceduresAdapter

class ProcedureListFragment : Fragment(R.layout.fragment_procedure_list) {

    private lateinit var binding: FragmentProcedureListBinding
    private lateinit var viewModel: ProcedureListViewModel
    private  lateinit var adapter: RcProceduresAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProcedureListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    
    private fun init(){
        val recyclerNotes = binding.recyclerProcedures
        recyclerNotes.layoutManager = LinearLayoutManager(requireContext())
        adapter = RcProceduresAdapter()
        recyclerNotes.adapter = adapter

        if(arguments != null){
            adapter.onItemClickListener = {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("procedure_id", it.Id)
                findNavController().popBackStack()
            }
        }else {
            adapter.onItemClickListener = {
                val args = Bundle()
                args.putString(SCREEN_MODE, MODE_EDIT)
                args.putInt(PROCEDURE_ID, it.Id)
                findNavController()
                    .navigate(R.id.action_procedureListFragment_to_procedureDetailFragment, args)
            }
        }
        viewModel = ViewModelProvider(this)[ProcedureListViewModel::class.java]
        viewModel.initDatabase()
        viewModel.notes.observe(viewLifecycleOwner) { listNotes ->
            listNotes.reversed()
            adapter.submitList(listNotes)
            for (l in listNotes) {
                Log.e("list", l.toString())
            }
        }
        binding.btnAddProcedure.setOnClickListener{
            findNavController().navigate(R.id.action_procedureListFragment_to_procedureDetailFragment, bundleOf(
                SCREEN_MODE to MODE_ADD))
        }
    }
}
package com.example.kursach.screens.parientslist

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
import com.example.kursach.R
import com.example.kursach.databinding.FragmentPatientsBinding
import com.example.kursach.screens.parientslist.adapter.RcPatientsAdapter
import com.example.kursach.*

class PatientsFragment : Fragment(R.layout.fragment_patients) {

    private lateinit var binding: FragmentPatientsBinding
    private lateinit var viewModel: PatientsViewModel

    private lateinit var adapter: RcPatientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = binding.recyclerPatients
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = RcPatientsAdapter()
        recycler.adapter = adapter

        viewModel = ViewModelProvider(this)[PatientsViewModel::class.java]
        viewModel.patients.observe(viewLifecycleOwner){
            for(i in it){
                Log.e("jhjh", i.toString())
            }
            adapter.submitList(it)
        }
        viewModel.initDatabase()

        binding.btnAddPatient.setOnClickListener {
            findNavController().navigate(R.id.action_patients_fragment_to_patientDetailFragment, bundleOf(
                SCREEN_MODE to MODE_ADD))
        }


        if(arguments != null){
            adapter.onItemClick = {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("patient_id", it.Id)
                findNavController().popBackStack()
            }
        }else {
            adapter.onItemClick = {
                val args = Bundle()
                args.putString(SCREEN_MODE, MODE_EDIT)
                args.putInt(PATIENT_ID, it.Id)
                findNavController().navigate(
                    R.id.action_patients_fragment_to_patientDetailFragment,
                    args
                )
            }
        }
    }

}
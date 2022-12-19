package com.example.kursach.screens.proceduredetail

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kursach.R
import com.example.kursach.database.procedure.entities.ProcedureEntity
import com.example.kursach.databinding.FragmentProcedureDetailBinding
import com.example.kursach.databinding.FragmentProcedureListBinding
import com.example.kursach.screens.procedurelist.ProcedureListFragment
import java.lang.Exception
import com.example.kursach.*

class ProcedureDetailFragment: Fragment(R.layout.fragment_procedure_detail) {

    private var screenMode = ""
    private lateinit var currentProcedure: ProcedureEntity

    private lateinit var binding: FragmentProcedureDetailBinding
    private lateinit var viewModel: ProcedureDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProcedureDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProcedureDetailViewModel::class.java]
        launchRightMode()
        viewModel.procedure.observe(viewLifecycleOwner){
            currentProcedure = it
            binding.etProcedureName.text = Editable.Factory.getInstance().newEditable(it.name)
        }
    }

    private fun launchRightMode() {
        screenMode = arguments?.getString(SCREEN_MODE).toString()
        when (screenMode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
            else -> throw RuntimeException("unknown screen mode: $screenMode")
        }
    }

    private fun launchEditMode() {
        val args = requireArguments()
        if(!args.containsKey(PROCEDURE_ID)){
            throw RuntimeException("param procedure id is absent")
        }
        val procedureId = args.getInt(PROCEDURE_ID)
        viewModel.getProcedureById(procedureId)
        binding.btnSaveProcedure.setOnClickListener{
            if(validateInput(binding.etProcedureName.text.toString())) {
                viewModel.updateProcedure(currentProcedure.copy(name = binding.etProcedureName.text.toString()))
                findNavController().popBackStack()
            }

        }
    }

    private fun launchAddMode() {
        binding.btnSaveProcedure.setOnClickListener{
            if(validateInput(binding.etProcedureName.text.toString())) {
                viewModel.createProcedure(
                    ProcedureEntity(
                        0,
                        binding.etProcedureName.text.toString()
                    )
                )
                findNavController().popBackStack()
            }
        }
    }

    private fun validateInput(name: String): Boolean {
        var result = true
        if (name.isBlank()){
            Toast.makeText(requireContext(), "поле не должно быть пустым", Toast.LENGTH_SHORT).show()
            result = false
        }

        return result
    }
}
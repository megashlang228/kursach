package com.example.kursach.screens.patientdetail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kursach.*
import com.example.kursach.database.patients.entities.PatientEntity
import com.example.kursach.databinding.FragmentPatientDetailBinding
import com.example.kursach.databinding.FragmentPatientsBinding
import java.text.SimpleDateFormat
import java.util.*

class PatientDetailFragment: Fragment(R.layout.fragment_patient_detail) {

    private lateinit var viewModel: PatientDetailViewModel
    private lateinit var binding: FragmentPatientDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PatientDetailViewModel::class.java]
        launchRightMode()
        viewModel.patient.observe(viewLifecycleOwner){
            with(binding){
                etFio.text = Editable.Factory.getInstance().newEditable(it.FIO)
                etWard.text = Editable.Factory.getInstance().newEditable(it.ward)
                tvDateOfDischarge.text = it.dateOfDischarge
                tvReceiptDate.text = it.receiptDate
            }
        }
        createPicker()



    }

    private fun launchRightMode() {
        val args = requireArguments()
        if(!args.containsKey(SCREEN_MODE)){
            throw RuntimeException("param screen mode is absent")
        }
        when(args.getString(SCREEN_MODE)){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
            else -> throw RuntimeException("unknown screen mode")
        }
    }

    private fun launchAddMode() {
        with(binding){
            btnSavePatient.setOnClickListener {
                if(validateInput(etFio.text.toString(), etWard.text.toString(), tvReceiptDate.text.toString(), tvDateOfDischarge.text.toString())){
                    viewModel.createPatient(
                        PatientEntity(
                            0,
                            etFio.text.toString(),
                            etWard.text.toString(),
                            tvReceiptDate.text.toString(),
                            tvDateOfDischarge.text.toString())
                    )
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun launchEditMode() {
        val args = requireArguments()
        if(!args.containsKey(PATIENT_ID)){
            throw RuntimeException("param patient id is absent")
        }
        val patientId = args.getInt(PATIENT_ID)
        viewModel.getPatientById(patientId)
        with(binding){
            btnSavePatient.setOnClickListener {
                if(validateInput(etFio.text.toString(), etWard.text.toString(), tvReceiptDate.text.toString(), tvDateOfDischarge.text.toString())){
                    viewModel.updatePatient(
                        PatientEntity(
                            patientId,
                            etFio.text.toString(),
                            etWard.text.toString(),
                            tvReceiptDate.text.toString(),
                            tvDateOfDischarge.text.toString())
                        )
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun createPicker() {
        val myCalendar = Calendar.getInstance()

        val datePicker1 = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateTvDate1(myCalendar)
        }
        binding.linearLayoutDate1.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker1, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        val datePicker2 = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateTvDate2(myCalendar)
        }
        binding.linearLayoutDate2.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker2, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun updateTvDate1(calendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        binding.tvReceiptDate.text = sdf.format(calendar.time)
    }
    private fun updateTvDate2(calendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        binding.tvDateOfDischarge.text = sdf.format(calendar.time)
    }

    private fun validateInput(fio: String, ward: String, date1: String, date2: String): Boolean{
        if (fio == "" || fio == " ") return false
        if (ward == "" || ward == " ") return false
        if (date1 == "" || date1 == " ") return false
        if (date2 == "" || date2 == " ") return false
        return true
    }
}
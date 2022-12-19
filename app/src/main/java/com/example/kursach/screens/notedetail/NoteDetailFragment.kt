package com.example.kursach.screens.notedetail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kursach.*
import com.example.kursach.databinding.FragmentNoteDetailBinding
import com.example.kursach.database.notes.entities.NoteEntity
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailFragment: Fragment(R.layout.fragment_note_detail) {

    private lateinit var binding: FragmentNoteDetailBinding
    private lateinit var viewModel: NoteDetailViewModel


    private var title: String? = null
    private var description: String? = null
    private var date: String? = null
    private var time: String? = null

    private var currentPatientId: Int? = null
    private var currentNoteId: Int? = null
    private var currentProcedureId: Int? = null
    private var currentDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NoteDetailViewModel::class.java]
        createObservable()
        createPicker()
        launchRightMode()
        initListener()

        Log.e("title", title.toString())
        with(binding){
            if(title != null) etTitleDetail.text = Editable.Factory.getInstance().newEditable(title)
            if(description != null) etDescriptionDetail.text = Editable.Factory.getInstance().newEditable(description)
            if(date != null) tvDateDetail.text = date
            if(time != null) tvTimeDetail.text = time
        }
        Log.e("ghdfg", "onViewCreated")
    }

    private fun createObservable() {
        with(binding) {

            viewModel.patient.observe(viewLifecycleOwner) {
                tvPatientName.text = it.FIO
                tvPatientWard.text = it.ward
                currentDate = it.dateOfDischarge
            }
            viewModel.procedure.observe(viewLifecycleOwner){
                tvProcedureName.text = it.name
            }
            viewModel.note.observe(viewLifecycleOwner){
                Log.e("title", it.title)
                if(title == null) etTitleDetail.text = Editable.Factory.getInstance().newEditable(it.title)
                else etTitleDetail.text = Editable.Factory.getInstance().newEditable(title)
                if(description == null) etDescriptionDetail.text = Editable.Factory.getInstance().newEditable(it.description)
                else etDescriptionDetail.text = Editable.Factory.getInstance().newEditable(description)
                if(date == null) tvDateDetail.text = it.date
                else tvDateDetail.text = date
                if(time == null) tvTimeDetail.text = it.time
                else tvTimeDetail.text = time
                if(currentProcedureId == null) currentProcedureId = it.procedureId
                if(currentPatientId == null) currentPatientId = it.patientId
                if(currentProcedureId != null) viewModel.getProcedureById(currentProcedureId!!)
                if(currentPatientId != null) viewModel.getPatientById(currentPatientId!!)
            }
        }
    }

    private fun initListener() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("patient_id")?.observe(viewLifecycleOwner) {result ->
            currentPatientId = result
            viewModel.getPatientById(result)
        }
        binding.cardViewPatient.setOnClickListener {
            findNavController().navigate(R.id.action_noteDetailFragment_to_patients_fragment, bundleOf(
                SCREEN_MODE to MODE_SELECTION ))
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("procedure_id")?.observe(viewLifecycleOwner) {result ->
            currentProcedureId = result
            viewModel.getProcedureById(result)
        }
        binding.cardViewProcedure.setOnClickListener {
            findNavController().navigate(R.id.action_noteDetailFragment_to_procedureListFragment, bundleOf(
                SCREEN_MODE to MODE_SELECTION ))
        }

    }

    private fun createPicker() {
        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateTvDate(myCalendar)
        }
        val timePicker = TimePickerDialog.OnTimeSetListener{view, hour, minute ->
            myCalendar.set(Calendar.HOUR, hour)
            myCalendar.set(Calendar.MINUTE, minute)
            updateTvTime(myCalendar)
        }
        binding.linearLayoutDetail.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()

            TimePickerDialog(requireContext(), timePicker, myCalendar.get(Calendar.HOUR),
                myCalendar.get(Calendar.MINUTE), true).show()
        }
    }

    private fun updateTvDate(calendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        binding.tvDateDetail.text = sdf.format(calendar.time)
    }
    private fun updateTvTime(calendar: Calendar) {
        val myFormat = "hh:mm"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        binding.tvTimeDetail.text = sdf.format(calendar.time)
    }

    private fun launchRightMode(){
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
            binding.button.setOnClickListener {
                if(validateInput()) {
                    viewModel.insert(
                        NoteEntity(
                            0,
                            binding.etTitleDetail.text.toString(),
                            binding.etDescriptionDetail.text.toString(),
                            currentProcedureId!!,
                            currentPatientId!!,
                            binding.tvDateDetail.text.toString(),
                            binding.tvTimeDetail.text.toString(),
                            true
                        )
                    )
                    findNavController().popBackStack()
                }
        }
    }


    private fun validateInput(): Boolean {
        var res = true
        with(binding) {
            if (etTitleDetail.text.toString() == "" || etTitleDetail.text.toString() == " ") {
                textInputLayout.error = "поле не должно быть пустым"
                res = false
            }
            if (etDescriptionDetail.text.toString() == "" || etDescriptionDetail.text.toString() == " ") {
                textInputLayout1.error = "поле не должно быть пустым"
                res = false
            }
            if (currentPatientId == null) res = false
            if (currentProcedureId == null) res = false
            if (currentDate != null) {
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val patientDate = sdf.parse(currentDate!!)
                val noteDate = sdf.parse(binding.tvDateDetail.text.toString())

                if(patientDate.before(noteDate)) {
                    Toast.makeText(
                        requireContext(),
                        "Дата записи не может быть позде даты выписки",
                        Toast.LENGTH_SHORT
                    ).show()
                    res = false
                }
            }
        }
        return res
    }

    private fun launchEditMode() {
        val args = requireArguments()
        if(!args.containsKey(NOTE_ID)){
            throw RuntimeException("param note id is absent")
        }
        val noteId = args.getInt(NOTE_ID)
        currentNoteId = noteId
        viewModel.getNoteById(noteId)


            binding.button.setOnClickListener {
                if(validateInput()){
                viewModel.update(
                    NoteEntity(
                        currentNoteId!!,
                        binding.etTitleDetail.text.toString(),
                        binding.etDescriptionDetail.text.toString(),
                        currentProcedureId!!,
                        currentPatientId!!,
                        binding.tvDateDetail.text.toString(),
                        binding.tvTimeDetail.text.toString(),
                        true
                    )
                )
                    findNavController().popBackStack()
                }
            }
    }

    override fun onDestroyView() {
        Log.e("hghghg", "onDestroyView")
        Log.e("title", binding.etTitleDetail.text.toString())
        with(binding){
            title = etTitleDetail.text.toString()
            description = etDescriptionDetail.text.toString()
            date = tvDateDetail.text.toString()
            time = tvTimeDetail.text.toString()
        }
        super.onDestroyView()
    }
}
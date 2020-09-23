package com.example.converter.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.converter.R
import com.example.converter.databinding.FragmentFieldsBinding
import com.example.converter.viewModel.ConverterViewModel

class FieldsFragment : Fragment() {
    private lateinit var binding: FragmentFieldsBinding
    private lateinit var viewModel: ConverterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_fields,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(activity!!).get(ConverterViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.converterViewModel = viewModel

        ConfigureAdaptersWhenCreated()
    }

    fun ConfigureAdaptersWhenCreated(){
        // Getting saved state of types
        val state = viewModel.resources.GetSavedState()
        var state_changed = false

        //Configure adapters
        val types_adapter = CreateAdapter(viewModel.resources.Types)
        val from_adapter = CreateAdapter(viewModel.resources.SubTypes[state[0]])
        val to_adapter = CreateAdapter(viewModel.resources.SubTypes[state[0]])
        binding.types.adapter = types_adapter
        binding.typeFrom.adapter = from_adapter
        binding.typeTo.adapter = to_adapter

        // Use saved state for type-spinner
        binding.types.setSelection(state[0])

        var previous_type_selection = binding.types.selectedItem.toString()

        binding.types.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long){
                // Use saved state for spinner views
                if(!state_changed){
                    binding.typeFrom.setSelection(state[1])
                    binding.typeTo.setSelection(state[2])
                    state_changed=true
                }
                else{
                    val current_type_selection = binding.types.selectedItem.toString()
                    if(previous_type_selection != current_type_selection){
                        val changedType = parent.getItemAtPosition(position).toString()
                        val new_adapter = CreateAdapter(viewModel.resources.GetSubTypeOnType(changedType))
                        binding.typeFrom.adapter = new_adapter
                        binding.typeTo.adapter = new_adapter
                        previous_type_selection = current_type_selection
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }
        val subtypeOnItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long){
                val type = binding.types.selectedItem.toString()
                val from_value = binding.typeFrom.selectedItem.toString()
                val to_value = binding.typeTo.selectedItem.toString()
                viewModel.coefficient.value = viewModel.resources.GetCoeffitientOnTypes(
                    type, from_value, to_value
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }
        binding.typeFrom.onItemSelectedListener = subtypeOnItemSelectedListener
        binding.typeTo.onItemSelectedListener = subtypeOnItemSelectedListener

        binding.swapBtn.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {
                    // swap selected subtypes
                    val typeFrom = binding.typeFrom.selectedItemId.toInt()
                    binding.typeFrom.setSelection(binding.typeTo.selectedItemId.toInt())
                    binding.typeTo.setSelection(typeFrom)
                    // swap values in number-fields
                    viewModel.SwapValuesInFields()
                }
            })

        binding.copyFromBtn.setOnClickListener(GenerateOnCopyListener(true))
        binding.copyToBtn.setOnClickListener(GenerateOnCopyListener(false))
    }

    fun GenerateOnCopyListener(is_from: Boolean):View.OnClickListener{
        val obj = object: View.OnClickListener {
            override fun onClick(v: View?) {
                val myClipboard: ClipboardManager =
                    activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                lateinit var newClip : ClipData
                if(is_from)
                    newClip= ClipData.newPlainText("Converted data", binding.currFromValue.text)
                else
                    newClip= ClipData.newPlainText("Converted data", binding.currToValue.text)
                myClipboard.setPrimaryClip(newClip)
                val myToast = Toast.makeText(
                    activity!!,
                    "Value was succesfully copied!", Toast.LENGTH_SHORT
                ).show();
            }
        }
        return obj
    }

    fun CreateAdapter(resource: Array<String>): ArrayAdapter<String> {
        val adapter = ArrayAdapter(
            activity!!,
            android.R.layout.simple_spinner_item,
            resource
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        return adapter
    }
}
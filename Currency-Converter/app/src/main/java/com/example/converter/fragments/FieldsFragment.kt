package com.example.converter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.converter.R
import com.example.converter.databinding.FragmentFieldsBinding
import com.example.converter.viewModel.ConverterViewModel

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner


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
        val types_adapter = CreateAdapter(viewModel.resources.Types)
        val from_adapter = CreateAdapter(viewModel.resources.SubTypes[0])
        val to_adapter = CreateAdapter(viewModel.resources.SubTypes[0])
        binding.types.adapter = types_adapter
        binding.typeFrom.adapter = from_adapter
        binding.typeTo.adapter = to_adapter

        binding.types.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                val changedType = parent.getItemAtPosition(position).toString()
                val new_subtypes = viewModel.resources.GetSubTypeOnType(changedType)
                val new_adapter = CreateAdapter(new_subtypes)
                binding.typeFrom.adapter = new_adapter
                binding.typeTo.adapter = new_adapter
                viewModel.coefficient.value = viewModel.resources.GetCoeffitientOnTypes(
                    changedType, new_subtypes[0], new_subtypes[0]
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }
        val subtypeOnItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
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
    }

    fun CreateAdapter(resource: Array<String>): ArrayAdapter<String>{
        val adapter = ArrayAdapter(
            activity!!,
            android.R.layout.simple_spinner_item,
            resource
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        return adapter
    }
}
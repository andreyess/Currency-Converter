package com.example.converter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.converter.viewModel.ConverterViewModel
import com.example.converter.R

class KeyboardFragment : Fragment() {
    private lateinit var viewModel: ConverterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_keyboard, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(activity!!).get(ConverterViewModel::class.java)
        view?.findViewById<Button>(R.id.keyboard_0)
            ?.setOnClickListener{viewModel.OnNumberButtonClickHandler("0")}
        view?.findViewById<Button>(R.id.keyboard_1)
            ?.setOnClickListener{viewModel.OnNumberButtonClickHandler("1")}
        view?.findViewById<Button>(R.id.keyboard_2)
            ?.setOnClickListener{viewModel.OnNumberButtonClickHandler("2")}
        view?.findViewById<Button>(R.id.keyboard_3)
            ?.setOnClickListener{viewModel.OnNumberButtonClickHandler("3")}
        view?.findViewById<Button>(R.id.keyboard_4)
            ?.setOnClickListener{viewModel.OnNumberButtonClickHandler("4")}
        view?.findViewById<Button>(R.id.keyboard_5)
            ?.setOnClickListener{viewModel.OnNumberButtonClickHandler("5")}
        view?.findViewById<Button>(R.id.keyboard_6)
            ?.setOnClickListener{viewModel.OnNumberButtonClickHandler("6")}
        view?.findViewById<Button>(R.id.keyboard_7)
            ?.setOnClickListener{viewModel.OnNumberButtonClickHandler("7")}
        view?.findViewById<Button>(R.id.keyboard_8)
            ?.setOnClickListener{viewModel.OnNumberButtonClickHandler("8")}
        view?.findViewById<Button>(R.id.keyboard_9)
            ?.setOnClickListener{viewModel.OnNumberButtonClickHandler("9")}
        view?.findViewById<Button>(R.id.keyboard_0)
            ?.setOnClickListener{viewModel.OnNumberButtonClickHandler("0")}
        view?.findViewById<Button>(R.id.keyboard_000)
            ?.setOnClickListener{viewModel.OnNumberButtonClickHandler("000")}
        view?.findViewById<Button>(R.id.keyboard_clear)
            ?.setOnClickListener{viewModel.OnResetButtonClicked()}
        view?.findViewById<Button>(R.id.keyboard_delete)
            ?.setOnClickListener{viewModel.OnDeleteButtonClicked()}
        view?.findViewById<Button>(R.id.keyboard_float)
            ?.setOnClickListener{viewModel.OnDotButtonClicked()}
        view?.findViewById<Button>(R.id.keyboard_convert)
            ?.setOnClickListener{viewModel.OnConvertButtonClicked()}
        /*viewModel.getText().observe(getViewLifecycleOwner(), Observer {
            test.text=it
        })*/
    }

    fun LinkButtonOnClickHandlers(view: View, viewModel: ConverterViewModel){
        view.findViewById<Button>(R.id.keyboard_0)
            .setOnClickListener{viewModel.OnNumberButtonClickHandler("0")}
    }
}
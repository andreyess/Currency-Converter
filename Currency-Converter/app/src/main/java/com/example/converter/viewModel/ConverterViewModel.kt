package com.example.converter.viewModel

import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.converter.viewModel.functionality.Resources
import java.lang.Exception

class ConverterViewModel: ViewModel() {
    var coefficient = MutableLiveData<Double>()

    var current_from = MutableLiveData<String>()
    var current_to = MutableLiveData<String>()
    var is_double = MutableLiveData<Boolean>()

    val resources = Resources()

    init{
        coefficient.value = 0.84
        current_from.value = "0"
        current_to.value = "0"
        is_double.value = false
    }

    fun OnNumberButtonClickHandler(number: String){
        if(current_from.value=="0"){
            when(number){
                in arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9") -> current_from.value=number
                else -> return
            }
        }
        else{
            current_from.value += number
        }

    }

    fun OnDeleteButtonClicked(){
        if(current_from.value?.last()!='.')
            if(current_from.value == "0")
                return
            else
                current_from.value = current_from.value
                    ?.substring(0, current_from.value?.length?.minus(1) as Int)
        else{
            is_double.value=false
            current_from.value = current_from.value
                ?.substring(0, current_from.value?.length?.minus(1) as Int)
        }
    }

    fun OnResetButtonClicked(){
        current_from.value="0"
        current_to.value="0"
        is_double.value = false
    }

    fun OnDotButtonClicked(){
        if(is_double.value != true){
            is_double.value=true
            current_from.value += '.'
        }
    }

    fun OnConvertButtonClicked(){
        val coeff = coefficient.value
        try{
            val from_value = current_from.value?.toDouble()
            val converted_value = (from_value as Double) * (coeff as Double)
            current_to.value = converted_value.toString()
        }
        catch(ex: Exception){
            //Toast.makeText(this, "Androidly Long Toasts", Toast.LENGTH_LONG).show();
        }
    }
}
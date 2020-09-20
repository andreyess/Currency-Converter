package com.example.converter.viewModel.functionality

class Resources {
    val Types: Array<String> = arrayOf("Currency", "Weight", "Distance")
    val SubTypes: Array<Array<String>> =arrayOf(
        arrayOf("Euro", "Dollar", "Ruble"),
        arrayOf("Kg", "lb", "oz"),
        arrayOf("Meter", "Mile", "Yard")
    )
    val CoefficientsOnType: Array<Array<Array<Double>>> = arrayOf(
        arrayOf(
            arrayOf<Double>(1.0, 1.12, 80.0),
            arrayOf<Double>(0.88, 1.0, 67.0),
            arrayOf<Double>(0.02, 0.03, 1.0)
        ),
        arrayOf(
            arrayOf<Double>(1.0, 2.2, 35.27),
            arrayOf<Double>(0.45, 1.0, 16.0),
            arrayOf<Double>(0.028, 0.063, 1.0)
        ),
        arrayOf(
            arrayOf<Double>(1.0, 0.00062, 1.09),
            arrayOf<Double>(1609.34, 1.0, 1760.0),
            arrayOf<Double>(0.91, 0.00057, 1.0)
        )
    )

    fun GetSubTypeOnType(type:String): Array<String>{
        val typeIndex = Types.indexOf(type)
        return SubTypes[typeIndex]
    }

    fun GetCoeffitientOnTypes(type: String, from_type: String, to_type: String): Double{
        val typeIndex = Types.indexOf(type)
        val fromTypeIndex = SubTypes[typeIndex].indexOf(from_type)
        val toTypeIndex = SubTypes[typeIndex].indexOf(to_type)
        return CoefficientsOnType[typeIndex][fromTypeIndex][toTypeIndex]
    }
}
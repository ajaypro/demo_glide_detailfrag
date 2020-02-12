package com.example.android.marsrealestate.overview

import com.example.android.marsrealestate.network.MarsProperty

class MarsPropertyClickListener(val clickListener: (marsProperty: MarsProperty) -> Unit) {

    fun onClick(marsProperty: MarsProperty) = clickListener(marsProperty)


}
/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.MarsApiStatus
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.network.Networking
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _property = MutableLiveData<List<MarsProperty>>()

    // The external immutable LiveData for the response String
    val property: LiveData<List<MarsProperty>>
        get() = _property

    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status

    private val _navigateToDetailFrag = MutableLiveData<MarsProperty>()
    val navigateToDetailFrag : LiveData<MarsProperty> = _navigateToDetailFrag


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private lateinit var marsList: List<MarsProperty>

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {

        uiScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                Networking.retrofitService.getProperties().await().run {
                    _status.value = MarsApiStatus.DONE
                    _property.value = this
                    marsList = this
                }
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _property.value = emptyList()
            }
        }

    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {

        when (filter) {
            MarsApiFilter.SHOW_RENT -> _property.value = marsList.filter { it.isRental }
            MarsApiFilter.SHOW_BUY -> _property.value = marsList.filter { !it.isRental }
        }
    }

    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }

    fun displayMarsPropertyDetails(marsProperty: MarsProperty){
        _navigateToDetailFrag.value = marsProperty
    }

    fun displayMarsPropertyDetailsComplete(){
        _navigateToDetailFrag.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

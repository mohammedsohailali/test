package com.disney.ui.component.mainlist

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.disney.data.DataRepositorySource
import com.disney.data.Resource
import com.disney.data.model.NYCGuesttem
import com.disney.ui.base.BaseViewModel
import com.disney.utils.SectionItem
import com.disney.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GuestViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : BaseViewModel() {

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val guestListLiveDataPrivate = MutableLiveData<Resource<NYCGuesttem>>()
    val guestListLiveData: LiveData<Resource<NYCGuesttem>> get() = guestListLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val enableButtonLiveDataPrivate = MutableLiveData<Boolean>()
    val enableButtonLiveData: LiveData<Boolean> get() = enableButtonLiveDataPrivate

    /**
     * Error handling as UI
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate


    fun getGuestList() {
        viewModelScope.launch {
            guestListLiveDataPrivate.value = Resource.Loading()
            dataRepositoryRepository.fetchList().collect {
                guestListLiveDataPrivate.value = it
            }
        }
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }

    fun manageContinueButton(list: ArrayList<SectionItem>) {
        if (list.isEmpty()) {
            enableButtonLiveDataPrivate.postValue(false)
        }
        val hasReservationData: SectionItem? = list.find {
            it.hasReservation == true
        }
        val needReservationData: SectionItem? = list.find {
            it.hasReservation == false
        }

        if (hasReservationData != null && needReservationData != null) {
            enableButtonLiveDataPrivate.postValue(true)
        } else {
            enableButtonLiveDataPrivate.postValue(false)
        }
    }
}

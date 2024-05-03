package ie.setu.mediapp.view.ui.homeDr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeDrViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Dr home Fragment"
    }
    val text: LiveData<String> = _text
}
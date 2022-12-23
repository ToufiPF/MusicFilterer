package ch.epfl.musicfilterer.viewmodel.fragment

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.core.content.res.ResourcesCompat.ID_NULL
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.epfl.musicfilterer.R
import ch.epfl.musicfilterer.ui.fragment.BottomBarFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

// AndroidViewModel is a subclass of ViewModel that provides application's Context
/**
 * ViewModel for all activities that want to hold a [BottomBarFragment]
 */
@HiltViewModel
class BottomBarHolderViewModel @Inject constructor() : ViewModel() {

    private val _layout = MutableStateFlow(ID_NULL)

    init {
        _layout.value = R.layout.fragment_bottom_bar_2
    }

    @LayoutRes
    val layout: StateFlow<Int> = _layout

    fun onActivityCreate(supportFragmentManager: FragmentManager, savedInstanceState: Bundle?) {
        if (savedInstanceState != null)
            return

        supportFragmentManager.commit {
            val fragment = BottomBarFragment(layout.value)

            setReorderingAllowed(true)
            replace(R.id.bottom_bar_container, fragment)
        }
    }
}

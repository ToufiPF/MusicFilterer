package ch.epfl.musicfilterer.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ch.epfl.musicfilterer.viewmodel.fragment.BottomBarHolderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomBarFragment(
    @LayoutRes
    private val layoutRes: Int
) : Fragment(layoutRes) {

    private val viewModel: BottomBarHolderViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener {
            viewModel.updateSizeMode(BottomBarHolderViewModel.Mode.MEDIUM)
        }
    }
}

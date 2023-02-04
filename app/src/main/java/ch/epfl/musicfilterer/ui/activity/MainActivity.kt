package ch.epfl.musicfilterer.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import ch.epfl.musicfilterer.R
import ch.epfl.musicfilterer.databinding.ActivityMainBinding
import ch.epfl.musicfilterer.ui.fragment.PlaylistFragment
import ch.epfl.musicfilterer.viewmodel.fragment.BottomBarHolderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val bottomBarViewModel: BottomBarHolderViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomBarViewModel.onActivityCreate(supportFragmentManager, savedInstanceState)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            val fragment = PlaylistFragment()
            replace(R.id.playlist_container, fragment)
        }

        lifecycleScope.launch {
            // Setup the observer that replaces the fragment's layout (eg. on click)
            val observer = bottomBarViewModel.updateFragmentObserver(supportFragmentManager)
            bottomBarViewModel.layout.collect(observer)
        }
    }
}

package com.themohitrao.main.impl


import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.themohitrao.core_models.JokeDataModel
import com.themohitrao.core_ui.BaseEdgeEffectFactory
import com.themohitrao.core_ui.playSafeAnimation
import com.themohitrao.core_ui.setDebounceClickListener
import com.themohitrao.core_ui.snackBar
import com.themohitrao.feature_main.R
import com.themohitrao.feature_main.databinding.FragmentMainBinding
import com.themohitrao.main_common.events.NextJokeIn
import com.themohitrao.main_common.events.RefreshJokeList
import com.themohitrao.main_common.worker_util.SyncWorkName
import com.themohitrao.main_common.worker_util.isWorkScheduled
import com.themohitrao.main_common.worker_util.startJokeSyncWorker
import com.themohitrao.main_common.worker_util.stopJokeSyncWorker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    private val mainViewModel by viewModel<MainViewModel>()
    private val jokeListAdapter: JokeListAdapter by inject()
    private val baseEdgeEffectFactory: BaseEdgeEffectFactory by inject()

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMainBinding.inflate(inflater).also {
            setUp(it)
            setUpListeners(it)
            observeData(it)
            getData()
        }.root
    }

    private fun setUp(binding: FragmentMainBinding) {
        binding.rvJokes.adapter = jokeListAdapter
        binding.rvJokes.edgeEffectFactory = baseEdgeEffectFactory
        setSyncButtonText(binding)
    }

    private fun setUpListeners(binding: FragmentMainBinding) {
        binding.btnSync.setDebounceClickListener {
            if (isWorkScheduled(SyncWorkName, requireContext())) {
                binding.tvBtnText.text = getString(R.string.start_fetching_jokes)
                stopJokeSyncWorker(requireContext())
                lifecycleScope.launch {
                    delay(2000)
                    binding.tvNextJokeIn.text = ""
                }
            } else {
                binding.tvBtnText.text = getString(R.string.stop_fetching_jokes)
                startJokeSyncWorker(requireContext())
            }
        }

        binding.animationViewTitle.setDebounceClickListener {
            binding.animationViewTitle.playSafeAnimation()
            playSound()
        }

    }

    private fun observeData(binding: FragmentMainBinding) {
        mainViewModel.userList.observe(viewLifecycleOwner) {
            processNewDataList(binding,it)
        }
        mainViewModel.errorValue.observe(viewLifecycleOwner) {
            (it?.toString() ?: getString(R.string.something_went_wrong)).snackBar(binding.root)
        }
        mainViewModel.nextJokeInSeconds.observe(viewLifecycleOwner) {
            it?.let {
                processJokeInTime(binding, it)
            }
        }
    }

    private fun processNewDataList(binding: FragmentMainBinding, it: List<JokeDataModel>?) {
        jokeListAdapter.submitList(it?.toMutableList())
        binding.rvJokes.isVisible = !it.isNullOrEmpty()
        binding.animationSearchingJokes.isVisible = it.isNullOrEmpty()
        if (!it.isNullOrEmpty()) {
            lifecycleScope.launch {
                delay(1000)
                binding.rvJokes.scrollToPosition(0)
            }
            binding.animationView3.playSafeAnimation()
        } else {
            binding.animationSearchingJokes.playSafeAnimation()
        }
    }

    private fun processJokeInTime(binding: FragmentMainBinding, secondsToNextJoke: Int) {
        lifecycleScope.launch {
            delay(1500)
            binding.tvNextJokeIn.text = if (secondsToNextJoke == 0) {
                binding.animationViewTitle.playSafeAnimation()
                playSound()
                getString(R.string.and_here_is_the_joke_)
            } else {
                String.format(
                    getString(R.string.next_joke_in_s_seconds),
                    secondsToNextJoke.toString()
                )
            }
        }
    }


    private fun getData() {
        mainViewModel.fetchUsers()
    }

    private fun setSyncButtonText(binding: FragmentMainBinding) {
        binding.tvBtnText.text = if (isWorkScheduled(SyncWorkName, requireContext())) {
            getString(R.string.stop_fetching_jokes)
        } else {
            getString(R.string.start_fetching_jokes)
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefreshList(event: RefreshJokeList) {
        getData()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNextJokeIn(nextJokeEvent: NextJokeIn) {
        mainViewModel.nextJokeIn(nextJokeEvent.timeInSeconds)
    }

    private fun playSound() {
        val mediaPlayer: MediaPlayer =
            MediaPlayer.create(context, com.themohitrao.feature_main_common.R.raw.joke_drum_effect)
        mediaPlayer.start()
    }
}
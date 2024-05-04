package com.kader.kotlin_hangman.ui.score


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kader.kotlin_hangman.databinding.FragmentScoresBinding
import com.kader.kotlin_hangman.ui.BaseFragment
import com.kader.kotlin_hangman.util.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoresFragment(override val screenName: String = ScreenName.SCORES_SCREEN) :
    BaseFragment<FragmentScoresBinding, ScoresViewModel>() {
    override fun initView() {
        viewModel.allScores.observe(viewLifecycleOwner) { scores ->

        }
    }

    override val viewModel by viewModels<ScoresViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScoresBinding = FragmentScoresBinding.inflate(inflater, container, false)
}
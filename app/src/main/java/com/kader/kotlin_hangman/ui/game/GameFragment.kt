package com.kader.kotlin_hangman.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kader.kotlin_hangman.databinding.FragmentGameBinding
import com.kader.kotlin_hangman.ui.BaseFragment
import com.kader.kotlin_hangman.util.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment(override val screenName: String = ScreenName.GAME_SCREEN) :
    BaseFragment<FragmentGameBinding, GameViewModel>() {
    override fun initView() {

    }

    override val viewModel by viewModels<GameViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameBinding = FragmentGameBinding.inflate(inflater, container, false)
}


package com.kader.kotlin_hangman.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kader.kotlin_hangman.databinding.FragmentWelcomeBinding
import com.kader.kotlin_hangman.util.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment(override val screenName: String = ScreenName.WELCOME_SCREEN) :
    BaseFragment<FragmentWelcomeBinding, WelcomeViewModel>() {
    override fun initView() {

    }

    override val viewModel by viewModels<WelcomeViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWelcomeBinding = FragmentWelcomeBinding.inflate(inflater, container, false)
}
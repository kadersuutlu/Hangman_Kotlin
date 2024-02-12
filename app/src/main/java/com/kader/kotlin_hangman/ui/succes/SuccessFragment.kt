package com.kader.kotlin_hangman.ui.succes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kader.kotlin_hangman.databinding.FragmentSuccessBinding
import com.kader.kotlin_hangman.ui.BaseFragment
import com.kader.kotlin_hangman.util.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessFragment(override val screenName: String = ScreenName.SUCCESS_SCREEN) :
    BaseFragment<FragmentSuccessBinding, SuccessViewModel>() {
    override fun initView() {

    }

    override val viewModel by viewModels<SuccessViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSuccessBinding = FragmentSuccessBinding.inflate(inflater, container, false)
}
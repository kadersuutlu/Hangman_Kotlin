package com.kader.kotlin_hangman.ui.success

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.databinding.FragmentSuccessBinding
import com.kader.kotlin_hangman.ui.BaseFragment
import com.kader.kotlin_hangman.ui.game.GameFragment
import com.kader.kotlin_hangman.util.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessFragment(override val screenName: String = ScreenName.SUCCESS_SCREEN) :
    BaseFragment<FragmentSuccessBinding, SuccessViewModel>() {
    override fun initView() {
        binding.buttonSucces.setOnClickListener {
            val gameFragment = GameFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, gameFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override val viewModel by viewModels<SuccessViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSuccessBinding = FragmentSuccessBinding.inflate(inflater, container, false)
}
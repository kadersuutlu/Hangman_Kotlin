package com.kader.kotlin_hangman.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.databinding.FragmentWelcomeBinding
import com.kader.kotlin_hangman.ui.BaseFragment
import com.kader.kotlin_hangman.ui.score.ScoresFragment
import com.kader.kotlin_hangman.util.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment(override val screenName: String = ScreenName.WELCOME_SCREEN) :
    BaseFragment<FragmentWelcomeBinding, WelcomeViewModel>() {
    override fun initView() {

        binding.buttonOk.setOnClickListener {
            viewModel.setUserName(binding.editTextName.text.toString())

            showFailedFragment()

            findNavController().navigate(R.id.action_welcomeFragment_to_startFragment)
        }
    }

    private fun showFailedFragment() {
        val failedFragment = ScoresFragment()
        val bundle = Bundle()
        bundle.putString("userName", viewModel.userName.value)
        failedFragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, failedFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override val viewModel by viewModels<WelcomeViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWelcomeBinding = FragmentWelcomeBinding.inflate(inflater, container, false)
}
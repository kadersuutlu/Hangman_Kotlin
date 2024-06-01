package com.kader.kotlin_hangman.ui.score


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.databinding.FragmentScoresBinding
import com.kader.kotlin_hangman.ui.BaseFragment
import com.kader.kotlin_hangman.ui.adapter.ScoresAdapter
import com.kader.kotlin_hangman.util.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoresFragment(override val screenName: String = ScreenName.SCORES_SCREEN) :
    BaseFragment<FragmentScoresBinding, ScoresViewModel>() {

    private var userName: String? = null

    private lateinit var adapter: ScoresAdapter
    override fun initView() {

        adapter = ScoresAdapter(userName)
        binding.scoreRecylerView.adapter = adapter
        binding.scoreRecylerView.layoutManager = LinearLayoutManager(context)

        viewModel.allScores.observe(viewLifecycleOwner) { scores ->
            scores.let { adapter.setScores(it) }
        }

        arguments?.let {
            userName = it.getString("userName")
        }

        binding.userButton.text = userName
    }

    override val viewModel by viewModels<ScoresViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScoresBinding = FragmentScoresBinding.inflate(inflater, container, false)
}
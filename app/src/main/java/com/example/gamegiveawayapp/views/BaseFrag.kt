package com.example.gamegiveawayapp.views

import androidx.fragment.app.Fragment
import com.example.gamegiveawayapp.adapter.GiveawaysAdapter
import com.example.gamegiveawayapp.viewmodel.GiveawaysViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseFrag : Fragment() {
    protected val giveawaysViewModel: GiveawaysViewModel by sharedViewModel()
    protected val giveawaysAdapter by lazy {
        GiveawaysAdapter()
    }
}
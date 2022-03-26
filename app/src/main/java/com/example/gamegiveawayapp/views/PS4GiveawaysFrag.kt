package com.example.gamegiveawayapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamegiveawayapp.R
import com.example.gamegiveawayapp.databinding.FragmentPCGiveawaysBinding
import com.example.gamegiveawayapp.databinding.FragmentPS4GiveawaysBinding
import com.example.gamegiveawayapp.model.Giveaways
import com.example.gamegiveawayapp.utils.GiveawaysState

class PS4GiveawaysFrag : BaseFrag() {

    private val binding by lazy {
        FragmentPS4GiveawaysBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding.ps4RecView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = giveawaysAdapter
        }

        giveawaysViewModel.giveaways.observe(viewLifecycleOwner) { state ->
            when(state) {
                is GiveawaysState.LOADING -> {
                    Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_LONG).show()
                }
                is GiveawaysState.SUCCESS<*> -> {
                    val giveaways = state.giveaways as List<Giveaways>
                    giveawaysAdapter.setNewGiveaways(giveaways)
                }
                is GiveawaysState.ERROR -> {
                    Toast.makeText(requireContext(), state.error.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

        giveawaysViewModel.getGiveawaysByPlatform()

        return binding.root
    }
}
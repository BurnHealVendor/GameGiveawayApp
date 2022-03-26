package com.example.gamegiveawayapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamegiveawayapp.R
import com.example.gamegiveawayapp.adapter.GiveawaysAdapter
import com.example.gamegiveawayapp.adapter.OnItemClickListener
import com.example.gamegiveawayapp.databinding.FragmentGiveawaysBinding
import com.example.gamegiveawayapp.model.Giveaways
import com.example.gamegiveawayapp.utils.GiveawaysState
import com.example.gamegiveawayapp.utils.PlatformType

class GiveawaysFrag : BaseFrag(), OnItemClickListener {

    private val binding by lazy {
        FragmentGiveawaysBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.giveawayRecView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = giveawaysAdapter
        }

        giveawaysViewModel.giveaways.observe(viewLifecycleOwner) { state ->
            when(state) {
                is GiveawaysState.LOADING -> {
                    Toast.makeText(requireContext(), "loading...", Toast.LENGTH_LONG).show()
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

        giveawaysViewModel.getSortedGiveaways()

        binding.pcBtn.setOnClickListener() {
            giveawaysViewModel.platform = PlatformType.PC
            findNavController().navigate(R.id.action_giveawaysFrag_to_PCGiveawaysFrag)
        }

        binding.ps4Btn.setOnClickListener() {
            giveawaysViewModel.platform = PlatformType.PS4
            findNavController().navigate(R.id.action_giveawaysFrag_to_PS4GiveawaysFrag)
        }

        return binding.root
    }

    override fun onItemClicked(position: Int) {
        val clickedEvent = giveawaysAdapter.getEventData(position)
        findNavController().navigate(R.id.action_giveawaysFrag_to_)
    }
}
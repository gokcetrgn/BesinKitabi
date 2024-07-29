package com.gokcenaztorgan.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.gokcenaztorgan.besinkitabi.databinding.FragmentBesinDetayBinding
import com.gokcenaztorgan.besinkitabi.util.indir
import com.gokcenaztorgan.besinkitabi.util.placeHolderYap
import com.gokcenaztorgan.besinkitabi.viewmodel.BesinDetayViewModel

class BesinDetayFragment : Fragment() {
    private var _binding: FragmentBesinDetayBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    var besinId = 0
    private lateinit var detayViewModel: BesinDetayViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBesinDetayBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detayViewModel = ViewModelProvider(this)[BesinDetayViewModel::class.java]

        arguments?.let {
            besinId = BesinDetayFragmentArgs.fromBundle(it).besinId

        }
        detayViewModel.roomVerisiniAl(besinId)
        liveDataObserver()
    }

    private fun liveDataObserver(){
        detayViewModel.besinLiveData.observe(viewLifecycleOwner){ besin->
            binding.besinIsmi.text = besin?.besinIsim
            binding.besinKalorisi.text = besin?.kalori
            binding.besinKarbonhidrat.text = besin?.karbonhidrat
            binding.besinProtein.text  = besin?.protein
            binding.besinYag.text = besin?.yag
            binding.besinGorseli.indir(besin?.gorsel, placeHolderYap(requireContext()))
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
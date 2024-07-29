package com.gokcenaztorgan.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gokcenaztorgan.besinkitabi.adapter.BesinAdapter
import com.gokcenaztorgan.besinkitabi.databinding.FragmentBesinListeBinding
import com.gokcenaztorgan.besinkitabi.viewmodel.BesinListeViewModel


class BesinListeFragment : Fragment() {
    private val besinAdapter = BesinAdapter(arrayListOf())
    private var _binding: FragmentBesinListeBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private lateinit var besinViewModel : BesinListeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBesinListeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        besinViewModel = ViewModelProvider(this)[BesinListeViewModel::class.java]
        besinViewModel.veriGuncelle()

        // adapter
        binding.besinRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.besinRecyclerView.adapter = besinAdapter

        binding.swipeRefreshId.setOnRefreshListener {
            binding.hata.visibility = View.GONE
            binding.besinRecyclerView.visibility = View.GONE
            binding.besinYukleniyor.visibility = View.VISIBLE
            besinViewModel.intVerileriGuncelle()
            binding.swipeRefreshId.isRefreshing = false
        }
        observeLiveData()

    }
    private fun observeLiveData(){
        besinViewModel.besinler.observe(viewLifecycleOwner){
            // adapter
            besinAdapter.besinListGuncelle(it)

            binding.besinRecyclerView.visibility = View.VISIBLE
        }
        besinViewModel.besinHataMesaji.observe(viewLifecycleOwner){
            if(it){
                binding.hata.visibility = View.VISIBLE
                binding.besinYukleniyor.visibility  = View.GONE
            }else{
                binding.hata.visibility = View.GONE
            }
        }
        besinViewModel.besinYuklenmesi.observe(viewLifecycleOwner) {
            if (it) {
                binding.hata.visibility = View.GONE
                binding.besinRecyclerView.visibility = View.GONE
                binding.besinYukleniyor.visibility = View.VISIBLE
            } else {
                binding.besinYukleniyor.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
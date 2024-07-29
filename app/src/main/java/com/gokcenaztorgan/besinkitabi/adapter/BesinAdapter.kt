package com.gokcenaztorgan.besinkitabi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gokcenaztorgan.besinkitabi.databinding.BesinRecyclerRowBinding
import com.gokcenaztorgan.besinkitabi.model.Besin
import com.gokcenaztorgan.besinkitabi.util.indir
import com.gokcenaztorgan.besinkitabi.util.placeHolderYap
import com.gokcenaztorgan.besinkitabi.view.BesinListeFragmentDirections

class BesinAdapter(val besinList : ArrayList<Besin>) : RecyclerView.Adapter<BesinAdapter.BesinViewHolder>() {
    class BesinViewHolder(val binding : BesinRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val binding = BesinRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BesinViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return besinList.size
    }

    fun besinListGuncelle(yeniListe : List<Besin>){
        besinList.clear()
        besinList.addAll(yeniListe)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.binding.isim.text = besinList[position].besinIsim
        holder.binding.kalori.text = besinList[position].kalori
        holder.itemView.setOnClickListener{
            val action = BesinListeFragmentDirections.actionBesinListeFragmentToBesinDetayFragment2(besinList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.imageView.indir(besinList[position].gorsel, placeHolderYap(holder.itemView.context))
    }
}
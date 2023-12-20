package com.example.capstone_yogain.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_yogain.data.model.PoseData
import com.example.capstone_yogain.data.model.PoseModel
import com.example.capstone_yogain.databinding.ItemPosesBinding

class HomeAdapter : PagingDataAdapter<PoseModel, HomeAdapter.HomeViewHolder>(DIFF_CALLBACK){

    var onItemClickCallback : OnItemCLickCallback ?= null
    
    inner class HomeViewHolder(private val binding : ItemPosesBinding) : RecyclerView.ViewHolder(binding.root){
        fun getPoses(item : PoseModel){
            with(binding){
                ivItem.setImageResource(item.imagePose)
                tvNamePose.text = item.poseName
                tvNumberMin.text = item.minutes
                
                root.setOnClickListener { 
                    onItemClickCallback?.onItemClicked(item)
                }
            }
            
        }
    }

    interface OnItemCLickCallback {
        fun onItemClicked(pose: PoseModel)
    }
    
    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        getItem(position)?.let { holder.getPoses(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val binding = ItemPosesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }
    
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PoseModel>() {
            override fun areItemsTheSame(oldItem: PoseModel, newItem: PoseModel): Boolean {
                return oldItem == newItem            }

            override fun areContentsTheSame(oldItem: PoseModel, newItem: PoseModel): Boolean {
                return oldItem.id == newItem.id
            }
            
        }
    }
}
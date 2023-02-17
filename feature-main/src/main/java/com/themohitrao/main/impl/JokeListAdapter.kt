package com.themohitrao.main.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.themohitrao.core_models.JokeDataModel
import com.themohitrao.feature_main.databinding.CellUserInfoBinding

class JokeListAdapter :
    ListAdapter<JokeDataModel, JokeViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<JokeDataModel>() {
            override fun areItemsTheSame(oldItem: JokeDataModel, newItem: JokeDataModel
            ): Boolean {
                return oldItem.downloadTimeStamp == newItem.downloadTimeStamp
            }

            override fun areContentsTheSame(oldItem: JokeDataModel, newItem: JokeDataModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JokeViewHolder {
        return JokeViewHolder(
            CellUserInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        getItem(position)?.let {
            holder.setValues(it)
        }
    }
}

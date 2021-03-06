/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.overview.MarsPropertyViewHolder.Companion.from
import kotlinx.android.synthetic.main.grid_view_item.view.*

class PhotoGridAdapter(val marsPropertyClickListener: MarsPropertyClickListener)
    : ListAdapter<MarsProperty, MarsPropertyViewHolder>(DiffUtilCallBack) {

    companion object DiffUtilCallBack : DiffUtil.ItemCallback<MarsProperty>() {

        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent)

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        holder.bind(getItem(position), marsPropertyClickListener)
    }
}

class MarsPropertyViewHolder(val gridViewItemBinding: GridViewItemBinding) :
        RecyclerView.ViewHolder(gridViewItemBinding.root) {

    companion object {
        fun from(parent: ViewGroup) = MarsPropertyViewHolder(GridViewItemBinding
                .inflate(LayoutInflater.from(parent.context)))
    }

    fun bind(property: MarsProperty, clickListener: MarsPropertyClickListener) {
        gridViewItemBinding.property = property
        gridViewItemBinding.marsPropertyListner = clickListener
        gridViewItemBinding.executePendingBindings()

    }
}

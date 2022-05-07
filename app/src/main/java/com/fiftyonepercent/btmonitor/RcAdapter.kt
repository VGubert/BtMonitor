package com.fiftyonepercent.btmonitor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fiftyonepercent.btmonitor.databinding.ListItemBinding

class RcAdapter(private val listener: Listener) : ListAdapter<ListItem, RcAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view) //view превращаем в bind
        fun setData(item: ListItem, listener: Listener) = with(binding) {
            tvName.text = item.name
            tvMac.text = item.mac
            itemView.setOnClickListener{
                listener.onClick(item)
            }
        }
        companion object {
            fun create(parent: ViewGroup) : ItemHolder { //будет возвращать иницилизированный ItemHolder
                return ItemHolder(LayoutInflater.
                from(parent.context).
                inflate(R.layout.list_item, parent, false)) //надуваем разметку
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ListItem>(){ //сравнивает элементы
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.mac == newItem.mac //сравниваем mac адресы, если одинаковые возвращает true, нет false
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem //сравнивает все элементы
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent) //каждый раз когда создается новый элемент
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) { //заполняется
        holder.setData(getItem(position), listener)
    }

    interface Listener{
        fun onClick(item: ListItem)
    }
}
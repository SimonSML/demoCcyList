package demo.currency.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import demo.currency.myapplication.databinding.ItemCurrencyListBinding
import demo.currency.myapplication.interfaces.CurrencyInfoItemClickListener
import demo.currency.myapplication.model.CurrencyInfo

class CurrencyListAdapter(private val itemClickListener: CurrencyInfoItemClickListener?) :
    ListAdapter<CurrencyInfo, CurrencyListAdapter.CurrencyInfoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyInfoViewHolder {
        return CurrencyInfoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CurrencyInfoViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    class CurrencyInfoViewHolder(private val binding: ItemCurrencyListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CurrencyInfo, itemClickListener: CurrencyInfoItemClickListener?) {
            binding.currencyInfo = item
            binding.root.setOnClickListener { itemClickListener?.onItemClick(item) }
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): CurrencyInfoViewHolder {
                return CurrencyInfoViewHolder(
                    ItemCurrencyListBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<CurrencyInfo>() {
        override fun areItemsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
            return oldItem == newItem
        }
    }
}





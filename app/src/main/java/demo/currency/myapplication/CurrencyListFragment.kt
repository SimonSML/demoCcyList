package demo.currency.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import demo.currency.myapplication.adapter.CurrencyListAdapter
import demo.currency.myapplication.databinding.FragmentCurrencyListBinding
import demo.currency.myapplication.interfaces.CurrencyInfoItemClickListener
import demo.currency.myapplication.model.CurrencyInfo

private const val KEY_CURRENCY_LIST = "currency_list"

class CurrencyListFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyListBinding
    private lateinit var onItemClickListener: CurrencyInfoItemClickListener
    private val currencyInfoList: ArrayList<CurrencyInfo> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let { bundle ->
            bundle.getSerializable(KEY_CURRENCY_LIST)?.let {
                currencyInfoList.clear()
                currencyInfoList.addAll(it as Array<CurrencyInfo>)
            }
        }
        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        val adapter = CurrencyListAdapter(onItemClickListener)
        binding.recyclerview.adapter = adapter
        adapter.submitList(currencyInfoList)
        return binding.root
    }

    fun updateList(list: ArrayList<CurrencyInfo>) {
        currencyInfoList.clear()
        currencyInfoList.addAll(list)
        binding.recyclerview.adapter?.notifyItemRangeChanged(0, list.size)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CurrencyInfoItemClickListener) {
            onItemClickListener = context
        } else throw Exception("${context.javaClass.name} need to implement CurrencyInfoItemClickListener")
    }

    companion object {
        @JvmStatic
        fun newInstance(data: ArrayList<CurrencyInfo>): CurrencyListFragment {
            return CurrencyListFragment().apply {
                arguments = bundleOf(KEY_CURRENCY_LIST to data)
            }
        }
    }
}
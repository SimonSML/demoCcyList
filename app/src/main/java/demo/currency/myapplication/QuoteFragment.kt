package demo.currency.myapplication

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import demo.currency.myapplication.databinding.FragmentQuoteBinding
import demo.currency.myapplication.model.CurrencyQuote
import demo.currency.myapplication.model.TradeSide
import demo.currency.myapplication.utils.FormatUtils
import demo.currency.myapplication.viewModels.QuoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormatSymbols


class QuoteFragment : Fragment() {

    lateinit var binding: FragmentQuoteBinding
    private val quoteViewModel: QuoteViewModel by viewModel()
    private lateinit var textWatcher: TextWatcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuoteBinding.inflate(inflater)
        arguments?.let {
            if (!it.isEmpty && it.containsKey(KEY_QUOTE_INFO)) {
                binding.quoteInfo = it[KEY_QUOTE_INFO] as CurrencyQuote
            }
        }
        with(binding) {
            viewModel = quoteViewModel
            lifecycleOwner = viewLifecycleOwner
            buySellTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        quoteViewModel.selectSide(if (it.position == 0) TradeSide.BUY else TradeSide.SELL)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
            inputPrice.doAfterTextChanged {
                applyNumberFormatToField(inputPrice)
            }.also { textWatcher = it }
        }

        return binding.root
    }

    private fun applyNumberFormatToField(editText: EditText) {
        editText.apply {
            removeTextChangedListener(textWatcher)
            val beforeFormatLen = text.length
            val cursorPosition = selectionStart
            val formatted = FormatUtils.formatStringNumber2dp(text.toString())
            val decimalSeparator = DecimalFormatSymbols.getInstance().decimalSeparator
            if (text.isNotEmpty() && text.indexOf(decimalSeparator) == text.lastIndex) {
                setText(formatted + DecimalFormatSymbols.getInstance().decimalSeparator)
            } else {
                setText(formatted)
            }
            val afterFormatLen = text.length
            val selection = cursorPosition + (afterFormatLen - beforeFormatLen)
            if (selection in 1..afterFormatLen) setSelection(selection) else setSelection(
                afterFormatLen
            )
            addTextChangedListener(textWatcher)
            quoteViewModel.setPrice(formatted)
        }
    }

    companion object {
        private const val KEY_QUOTE_INFO = "key_quote_info"

        @JvmStatic
        fun newInstance(data: CurrencyQuote): QuoteFragment {
            return QuoteFragment().apply {
                arguments = bundleOf(KEY_QUOTE_INFO to data)
            }
        }
    }

}
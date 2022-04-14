package demo.currency.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import demo.currency.myapplication.databinding.ActivityMainBinding
import demo.currency.myapplication.interfaces.CurrencyInfoItemClickListener
import demo.currency.myapplication.model.CurrencyInfo
import demo.currency.myapplication.model.CurrencyQuote
import demo.currency.myapplication.utils.SafeClickListener
import demo.currency.myapplication.viewModels.DemoActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal

class DemoActivity : AppCompatActivity(), CurrencyInfoItemClickListener {

    private val demoActivityViewModel: DemoActivityViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.btnLoadData.setOnClickListener(SafeClickListener { demoActivityViewModel.fetchCurrencyList() })
        binding.btnSort.setOnClickListener(SafeClickListener { demoActivityViewModel.sortCurrentList() })
        demoActivityViewModel.currencyInfoList.observe(
            this,
            this::showCurrencyList
        )
        demoActivityViewModel.disableSorting.observe(
            this
        ) { disabled -> binding.btnSort.isEnabled = !disabled }
    }

    private fun showCurrencyList(currencyList: ArrayList<CurrencyInfo>) {
        supportFragmentManager.findFragmentById(R.id.fl_content)?.let {
            if (it is CurrencyListFragment) {
                it.updateList(currencyList)
            }
        } ?: supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, CurrencyListFragment.newInstance(currencyList))
            .addToBackStack("HOME").commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            val tagName =
                supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 2).name
            if (tagName == "HOME") {
                binding.btnLoadData.visibility = View.VISIBLE
                binding.btnSort.visibility = View.VISIBLE
            }
            super.onBackPressed()
        } else {
            finish()
        }
    }

    override fun onItemClick(item: CurrencyInfo) {
        //TODO fetch data
        val quoteInfo = CurrencyQuote(
            "EX",
            "USD",
            item.symbol,
            item.name,
            BigDecimal(1244.149),
            BigDecimal(-19.2),
            BigDecimal(0.15),
            BigDecimal(1200.100),
            BigDecimal(1288.780),
            BigDecimal(1188.238),
            null,
            BigDecimal(1235678)
        )
        binding.btnLoadData.visibility = View.GONE
        binding.btnSort.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, QuoteFragment.newInstance(quoteInfo))
            .addToBackStack("QuoteFragment")
            .commit()
    }
}
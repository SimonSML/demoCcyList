package demo.currency.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import demo.currency.myapplication.databinding.ActivityMainBinding
import demo.currency.myapplication.interfaces.CurrencyInfoItemClickListener
import demo.currency.myapplication.model.CurrencyInfo
import demo.currency.myapplication.utils.SafeClickListener
import demo.currency.myapplication.viewModels.DemoActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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
            .replace(R.id.fl_content, CurrencyListFragment.newInstance(currencyList)).commit()
    }

    override fun onItemClick(item: CurrencyInfo) {
        Toast.makeText(this, "item clicked: ${item.name}", Toast.LENGTH_SHORT).show()
    }
}
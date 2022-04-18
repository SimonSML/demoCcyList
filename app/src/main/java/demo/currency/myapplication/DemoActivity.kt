package demo.currency.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
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
            this::displayCurrencyListFromNav
        )
        demoActivityViewModel.disableSorting.observe(
            this
        ) { disabled -> binding.btnSort.isEnabled = !disabled }


    }

    @Deprecated(
        "Not used any more , switch to use navigation graph functions",
        ReplaceWith("displayCurrencyListFromNav()")
    )
    private fun showCurrencyList(currencyList: ArrayList<CurrencyInfo>) {
        supportFragmentManager.findFragmentById(R.id.fl_content)?.let {
            if (it is CurrencyListFragment) {
                it.updateList(currencyList)
            }
        } ?: supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, CurrencyListFragment.newInstance(currencyList))
            .addToBackStack("HOME").commit()
    }

    override fun onStart() {
        super.onStart()
        binding.navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                binding.layoutButtons.visibility =
                    if (destination.id == R.id.quoteFragment) View.GONE else View.VISIBLE
            }
    }

    override fun onItemClick(item: CurrencyInfo) {
        goToTradeFromNav(item)
    }

    @Deprecated(
        "Not used any more , switch to use navigation graph functions",
        ReplaceWith("goToTradeFromNav()")
    )
    fun onItemClickToTradeFragment(item: CurrencyInfo) {
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
        binding.layoutButtons.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, QuoteFragment.newInstance(quoteInfo))
            .addToBackStack("QuoteFragment")
            .commit()
    }

    private fun displayCurrencyListFromNav(currencyList: ArrayList<CurrencyInfo>) {
        val navController = binding.navHostFragment.findNavController()
        if (navController.currentDestination?.id == R.id.currencyListFragment) {
            val action =
                CurrencyListFragmentDirections.actionCurrencyListFragmentSelf(currencyList.toTypedArray())
            navController.navigate(action)
        } else {
            val action =
                PlaceholderFragmentDirections.actionPlaceholderFragmentToCurrencyListFragment(
                    currencyList.toTypedArray()
                )
            navController.navigate(action)
        }

    }

    private fun goToTradeFromNav(item: CurrencyInfo) {
        //TODO fetch data from api using okhttp
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
        val navController = binding.navHostFragment.findNavController()
        val action = CurrencyListFragmentDirections.actionToQuote(quoteInfo)
        navController.navigate(action)
    }
}
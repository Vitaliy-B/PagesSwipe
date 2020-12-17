package bv.dev.pagesswipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import bv.dev.pagesswipe.adapters.PagerFragmentAdapter
import bv.dev.pagesswipe.databinding.ActivityMainBinding
import bv.dev.pagesswipe.fragments.PageFragment
import bv.dev.pagesswipe.utils.NotificationUtils

class MainActivity : AppCompatActivity(), PageFragment.PagesControl {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pagerAdapter: PagerFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NotificationUtils.createNotificationChannel(this)

        pagerAdapter = PagerFragmentAdapter(this)
        binding.viewPager.adapter = pagerAdapter
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i("log_t", "${intent?.extras?.getInt(PageFragment.ARG_PAGE) ?: 0}")
        binding.viewPager.currentItem = intent?.extras?.getInt(PageFragment.ARG_PAGE) ?: 0
    }

    override fun onBackPressed() {
        if (binding.viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.viewPager.currentItem = binding.viewPager.currentItem - 1
        }
    }

    override fun addPage() {
        pagerAdapter.addFragment()
        pagerAdapter.notifyItemInserted(pagerAdapter.itemCount)
        if (binding.viewPager.currentItem == pagerAdapter.itemCount - 2) {
            binding.viewPager.currentItem++
        }
    }

    override fun removePage(number: Int) {
        pagerAdapter.removeFragment(number)
        pagerAdapter.notifyItemRemoved(number)
        binding.viewPager.currentItem--
    }
}
package bv.dev.pagesswipe.adapters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import bv.dev.pagesswipe.fragments.PageFragment

class PagerFragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private var pagesList: MutableList<PageFragment> = mutableListOf()

    override fun getItemCount(): Int = pagesList.size.let { if (it > 0) it else 1 }

    override fun getItemId(position: Int): Long {
        return if (pagesList.size == 0) {
            0
        } else {
            pagesList[position].number.toLong()
        }
    }

    override fun createFragment(position: Int): Fragment {
        return if (pagesList.indices.contains(position)) {
            pagesList[position]
        } else {
            addFragment()
            pagesList.last()
        }
    }

    fun addFragment() {
        val number = if (pagesList.isEmpty()) 0 else pagesList.last().number + 1
        val fragment = PageFragment()
        fragment.number = number

        pagesList.add(fragment)
    }

    fun removeFragment(number: Int) {
        Log.i("log_t", "${number + 1}")
        for (i in pagesList.indices) {
            if (pagesList[i].number == number) {
                val removed = pagesList.removeAt(i)
                Log.i("log_t", "removed: ${removed.number + 1}")
                break
            }
        }

        Log.i("log_t", "pagesList")
        for (i in pagesList.indices) {
            Log.i("log_t", "${pagesList[i].number + 1},")
        }
    }
}

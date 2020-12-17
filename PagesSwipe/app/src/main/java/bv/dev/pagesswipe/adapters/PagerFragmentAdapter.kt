package bv.dev.pagesswipe.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import bv.dev.pagesswipe.fragments.PageFragment

class PagerFragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private var pagesList: MutableList<PageFragment> = mutableListOf()

    override fun getItemCount(): Int = pagesList.size.let { if (it > 0) it else 1 }

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
        fragment.arguments = Bundle(1).apply {
            putInt(PageFragment.ARG_PAGE, number)
        }
        pagesList.add(fragment)
    }

    fun removeFragment(number: Int) {
        for (i in pagesList.indices) {
            if (pagesList[i].number == number) {
                pagesList.removeAt(i)
                break
            }
        }
    }
}

@file:Suppress("DEPRECATION")

package com.example.thwissa.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter : FragmentPagerAdapter {

    private val fragmentArray = ArrayList<Fragment>()
    private val fragmentTitle = ArrayList<String>()

    constructor(fm: FragmentManager) : super(fm)


    fun addFragment(fragment: Fragment, title: String) {
        fragmentArray.add(fragment)
        fragmentTitle.add(title)
    }

    override fun getCount(): Int {
        return fragmentArray.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentArray[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitle[position]
    }
}
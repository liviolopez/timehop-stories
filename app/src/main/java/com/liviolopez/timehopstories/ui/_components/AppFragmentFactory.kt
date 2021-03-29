package com.liviolopez.timehopstories.ui._components

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.liviolopez.timehopstories.ui.source.SourceFragment
import com.liviolopez.timehopstories.ui.stories.StoriesFragment
import javax.inject.Inject

class AppFragmentFactory @Inject constructor()
: FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            StoriesFragment::class.java.name -> StoriesFragment()
            SourceFragment::class.java.name -> SourceFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}
package bv.dev.pagesswipe.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import bv.dev.pagesswipe.databinding.FragmentPageBinding
import bv.dev.pagesswipe.utils.NotificationUtils
import java.lang.ClassCastException

class PageFragment : Fragment() {

    companion object {
        const val ARG_PAGE = "page"
    }

    interface PagesControl {
        fun addPage()
        fun removePage(number: Int)
    }

    var number: Int = 0
    private val notificationIds: MutableList<Int> = mutableListOf()
    private lateinit var binding: FragmentPageBinding
    private lateinit var pagesControl: PagesControl

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is PagesControl) {
            throw ClassCastException("$context must implement PagesControl")
        } else {
            pagesControl = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_PAGE) }?.apply {
            number = getInt(ARG_PAGE)
            binding.tvNumber.text = "${(number + 1)}"
        }

        binding.fabPlus.setOnClickListener {
            pagesControl.addPage()
        }

        binding.fabMinus.setOnClickListener {
            for (notification in notificationIds) {
                NotificationUtils.cancelNotification(requireContext(), notification)
            }
            pagesControl.removePage(number)
        }

        binding.ivCircle.setOnClickListener {
            val notification = if (notificationIds.isEmpty()) {
                number * 100
            } else {
                notificationIds.last() + 1
            }
            notificationIds.add(notification)
            NotificationUtils.showNotification(requireContext(), notification, number)
        }
    }
}

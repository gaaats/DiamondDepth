package com.ngelgames.herocantar.gimigimipack

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ngelgames.herocantar.R
import com.ngelgames.herocantar.databinding.FragmentGameGameBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class GameGameFragment : Fragment() {
    private val slotListAdapterLeft = SlotListAdapter()
    private val slotListAdapterCenter = SlotListAdapter()
    private val slotListAdapterRight = SlotListAdapter()

    private val listImageeees = generateListImages()

    private var _binding: FragmentGameGameBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    private var cuurentCount = 300

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameGameBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.tvUserBetCount.text = cuurentCount.toString()

        binding.btnImgPlus.setOnClickListener {
            cuurentCount += 100
            binding.tvUserBetCount.text = cuurentCount.toString()
        }
        binding.btnImgMinus.setOnClickListener {
            if (cuurentCount >= 100) cuurentCount -= 100
            binding.tvUserBetCount.text = cuurentCount.toString()
        }


        initExitBtn()
        val linearLayoutManagerLeft = binding.recVLeft.layoutManager as LinearLayoutManager
        val linearLayoutManagerCenter = binding.recVCenter.layoutManager as LinearLayoutManager
        val linearLayoutManagerRight = binding.recVRight.layoutManager as LinearLayoutManager

        disableScrollingRecVeivs()
        initAdaptersRecV()
        submitListsForRecV()

        binding.btnGoSpin.setOnClickListener {
            // just change time of each scrolling recViev for better performance
            initScrollingSlotMachine(linearLayoutManagerLeft, 8, 12)
            initScrollingSlotMachine(linearLayoutManagerCenter, 12, 18)
            initScrollingSlotMachine(linearLayoutManagerRight, 20, 27)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initExitBtn() {
        binding.btnImgExitttttt.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun submitListsForRecV() {
        slotListAdapterLeft.submitList(generateSlotList())
        slotListAdapterCenter.submitList(generateSlotList())
        slotListAdapterRight.submitList(generateSlotList())
    }

    private fun initAdaptersRecV() {
        binding.recVLeft.adapter = slotListAdapterLeft
        binding.recVRight.adapter = slotListAdapterCenter
        binding.recVCenter.adapter = slotListAdapterRight
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun disableScrollingRecVeivs() {
        binding.recVLeft.setOnTouchListener { _, _ -> true }
        binding.recVRight.setOnTouchListener { _, _ -> true }
        binding.recVCenter.setOnTouchListener { _, _ -> true }
    }

    private fun initScrollingSlotMachine(
        linearLayoutManager: LinearLayoutManager,
        minNumberScrolling: Int,
        maxNumberScrolling: Int
    ) {
        lifecycleScope.launch {
            val numberTop = Random.nextInt(minNumberScrolling, maxNumberScrolling)
            var timeForDelayLeft = 100L
            for (i in 1..numberTop) {
                linearLayoutManager.scrollToPositionWithOffset(i, 0)
                delay(timeForDelayLeft)
                timeForDelayLeft += 5
            }
            if (maxNumberScrolling == 27) {
                Snackbar.make(
                    binding.root,
                    "You win ${Random.nextInt(from = 100, until = 1500)} points",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun generateSlotList(): List<SlotElement> {
        val preList = mutableListOf<SlotElement>()
        for (i in 1..50) {
            preList.add(
                SlotElement(
                    Random.nextInt(Int.MAX_VALUE),
                    listImageeees.random(),
                )
            )
        }
        return preList
    }

    private fun generateListImages(): List<Int> {
        return listOf(
            R.drawable.chip,
            R.drawable.chip,
            R.drawable.chip,
            R.drawable.chip,
            R.drawable.chip,
            R.drawable.chip,
            R.drawable.chip,
            R.drawable.chip,
        )
    }
}
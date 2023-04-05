package com.shurik.memwor_22

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import com.google.android.material.tabs.TabLayoutMediator
import com.shurik.memwor_22.databinding.ActivityMainBinding
import com.shurik.memwor_22.fragments.*
import com.shurik.memwor_22.fragments.adapter.ViewPagerAdapter
import com.shurik.memwor_22.fragments_all.RedditAllFragment
import com.shurik.memwor_22.fragments_all.TelegramAllFragment
import com.shurik.memwor_22.fragments_all.TikTokAllFragment
import com.shurik.memwor_22.fragments_all.VkAllFragment
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggleSwitch: Switch

    private fun Context.startActivityWithAnimation(intent: Intent) {
        startActivity(intent)
        if (this is MainActivity || this is SecondActivity) {
            (this as AppCompatActivity).overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }
    }

    private val fragList = listOf(
        InDevelopmentFragment.newInstance(),
        TelegramFragment.newInstance(),
        InDevelopmentFragment.newInstance(),
        InDevelopmentFragment.newInstance(),
    )
    private val fragListTitles = listOf(
        "Vk",
        "Telegram",
        "Reddit",
        "TikTok",
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //VK.initialize(this)

        binding.vp2.setPageTransformer(ZoomOutPageTransformer())
        toggleSwitch = binding.toggleSwitch
//        toggleSwitch.setOnClickListener {
//            toggleSwitch.isChecked = !toggleSwitch.isChecked
//            if (toggleSwitch.isChecked) {
//                val intent = Intent(this, SecondActivity::class.java)
//                startActivityWithAnimation(intent)
//            } else {
//                // Здесь код, который будет выполнен, когда toggle будет выключен
//            }
//        }
        toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val intent = Intent(this, SecondActivity::class.java)
                startActivityWithAnimation(intent)
            } else {
                // Действия при выключении тогла
            }
        }

        val adapter = ViewPagerAdapter(this, fragList) // создаем адаптер и передаем список фрагментов
        binding.vp2.adapter = adapter // устанавливаем адаптер для ViewPager2
        TabLayoutMediator(binding.ourtablayout, binding.vp2) { tab, pos ->
            tab.text = fragListTitles[pos]
        }.attach()
    }
}
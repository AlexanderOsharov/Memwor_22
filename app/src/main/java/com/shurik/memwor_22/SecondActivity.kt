package com.shurik.memwor_22

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Switch
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.shurik.memwor_22.databinding.ActivityMainBinding
import com.shurik.memwor_22.databinding.ActivitySecondBinding
import com.shurik.memwor_22.fragments.InDevelopmentFragment
import com.shurik.memwor_22.fragments.TelegramFragment
import com.shurik.memwor_22.fragments.adapter.ViewPagerAdapter
import com.shurik.memwor_22.fragments_all.RedditAllFragment
import com.shurik.memwor_22.fragments_all.TelegramAllFragment
import com.shurik.memwor_22.fragments_all.TikTokAllFragment
import com.shurik.memwor_22.fragments_all.VkAllFragment

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var toggleSwitch: Switch

    private fun Context.startActivityWithAnimation(intent: Intent) {
        startActivity(intent)
        if (this is MainActivity || this is SecondActivity) {
            (this as AppCompatActivity).overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        }
    }

    private val fragList = listOf(
        VkAllFragment.newInstance(),
        TelegramAllFragment.newInstance(),
        RedditAllFragment.newInstance(),
        TikTokAllFragment.newInstance(),
    )
    private val fragListTitles = listOf(
        "Vk",
        "Telegram",
        "Reddit",
        "TikTok",
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //VK.initialize(this)

        binding.vp2.setPageTransformer(ZoomOutPageTransformer())
        toggleSwitch = binding.toggleSwitch
        toggleSwitch.setOnClickListener {
            toggleSwitch.isChecked = !toggleSwitch.isChecked
            if (toggleSwitch.isChecked) {
                val intent = Intent(this, MainActivity::class.java)
                startActivityWithAnimation(intent)
            } else {
                // Здесь код, который будет выполнен, когда toggle будет выключен
            }
        }

        val adapter = ViewPagerAdapter(this, fragList) // создаем адаптер и передаем список фрагментов
        binding.vp2.adapter = adapter // устанавливаем адаптер для ViewPager2
        TabLayoutMediator(binding.ourtablayout, binding.vp2) { tab, pos ->
            tab.text = fragListTitles[pos]
        }.attach()
    }
}
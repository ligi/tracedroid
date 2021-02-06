package org.ligi.tracedroid.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.ligi.tracedroid.TraceDroid
import org.ligi.tracedroid.demo.databinding.ActivityDemoBinding
import org.ligi.tracedroid.sending.sendTraceDroidStackTracesIfExist
import timber.log.Timber
import java.lang.IllegalArgumentException

class DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TraceDroid.init(this)
        sendTraceDroidStackTracesIfExist("ligi+tracedroid@ligi.de", this)

        val binding = ActivityDemoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.logButton.setOnClickListener {
            Timber.i("something")
        }
        binding.crashMeButton.setOnClickListener {
            throw IllegalArgumentException("Forced crash")
        }
    }
}
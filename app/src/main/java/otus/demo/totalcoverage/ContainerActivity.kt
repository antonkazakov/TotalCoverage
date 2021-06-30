package otus.demo.totalcoverage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import otus.demo.totalcoverage.di.AppComponent
import otus.demo.totalcoverage.di.DaggerAppComponent

class ContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
    }
}
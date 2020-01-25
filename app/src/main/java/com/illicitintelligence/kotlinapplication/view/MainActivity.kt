package com.illicitintelligence.kotlinapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.illicitintelligence.kotlinapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //git_username_edittext.getText()
        //git_username_edittext.text

        //git_username_edittext.setText("1234")
        //git_username_edittext.text = "1234"

        get_repositories_button.setOnClickListener {

            var name = git_username_edittext.text.toString()

            val intent = Intent(this, RepositoriesActivity::class.java)
            intent.putExtra(RepositoriesActivity.NAME_KEY, name)
            startActivity(intent)
        }

    }

    private fun makeToast(name: String) {
        Toast.makeText(this, "Get repositories for $name", Toast.LENGTH_SHORT).show()
    }


}

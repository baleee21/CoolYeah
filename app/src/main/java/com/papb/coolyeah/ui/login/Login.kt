package com.papb.coolyeah.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import com.papb.coolyeah.listKelas.ListKelasRecycler
//import com.papb.coolyeah.tambahKelas.TambahKelasRecycler
import com.papb.coolyeah.ui.home.HomeFragment
import com.papb.coolyeah.R

class Login : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var _txEmail: TextView
    private lateinit var _txPass: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data?.getStringExtra("from").toString() == "logout") {
                    Toast.makeText(this, "has logged out", Toast.LENGTH_LONG).show()
                }
                else {
                    var _tempEmail = data?.getStringExtra("email").toString()
                    _txEmail.setText(_tempEmail)
                    Toast.makeText(this, "You are registered!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                auth.signInWithEmailAndPassword(_txEmail.text.toString(), _txPass.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            var intent = Intent(this, HomeFragment::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

            R.id.goRegister -> {
                var intent = Intent(this, Register::class.java)
//                resultLauncher.launch(intent)
                startActivity(intent)
            }
        }
    }
}
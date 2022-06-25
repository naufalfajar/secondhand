package id.finalproject.binar.secondhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnShow = findViewById<Button>(R.id.btn_show)

        btnShow.setOnClickListener {
            val view = LayoutInflater.from(this).inflate(R.layout.notif_status, null)

            val success = view.findViewById<RadioButton>(R.id.rb_success)
            val fail = view.findViewById<RadioButton>(R.id.rb_failed)
            val btnSend = view.findViewById<Button>(R.id.btn_send)

            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)

            btnSend.setOnClickListener {
                if (success.isChecked) {
                    Snackbar.make(it, "Status produk berhasil diperbarui", Snackbar.LENGTH_LONG)
                        .setActionTextColor(resources.getColor(R.color.white))
                        .setTextColor(resources.getColor(R.color.white))
                        .setBackgroundTint(resources.getColor(R.color.snackbar))
                        .show()
                    dialog.dismiss()
                } else if (fail.isChecked){
                    Snackbar.make(it, "Status produk gagal diperbarui", Snackbar.LENGTH_LONG)
                        .setActionTextColor(resources.getColor(R.color.white))
                        .setTextColor(resources.getColor(R.color.white))
                        .setBackgroundTint(resources.getColor(R.color.snackbar))
                        .show()
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
    }
}
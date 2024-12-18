package com.example.stylish.ui.homepage.cart.chechout

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.stylish.R
import com.example.stylish.databinding.ActivityCheckOutBinding
import com.example.stylish.local.MyDataBase
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import com.paypal.android.sdk.payments.PaymentConfirmation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.math.BigDecimal

class CheckOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckOutBinding


    val clientId =
        "AVsdzXvLic-7gdWULsfufyjWoXcCeuKG9Nk2a9Qzs2uFDwT-nkSfr1Uxw5Ku2Pw08kVTQglZ4-fJs1_l"
    private val payPalRequestCode = 123

    val configartion = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
        .clientId(clientId)

    private var totalPrice: Int = 0
    private var orderPrice: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getOrderPrice()

    }

    private fun getTotalPrice(orderPrice: Int, shippingPrice: Int) {

        val totalPrice = orderPrice + shippingPrice
        this.totalPrice = totalPrice
    }


    private fun getShippingPrice(): Int {
        return 30
    }

    @SuppressLint("SetTextI18n")
    private fun getOrderPrice() {
        var amount = 0
        MyDataBase.cartDataBase?.myDao()?.getAllFavoriteProducts()?.observe(this, {
            for (x in it) {
                val newPrice =
                    x.productPrice!! - ((x.productPrice * x.productDiscount!!) / 100)
                amount += newPrice
            }

            orderPrice = amount
            getTotalPrice(orderPrice, getShippingPrice())
            binding.orderPrice.text = orderPrice.toString()
            binding.shippingPrice.text = getShippingPrice().toString()
            binding.totalPrice.text = totalPrice.toString()
        })

    }

    override fun onStart() {
        super.onStart()
        onClick()
    }


    private fun onClick() {
        binding.btnPayPal.setOnClickListener {
            payPalPayment(totalPrice.toString())
        }

        binding.btnBackArrow.setOnClickListener {
            finish()
        }

    }

    private fun payPalPayment(amount: String) {
        val payment = PayPalPayment(
            BigDecimal(amount),
            "USD",
            "Description",
            PayPalPayment.PAYMENT_INTENT_SALE
        )

        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configartion)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
        startActivityForResult(intent, payPalRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == payPalRequestCode) {
            val paymentConfirmation =
                data?.getParcelableExtra<PaymentConfirmation>(PaymentActivity.EXTRA_RESULT_CONFIRMATION)

            if (paymentConfirmation != null) {

                try {
                    val paymentDetails = paymentConfirmation.toJSONObject().toString()
                    val jsonObject = JSONObject(paymentDetails)

                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }

        } else if (requestCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
        } else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show()
        }
    }

}
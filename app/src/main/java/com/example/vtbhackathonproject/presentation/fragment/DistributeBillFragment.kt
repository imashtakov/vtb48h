package com.example.vtbhackathonproject.presentation.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.model.DistributeBillModel
import com.example.vtbhackathonproject.model.entity.CreatePaymentRequest
import com.example.vtbhackathonproject.model.entity.Payment
import com.example.vtbhackathonproject.presentation.activity.LoginActivity
import com.example.vtbhackathonproject.presentation.adapter.PayersAdapter
import com.example.vtbhackathonproject.presentation.base.BaseFragment
import com.example.vtbhackathonproject.repository.LoginActivityRepository
import com.example.vtbhackathonproject.utils.ViewUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_distribute_bill.*
import retrofit2.converter.gson.GsonConverterFactory

class DistributeBillFragment(val repository: LoginActivityRepository) : BaseFragment<DistributeBillModel>(repository),
    PayersAdapter.AmountChangeListener {
    private val adapter = PayersAdapter(this)

    override fun initModel(): DistributeBillModel = DistributeBillModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_distribute_bill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as LoginActivity).myToolbar.title = "Выбор плательщиков"
        initRecyclerView()
        initBtnListeners()
        (requireActivity() as LoginActivity).myToolbar.title =
            "Сумма: " + repository.receipt!!.total!! + " руб"
        countBill.text = "Остаток: ${(repository.receipt!!.total!! - adapter.amount)} руб"
    }

    override fun onAmountChange(amount: Int) {
        countBill.text = "Остаток: ${(repository.receipt!!.total!! - adapter.amount)} руб"
    }

    private fun initRecyclerView() {
        payersRv.adapter = adapter
        payersRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun initBtnListeners() {
        addPayerBtn.setOnClickListener { showAddPayerDialog() }
        cancelBtn.setOnClickListener { navigator.backAt(CheckListFragment.TAG!!) }
        doneBtn.setOnClickListener { sendInvoice() }
    }

    private fun sendInvoice() {
        var sumOfPatrs = 0
        for (payerItem in adapter.getPayerItems()) {
            sumOfPatrs += payerItem.amount
        }
        val ownerAmount = repository.receipt!!.total!!.minus(sumOfPatrs)
        val payment =
            Payment(adapter.getPayerItems(), repository.receipt!!.description!!, ownerAmount!!, repository.receipt!!.total!!)
        val createPaymentRequest = CreatePaymentRequest(repository.userName!!, payment)
        val gson = Gson()
        val paymentJson = gson.toJson(createPaymentRequest)
        unsubscribeAfterward(model.createPayment(paymentJson)
            .subscribe({
                navigator.backAt(CheckListFragment.TAG!!)
            }, {
                Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show()
            }))
    }

    private fun showAddPayerDialog() {
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("Введите имя с кем хотите разделить счет")
            .setCancelable(false)
        val nameEt = EditText(activity)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        context?.let {
            lp.marginStart = ViewUtils.convertDpToPixel(16f, it).toInt()
            lp.marginEnd = ViewUtils.convertDpToPixel(16f, it).toInt()
        }
        nameEt.layoutParams = lp
        builder.setView(nameEt)

        builder
            .setNegativeButton("ОТМЕНА") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("ДОБАВИТЬ", null)
        val dialog = builder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                checkUserCreated(nameEt.text.toString(), dialog)
            }
        }
        dialog.show()
    }

    private fun checkUserCreated(name: String, dialog: DialogInterface) {
        model.getUserAddress(name)
            .subscribe({
                adapter.addPayer(name, it)
                dialog.dismiss()
            }, {
                Toast.makeText(requireContext(), "Такого пользователя нет в базе", Toast.LENGTH_SHORT).show()
            })
    }
}
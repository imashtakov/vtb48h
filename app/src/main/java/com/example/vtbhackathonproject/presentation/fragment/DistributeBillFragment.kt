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
import com.example.vtbhackathonproject.presentation.adapter.PayersAdapter
import com.example.vtbhackathonproject.presentation.base.BaseFragment
import com.example.vtbhackathonproject.repository.LoginActivityRepository
import com.example.vtbhackathonproject.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_distribute_bill.*

class DistributeBillFragment(val repository: LoginActivityRepository): BaseFragment<DistributeBillModel>(repository) {

    private val adapter = PayersAdapter()

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
        initRecyclerView()
        initBtnListeners()
        countBill.text = "Ваш общий счет составляет: " + repository.sum
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
        for(payerItem in adapter.getPayerItems()) {
            sumOfPatrs+= payerItem.amount
        }
        val ownerAmount = repository.sum?.minus(sumOfPatrs)
        val payment = Payment(adapter.getPayerItems(), "gfsfse", ownerAmount!!, repository.sum!!)
        val createPaymentRequest = CreatePaymentRequest(repository.userName!!, payment)
        model.createPayment(createPaymentRequest)
            .subscribe({
                navigator.backAt(CheckListFragment.TAG!!)
            }, {
                Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show()
            })
    }

    private fun showAddPayerDialog() {
        val builder = AlertDialog.Builder(context)
        builder
            .setTitle("Введите имя с кем хотите разделить счет")
            .setCancelable(false)
        val nameEt = EditText(activity)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)
        context?.let {
            lp.marginStart = ViewUtils.convertDpToPixel(16f, it).toInt()
            lp.marginEnd = ViewUtils.convertDpToPixel(16f, it).toInt()
        }
        nameEt.layoutParams = lp
        builder.setView(nameEt)

        builder
            .setNegativeButton("ОТМЕНА") {dialog, _ ->
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
        Log.e("check", name)
        model.getUserAddress(name)
            .subscribe({
                adapter.addPayer(name, it)
                dialog.dismiss()
            }, {
                Toast.makeText(requireContext(), "Такого пользователя нет в базе", Toast.LENGTH_SHORT).show()
            })
    }
}
package me.dio.credit.application.system.services

import me.dio.credit.application.system.entity.Credit
import java.util.*

interface ICreditService {
    fun save(credit: Credit): Credit

    fun findAllByCustomer(customerId: Long): List<Credit>

    fun findByCreditCode(custumerId: Long, creditCode: UUID): Credit
}
package me.dio.credit.application.system.services.impl

import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.repositories.CreditRepository
import me.dio.credit.application.system.services.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService (
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
): ICreditService {
    override fun save(credit: Credit): Credit{
        credit.apply { customer = customerService.findById(credit.customer?.id!!) }
        return this.creditRepository.save(credit)
    }

    override fun findAllByCustomer(customerId: Long): List<Credit> =
        this.creditRepository.findAllByCustomerId(customerId)

    override fun findByCreditCode(custumerId: Long, creditCode: UUID): Credit {
        val credit: Credit = (this.creditRepository.findByCreditCode(creditCode) ?:
        throw RuntimeException("credit code $creditCode not exist"))

        return if (credit.customer?.id == custumerId) credit else throw RuntimeException("Contact admin")


    }
}
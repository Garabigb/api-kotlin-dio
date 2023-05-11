package me.dio.credit.application.system.controller

import me.dio.credit.application.system.dto.CreditDto
import me.dio.credit.application.system.dto.CreditViewList
import me.dio.credit.application.system.dto.CreditView
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.services.impl.CreditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/credits")
class CreditController(
    private val creditService: CreditService
) {
    @PostMapping
    fun saveCredit(@RequestBody creditDto: CreditDto): ResponseEntity<String>{
        val credit: Credit = this.creditService.save(creditDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Credit ${credit.creditCode} - Customer ${credit.customer?.firstName} saved!")
    }

    @GetMapping
    fun findByAllCustomerId(@RequestParam(value= "customerId") customerId: Long): ResponseEntity<List<CreditViewList>>{
        return ResponseEntity.status(HttpStatus.OK)
            .body(this.creditService.findAllByCustomer(customerId).stream()
            .map{credit: Credit -> CreditViewList(credit) }
            .collect(Collectors.toList()))
    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(@RequestParam(value = "customerId") customerId: Long,
                         @PathVariable creditCode: UUID): ResponseEntity<CreditView> {
        val credit: Credit = this.creditService.findByCreditCode(customerId, creditCode)
        return  ResponseEntity.status(HttpStatus.OK).body(CreditView(credit))

    }
}
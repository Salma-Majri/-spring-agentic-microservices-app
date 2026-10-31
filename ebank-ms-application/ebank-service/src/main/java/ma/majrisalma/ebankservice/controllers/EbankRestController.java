package ma.majrisalma.ebankservice.controllers;

import ma.majrisalma.ebankservice.entities.BankAccount;
import ma.majrisalma.ebankservice.repository.BankAccountRepository;
import ma.majrisalma.ebankservice.services.EbankService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EbankRestController {
    private final EbankService ebankService;

    public EbankRestController(EbankService ebankService) {
        this.ebankService = ebankService;
    }

    @GetMapping("/accounts")
    public List<BankAccount> getAllBankAccounts(){
        return ebankService.getAllBankAccounts();
    }

    @GetMapping("/accounts/{id}")
    public BankAccount getBankAccountById(@PathVariable String id){
        return ebankService.getBankAccountById(id);
    }

    @PostMapping("/accounts")
    public BankAccount save(@RequestBody BankAccount bankAccount){
        return ebankService.save(bankAccount);
    }
}
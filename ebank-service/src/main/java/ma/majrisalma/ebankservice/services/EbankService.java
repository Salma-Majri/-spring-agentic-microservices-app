package ma.majrisalma.ebankservice.services;

import ma.majrisalma.customerservice.repository.CustomerRepository;
import ma.majrisalma.ebankservice.entities.BankAccount;
import ma.majrisalma.ebankservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class EbankService {
    private final BankAccountRepository accountRepository;
    public EbankService(BankAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<BankAccount> getAllBankAccounts(){
        return accountRepository.findAll();
    }

    public BankAccount getBankAccountById(String id){
        return accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account not found"));
    }

    public BankAccount save(BankAccount bankAccount){
        return accountRepository.save(bankAccount);
    }
}
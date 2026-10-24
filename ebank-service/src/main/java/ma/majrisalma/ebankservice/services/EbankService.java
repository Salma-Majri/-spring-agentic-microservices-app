package ma.majrisalma.ebankservice.services;

import ma.majrisalma.customerservice.entities.Customer;
import ma.majrisalma.ebankservice.feign.CustomerRestClient;
import ma.majrisalma.ebankservice.entities.BankAccount;
import ma.majrisalma.ebankservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EbankService {
    private final BankAccountRepository accountRepository;
    private final CustomerRestClient customerRestClient;
    public EbankService(BankAccountRepository accountRepository, CustomerRestClient customerRestClient, CustomerRestClient customerRestClient1) {
        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient1;
    }

    public List<BankAccount> getAllBankAccounts(){
        return accountRepository.findAll();
    }

    public BankAccount getBankAccountById(String id){
        BankAccount bankAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());
        bankAccount.setCustomer(customer);
        return bankAccount;
    }

    public BankAccount save(BankAccount bankAccount){
        return accountRepository.save(bankAccount);
    }
}
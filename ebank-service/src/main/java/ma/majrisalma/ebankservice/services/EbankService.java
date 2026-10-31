package ma.majrisalma.ebankservice.services;

import ma.majrisalma.customerservice.entities.Customer;
import ma.majrisalma.ebankservice.feign.CustomerRestClient;
import ma.majrisalma.ebankservice.entities.BankAccount;
import ma.majrisalma.ebankservice.repository.BankAccountRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EbankService {
    private final BankAccountRepository accountRepository;
    private final CustomerRestClient customerRestClient;

    public EbankService(BankAccountRepository accountRepository, CustomerRestClient customerRestClient) {
        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient;
    }

    @McpTool(description = "Get all bank accounts")
    public List<BankAccount> getAllBankAccounts(){
        return accountRepository.findAll();
    }

    @McpTool(description = "Get a bank account by its ID including customer details")
    public BankAccount getBankAccountById(@McpToolParam(description = "The bank account id") String id){
        BankAccount bankAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());
        bankAccount.setCustomer(customer);
        return bankAccount;
    }

    @McpTool(description = "Save a new bank account")
    public BankAccount save(@McpToolParam(description = "The bank account to save") BankAccount bankAccount){
        return accountRepository.save(bankAccount);
    }
}
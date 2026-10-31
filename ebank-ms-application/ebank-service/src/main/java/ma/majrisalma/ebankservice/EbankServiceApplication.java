package ma.majrisalma.ebankservice;

import ma.majrisalma.ebankservice.entities.BankAccount;
import ma.majrisalma.ebankservice.repository.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class EbankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BankAccountRepository bankAccountRepository) {
        return args -> {
            for (long i = 1; i <= 3; i++) {
                BankAccount bankAccount1 = BankAccount.builder()
                        .id(UUID.randomUUID().toString())
                        .balance(10000 + Math.random() * 50000)
                        .createdAt(new Date())
                        .type("CURRENT_ACCOUNT")
                        .customerId(i)
                        .build();

                bankAccountRepository.save(bankAccount1);

                BankAccount bankAccount2 = BankAccount.builder()
                        .id(UUID.randomUUID().toString())
                        .balance(20000 + Math.random() * 80000)
                        .createdAt(new Date())
                        .type("SAVING_ACCOUNT")
                        .customerId(i)
                        .build();

                bankAccountRepository.save(bankAccount2);
            }
        };
    }
}
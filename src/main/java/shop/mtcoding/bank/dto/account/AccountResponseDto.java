package shop.mtcoding.bank.dto.account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.util.CustomDateUtil;

public class AccountResponseDto {

     @Getter
     @Setter
     public static class AccountSaveResponseDto {

          private Long id;
          private Long number;
          private Long balance;

          public AccountSaveResponseDto(Account account) {
               this.id = account.getId();
               this.number = account.getNumber();
               this.balance = account.getBalance();
          }
     }

     @Getter
     @Setter
     public static class AccountListResponseDto {

          private String fullname;
          private List<AccountDto> accounts = new ArrayList<>();

          public AccountListResponseDto(User user, List<Account> accounts) {
               this.fullname = user.getFullname();
               this.accounts = accounts.stream().map(AccountDto::new).collect(Collectors.toList());
          }

          @Getter
          @Setter
          public class AccountDto {

               private Long id;
               private Long number;
               private Long balance;

               public AccountDto(Account account) {
                    this.id = account.getId();
                    this.number = account.getNumber();
                    this.balance = account.getBalance();
               }
          }
     }

     @Getter
     @Setter
     public static class AccountDepositResponseDto {

          private Long id; // 계좌 ID
          private Long number; // 계좌번호
          private TransactionDto transaction;

          public AccountDepositResponseDto(Account account, Transaction transaction) {
               this.id = account.getId();
               this.number = account.getNumber();
               this.transaction = new TransactionDto(transaction);
          }

          @Getter
          @Setter
          public class TransactionDto {
               private Long id;
               private String gubun;
               private String sender;
               private String receiver;
               private Long amount;
               @JsonIgnore
               private Long depositAccountBalance;
               private String tel;
               private String createdAt;

               public TransactionDto(Transaction transaction) {
                    this.id = transaction.getId();
                    this.gubun = transaction.getGubun().getValue();
                    this.sender = transaction.getSender();
                    this.receiver = transaction.getReceiver();
                    this.amount = transaction.getAmount();
                    this.depositAccountBalance = transaction.getDepositAccountBalance();
                    this.tel = transaction.getTel();
                    this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreateAt());
               }
          }
     }

     @Getter
     @Setter
     public static class AccountWithdrawResponseDto {

          private Long id; // 계좌 ID
          private Long number; // 계좌번호
          private Long balance; // 잔액
          private TransactionDto transaction;

          public AccountWithdrawResponseDto(Account account, Transaction transaction) {
               this.id = account.getId();
               this.number = account.getNumber();
               this.balance = account.getBalance();
               this.transaction = new TransactionDto(transaction);
          }

          @Getter
          @Setter
          public class TransactionDto {
               private Long id;
               private String gubun;
               private String sender;
               private String receiver;
               private Long amount;
               private String createdAt;

               public TransactionDto(Transaction transaction) {
                    this.id = transaction.getId();
                    this.gubun = transaction.getGubun().getValue();
                    this.sender = transaction.getSender();
                    this.receiver = transaction.getReceiver();
                    this.amount = transaction.getAmount();
                    this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreateAt());
               }
          }
     }

     @Setter
     @Getter
     public static class AccountTransferResponseDto {

          private Long id; // 계좌 ID
          private Long number; // 계좌번호
          private Long balance; // 출금 계좌 잔액
          private TransactionDto transaction;

          public AccountTransferResponseDto(Account account, Transaction transaction) {
               this.id = account.getId();
               this.number = account.getNumber();
               this.balance = account.getBalance();
               this.transaction = new TransactionDto(transaction);
          }

          @Setter
          @Getter
          public class TransactionDto {
               private Long id;
               private String gubun;
               private String sender;
               private String reciver;
               private Long amount;
               @JsonIgnore
               private Long depositAccountBalance;
               private String createdAt;

               public TransactionDto(Transaction transaction) {
                    this.id = transaction.getId();
                    this.gubun = transaction.getGubun().getValue();
                    this.sender = transaction.getSender();
                    this.reciver = transaction.getReceiver();
                    this.amount = transaction.getAmount();
                    this.depositAccountBalance = transaction.getDepositAccountBalance();
                    this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreateAt());
               }
          }
     }

     @Getter
     @Setter
     public static class AccountDetailResponseDto {

          private Long id;
          private Long number;
          private Long balance;
          private List<TransactionDto> transactions = new ArrayList<>();

          public AccountDetailResponseDto(Account account, List<Transaction> transactions) {
               this.id = account.getId();
               this.number = account.getNumber();
               this.balance = account.getBalance();
               this.transactions = transactions.stream()
                         .map((transaction) -> new TransactionDto(transaction, account.getNumber()))
                         .collect(Collectors.toList());
          }

          @Getter
          @Setter
          public class TransactionDto {

               private Long id;
               private String gubun;
               private Long amount;

               private String sender;
               private String reciver;

               private String tel;
               private String createdAt;
               private Long balance;

               public TransactionDto(Transaction transaction, Long accountNumber) {
                    this.id = transaction.getId();
                    this.gubun = transaction.getGubun().getValue();
                    this.amount = transaction.getAmount();
                    this.sender = transaction.getSender();
                    this.reciver = transaction.getReceiver();
                    this.createdAt = CustomDateUtil.toStringFormat(transaction.getCreateAt());
                    this.tel = transaction.getTel() == null ? "없음" : transaction.getTel();

                    if (transaction.getDepositAccount() == null) {
                         this.balance = transaction.getWithdrawAccountBalance();
                    } else if (transaction.getWithdrawAccount() == null) {
                         this.balance = transaction.getDepositAccountBalance();
                    } else {
                         if (accountNumber.longValue() == transaction.getDepositAccount().getNumber().longValue()) {
                              this.balance = transaction.getDepositAccountBalance();
                         } else {
                              this.balance = transaction.getWithdrawAccountBalance();
                         }
                    }

               }
          }
     }
}

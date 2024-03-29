package shop.mtcoding.bank.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.account.AccountRequestDto.AccountDepositRequestDto;
import shop.mtcoding.bank.dto.account.AccountRequestDto.AccountSaveRequestDto;
import shop.mtcoding.bank.dto.account.AccountRequestDto.AccountTransferRequestDto;
import shop.mtcoding.bank.dto.account.AccountRequestDto.AccountWithdrawRequestDto;
import shop.mtcoding.bank.dto.account.AccountResponseDto.AccountDepositResponseDto;
import shop.mtcoding.bank.dto.account.AccountResponseDto.AccountDetailResponseDto;
import shop.mtcoding.bank.dto.account.AccountResponseDto.AccountListResponseDto;
import shop.mtcoding.bank.dto.account.AccountResponseDto.AccountSaveResponseDto;
import shop.mtcoding.bank.dto.account.AccountResponseDto.AccountTransferResponseDto;
import shop.mtcoding.bank.dto.account.AccountResponseDto.AccountWithdrawResponseDto;
import shop.mtcoding.bank.service.AccountService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {

     private final AccountService accountService;

     @PostMapping("/s/account")
     public ResponseEntity<?> saveAccount(@RequestBody @Valid AccountSaveRequestDto accountSaveRequestDto,
               BindingResult bindingResult,
               @AuthenticationPrincipal LoginUser loginUser) {
          AccountSaveResponseDto accountSaveResponseDto = accountService.계좌등록(accountSaveRequestDto,
                    loginUser.getUser().getId());
          return new ResponseEntity<>(new ResponseDto<>(1, "계좌등록 성공", accountSaveResponseDto), HttpStatus.CREATED);
     }

     @GetMapping("/s/account/login-user")
     public ResponseEntity<?> findUserAccount(@AuthenticationPrincipal LoginUser loginUser) {
          AccountListResponseDto accountListResponseDto = accountService.계좌목록보기_유저별(loginUser.getUser().getId());
          return new ResponseEntity<>(new ResponseDto<>(1, "계좌목록보기_유저별 성공", accountListResponseDto), HttpStatus.OK);
     }

     @DeleteMapping("/s/account/{number}")
     public ResponseEntity<?> deleteAcoount(@PathVariable Long number, @AuthenticationPrincipal LoginUser loginUser) {
          accountService.계좌삭제(number, loginUser.getUser().getId());
          return new ResponseEntity<>(new ResponseDto<>(1, "계좌삭제 성공", null), HttpStatus.OK);
     }

     @PostMapping("/account/deposit")
     public ResponseEntity<?> depositAccount(@RequestBody @Valid AccountDepositRequestDto accountDepositRequestDto,
               BindingResult bindingResult) {
          AccountDepositResponseDto accountDepositResponseDto = accountService.계좌입금(accountDepositRequestDto);
          return new ResponseEntity<>(new ResponseDto<>(1, "계좌입금 성공", accountDepositResponseDto), HttpStatus.CREATED);
     }

     @PostMapping("/s/account/withdraw")
     public ResponseEntity<?> withdrawAccount(@RequestBody @Valid AccountWithdrawRequestDto accountWithdrawRequestDto,
               BindingResult bindingResult,
               @AuthenticationPrincipal LoginUser loginUser) {
          AccountWithdrawResponseDto accountWithdrawResponseDto = accountService.계좌출금(accountWithdrawRequestDto,
                    loginUser.getUser().getId());
          return new ResponseEntity<>(new ResponseDto<>(1, "계좌출금 성공", accountWithdrawResponseDto), HttpStatus.CREATED);
     }

     @PostMapping("/s/account/transfer")
     public ResponseEntity<?> transferAccount(@RequestBody @Valid AccountTransferRequestDto accountTransferRequestDto,
               BindingResult bindingResult,
               @AuthenticationPrincipal LoginUser loginUser) {
          AccountTransferResponseDto accountTransferResponseDto = accountService.계좌이체(accountTransferRequestDto,
                    loginUser.getUser().getId());
          return new ResponseEntity<>(new ResponseDto<>(1, "계좌이체 성공", accountTransferResponseDto), HttpStatus.CREATED);
     }

     @GetMapping("/s/account/{number}")
     public ResponseEntity<?> findDetailAccount(@PathVariable Long number,
               @RequestParam(value = "page", defaultValue = "0") Integer page,
               @AuthenticationPrincipal LoginUser loginUser) {
          AccountDetailResponseDto accountDetailResponseDto = accountService.계좌상세보기(number,
                    loginUser.getUser().getId(), page);
          return new ResponseEntity<>(new ResponseDto<>(1, "계좌상세보기 성공", accountDetailResponseDto), HttpStatus.CREATED);
     }
}

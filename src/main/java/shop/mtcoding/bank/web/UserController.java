package shop.mtcoding.bank.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.user.UserRequestDto.JoinRequestDto;
import shop.mtcoding.bank.dto.user.UserResponseDto.JoinResponseDto;
import shop.mtcoding.bank.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

     private final UserService userService;

     @PostMapping("/join")
     public ResponseEntity<?> join(@RequestBody @Valid JoinRequestDto joinRequestDto, BindingResult bindingResult) {
          JoinResponseDto joinResponseDto = userService.회원가입(joinRequestDto);
          return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinResponseDto), HttpStatus.CREATED);
     }
}

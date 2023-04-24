package shop.mtcoding.bank.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.user.UserRequestDto.JoinRequestDto;
import shop.mtcoding.bank.dto.user.UserResponseDto.JoinResponseDto;
import shop.mtcoding.bank.service.UserService;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserController {

     private final UserService userService;

     @PostMapping("/join") // x-www-form(defalut) -> json(+requestBody)
     public ResponseEntity<?> join(@Valid @RequestBody JoinRequestDto joinRequestDto, BindingResult bindingResult) {

          if (bindingResult.hasErrors()) {
               Map<String, String> errorMap = new HashMap<>();

               for (FieldError error : bindingResult.getFieldErrors()) {
                    errorMap.put(error.getField(), error.getDefaultMessage());
               }
               return new ResponseEntity<>(new ResponseDto<>(-1, "유효성 검사 실패", errorMap.toString()),
                         HttpStatus.BAD_REQUEST);
          }

          JoinResponseDto joinResponseDto = userService.회원가입(joinRequestDto);
          return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinResponseDto), HttpStatus.CREATED);
     }
}

package kr.co.chooz.user;

import kr.co.chooz.user.port.in.UserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RegisterUserController {
    private final UserCase userUserCase;



}

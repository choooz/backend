package kr.co.chooz.user;

import kr.co.chooz.user.port.in.UserUserCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserUserCase userUserCase;



}

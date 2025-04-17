package cholog;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String world(Model model, @RequestParam(value = "name") String name) {
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/json")
    public Person json() {
        return new Person("brown", 20);
    }
}

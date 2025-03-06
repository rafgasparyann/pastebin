package am.logiclab.pastebin;

import am.logiclab.pastebin.model.Message;
import am.logiclab.pastebin.model.MessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ApplicationController {

    private final MessageRepository messageRepository;

    public ApplicationController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "index";
    }

    @PostMapping("/save")
    public String save(@RequestParam("text") String text, RedirectAttributes redirectAttributes) {
        if (text == null || text.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Message cannot be empty!");
            return "redirect:/";
        }

        Message message = new Message(text);
        messageRepository.save(message);

        redirectAttributes.addFlashAttribute("success", "Message saved successfully!");
        return "redirect:/";
    }

    @GetMapping("/paste/{id}")
    public String viewPaste(@PathVariable Long id, Model model) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            model.addAttribute("message", message.get());
            return "paste";
        }
        return "redirect:/";
    }
}

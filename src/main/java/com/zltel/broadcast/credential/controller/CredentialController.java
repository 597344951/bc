package com.zltel.broadcast.credential.controller;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.credential.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/credential")
public class CredentialController extends BaseController {
    @Autowired
    private CredentialService credentialService;

    @GetMapping("/partyDue/{id}")
    public String partyDue(Model model, @PathVariable("id") int id) {
        Map<String, Object> partyDueCredentialData = credentialService.wrapPartyDueCredentialData(id);
        if(partyDueCredentialData == null) {
            return "redirect:/404";
        }
        model.addAttribute("due", partyDueCredentialData);
        return "/view/credential/partyDue";
    }
}

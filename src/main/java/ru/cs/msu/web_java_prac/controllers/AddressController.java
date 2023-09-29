package ru.cs.msu.web_java_prac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.cs.msu.web_java_prac.dao.impl.PersonDAOImpl;
import ru.cs.msu.web_java_prac.dao.impl.AddressDaoImpl;
import ru.cs.msu.web_java_prac.entities.Address;

import java.util.List;

@Controller
public class AddressController {
    @Autowired
    private final PersonDAOImpl personDAO = new PersonDAOImpl();

    @Autowired
    private final AddressDaoImpl addressDao = new AddressDaoImpl();

    @GetMapping("/addresses")
    public String addressListPage(Model model) {

        List<Address> addresses = (List<Address>) addressDao.getAll();
        if (addresses == null) {
            model.addAttribute("error_msg", "Bruh");
            return "errorPage";
        }
        model.addAttribute("addresses", addresses);
        model.addAttribute("addressService", addressDao);
        return "addresses";
    }

    @GetMapping("/address")
    public String addressPage(@RequestParam(name = "addrId") Long addrId, Model model) {
        Address address = addressDao.getById(addrId);

        if (address == null) {
            model.addAttribute("error_msg", "В базе нет населённого пункта с ID = " + addrId);
            return "errorPage";
        }

        model.addAttribute("address", address);
        model.addAttribute("personService", personDAO);
        model.addAttribute("addressService", addressDao);
        return "address";
    }

    @GetMapping("/editAddress")
    public String editAddressPage(@RequestParam(name = "addrId", required = false) Long addrId, Model model) {
        if (addrId == null) {
            model.addAttribute("address", new Address());
            return "editAddress";
        }

        Address address = addressDao.getById(addrId);

        if (address == null) {
            model.addAttribute("error_msg", "В базе нет места с ID = " + addrId);
            return "errorPage";
        }

        model.addAttribute("address", address);
        return "editAddress";
    }

    @PostMapping("/saveAddress")
    public String saveAddressPage(@RequestParam(name = "street_address", required = false) String street,
                                  @RequestParam(name = "city") String city,
                                  @RequestParam(name = "state_province", required = false) String state,
                                  @RequestParam(name = "country") String country,
                                Model model) {

        Address address = new Address(street, city, state, country);
        addressDao.save(address);

        return String.format("redirect:/address?addrId=%d", address.getId());
    }

    @PostMapping("/removeAddress")
    public String removeAddressPage(@RequestParam(name = "addrId") Long addrId) {
        addressDao.deleteById(addrId);
        return "redirect:/addresses";
    }
}

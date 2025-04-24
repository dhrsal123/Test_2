package com.ejercicio.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ejercicio.demo.dto.MessageDto;
import com.ejercicio.demo.dto.PhoneDto;
import com.ejercicio.demo.dto.RegistryDto;
import com.ejercicio.demo.dto.RegistryReturnDto;
import com.ejercicio.demo.repository.PhoneRepository;
import com.ejercicio.demo.repository.RegistryRepository;
import com.ejercicio.demo.service.impl.RegistryServiceImpl;

@SpringBootTest
public class registrySeviceTest {
    @InjectMocks
    private RegistryServiceImpl registryService;
    @Mock
    private RegistryRepository registryRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PhoneRepository phoneRepository;
    @Mock
    private Environment environment;

    @BeforeEach

    public void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidationFullNull() {
        Object response = registryService.register(null);
        assertTrue(response instanceof MessageDto);
        MessageDto message = (MessageDto) response;
        assertEquals("Es necesario que ingreses todos tus datos", message.getMensaje());
    }

    @Test
    public void testValidationFieldNull() {
        RegistryDto registryDto = new RegistryDto("Hey", "hey@gmail.com", "Hey123123#", null);
        Object response = registryService.register(registryDto);
        assertTrue(response instanceof MessageDto);
        MessageDto message = (MessageDto) response;
        assertEquals("Es necesario que ingreses todos tus datos", message.getMensaje());
    }

    @Test
    public void testValidationNoPhone() {
        List<PhoneDto> phones = new ArrayList<>();
        RegistryDto registryDto = new RegistryDto("Hey", "hey@gmail.com", "Hey123123#", phones);
        Object response = registryService.register(registryDto);
        assertTrue(response instanceof MessageDto);
        MessageDto message = (MessageDto) response;
        assertEquals("Es necesario que ingreses todos tus datos", message.getMensaje());
    }

    @Test
    public void testValidationInvalidPhone() {
        PhoneDto phone = new PhoneDto("1", "1", "1");
        List<PhoneDto> phones = new ArrayList<>();
        phones.add(phone);
        RegistryDto registryDto = new RegistryDto("Hey", "hey@gmail.com", "Hey123123#", phones);
        Object response = registryService.register(registryDto);
        assertTrue(response instanceof MessageDto);
        MessageDto message = (MessageDto) response;
        assertEquals("Numero telefonico no valido", message.getMensaje());
    }

    @Test
    public void testValidationInvalidName() {
        PhoneDto phone = new PhoneDto("148572389579", "12", "12");
        List<PhoneDto> phones = new ArrayList<>();
        phones.add(phone);
        RegistryDto registryDto = new RegistryDto("He", "hey@gmail.com", "Hey123123#", phones);
        Object response = registryService.register(registryDto);
        assertTrue(response instanceof MessageDto);
        MessageDto message = (MessageDto) response;
        assertEquals("Ingresa un nombre valido", message.getMensaje());
    }

    @Test
    public void testValidationInvalidEmail() {
        PhoneDto phone = new PhoneDto("148572389579", "12", "12");
        List<PhoneDto> phones = new ArrayList<>();
        phones.add(phone);
        RegistryDto registryDto = new RegistryDto("Joe", "hey@gm.z", "Hey123123#", phones);
        Object response = registryService.register(registryDto);
        assertTrue(response instanceof MessageDto);
        MessageDto message = (MessageDto) response;
        assertEquals("Email deformado", message.getMensaje());
    }

    @Test
    public void testValidationInvalidPassword() {
        PhoneDto phone = new PhoneDto("148572389579", "12", "12");
        List<PhoneDto> phones = new ArrayList<>();
        phones.add(phone);
        RegistryDto registryDto = new RegistryDto("Joe Swagson", "jswag@gmail.com", "hey1231", phones);
        Object response = registryService.register(registryDto);
        assertTrue(response instanceof MessageDto);
        MessageDto message = (MessageDto) response;
        assertEquals("La contraseña debe tener: 1 Mayuscula, 1 Minuscula, 1 Numero y ser como minimo de 6 caracteres",
                message.getMensaje());
    }

    @Test
    public void testValidationSuccessful() {
        PhoneDto phone = new PhoneDto("148572389579", "12", "12");
        List<PhoneDto> phones = new ArrayList<>();
        phones.add(phone);
        RegistryDto registryDto = new RegistryDto("Joe", "hey@gmail.com", "Hey123123#", phones);
        Object response = registryService.register(registryDto);
        assertTrue(response instanceof RegistryReturnDto);
    }

}
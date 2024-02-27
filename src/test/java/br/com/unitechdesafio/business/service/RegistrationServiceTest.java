package br.com.unitechdesafio.business.service;

import br.com.unitechdesafio.BaseTestRunner;
import br.com.unitechdesafio.business.domain.dto.request.RegistrationRequest;
import br.com.unitechdesafio.business.domain.entity.RegistrationEntity;
import br.com.unitechdesafio.business.domain.repository.RegistrationRepository;
import br.com.unitechdesafio.business.domain.repository.UserDetailsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RegistrationService.class})
class RegistrationServiceTest extends BaseTestRunner {
    @MockBean
    private ApplicationEventPublisher applicationEventPublisher;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private RegistrationRepository registrationRepository;

    @Autowired
    private RegistrationService registrationService;

    @MockBean
    private UserDetailsRepository userDetailsRepository;


    @Test
    void testValidatePersistVO() {
        // Arrange
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setEmail("jane.doe@example.org");
        registrationEntity.setId("42");
        registrationEntity.setName("Name");
        registrationEntity.setStatus("Status");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<RegistrationEntity>>any()))
                .thenReturn(registrationEntity);

        RegistrationRequest valueObject = new RegistrationRequest();
        valueObject.setEmail("jane.doe@example.org");
        valueObject.setId("42");
        valueObject.setName("Name");
        valueObject.setStatus("Status");

        // Act
        registrationService.validatePersistVO(valueObject);

        // Assert
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<RegistrationEntity>>any());
    }


    @Test
    void testValidatePersistVO2() {
        // Arrange
        RegistrationEntity buildResult = RegistrationEntity.builder()
                .email("jane.doe@example.org")
                .id("42")
                .name("Name")
                .status("Status")
                .build();
        buildResult.setEmail("jane.doe@example.org");
        buildResult.setId("42");
        buildResult.setName("Name");
        buildResult.setStatus("Status");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<RegistrationEntity>>any())).thenReturn(buildResult);

        RegistrationRequest valueObject = new RegistrationRequest();
        valueObject.setEmail("jane.doe@example.org");
        valueObject.setId("42");
        valueObject.setName("Name");
        valueObject.setStatus("Status");

        // Act
        registrationService.validatePersistVO(valueObject);

        // Assert
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<RegistrationEntity>>any());
    }


    @Test
    void testValidateDeleteVO() {
        // Arrange
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setEmail("jane.doe@example.org");
        registrationEntity.setId("42");
        registrationEntity.setName("Name");
        registrationEntity.setStatus("Status");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<RegistrationEntity>>any()))
                .thenReturn(registrationEntity);

        RegistrationRequest valueObject = new RegistrationRequest();
        valueObject.setEmail("jane.doe@example.org");
        valueObject.setId("42");
        valueObject.setName("Name");
        valueObject.setStatus("Status");

        // Act
        RegistrationEntity actualValidateDeleteVOResult = registrationService.validateDeleteVO(valueObject);

        // Assert
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<RegistrationEntity>>any());
        assertSame(registrationEntity, actualValidateDeleteVOResult);
    }


    @Test
    void testValidateUpdateVO() {
        // Arrange
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setEmail("jane.doe@example.org");
        registrationEntity.setId("42");
        registrationEntity.setName("Name");
        registrationEntity.setStatus("Status");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<RegistrationEntity>>any()))
                .thenReturn(registrationEntity);

        RegistrationRequest valueObject = new RegistrationRequest();
        valueObject.setEmail("jane.doe@example.org");
        valueObject.setId("42");
        valueObject.setName("Name");
        valueObject.setStatus("Status");

        // Act
        registrationService.validateUpdateVO("42", valueObject);

        // Assert
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<RegistrationEntity>>any());
    }
}

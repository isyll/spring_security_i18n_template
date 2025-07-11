package com.example.demo.startup;

import com.example.demo.model.SuperAdmin;
import com.example.demo.model.UsedEmail;
import com.example.demo.repository.UsedEmailRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.context.AppStartupState;
import com.example.demo.utils.EnvUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialUserLoader implements ApplicationRunner {

  private final UserRepository userRepository;
  private final UsedEmailRepository usedEmailRepository;
  private final AppStartupState appStartupState;

  @Override
  public void run(ApplicationArguments args) {
    if (userRepository.count() == 0) {
      createSuperAdmin();
      appStartupState.setAppReady(true);
      log.info("Root user has been created.");
    }
  }

  private void createSuperAdmin() {
    String regNumber = "ROOT";
    String emailAddress = EnvUtils.get("ADMIN_EMAIL", "admin@yaatal.com");
    String password = "root123";

    SuperAdmin rootUser = new SuperAdmin();
    rootUser.setRegistrationNumber(regNumber);
    rootUser.setFirstName("Yaatal");
    rootUser.setLastName("Admin");
    rootUser.setEmail(emailAddress);
    rootUser.setPassword(password);
    rootUser.setMustChangePassword(true);

    // Inserted manually because listeners can't access Spring beans at startup
    usedEmailRepository.save(new UsedEmail(emailAddress));
    userRepository.save(rootUser);
  }
}

package com.example.demo.startup;

import com.example.demo.context.AppStartupState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialUserLoader implements ApplicationRunner {

  private final AppStartupState appStartupState;

  @Override
  public void run(ApplicationArguments args) {}
}

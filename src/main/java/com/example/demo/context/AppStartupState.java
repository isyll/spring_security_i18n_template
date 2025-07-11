package com.example.demo.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class AppStartupState {

  private boolean appReady = false;
}

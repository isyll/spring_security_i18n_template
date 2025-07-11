package com.example.demo.context;

import com.example.demo.model.School;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
public class SchoolContextHolder {

  private School school;
}

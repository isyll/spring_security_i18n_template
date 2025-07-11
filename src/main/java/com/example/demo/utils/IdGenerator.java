package com.example.demo.utils;

import com.example.demo.repository.SchoolRepository;
import com.example.demo.repository.UserRepository;
import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdGenerator {

  private static final SecureRandom random = new SecureRandom();

  private final SchoolRepository schoolRepository;
  private final UserRepository userRepository;

  private String generateNumberId(int[] segmentLengths) {
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < segmentLengths.length; i++) {
      for (int j = 0; j < segmentLengths[i]; j++) {
        builder.append(random.nextInt(10));
      }
      if (i < segmentLengths.length - 1) {
        builder.append("-");
      }
    }

    return builder.toString();
  }

  /**
   * Generates a unique 10-digit number for a school.
   *
   * <p>This method ensures uniqueness by checking the generated ID against existing numbers in the
   * database. It uses a cryptographically strong random number generator to create the ID and
   * retries if a collision is detected.
   *
   * @return a unique 12-digit numeric string representing the school's account ID
   */
  public String generateUniqueSchoolNumber() {
    String number;
    do {
      number = generateNumberId(new int[] {3, 4, 3});
    } while (schoolRepository.existsById(number));
    return number;
  }

  /**
   * Generates a unique ID for users.
   *
   * @return unique ID as a String
   */
  public String generateUserId() {
    String number;
    do {
      number = generateNumberId(new int[] {3, 2, 4});
    } while (userRepository.existsByRegistrationNumber(number));
    return number;
  }
}

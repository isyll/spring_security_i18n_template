package com.example.demo.service;

import com.example.demo.exceptions.UniqueConstraintViolationException;
import com.example.demo.model.UsedEmail;
import com.example.demo.model.UsedPhoneNumber;
import com.example.demo.model.User;
import com.example.demo.repository.UsedEmailRepository;
import com.example.demo.repository.UsedPhoneRepository;
import com.example.demo.utils.SecurityUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

abstract class BaseService {

  @Autowired private UsedEmailRepository usedEmailRepository;

  @Autowired private UsedPhoneRepository usedPhoneRepository;

  protected User currentUser() {
    if (SecurityUtils.isUnauthenticated()) {
      throw new RuntimeException("No authenticated user found");
    }
    return SecurityUtils.getCurrentUser();
  }

  protected void persistUsedEmail(String email) {
    if (StringUtils.hasText(email)) {
      usedEmailRepository.save(new UsedEmail(email));
    }
  }

  protected void persistUsedPhoneNumber(String phoneNumber) {
    if (StringUtils.hasText(phoneNumber)) {
      usedPhoneRepository.save(new UsedPhoneNumber(phoneNumber));
    }
  }

  protected void persistUsedEmailAndPhone(String email, String phoneNumber) {
    persistUsedEmail(email);
    persistUsedPhoneNumber(phoneNumber);
  }

  protected void deleteUsedEmail(String email) {
    if (StringUtils.hasText(email)) {
      usedEmailRepository.deleteById(email);
    }
  }

  protected void deleteUsedPhoneNumber(String phoneNumber) {
    if (StringUtils.hasText(phoneNumber)) {
      usedPhoneRepository.deleteById(phoneNumber);
    }
  }

  protected void deleteEmailAndPhone(String email, String phoneNumber) {
    deleteUsedEmail(email);
    deleteUsedPhoneNumber(phoneNumber);
  }

  protected boolean updateUsedContactInfo(
      String oldValue,
      String newValue,
      Function<String, Boolean> existsFn,
      Consumer<String> persistFn,
      Consumer<String> deleteFn) {
    if (StringUtils.hasText(newValue) && !newValue.equals(oldValue)) {
      if (existsFn.apply(newValue)) {
        return true;
      }
      persistFn.accept(newValue);
      deleteFn.accept(oldValue);
    }
    return false;
  }

  protected Map<String, String> validateUsedContactInfo(
      String oldEmail, String newEmail, String oldPhone, String newPhone) {
    Map<String, String> errors = new HashMap<>();

    if (updateUsedContactInfo(
        oldEmail,
        newEmail,
        usedEmailRepository::existsById,
        this::persistUsedEmail,
        usedEmailRepository::deleteById)) {
      errors.put("email", "validation.email_already_exists");
    }

    if (updateUsedContactInfo(
        oldPhone,
        newPhone,
        usedPhoneRepository::existsById,
        this::persistUsedPhoneNumber,
        usedPhoneRepository::deleteById)) {
      errors.put("phone", "validation.phone_already_exists");
    }

    return errors;
  }

  protected <E, R> E processUserUpdate(
      E entity,
      R request,
      Function<E, String> getOldEmail,
      Function<E, String> getOldPhone,
      Function<R, String> getNewEmail,
      Function<R, String> getNewPhone,
      BiConsumer<R, E> updateMapper,
      Function<E, E> saveFn) {
    Map<String, String> errors =
        validateUsedContactInfo(
            getOldEmail.apply(entity), getNewEmail.apply(request),
            getOldPhone.apply(entity), getNewPhone.apply(request));

    if (!errors.isEmpty()) {
      throw new UniqueConstraintViolationException(errors);
    }

    updateMapper.accept(request, entity);
    return saveFn.apply(entity);
  }
}
